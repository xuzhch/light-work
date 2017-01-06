/**
* 封装公共前端组件
*/

define(["bootbox","datatables","datatables.bootstrap","jquery.validform",,"jquery.easyui"],function (bootbox) {
	/**  
	* @description 重写alert
	* @param message 弹出消息
	* @param width 宽度
	*/ 
	var alert = function(message,width){
			var dialog = bootbox.dialog({
	        message: message,
	        title: "<i class='fa fa-exclamation-triangle orange'></i> 提示信息",
	        buttons:
	        {
	            "success" :
	            {
	                "label" : "<i class='fa fa-check'></i> 确定",
	                "className" : "btn-warning"
	            }
	        }
	    }).css("z-index",9999999999999);
	    if(width){
	        $(".modal-dialog").width(width);
	    }
	};
	/**  
	* @description 重写confirm
	* @param message 消息
	* @param callback 回调函数
	* @param width 宽度
	*/ 
	var confirm = function (message,callback,width){
	    bootbox.dialog({
	        message: message,
	        title: "<i class='fa fa-question-circle orange'></i> 确认信息",
	        buttons:
	        {
	            "success" :
	            {
	                "label" : "<i class='fa fa-check'></i> 确定",
	                "className" : "btn-danger",
	                "callback":function(){
	                    callback();
	                }
	            },
	            "reject" :
	            {
	                "label" : "<i class='fa fa-times'></i> 取消",
	                "className" : "btn-info"
	            }
	        }
	    }).css("z-index",9999999999999);

	    if(width){
	        $(".modal-dialog").width(width);
	    }
	};
	/**  
	* @description 重写prompt
	* @param title 标题
	* @param callback 回调函数
	*/ 
	var prompt = function(title,callback){
	    var promptOptions = {
	        title: title,
	        buttons: {
	            confirm: {
	                label: "<i class='fa fa-check'></i> 确认",
	                "className" : "btn-danger"

	            },
	            cancel:{
	                label: "<i class='fa fa-times'></i>取消",
	                "className" : "btn-info"
	            }
	        },
	        inputType:"textarea",
	        callback: function(result){
	            callback(result);
	        }
	    };
	    bootbox.prompt(promptOptions);
	};
	/**  
	* @description 消息提示 一闪而过
	* @param msg 消息内容
	* @param type 提示消息的类型
	*/ 
	var message = function(msg,type){
		 var messageAlert = $('<div class="alert alert-'+type+' text-center"><button type="button" class="close" data-dismiss="alert"><i class="fa fa-times"></i></button>'+msg+'<br /></div>');
	    $("body").prepend(messageAlert);
	    setTimeout(function(){
	        messageAlert.hide(1000,"swing");
	    },1500);
	}
	/**  
	* @description checkbox标签
	* @param id checkbox值
	* @param name checkbox名字
	* @return checkbox标签
	*/ 
    var getTableCheckbox = function(id,name){
        return '<label><input class="ace" type="checkbox" name="'+name+'" value="'+id+'" /><span class="lbl"></span></label>';

    };
    /**  
	* @description 获取checkbox值
	* @param pos jquery定位符
	* @return checkbox选中的值
	*/ 
    var getTableCheckedbox = function(pos){
	    var values = [];
	    var $input = $(pos).find("input[type='checkbox']");
	    $.each($input,function(index,input){
	        if(input.checked == true){
	            values.push(input.value);
	        }
	    });
	    return values;
	};
    /**  
	* @description radio标签
	* @param id radio
	* @param name radio
	* @return radio标签
	*/ 
    var getTableRadio = function(id,name){
        return '<label><input class="ace" type="radio" name="'+name+'" value="'+id+'" /><span class="lbl"></span></label>';
    };
    /**  
	* @description 获取radio值
	* @param pos jquery定位符
	* @return radio选中的值
	*/ 
    var getTableRadioVal = function(pos){
    	return $(pos).find("input[type='radio'].ace:checked").val();
    };
    /**  
	* @description 填充表单
	* @param pos jquery定位符
	* @param obj 填充的对象值
	*/
    var renderForm = function(pos,obj){
    	var key,value,tagName,type,arr;
	    for(x in obj){
	        key = x;
	        value = obj[x];
	         
	        $(pos).find("[name='"+key+"'],[name='"+key+"[]']").each(function(){
	            tagName = $(this)[0].tagName;
	            type = $(this).attr('type');
	            if(tagName=='INPUT'){
	                if(type=='radio'){
	                    $(this).attr('checked',$(this).val()==value);
	                }else if(type=='checkbox'){
	                    arr = value.split(',');
	                    for(var i =0;i<arr.length;i++){
	                        if($(this).val()==arr[i]){
	                            $(this).attr('checked',true);
	                            break;
	                        }
	                    }
	                }else{
	                    $(this).val(value);
	                }
	            }else if(tagName=='SELECT' || tagName=='TEXTAREA'){
	                $(this).val(value);
	            }
	             
	        });
	    }
    }
    /**  
	* @description jqueryDataTable
	* @param pos jquery定位符
	* @param config table配置
	*/
    var renderDataTable = function(pos,config){
        var defaultConfig = {
            "ordering":false,//是否排序
            "bProcessing": true,//是否显示正在处理信息
            "bServerSide": true,//是否启动服务器端数据导入，即要和sAjaxSource结合使用
            "bDestroy":true,//用于当要在同一个元素上执行新的dataTable绑定时，将之前的那个数据对象清除掉，换以新的对象设置
            'bPaginate': true,//是否分页
            "bFilter": false,
            "oLanguage": {
                "sProcessing": "正在加载中......",
                "sInfoEmpty": "没有记录...",
                "sEmptyTable": "查询记录为空...",
                "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录"
             },
             //"sAjaxSource":config.sAjaxSource,//后台访问地址
             //"fnServerParams"://发送额外的数据
             "fnServerData":function(sSource, aoData, fnCallback ){
                $.ajax( {
                    "type": "GET",
                    "contentType": "application/json",
                    "url": sSource,
                    "dataType": "json",
                    "data": aoData, //以json格式传递
                    "success": function(resp) {
                        if(resp.error!=undefined){
                            component.alert(resp.error);
                            return;
                        }
                        if(resp=="NoPermission"){
                            var object={};
                            object.aaData=[{"permission":"NoPermission"}];
                            resp=object;
                        }
                        fnCallback(resp.doc); //服务器端返回的对象的returnObject部分是要求的格式
                    }
                });
             },//用于替换默认发到服务端的请求操作
             //"aoColumns":config.columns//定义列
             //"fnPreDrawCallback"://用于在开始绘制之前调用，返回false的话，会阻止draw事件发生；返回其它值，draw可以顺利执行
        }
        //自定义
        for(var key in config){
            defaultConfig[key] = config[key];
        }

        $(pos).dataTable(defaultConfig);
    };
    /**  
	* @description $obj对象封装成json形式
	* @param $obj $obj对象
	* @return json结果
	*/
    var toJsonObject = function($obj){
        var serializeObj={};
        var array=$obj.serializeArray();
        $(array).each(function(){
            if(this.value){
                if(serializeObj[this.name]){
                    if($.isArray(serializeObj[this.name])){
                        serializeObj[this.name].push(this.value);
                    }else{
                        serializeObj[this.name]=[serializeObj[this.name],this.value];
                    }
                }else{
                    serializeObj[this.name]=this.value;
                }
            }
        });
        return serializeObj;
    }
    /**  
	* @description 迭代处理菜单信息
	* @param code 初始化编码
	* @param pCode 父及菜单编码
	* @param config 菜单配置信息
	* @param menus 存放处理完成菜单的变量
	* @return 处理后的菜单结果
	*/
    var menuIterator = function(code,pCode,config,menus){

		for(var i=0,len=config.length;i<len;i++){
    		var obj = {
    			"name":config[i].name,
    			"hash":config[i].hash,
    			"pic":config[i].pic,
    			"desc":config[i].desc,
    			"code":code+config[i].hash,
    			"pCode":pCode
    		};
    		menus.push(obj);
    		var flag = config[i].children&&config[i].children.length>0;
	        if(flag){
	            menuIterator(code,obj.code,config[i].children,menus);
	        }
    	}
    	return menus;	

    }

    /**  
	* @description 获取菜单信息
	* @param app 那个应用的菜单 null为全部获取
	* @param flag 是否需要迭代获取数组形式的菜单
	* @param callback 回调函数
	*/
    var getMenus = function(app,flag,callback){
        $.getJSON(APP_CONTEXT.appMenuPath, function(config){
        	var menus = [];
            for(var i=0,len=config.length;i<len;i++){
            	if(!app){//全部获取
            		if(flag){
            			return callback(config[i]);
            		}else{
            			menus = menuIterator("menu_"+config[i].app,"menu_root",[config[i]],menus);
            		}
            	}else if(config[i].app == app){//获取对应的一个
            		if(flag){
            			return callback(config[i]);
            		}else{
            			menus = menuIterator("menu_"+config[i].app,"menu_root",[config[i]],[]);
            			break;
            		}
            		
            	}
            }
            //判断是否是管理员
            get("system/access/isAdmin",{},function(flag){
            	if(flag){//管理员无需判断权限
            		return callback(menus);
            	}else{
            		//获取菜单权限
            		get("system/access/getAuthorize",{"type":"menu"},function(data){
            			var ret = [];
            			for(var i=0,len=menus.length;i<len;i++){
            				if(contains(data,menus[i].code)){
            					ret.push(menus[i]);
            				}
            			}
            			return callback(ret);
            		})
            	}
            })
            
        })
    }
    /**  
	* @description tab切换 阻止链接跳转
	* @param pos jquery定位符
	*/
    var bootstrapTab = function(pos){
    	$(pos+' a:first').tab('show');//初始化显示tab 
        $(pos+' a').click(function (e) { 
        	e.preventDefault();//阻止a链接的跳转行为 
          	$(this).tab('show');//显示当前选中的链接及关联的content 
        }) 
    }
    /**  
	* @description 获取页面所有组件
	* @param params 获取条件
	* @param callback 回调函数
	*/
    var getComponent = function(params,callback){
    	$.getJSON(APP_CONTEXT.appComponentPath,function(component){
    		var paths = component.path;
    		var ret = [];
    		//组装组件信息
    		for(var i=0,len=paths.length;i<len;i++){
    			(function(path,i){
    				$.getJSON(path,function(config){
	    				var obj = {
	    					"name":config.name,
	    					"code":config.code,
	    					"pCode":config.pCode
	    				}
	    				ret.push(obj);
	    				var modules = config.modules;
	    				for(var j=0,moduleLen=modules.length;j<moduleLen;j++){
	    					var moduleObj = {
	    						"name":modules[j].caption,
	    						"code":obj.code+modules[j].module,
	    						"pCode":obj.code
	    					} 
	    					ret.push(moduleObj);
	    					var pages = modules[j].pages;
	    					for(var k=0,pageLen=pages.length;k<pageLen;k++){
	    						var pageObj = {
	    							"name":pages[k].caption,
	    							"code":moduleObj.code+pages[k].id,
	    							"pCode":moduleObj.code
	    						}
	    						ret.push(pageObj);
	    						var components = pages[k].components;
	    						for(var l=0,componentLen=components.length;l<componentLen;l++){
	    							var componentObj = {
	    								"name":components[l].caption,
	    								"code":pageObj.code+components[l].component,
	    								"pCode":pageObj.code
	    							}
	    							ret.push(componentObj);
	    						}
	    					}
	    				} 
	    				if(i == len-1){//表示全部执行完成 执行回调
    						return callback(ret);
    					}
	    			})
    			})(paths[i],i)
    		}
    	})
    }
    /**  
	* @description 判断数组中是否有obj元素
	* @param arr 数组
	* @param obj 元素
	* @return boolean
	*/
	var contains = function (arr, obj) {
		if(arr){
			var i = arr.length;
		    while (i--) {
		        if (arr[i] === obj) {
		            return true;
		        }
		    }
		}
	    
	    return false;
	}
	 /**  
    * @description 返回结果统一处理
    * @param data 返回结果
    * @param callback 回调函数
    */ 
	var resProcess = function(data,callback){
		var _this = this;
	    if(data.status==-1){
	        alert("运行异常，异常信息："+data.message+",请与管理员联系！");
	        return ;
	    }else{
	        return callback(data.doc);
	    }
	};
    /**  
    * @description 封装get请求
    * @param path 请求路径
    * @param params 请求条件
    * @param callback 回调函数
    */ 
    var get = function(path,params,callback){
    	params.random = Math.random();
    	$.get(path, params,function(data){
            return resProcess(data,callback);
        });
    };
    /**  
    * @description 封装post请求
    * @param path 请求路径
    * @param params 请求条件
    * @param callback 回调函数
    */ 
    var post = function(path,params,callback){
    	var params = JSON.stringify(params);
	    $.post(path,{"params":params},function(data){
	        return resProcess(data,callback);
	    });
    };
    /**  
    * @description 验证表单
    * @param $form jquery表单
    * @param option validform配置
    * @param callback 回调函数
    */ 
    var validform = function($form,option,callback){
    	var options = option ||{
    		tiptype:3,
        	label:".label",
        	showAllError:true,
        	callback:callback
    	}
    	$form.Validform(options);
    }

    return {
        alert:alert,
        confirm:confirm,
        prompt:prompt,
        message:message,
        getTableCheckbox:getTableCheckbox,
        getTableCheckedbox:getTableCheckedbox,
        getTableRadio:getTableRadio,
        getTableRadioVal:getTableRadioVal,
        renderForm:renderForm,
        renderDataTable:renderDataTable,
        toJsonObject:toJsonObject,
        getMenus:getMenus,
        bootstrapTab:bootstrapTab,
        getComponent:getComponent,
        contains:contains,
        get:get,
    	post:post,
    	validform:validform
    }
});