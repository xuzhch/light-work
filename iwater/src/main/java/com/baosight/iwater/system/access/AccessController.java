package com.baosight.iwater.system.access;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/access")
public class AccessController {
	
	/**
	 * 返回json数据
	 * @param id
	 * @return
	 */
	@RequestMapping("/isAdmin")
	public String isAdmin(){
		String jsonStr = "{\"doc\":true,\"status\":1}";
		return jsonStr;
	}
	
	/**
	 * 返回json数据
	 * @param id
	 * @return
	 */
	@RequestMapping("/getAuthorize")
	public String getAuthorize(){
		String jsonStr = "{\"doc\":[\"menu_smartcloudServermenu.list\",\"menu_smartcloudServermenu\",\"menu_smartcloudServeruser.list\",\"menu_smartcloudServer\",\"menu_smartcloudServerorg\"],\"status\":1}";
		return jsonStr;
	}

}
