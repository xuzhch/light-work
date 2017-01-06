/**
* 用户信息操作
*/
define(["page","util","app/system/resourceController","app/system/service/userService","ztree"],function (page,util,resource,userService) {

	/**  
    * @description 页面初始化 列表显示
    * @param params 查询条件
    */
	var list = function(params){

		var params = params||{};//查询条件
		var config = {
			//http://10.25.76.206:18080/api/smartcloudServer/security/users/list?app_key=fdioanferik&app_secret=secret1&callback=?
			//在url后加上查询参数，如?username=sss
				"sAjaxSource":"system/users/list",
			"aoColumns":[
					{ //自定义列多选框
			            "mDataProp": "username",
			            "sClass": "center",
			            'bSortable': false,
			            "mRender": function (username) {
			                return util.getTableRadio(username,"users-table");
			            }
			        },
		            { "sClass": "center", 'bSortable': false,"mDataProp": "username"},
		            { "sClass": "center", 'bSortable': false,"mDataProp": "email", },
		            { "sClass": "center", 'bSortable': false,"mDataProp": "gender" },
		            { "sClass": "center", 'bSortable': false,"mDataProp": "status" }
        		]
		};
		
		util.renderDataTable("#users-table",config);
	};
	/**  
    * @description 新增用户页面显示
    */
	var addUser = function(){
		page.slidePage("system.users.add");
	};
	
	var add = function(){
		var $form = $("#addUserForm");
		util.validform($form,null,function(form){
			//注意此处出现异常时会导致页面刷新，应使用try catch
			try{
				var jsonForm = util.toJsonObject($form);
	    		userService.addUser(jsonForm,function(ret){
	    			page.slideHide();
	    			list();    			
	    			util.alert("保存成功","success"); 
	    		});    	
			}catch(e){
				console.error(e);
			}    			
    		return false;
    	})
	}
	
	var test = function(){
		var dg = $('#dg').datagrid({
            url: 'public/core/datagrid_data1.json',
            singleSelect:true,
            collapsible:true,
            pagination: false,
            remoteFilter: true,
            rownumbers: true,
            method: 'get'
            
        });
	}
	
	/**  
    * @description 新增用户操作
    */
	var addUserCtrl = function(){
		
	};
	/**  
    * @description 查询用户页面显示
    */
	var queryUser = function(){
		page.slidePage("user.query");
	};
	/**  
    * @description 查询用户操作
    */
	var queryUserCtrl = function(){
		var $form = $("#queryUserForm");
		util.validform($form,null,function(form){
    		var jsonForm = util.toJsonObject($form);
    		init(jsonForm);
    		page.slideHide();
    		return false;
    	})
	};
	/**  
    * @description 编辑用户页面显示
    */
	var  editUser = function(){
		var username = util.getTableRadioVal("#users-table");
		if(username){
			page.slidePage("user.edit");
		}else{
			util.alert("请选择需要编辑的用户！");
		}
	};
	/**  
    * @description 编辑页面操作
    */
	var editUserCtrl = function(){
		var username = util.getTableRadioVal("#users-table");
		userService.getUser(username,function(data){
			data.rePassword = data.password;
			util.renderForm("#editUserForm",data);
			var $form = $("#editUserForm");
			util.validform($form,null,function(form){
                //更新数据
                var jsonForm = util.toJsonObject($form);
                userService.saveUser(jsonForm,function(result){
                	util.message("更新成功","success");
                	list();//重新初始化
                });
                page.slideHide();
                return false;
            })
		});
	}
	/**  
    * @description 删除用户
    */
	var deleteUser = function(){
		var username = util.getTableRadioVal("#users-table");
		if(username){
			util.post("system/users/delete",{"username":username},function(result){
				util.message("删除成功","success");
				init();
			})
		}else{
			util.alert("请选择需要删除的用户！");
		}
	};
	/**  
    * @description 用户权限信息列表
    */
	var userPermission = function(){
		alert("userPermission...");
	};
	/**  
    * @description 授权页面显示
    */
	var authorize = function(){
		var username = util.getTableRadioVal("#users-table");
		if(username){
			page.slidePage("user.authorize",url);
		}else{
			util.alert("请选择授权的用户！");
		}
		
	}
	/**  
    * @description 授权操作
    */
	var authorizeCtrl = function(){
		resource.getResource(function(){
			var username = util.getTableRadioVal("#users-table");
			util.get("system/users/getUser",{"username":username},function(data){
				var obj = {
					"menus":data[0].menus,
					"components":data[0].components,
					"urls":data[0].urls
				}
				resource.renderResource(obj);//渲染已授权的资源
			})
		});
	}
	/**  
    * @description 确认授权
    */
	var auth = function(){
		var username = util.getTableRadioVal("#users-table");
		var resourceObj = resource.getSelectedResource();
		var obj = {
			"username":username,
			"resource":resourceObj
		}
		util.post("system/ussers/addResource",obj,function(data){
			util.message("授权成功","success");	
            page.slideHide();
            return false;
		})
	}


	return {
		list:list,
		add:add,
		test:test,
		addUser:addUser,
		queryUser:queryUser,
		editUser:editUser,
		editUserCtrl:editUserCtrl,
		deleteUser:deleteUser,
		userPermission:userPermission,
		addUserCtrl:addUserCtrl,
		queryUserCtrl:queryUserCtrl,
		authorize:authorize,
		authorizeCtrl:authorizeCtrl,
		auth:auth
	}
	
});