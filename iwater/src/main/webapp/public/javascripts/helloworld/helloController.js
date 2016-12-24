
define(["page","common/util"],function (page,util) {
	
	var init = function(){
		util.get("/helloServer/hellomodule/hellocmd/hello",{"name":"baosight"},function(data){
			$("#msg").text(data);
		})
	}

	return {
		init:init
	}
})