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
				util.alert("发送请求到服务端出现异常：服务器系统内部错误，请与管理员联系");
				break;
			case (404):
				util.alert("发送请求到服务端出现异常：资源无法找到，请与管理员联系");
				break;
			case (401):
				util.alert("发送请求到服务端出现异常：未登录，请与管理员联系");
				break;
			case (403):
				util.alert("发送请求到服务端出现异常：无权限执行此操作，请与管理员联系");
				break;
			case (408):
				util.alert("发送请求到服务端出现异常：请求超时，请与管理员联系");
				break;
			default:
				util.alert("发送请求到服务端出现异常，请与管理员联系");
			}
			console.error(errorThrown);
			throw errorThrown;
		}
	});

	/**
	 * @description 获取匹配的配置信息,hashStr形式有以下两种：htmlPackage$$jsPackage，或简化写法，只写htmlPath
	 * htmlPackage$$jsPackage，如system.users.list$$system.users.func，即加载system/users/list.html，然后调用system/users.js的func()方法，也可替换成其他方法
	 * 只写htmlPackage，如system.users.list，默认js方法与html页面名称相同，相当于system.users.list$$system.users.list
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
		var hashStrArr = hashStr.split("$$");
		var htmlPackageStr = hashStrArr[0];
		var jsPackageStr = htmlPackageStr; //默认与htmlPackage相同
		if(hashStrArr.length>1){
			jsPackageStr = hashStrArr[1];
		}
		
		var htmlParse = parsePackageString(htmlPackageStr);
		var jsParse = parsePackageString(jsPackageStr);

		var page = {};
		page.hashStr = hashStr;
		page.htmlParse = htmlParse;
		page.jsParse = jsParse;
		page.htmlPath = APP_CONTEXT.htmlPath + "/" + htmlParse.pagePath + "/"
				+ htmlParse.pageName + ".html"; // 结果类似system/users/list.html
		page.jsPath = APP_CONTEXT.javascriptsPath + "/" + jsParse.pagePath
				+ ".js"; // 结果类似system/users.js
		page.methodName = jsParse.pageName;
		
		page.htmlFullPath = APP_CONTEXT.appPath + page.htmlPath;
		page.jsFullPath = APP_CONTEXT.appPath + page.jsPath;
		return callback(page);
	};
	
	var parsePackageString = function(packageStr){
		// 取页面名pageName
		var packageStrArr = packageStr.split(".");
		var pageName = packageStrArr[packageStrArr.length - 1];

		// 获得controller的路径controllerPath
		var lastIndex = packageStr.lastIndexOf(".");
		var pagePackage = packageStr.substring(0, lastIndex);
		var pagePath = pagePackage.replace(".", "/");

		var parse = {};
		parse.pagePackage = pagePackage; // 结果类似system.users
		parse.pagePath = pagePath; // 结果类似system/users
		parse.pageName = pageName; // 结果类似list
		return parse;
	}

	/**
	 * @description 对动态加载的页面进行操作
	 * @param hashStr
	 *            页面定位字符串
	 * @param page
	 *            配置信息
	 */
	var pageController = function(page) {
		// 给页面赋予属性
		$("div[ng-page]").each(function() {
			if (typeof ($(this).attr("ng-page-id")) == "undefined") {
				$(this).attr("ng-page-id", page.hashStr);
			}
		});
		// 加载对应的JS
		require([ page.jsFullPath], function(controllerObject) {
			
			//执行页面对应的js方法
			executeMethod(controllerObject,page.methodName);
			
			// 为页面属性赋予事件功能
			var $ngpage = $("div[ng-page-id='" + page.hashStr + "']");
			if($ngpage.length>0){
				$ngpage.find("*[ng-click]")
				.each(function(index, val) {
					$(this).click(function(event) {
						var methodName = $(this).attr("ng-click");// 获取属性值
						console.info("开始调用'"+APP_CONTEXT.CURRENT_PAGE.jsFullPath+"中"+methodName+"()'方法。");
						buttonClick(controllerObject,methodName,event); // 在buttonClick中执行方法
					});
				});
			}else{
				console.error("当前页面中无id为'"+page.hashStr+"'的DIV，请检查页面内容是否包含在<div ng-page/>中！");
			}
			

			// select 绑定option
			$("div[ng-page-id='" + page.hashStr + "']").find(
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
				console.error("请检查'"+APP_CONTEXT.CURRENT_PAGE.jsFullPath+"'中是否有'"+methodName+"()'方法，并配置了requirejs导出。");
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
		parseHashStr(hashStr, function(page) {			
			APP_CONTEXT.CURRENT_PAGE = page;
			// 加载页面
			pos = pos || "#page";
			$(pos).load(page.htmlFullPath, function() {
				// 对加载的页面进行处理
				pageController(page);
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
		parseHashStr(hashStr, function(page) {
			// 加载页面
			$.pageslide({
				href : page.htmlFullPath,
				direction : "left"
			}, function() {
				// 对加载的页面进行处理
				pageController(page);
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