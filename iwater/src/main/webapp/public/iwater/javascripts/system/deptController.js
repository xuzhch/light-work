/**
* 部门信息操作
*/
define(["page","util","smartcloud/resourceController","ztree"],function (page,util,resource) {

	var url = "public/smartcloud/config/app.json";
	/**  
    * @description 页面初始化 列表显示
    * @param params 查询条件
    */ 
	var init = function(params){

		util.get("/smartcloudServer/basic/dept/getDept",{},function(data){
			var setting = {
	            data: {
	                simpleData: {
	                    enable: true,
	                    idKey: "code",
	                    pIdKey: "pCode",
	                    rootPId: "dept_root"
	                },
	                key:{
	                    name:"name"
	                }
	            },
	            check: {
	                enable: true,
	                chkStyle: "radio",
	                radioType: "all"
	            }
	        };
	        var zTreeObj = $.fn.zTree.init($("#deptTree"),setting,data);
	        zTreeObj.expandAll(true);
		})
        
	};

	/**  
    * @description 新增角色页面显示
    */ 
	var addDept = function(){

		var zTree = $.fn.zTree.getZTreeObj("deptTree");
	    var node = zTree.getCheckedNodes(true)[0];
	    if(node){
	        page.slidePage("dept.add",url);
	    }else{
	    	util.confirm("您将创建的是根目录，请确认！",function(){
	            page.slidePage("dept.add",url);
	        }); 
	    }
		
	};
	/**  
    * @description 新增角色操作
    */ 
	var addDeptCtrl = function(){
		var zTree = $.fn.zTree.getZTreeObj("deptTree");
	    var node = zTree.getCheckedNodes(true)[0];
		var $form = $("#addDeptForm");
		util.validform($form,null,function(form){
    		var jsonForm = util.toJsonObject($form);
    		jsonForm.pCode = node?node.code:"dept_root";//父节点信息
    		util.post("/smartcloudServer/basic/dept/add",jsonForm,function(ret){
    			util.message("保存成功","success");
    			init();
    		})
    		page.slideHide();
    		return false;
    	})
		
	};
	/**  
    * @description 删除操作
    */
	var deleteDept = function(){
		var zTree = $.fn.zTree.getZTreeObj("deptTree");
	    var node = zTree.getCheckedNodes(true)[0];
	    if(node&&!node.isParent){
	        util.post("/smartcloudServer/basic/dept/delete", {"_id":node._id}, function(data) {
		    	util.message("删除成功","success");
				init();
		    });
	    }else{
	    	util.alert("请选择需删除的部门，注意：不能直接删除上级部门！"); 
	    }
	}
	/**  
    * @description 用户管理页面
    */
	var userManage = function(){

		var zTree = $.fn.zTree.getZTreeObj("deptTree");
	    var node = zTree.getCheckedNodes(true)[0];
	    if(node){
	        page.slidePage("dept.user",url);
	    }else{
	    	util.alert("请选择具体部门进行用户管理！"); 
	    }
	}
	/**  
    * @description 用户管理操作 获取部门已添加的用户
    */
	var userManageCtrl = function(){
		var zTree = $.fn.zTree.getZTreeObj("deptTree");
	    var node = zTree.getCheckedNodes(true)[0];
		var params = {"_id":node._id};//查询条件
		var config = {
			//http://10.25.76.206:18080/api/smartcloudServer/security/users/list?app_key=fdioanferik&app_secret=secret1&callback=?
			"sAjaxSource":"/smartcloudServer/basic/dept/getAddedUser?params="+JSON.stringify(params),
			"aoColumns":[
					{ //自定义列多选框
			            "mDataProp": "username",
			            "sClass": "center",
			            'bSortable': false,
			            "mRender": function (username) {
			                return util.getTableRadio(username,"role-user-table");
			            }
			        },
		            { "sClass": "center", 'bSortable': false,"mDataProp": "username"},
		            { "sClass": "center", 'bSortable': false,"mDataProp": "email", },
		            { "sClass": "center", 'bSortable': false,"mDataProp": "gender" }
        		]
		};
		util.renderDataTable("#dept-user-table",config);
	}
	/**  
    * @description 部门添加用户操作 显示可添加的用户
    */
	var deptAddUser = function(){
		var zTree = $.fn.zTree.getZTreeObj("deptTree");
	    var node = zTree.getCheckedNodes(true)[0];
		var params = {"_id":node._id};//查询条件
		var config = {
			//http://10.25.76.206:18080/api/smartcloudServer/security/users/list?app_key=fdioanferik&app_secret=secret1&callback=?
			"sAjaxSource":"/smartcloudServer/basic/dept/getNeedUser?params="+JSON.stringify(params),
			"aoColumns":[
					{ //自定义列多选框
			            "mDataProp": "username",
			            "sClass": "center",
			            'bSortable': false,
			            "mRender": function (username) {
			                return util.getTableCheckbox(username,"deptUserModalTable");
			            }
			        },
		            { "sClass": "center", 'bSortable': false,"mDataProp": "username"},
		            { "sClass": "center", 'bSortable': false,"mDataProp": "email", },
		            { "sClass": "center", 'bSortable': false,"mDataProp": "gender" }
        		]
		};
		
		util.renderDataTable("#deptUserModalTable",config);
		$("#deptUserModal").modal({
	        backdrop:false
	    }).css("z-index",99999999999);
	}
	/**  
    * @description 为部门添加用户
    */
	var deptAddUserCtrl = function(){
		var values = util.getTableCheckedbox("#deptUserModalTable");
		var zTree = $.fn.zTree.getZTreeObj("deptTree");
	    var node = zTree.getCheckedNodes(true)[0];
		$("#deptUserModal").modal("toggle");//隐藏
		if(values.length>0&&node){
			util.post("/smartcloudServer/basic/dept/addUser",{"username":values,"_id":node._id},function(result){
				util.message("添加成功","success");
				userManage();
			})
		}
		
	}
	/**  
    * @description 部门删除用户
    */
	var deptDeleteUser = function(){
		var username = util.getTableRadioVal("#dept-user-table");
		var zTree = $.fn.zTree.getZTreeObj("deptTree");
	    var node = zTree.getCheckedNodes(true)[0];
		if(node){
			util.post("/smartcloudServer/basic/dept/deleteUser",{"username":username,"_id":node._id},function(result){
				util.message("删除成功","success");
				userManage();
			})
		}else{
			util.alert("请选择删除的用户！");
		}
		
	}
	/**  
    * @description 授权页面显示
    */
	var authorize = function(){
		var dept_zTree = $.fn.zTree.getZTreeObj("deptTree");
		var selectedNode = dept_zTree.getCheckedNodes(true)[0];
		if(selectedNode){
			page.slidePage("dept.authorize",url);
		}else{
			util.alert("请选择授权的部门！");
		}
		
	}
	/**  
    * @description 授权操作
    */
	var authorizeCtrl = function(){
		resource.getResource(function(){
			var dept_zTree = $.fn.zTree.getZTreeObj("deptTree");
			var selectedNode = dept_zTree.getCheckedNodes(true)[0];
			util.get("/smartcloudServer/basic/dept/getDept",{"code":selectedNode.code},function(data){
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
		var dept_zTree = $.fn.zTree.getZTreeObj("deptTree");
		var selectedNode = dept_zTree.getCheckedNodes(true)[0];
		var resourceObj = resource.getSelectedResource();
		var obj = {
			"code":selectedNode.code,
			"resource":resourceObj
		}
		util.post("/smartcloudServer/basic/dept/addResource",obj,function(data){
			util.message("授权成功","success");	
            page.slideHide();
            return false;
		})
	}

	return {
		init:init,
		addDept:addDept,
		addDeptCtrl:addDeptCtrl,
		deleteDept:deleteDept,
		userManage:userManage,
		userManageCtrl:userManageCtrl,
		deptDeleteUser:deptDeleteUser,
		deptAddUserCtrl:deptAddUserCtrl,
		deptAddUser:deptAddUser,
		authorize:authorize,
		authorizeCtrl:authorizeCtrl,
		auth:auth
	}
	
});