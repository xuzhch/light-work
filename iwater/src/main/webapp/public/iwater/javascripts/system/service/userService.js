/**
 * 用户信息操作
 */
define(["util"], function(page, util, resource) {
	
	var getUser = function(username, callback) {
		util.get("system/users/getUser",{"username":username},function(result){
			var data = result[0];
			return callback(data);			
		});
	};
	
	var saveUser = function(data, callback) {
		util.post("system/users/update",data,function(result){
			//do something
			callback(result);
        })
	};

	var addUser = function(data, callback) {
		util.post("system/users/add", data, function(result) {
			//do something
			util.alert("保存成功4","success");
			callback(result);
		})
		return false;
    };

	return {
		getUser : getUser,
		saveUser : saveUser,
		addUser : addUser
	}

});