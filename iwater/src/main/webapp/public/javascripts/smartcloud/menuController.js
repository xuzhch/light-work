/**
* 菜单信息操作
*/
define(["util","ztree"],function (util) {
	/**  
    * @description 菜单初始化
    */
	var init = function(){
		util.getMenus(null,false,function(config){
			var setting = {
				data:{
					simpleData:{
						enable:true,
						idKey:"code",
						pIdKey:"pCode",
						rootPid:"menu_root"
					},
					key:{
                        name:"name"
                    }
				}
			};

			var zTreeObj = $.fn.zTree.init($("#menuTree"), setting, config);
			zTreeObj.expandAll(true);
		})
	}

	return {
		init:init
	}
})