package com.baosight.iwater.system.user.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baosight.iwater.system.user.pojo.SystemUser;
import com.baosight.iwater.system.user.pojo.User;
import com.baosight.iwater.system.user.service.ISystemUserService;
import com.baosight.iwater.system.user.service.IUserService;

@RestController
@RequestMapping("/system/users")
public class SystemUserController {
	@Resource
	private ISystemUserService systemUserService;
	
	/**
	 * 返回json数据
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String listUser(){
		String str = "{\"doc\":{\"sEcho\":\"1\",\"iTotalRecords\":5,\"iTotalDisplayRecords\":5,\"iDisplayStart\":\"0\",\"aaData\":[{\"_id\":\"55348ffb1fadb4a4227f1853\",\"username\":\"szyadmin\",\"password\":\"kJN57DtV0Y+saUOBd8NCzA==\",\"userGroupArr\":[],\"__v\":0,\"components\":[],\"urls\":[],\"menus\":[],\"usergroupArr\":[],\"deptArr\":[],\"roleArr\":[\"553ee0a3cbb9da902f6532d3\"],\"status\":\"normal\",\"updateTime\":\"2015-04-20T05:34:51.692Z\",\"createTime\":\"2015-04-20T05:34:51.692Z\",\"gender\":\"male\",\"email\":\"hsh.0223.ok@163.com\"},{\"_id\":\"553891f5ea26bd980e464a20\",\"username\":\"admin\",\"password\":\"lueSGJZetyySpUndWjMBEg==\",\"birthProvince\":\"sh\",\"userGroupArr\":[],\"__v\":0,\"token\":\"2bfBSR8FZWa3h0rSN86gUA==\",\"components\":[\"component_smartcloudServer\",\"component_smartcloudServeruser\",\"component_smartcloudServeruserlist\",\"component_smartcloudServeruserlistqueryUser\"],\"urls\":[],\"menus\":[\"menu_smartcloudServer\",\"menu_smartcloudServerorg\",\"menu_smartcloudServeruser.list\",\"menu_smartcloudServermenu\",\"menu_smartcloudServermenu.list\"],\"usergroupArr\":[],\"deptArr\":[],\"roleArr\":[\"553ee0a3cbb9da902f6532d3\"],\"status\":\"normal\",\"updateTime\":\"2015-04-23T06:32:21.304Z\",\"createTime\":\"2015-04-23T06:32:21.304Z\",\"gender\":\"male\",\"email\":\"admin@163.com\"},{\"_id\":\"55389299ea26bd980e464a21\",\"username\":\"hsh0756232\",\"password\":\"lueSGJZetyySpUndWjMBEg==\",\"birthProvince\":\"sh\",\"userGroupArr\":[],\"__v\":0,\"components\":[],\"urls\":[\"url_smartcloudServer\",\"url_smartcloudServerbasic\",\"url_smartcloudServerbasicuser\",\"url_smartcloudServerbasicuserpagination\"],\"menus\":[],\"usergroupArr\":[\"555ee19cc0b42a002342e8a0\"],\"deptArr\":[],\"roleArr\":[\"553ee7a5ffa1d4702ec339b9\",\"5548162e7d51d5f4158af6f4\"],\"status\":\"normal\",\"updateTime\":\"2015-04-23T06:35:05.039Z\",\"createTime\":\"2015-04-23T06:35:05.039Z\",\"gender\":\"male\",\"email\":\"hsh.0223.ok@163.com\"},{\"_id\":\"553d9909b490fc5c23c60ca6\",\"username\":\"Test\",\"password\":\"lueSGJZetyySpUndWjMBEg==\",\"birthProvince\":\"zj\",\"__v\":0,\"components\":[],\"urls\":[\"url_smartcloudServer\",\"url_smartcloudServerbasic\",\"url_smartcloudServerbasicuser\",\"url_smartcloudServerbasicuserpagination\"],\"menus\":[\"menu_smartcloudServer\",\"menu_smartcloudServerorg\",\"menu_smartcloudServeruser.list\"],\"usergroupArr\":[],\"deptArr\":[],\"roleArr\":[\"553ee0a3cbb9da902f6532d3\"],\"status\":\"normal\",\"updateTime\":\"2015-04-27T02:03:53.668Z\",\"createTime\":\"2015-04-27T02:03:53.668Z\",\"gender\":\"male\",\"email\":\"Test@Test.com\"},{\"_id\":\"553d995ab490fc5c23c60ca7\",\"username\":\"shou\",\"password\":\"lueSGJZetyySpUndWjMBEg==\",\"birthProvince\":\"sh\",\"__v\":0,\"components\":[],\"urls\":[],\"menus\":[],\"usergroupArr\":[],\"deptArr\":[],\"roleArr\":[\"553ee7a5ffa1d4702ec339b9\",\"5548162e7d51d5f4158af6f4\"],\"status\":\"normal\",\"updateTime\":\"2015-04-27T02:05:14.533Z\",\"createTime\":\"2015-04-27T02:05:14.533Z\",\"gender\":\"female\",\"email\":\"shou@shou.com\"}]},\"status\":1}";
		return str;
	}
	
	/**
	 * 返回json数据
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/listtest", method=RequestMethod.GET)
	public List<SystemUser> list(){
		SystemUser systemUser = new SystemUser();
		List<SystemUser> list = this.systemUserService.getSystemUserList(systemUser);
		return list;
	}
	
	/**
	 * 返回json数据
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(){
		String str = "{\"doc\":{\"sEcho\":\"1\",\"iTotalRecords\":5,\"iTotalDisplayRecords\":5,\"iDisplayStart\":\"0\",\"aaData\":[{\"_id\":\"55348ffb1fadb4a4227f1853\",\"username\":\"szyadmin\",\"password\":\"kJN57DtV0Y+saUOBd8NCzA==\",\"userGroupArr\":[],\"__v\":0,\"components\":[],\"urls\":[],\"menus\":[],\"usergroupArr\":[],\"deptArr\":[],\"roleArr\":[\"553ee0a3cbb9da902f6532d3\"],\"status\":\"normal\",\"updateTime\":\"2015-04-20T05:34:51.692Z\",\"createTime\":\"2015-04-20T05:34:51.692Z\",\"gender\":\"male\",\"email\":\"hsh.0223.ok@163.com\"},{\"_id\":\"553891f5ea26bd980e464a20\",\"username\":\"admin\",\"password\":\"lueSGJZetyySpUndWjMBEg==\",\"birthProvince\":\"sh\",\"userGroupArr\":[],\"__v\":0,\"token\":\"2bfBSR8FZWa3h0rSN86gUA==\",\"components\":[\"component_smartcloudServer\",\"component_smartcloudServeruser\",\"component_smartcloudServeruserlist\",\"component_smartcloudServeruserlistqueryUser\"],\"urls\":[],\"menus\":[\"menu_smartcloudServer\",\"menu_smartcloudServerorg\",\"menu_smartcloudServeruser.list\",\"menu_smartcloudServermenu\",\"menu_smartcloudServermenu.list\"],\"usergroupArr\":[],\"deptArr\":[],\"roleArr\":[\"553ee0a3cbb9da902f6532d3\"],\"status\":\"normal\",\"updateTime\":\"2015-04-23T06:32:21.304Z\",\"createTime\":\"2015-04-23T06:32:21.304Z\",\"gender\":\"male\",\"email\":\"admin@163.com\"},{\"_id\":\"55389299ea26bd980e464a21\",\"username\":\"hsh0756232\",\"password\":\"lueSGJZetyySpUndWjMBEg==\",\"birthProvince\":\"sh\",\"userGroupArr\":[],\"__v\":0,\"components\":[],\"urls\":[\"url_smartcloudServer\",\"url_smartcloudServerbasic\",\"url_smartcloudServerbasicuser\",\"url_smartcloudServerbasicuserpagination\"],\"menus\":[],\"usergroupArr\":[\"555ee19cc0b42a002342e8a0\"],\"deptArr\":[],\"roleArr\":[\"553ee7a5ffa1d4702ec339b9\",\"5548162e7d51d5f4158af6f4\"],\"status\":\"normal\",\"updateTime\":\"2015-04-23T06:35:05.039Z\",\"createTime\":\"2015-04-23T06:35:05.039Z\",\"gender\":\"male\",\"email\":\"hsh.0223.ok@163.com\"},{\"_id\":\"553d9909b490fc5c23c60ca6\",\"username\":\"Test\",\"password\":\"lueSGJZetyySpUndWjMBEg==\",\"birthProvince\":\"zj\",\"__v\":0,\"components\":[],\"urls\":[\"url_smartcloudServer\",\"url_smartcloudServerbasic\",\"url_smartcloudServerbasicuser\",\"url_smartcloudServerbasicuserpagination\"],\"menus\":[\"menu_smartcloudServer\",\"menu_smartcloudServerorg\",\"menu_smartcloudServeruser.list\"],\"usergroupArr\":[],\"deptArr\":[],\"roleArr\":[\"553ee0a3cbb9da902f6532d3\"],\"status\":\"normal\",\"updateTime\":\"2015-04-27T02:03:53.668Z\",\"createTime\":\"2015-04-27T02:03:53.668Z\",\"gender\":\"male\",\"email\":\"Test@Test.com\"},{\"_id\":\"553d995ab490fc5c23c60ca7\",\"username\":\"shou\",\"password\":\"lueSGJZetyySpUndWjMBEg==\",\"birthProvince\":\"sh\",\"__v\":0,\"components\":[],\"urls\":[],\"menus\":[],\"usergroupArr\":[],\"deptArr\":[],\"roleArr\":[\"553ee7a5ffa1d4702ec339b9\",\"5548162e7d51d5f4158af6f4\"],\"status\":\"normal\",\"updateTime\":\"2015-04-27T02:05:14.533Z\",\"createTime\":\"2015-04-27T02:05:14.533Z\",\"gender\":\"female\",\"email\":\"shou@shou.com\"}]},\"status\":1}";
		return str;
	}
	
	/**
	 * 返回json数据
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/get", method=RequestMethod.GET)
	public String get(){
		String str = "{\"doc\":{\"sEcho\":\"1\",\"iTotalRecords\":5,\"iTotalDisplayRecords\":5,\"iDisplayStart\":\"0\",\"aaData\":[{\"_id\":\"55348ffb1fadb4a4227f1853\",\"username\":\"szyadmin\",\"password\":\"kJN57DtV0Y+saUOBd8NCzA==\",\"userGroupArr\":[],\"__v\":0,\"components\":[],\"urls\":[],\"menus\":[],\"usergroupArr\":[],\"deptArr\":[],\"roleArr\":[\"553ee0a3cbb9da902f6532d3\"],\"status\":\"normal\",\"updateTime\":\"2015-04-20T05:34:51.692Z\",\"createTime\":\"2015-04-20T05:34:51.692Z\",\"gender\":\"male\",\"email\":\"hsh.0223.ok@163.com\"},{\"_id\":\"553891f5ea26bd980e464a20\",\"username\":\"admin\",\"password\":\"lueSGJZetyySpUndWjMBEg==\",\"birthProvince\":\"sh\",\"userGroupArr\":[],\"__v\":0,\"token\":\"2bfBSR8FZWa3h0rSN86gUA==\",\"components\":[\"component_smartcloudServer\",\"component_smartcloudServeruser\",\"component_smartcloudServeruserlist\",\"component_smartcloudServeruserlistqueryUser\"],\"urls\":[],\"menus\":[\"menu_smartcloudServer\",\"menu_smartcloudServerorg\",\"menu_smartcloudServeruser.list\",\"menu_smartcloudServermenu\",\"menu_smartcloudServermenu.list\"],\"usergroupArr\":[],\"deptArr\":[],\"roleArr\":[\"553ee0a3cbb9da902f6532d3\"],\"status\":\"normal\",\"updateTime\":\"2015-04-23T06:32:21.304Z\",\"createTime\":\"2015-04-23T06:32:21.304Z\",\"gender\":\"male\",\"email\":\"admin@163.com\"},{\"_id\":\"55389299ea26bd980e464a21\",\"username\":\"hsh0756232\",\"password\":\"lueSGJZetyySpUndWjMBEg==\",\"birthProvince\":\"sh\",\"userGroupArr\":[],\"__v\":0,\"components\":[],\"urls\":[\"url_smartcloudServer\",\"url_smartcloudServerbasic\",\"url_smartcloudServerbasicuser\",\"url_smartcloudServerbasicuserpagination\"],\"menus\":[],\"usergroupArr\":[\"555ee19cc0b42a002342e8a0\"],\"deptArr\":[],\"roleArr\":[\"553ee7a5ffa1d4702ec339b9\",\"5548162e7d51d5f4158af6f4\"],\"status\":\"normal\",\"updateTime\":\"2015-04-23T06:35:05.039Z\",\"createTime\":\"2015-04-23T06:35:05.039Z\",\"gender\":\"male\",\"email\":\"hsh.0223.ok@163.com\"},{\"_id\":\"553d9909b490fc5c23c60ca6\",\"username\":\"Test\",\"password\":\"lueSGJZetyySpUndWjMBEg==\",\"birthProvince\":\"zj\",\"__v\":0,\"components\":[],\"urls\":[\"url_smartcloudServer\",\"url_smartcloudServerbasic\",\"url_smartcloudServerbasicuser\",\"url_smartcloudServerbasicuserpagination\"],\"menus\":[\"menu_smartcloudServer\",\"menu_smartcloudServerorg\",\"menu_smartcloudServeruser.list\"],\"usergroupArr\":[],\"deptArr\":[],\"roleArr\":[\"553ee0a3cbb9da902f6532d3\"],\"status\":\"normal\",\"updateTime\":\"2015-04-27T02:03:53.668Z\",\"createTime\":\"2015-04-27T02:03:53.668Z\",\"gender\":\"male\",\"email\":\"Test@Test.com\"},{\"_id\":\"553d995ab490fc5c23c60ca7\",\"username\":\"shou\",\"password\":\"lueSGJZetyySpUndWjMBEg==\",\"birthProvince\":\"sh\",\"__v\":0,\"components\":[],\"urls\":[],\"menus\":[],\"usergroupArr\":[],\"deptArr\":[],\"roleArr\":[\"553ee7a5ffa1d4702ec339b9\",\"5548162e7d51d5f4158af6f4\"],\"status\":\"normal\",\"updateTime\":\"2015-04-27T02:05:14.533Z\",\"createTime\":\"2015-04-27T02:05:14.533Z\",\"gender\":\"female\",\"email\":\"shou@shou.com\"}]},\"status\":1}";
		return str;
	}
	
	
}
