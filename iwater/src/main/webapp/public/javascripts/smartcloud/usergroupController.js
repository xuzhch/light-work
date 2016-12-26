/**
* 用户组信息操作
*/
define(["page","util","smartcloud/resourceController"],function (page,util,resource) {

	var url = "public/smartcloud/config/app.json";
	/**  
    * @description 页面初始化 列表显示
    * @param params 查询条件
    */
	var init = function(params){

		var params = params||{};//查询条件
		var config = {
			//http://10.25.76.206:18080/api/smartcloudServer/security/users/list?app_key=fdioanferik&app_secret=secret1&callback=?
			"sAjaxSource":"/smartcloudServer/basic/usergroup/pagination?params="+JSON.stringify(params),
			"aoColumns":[
					{ //自定义列多选框
			            "mDataProp": "_id",
			            "sClass": "center",
			            'bSortable': false,
			            "mRender": function (_id) {
			                return util.getTableRadio(_id,"usergroup-table");
			            }
			        },
		            { "sClass": "center", 'bSortable': false,"mDataProp": "name"},
		            { "sClass": "center", 'bSortable': false,"mDataProp": "code", },
		            { "sClass": "center", 'bSortable': false,"mDataProp": "createUsername" },
		            { "sClass": "center", 'bSortable': false,"mDataProp": "DateTime" },
		            { "sClass": "center", 'bSortable': false,"mDataProp": "desc" }
        		]
		};
		
		util.renderDataTable("#usergroup-table",config);
	};
	/**  
    * @description 新增用户组页面显示
    */
	var addUsergroup = function(){
		page.slidePage("usergroup.add",url);
	};
	/**  
    * @description 新增用户操作
    */
	var addUsergroupCtrl = function(){
		var $form = $("#addUsergroupForm");
		util.validform($form,null,function(form){
    		var jsonForm = util.toJsonObject($form);
    		util.post("/smartcloudServer/basic/usergroup/add",jsonForm,function(ret){
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
	var queryUsergroup = function(){
		page.slidePage("usergroup.query",url);
	};
	/**  
    * @description 查询用户操作
    */
	var queryUsergroupCtrl = function(){
		var $form = $("#queryUsergroupForm");
		util.validform($form,null,function(form){
    		var jsonForm = util.toJsonObject($form);
    		init(jsonForm);
    		page.slideHide();
    		return false;
    	})
	};
	/**  
    * @description 编辑用户组页面显示
    */
	var  editUsergroup = function(){
		var _id = util.getTableRadioVal("#usergroup-table");
		if(_id){
			page.slidePage("usergroup.edit",url);
		}else{
			util.alert("请选择需要编辑的用户组！");
		}
	};
	/**  
    * @description 编辑页面操作
    */
	var editUsergroupCtrl = function(){
		var _id = util.getTableRadioVal("#usergroup-table");
		util.get("/smartcloudServer/basic/usergroup/getUsergroup",{"_id":_id},function(result){
			var data = result[0];
			util.renderForm("#editUsergroupForm",result[0]);
			var $form = $("#editUsergroupForm");
			util.validform($form,null,function(form){
                //更新数据
                var jsonForm = util.toJsonObject($form);
                util.post("/smartcloudServer/basic/usergroup/update",jsonForm,function(result){
                	util.message("更新成功","success");
                	init();//重新初始化
                })
                page.slideHide();
                return false;
            })
			
		});
	}
	/**  
    * @description 删除用户组
    */
	var deleteUsergroup = function(){
		var _id = util.getTableRadioVal("#usergroup-table");
		if(_id){
			util.post("/smartcloudServer/basic/usergroup/delete",{"_id":_id},function(result){
				util.message("删除成功","success");
				init();
			})
		}else{
			util.alert("请选择需要删除的用户组！");
		}
	};
	
	/**  
    * @description 授权页面显示
    */
	var authorize = function(){
		var _id = util.getTableRadioVal("#usergroup-table");
		if(_id){
			page.slidePage("usergroup.authorize",url);
		}else{
			util.alert("请选择授权的用户组！");
		}
		
	}
	/**  
    * @description 授权操作
    */
	var authorizeCtrl = function(){
		resource.getResource(function(){
			var _id = util.getTableRadioVal("#usergroup-table");
			util.get("/smartcloudServer/basic/usergroup/getUsergroup",{"_id":_id},function(data){
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
		var _id = util.getTableRadioVal("#usergroup-table");
		var resourceObj = resource.getSelectedResource();
		var obj = {
			"_id":_id,
			"resource":resourceObj
		}
		util.post("/smartcloudServer/basic/usergroup/addResource",obj,function(data){
			util.message("授权成功","success");	
            page.slideHide();
            return false;
		})
	}
	/**  
    * @description 用户管理页面显示
    */
	var userManage = function(){
		var _id = util.getTableRadioVal("#usergroup-table");
		if(_id){
			page.slidePage("usergroup.user",url);
		}else{
			util.alert("请选择对应的用户组！");
		}
	}
	/**  
    * @description 用户管理操作 获取用户组已添加的用户
    */
	var userManageCtrl = function(){
		var _id = util.getTableRadioVal("#usergroup-table");
		var params = {"_id":_id};//查询条件
		var config = {
			"sAjaxSource":"/smartcloudServer/basic/usergroup/getAddedUser?params="+JSON.stringify(params),
			"aoColumns":[
					{ //自定义列多选框
			            "mDataProp": "username",
			            "sClass": "center",
			            'bSortable': false,
			            "mRender": function (username) {
			                return util.getTableRadio(username,"usergroup-user-table");
			            }
			        },
		            { "sClass": "center", 'bSortable': false,"mDataProp": "username"},
		            { "sClass": "center", 'bSortable': false,"mDataProp": "email", },
		            { "sClass": "center", 'bSortable': false,"mDataProp": "gender" }
        		]
		};
		
		util.renderDataTable("#usergroup-user-table",config);
		
	}
	/**  
    * @description 用户组添加用户操作 显示可添加的用户
    */
	var usergroupAddUser = function(){
		var _id = util.getTableRadioVal("#usergroup-table");
		var params = {"_id":_id};//查询条件
		var config = {
			//http://10.25.76.206:18080/api/smartcloudServer/security/users/list?app_key=fdioanferik&app_secret=secret1&callback=?
			"sAjaxSource":"/smartcloudServer/basic/usergroup/getNeedUser?params="+JSON.stringify(params),
			"aoColumns":[
					{ //自定义列多选框
			            "mDataProp": "username",
			            "sClass": "center",
			            'bSortable': false,
			            "mRender": function (username) {
			                return util.getTableCheckbox(username,"usergroupUserModalTable");
			            }
			        },
		            { "sClass": "center", 'bSortable': false,"mDataProp": "username"},
		            { "sClass": "center", 'bSortable': false,"mDataProp": "email", },
		            { "sClass": "center", 'bSortable': false,"mDataProp": "gender" }
        		]
		};
		
		util.renderDataTable("#usergroupUserModalTable",config);
		$("#usergroupUserModal").modal({
	        backdrop:false
	    }).css("z-index",99999999999);
	}
	/**  
    * @description 为角色添加用户
    */
	var usergroupAddUserCtrl = function(){
		var values = util.getTableCheckedbox("#usergroupUserModalTable");
		var _id = util.getTableRadioVal("#usergroup-table");
		$("#usergroupUserModal").modal("toggle");//隐藏
		if(values.length>0&&_id){
			util.post("/smartcloudServer/basic/usergroup/addUser",{"username":values,"_id":_id},function(result){
				util.message("添加成功","success");
				userManage();
			})
		}
		
	}
	/**  
    * @description 角色删除用户
    */
	var usergroupDeleteUser = function(){
		var username = util.getTableRadioVal("#usergroup-user-table");
		var _id = util.getTableRadioVal("#usergroup-table");
		if(_id){
			util.post("/smartcloudServer/basic/usergroup/deleteUser",{"username":username,"_id":_id},function(result){
				util.message("删除成功","success");
				userManage();
			})
		}else{
			util.alert("请选择删除的用户！");
		}
		
	}

	return {
		init:init,
		addUsergroup:addUsergroup,
		queryUsergroup:queryUsergroup,
		editUsergroup:editUsergroup,
		editUsergroupCtrl:editUsergroupCtrl,
		deleteUsergroup:deleteUsergroup,
		addUsergroupCtrl:addUsergroupCtrl,
		queryUsergroupCtrl:queryUsergroupCtrl,
		authorize:authorize,
		authorizeCtrl:authorizeCtrl,
		auth:auth,
		userManage:userManage,
		userManageCtrl:userManageCtrl,
		usergroupAddUser:usergroupAddUser,
		usergroupAddUserCtrl:usergroupAddUserCtrl,
		usergroupDeleteUser:usergroupDeleteUser
	}
	
});