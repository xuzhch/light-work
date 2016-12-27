/**
 * 动态加载页面的处理文件
 */
define([ "util", "pageslide" ], function(util) {

	/**
	 * @description ajax统一错误处理（getJSON）
	 */
	$.ajaxSetup({
		error : function(jqXHR, textStatus, errorThrown) {			
			switch (jqXHR.status) {
			case (500):
				alert("服务器系统内部错误");
				break;
			case (401):
				alert("未登录");
				break;
			case (403):
				alert("无权限执行此操作");
				break;
			case (408):
				alert("请求超时");
				break;
			default:
				alert("未知错误");
			}
			throw errorThrown;
		}
	});

	/**
	 * @description 获取匹配的配置信息
	 * @param hashStr
	 *            页面定位字符串
	 * @param url
	 *            获取配置信息的地址
	 * @param callback
	 *            回调函数
	 */
	var parseHashStr = function(hashStr, callback) {
		// TODO hashStr 权限检查
		hashStr = hashStr || APP_CONTEXT.indexPage; // hashStr为空，则使用首页设置

		// 取得方法名methodName
		var hashPathArr = hashStr.split(".");
		var methodName = hashPathArr[hashPathArr.length - 1];

		// 获得controller的路径controllerPath
		var lastIndex = hashStr.lastIndexOf(".");
		var controllerPackage = hashStr.substring(0, lastIndex);
		var controllerPath = controllerPackage.replace(".", "/");

		var retObj = {};
		retObj.hashStr = hashStr; // 结果类似system.users.list
		retObj.controllerPackage = controllerPackage; // 结果类似system.users
		retObj.controllerPath = controllerPath; // 结果类似system/users
		retObj.methodName = methodName; // 结果类似list
		retObj.htmlPath = APP_CONTEXT.htmlPath + "/" + controllerPath + "/"
				+ methodName + ".html"; // 结果类似system/users/list.html
		retObj.jsPath = APP_CONTEXT.javascriptsPath + "/" + controllerPath
				+ ".js"; // 结果类似system/users.js
		retObj.htmlFullPath = APP_CONTEXT.appPath + retObj.htmlPath;
		retObj.jsFullPath = APP_CONTEXT.appPath + retObj.jsPath;
		
		APP_CONTEXT.CURRENT_HASH_STR = hashStr; // 设置为当前hashStr
		APP_CONTEXT.CURRENT_CONTROLLER = retObj;
		return callback(retObj);
	};

	/**
	 * @description 对动态加载的页面进行操作
	 * @param hashStr
	 *            页面定位字符串
	 * @param config
	 *            配置信息
	 */
	var pageController = function(config) {
		// 给页面赋予属性
		$("div[ng-page]").each(function() {
			if (typeof ($(this).attr("ng-page-id")) == "undefined") {
				$(this).attr("ng-page-id", config.hashStr);
			}
		});
		// 加载对应的JS
		require([ config.jsFullPath], function(controllerObject) {
			
			//执行页面对应的js方法
			executeMethod(controllerObject,config.methodName);
			
			// 为页面属性赋予事件功能
			$("div[ng-page-id='" + config.hashStr + "']").find("*[ng-click]")
					.each(function(index, val) {
						$(this).click(function(event) {
							var methodName = $(this).attr("ng-click");// 获取属性值
							buttonClick(controllerObject,methodName,event); // 在buttonClick中执行方法
						});
					});

			// select 绑定option
			$("div[ng-page-id='" + config.hashStr + "']").find(
					"select[optionCode]").each(
					function() {
						var optionCode = $(this).attr("optionCode");// 类型 +
						// 方法KEY
						var optionKey = $(this).attr("optionKey");
						var optionValue = $(this).attr("optionValue");
						var id = $(this).attr("id");
						var optionCodeArr = optionCode.split(".");
						$("#" + id).empty();
						$.getJSON(APP_CONTEXT.appSelectPath, function(select) {
							if (optionCodeArr[0] == "JSON") {
								var optionJSON = select.JSON[optionCodeArr[1]];
								// 动态生成option
								var str = "";
								for (var i = 0; i < optionJSON.length; i++) {
									str += "<option value=" + optionJSON[i].key
											+ ">" + optionJSON[i].value
											+ "</option>";
								}
								$("#" + id).append(str);
							} else {

							}
						});
					});

		})
	};
	
	 /**
	 * 按钮事件执行
	 */
	var buttonClick = function(controllerObject,methodName, event){
		//TODO 执行方法之前的工作
		executeMethod(controllerObject,methodName);
	};
	
	
	 /**
	 * 执行controller的方法
	 */
	var executeMethod = function(controllerObject,methodName){
		if (methodName) {
			try{
				controllerObject[methodName]();// 页面初始化方法
			}catch(e){
				console.error("请检查'"+currentController.jsFullPath+"'中是否有'"+methodName+"()'方法，并配置了requirejs导出。");
			}
		}
	};
	
	/**
	 * @description 页面解析
	 * @param hashStr
	 *            页面定位字符串
	 * @param url
	 *            获取配置信息地址
	 * @param pos
	 *            jquery 定位符 动态页面显示的位置
	 */
	var parsePage = function(hashStr, pos) {
		parseHashStr(hashStr, function(config) {
			// 加载页面
			pos = pos || "#page";
			$(pos).load(config.htmlFullPath, function() {
				// 对加载的页面进行处理
				pageController(config);
			});
		})
	};

	/**
	 * @description 侧滑页面显示
	 * @param hashStr
	 *            页面定位符
	 * @param url
	 *            配置信息路径
	 * @param callback
	 *            回调函数
	 */
	var slidePage = function(hashStr, callback) {
		parseHashStr(hashStr, function(config) {
			// 加载页面
			$.pageslide({
				href : config.htmlFullPath,
				direction : "left"
			}, function() {
				// 对加载的页面进行处理
				pageController(config);
			});
		})
	};

	/**
	 * @description 关闭侧滑页面
	 */
	var slideHide = function() {
		$.pageslide.close();
	};

	return {
		parsePage : parsePage,
		slidePage : slidePage,
		slideHide : slideHide
	}
});