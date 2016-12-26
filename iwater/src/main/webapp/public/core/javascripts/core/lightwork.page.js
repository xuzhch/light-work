/**
* 动态加载页面的处理文件
*/
define(["util","pageslide"],function (util) {
   
   /**  
    * @description ajax统一错误处理（getJSON）
    */ 
    $.ajaxSetup({
        error:function(x,e){
            console.error(e);
            return false;
        }
    });

    /**  
    * @description 获取匹配的配置信息
    * @param hashStr 页面定位字符串
    * @param url 获取配置信息的地址
    * @param callback 回调函数
    */ 
	var getConfig = function(hashStr,url,callback){
        //TODO　hashStr 权限检查
        var pathName = window.location.pathname.replace(/\//,"")||"smartcloudServer";
        //util.get("system/access/isAdmin",{},function(flag){
        //    util.get("system/access/getAuthorize",{"type":"menu"},function(data){
                //if(flag||util.contains(data,"menu_"+pathName+hashStr)){
                    $.getJSON(url,function(config){
                        hashStr = hashStr||config.indexPage;//hashStr为空，则使用首页设置
                        var hashPathArr = hashStr.split(".");
                        var retObj = {"htmlPathBase":config.htmlPathBase,"jsPathBase":config.jsPathBase,"code":config.code,"hashStr":hashStr};
                        var flag = true ;
                        //组装配置文件中的配置信息
                        for(var i=0,len=config.modules.length;i<len;i++){
                            if(hashPathArr[0] == config.modules[i].module){
                                retObj.jsPath = config.modules[i].js;
                                retObj.module = config.modules[i].module;
                                for(var j=0,subLen=config.modules[i].pages.length;j<subLen;j++){
                                    if(hashPathArr[1] == config.modules[i].pages[j].id){
                                        retObj.id = config.modules[i].pages[j].id;
                                        retObj.htmlPath = config.modules[i].pages[j].url;
                                        retObj.initMethod = config.modules[i].pages[j].method;
                                        retObj.components = config.modules[i].pages[j].components;
                                        flag = false;
                                    }
                                }
                            }
                        }
//                        if(flag){
//                            util.alert("地址错误！");
//                            return;
//                        }
                        return callback(retObj);
                    })
                //}else{
                //    util.alert("地址错误或无权限访问！");
                //    return;
                //}
 //           })
 //       })
	};
	/**  
    * @description 对动态加载的页面进行操作
    * @param hashStr 页面定位字符串
    * @param config 配置信息
    */ 
	var pageController = function(config){
		//给页面赋予属性
		$("div[ng-page]").each(function(){
	        if(typeof($(this).attr("ng-page-id"))=="undefined"){
	            $(this).attr("ng-page-id",config.hashStr);
	        }
	    });
		//加载对应的JS
        require([APP_CONTEXT.appPath+config.jsPathBase+config.jsPath],function(objCtrl){
            
            if(config.initMethod){
                objCtrl[config.initMethod]();//页面初始化方法
            }
            //为页面属性赋予事件功能
            $("div[ng-page-id='"+config.hashStr+"']").find("*[ng-click]").each(function(index, val) {
                 $(this).click(function(event) {
                      var method = $(this).attr("ng-click");//获取属性值
                      objCtrl[method]();//赋予事件功能
                 });
            });

	        //获取组件资源权限 判断是否显示资源
            util.get("system/access/isAdmin",{},function(flag){
                util.get("system/access/getAuthorize",{"type":"component"},function(data){
                    for(var i=0,len=config.components.length;i<len;i++){
                        if(config.components[i].limit){
                            //组件资源
                            var componentResource = config.code+config.module+config.id+config.components[i].component;
                            if(!flag&&!util.contains(data,componentResource)){
                                $("div[ng-page-id='"+config.hashStr+"']").find("#"+config.components[i].component).remove();//移除组件
                            }
                        }
                    }
                })
            })
            
             //select 绑定option
            $("div[ng-page-id='"+config.hashStr+"']").find("select[optionCode]").each(function(){
                var optionCode = $(this).attr("optionCode");//类型 + 方法KEY
                var optionKey = $(this).attr("optionKey");
                var optionValue = $(this).attr("optionValue");
                var id = $(this).attr("id");
                var optionCodeArr = optionCode.split(".");
                $("#"+id).empty();
                $.getJSON(APP_CONTEXT.appSelectPath,function(select){
                    if(optionCodeArr[0]=="JSON"){
                        var optionJSON = select.JSON[optionCodeArr[1]];
                        //动态生成option
                        var str = "";
                        for(var i=0;i<optionJSON.length;i++){
                            str += "<option value="+optionJSON[i].key+">"+optionJSON[i].value+"</option>";
                        }
                        $("#"+id).append(str);
                    }else{
                        
                    }
                });
            });
           
        })
	};
	/**  
    * @description 页面解析
    * @param hashStr 页面定位字符串
    * @param url 获取配置信息地址
    * @param pos jquery 定位符 动态页面显示的位置
    */ 
	var parsePage = function(hashStr,pos){
		var url = APP_CONTEXT.appConfigPath;
		getConfig(hashStr,url,function(config){
			//加载页面
            pos = pos||"#page";
            $(pos).load(APP_CONTEXT.appPath+config.htmlPathBase+config.htmlPath,function() {
            	//对加载的页面进行处理
                pageController(config);
            });
		})
	};

   
    /**  
    * @description 侧滑页面显示
    * @param hashStr 页面定位符
    * @param url 配置信息路径
    * @param callback 回调函数
    */ 
    var slidePage = function(hashStr,callback){
    	var url = APP_CONTEXT.appConfigPath;
    	getConfig(hashStr,url,function(config){
			//加载页面
            $.pageslide({href:APP_CONTEXT.appPath+config.htmlPathBase+config.htmlPath,direction:"left"},function() {
            	//对加载的页面进行处理
                pageController(config);
            });
		})
    };

    /**  
    * @description 关闭侧滑页面
    */ 
    var slideHide = function(){
    	$.pageslide.close();
    };
    
    

    return {
    	parsePage:parsePage,
    	slidePage:slidePage,
    	slideHide:slideHide 
    }
});