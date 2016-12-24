/**
* 用户信息操作
*/
define(["page","util","smartcloud/resourceController","ztree"],function (page,util,resource) {

	var url = "smartcloud/config/app.json";
	/**  
    * @description 页面初始化 列表显示
    * @param params 查询条件
    */
	var init = function(params){

		var params = params||{};//查询条件
		var config = {
			//http://10.25.76.206:18080/api/smartcloudServer/security/users/list?app_key=fdioanferik&app_secret=secret1&callback=?
			"sAjaxSource":"/smartcloudServer/basic/user/pagination?params="+JSON.stringify(params),
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
		page.slidePage("user.add",url);
	};
	/**  
    * @description 新增用户操作
    */
	var addUserCtrl = function(){
		var $form = $("#addUserForm");
		util.validform($form,null,function(form){
    		var jsonForm = util.toJsonObject($form);
    		util.post("/smartcloudServer/basic/user/add",jsonForm,function(ret){
    			util.message("保存成功","success");
    			init();
    		})
    		page.slideHide();
    		return false;
    	})
	};
	/**  
    * @description 查询用户页面显示
    */
	var queryUser = function(){
		page.slidePage("user.query",url);
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
			page.slidePage("user.edit",url);
		}else{
			util.alert("请选择需要编辑的用户！");
		}
	};
	/**  
    * @description 编辑页面操作
    */
	var editUserCtrl = function(){
		var username = util.getTableRadioVal("#users-table");
		util.get("/smartcloudServer/basic/user/getUser",{"username":username},function(result){
			var data = result[0];
			data.rePassword = data.password;
			util.renderForm("#editUserForm",result[0]);
			var $form = $("#editUserForm");
			util.validform($form,null,function(form){
                //更新数据
                var jsonForm = util.toJsonObject($form);
                util.post("/smartcloudServer/basic/user/update",jsonForm,function(result){
                	util.message("更新成功","success");
                	init();//重新初始化
                })
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
			util.post("/smartcloudServer/basic/user/delete",{"username":username},function(result){
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
			util.get("/smartcloudServer/basic/user/getUser",{"username":username},function(data){
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
		util.post("/smartcloudServer/basic/user/addResource",obj,function(data){
			util.message("授权成功","success");	
            page.slideHide();
            return false;
		})
	}


	return {
		init:init,
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