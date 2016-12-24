/**
* 模板页面初始化
*/
define(["util"],function (util) {

	/**  
    * @description 初始化菜单信息
    */
	var initMenus = function(){
		util.getMenus("smartcloudServer",true,function(config){
	        util.get("/smartcloudServer/basic/user/isAdmin",{},function(flag){
	            var str = "",menus=config.children;
	            //菜单权限控制
	            util.get("/smartcloudServer/cache/redis/getAuthorize",{"type":"menu"},function(data){
	            	//获取菜单信息 动态生成菜单代码
	                for(var i=0,len=menus.length;i<len;i++){
	                    if(flag||component.contains(data,"menu_smartcloudServer"+menus[i].hash)){
	                        str += "<li>";
	                        if(menus[i].children&&menus[i].children.length>0){
	                            str +="<a href='javascript:;' class='dropdown-toggle'>"+
	                            "<i class='"+menus[i].pic+"'></i>"+
	                            "<span class='menu-text'>"+menus[i].name+"</span>"+
	                            "<b class='arrow icon-angle-down'></b>"+
	                            "</a>"+
	                            "<ul class='submenu' style='display: none;'>";
	                            for(var j=0,subLen=menus[i].children.length;j<subLen;j++){
	                                if(flag||component.contains(data,"menu_smartcloudServer"+menus[i].children[j].hash)){
	                                    str +="<li>"+
	                                    "<a href='#"+menus[i].children[j].hash+"'>"+
	                                    "<i class='icon-double-angle-right'></i>"+menus[i].children[j].name+"</a></li>";
	                                }
	                                
	                            }
	                            str += "</ul>";
	                        }else{
	                            str += "<a href='#"+menus[i].hash+"' class='dropdown-toggle'>"+
	                            "<i class='icon-double-angle-right></i>"+menus[i].name+"</a>";
	                        }
	                        str +="</li>";
	                    }
	                    
	                }
	                $("#menus").empty().append(str);
	            })
	        })    
   		})
	}

	return {
		initMenus:initMenus
	}
})