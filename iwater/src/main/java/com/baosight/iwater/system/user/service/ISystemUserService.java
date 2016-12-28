package com.baosight.iwater.system.user.service;

import java.util.List;

import com.baosight.iwater.system.user.pojo.SystemUser;

public interface ISystemUserService {
	
	public List<SystemUser> getSystemUserList(SystemUser systemUser);
	
}
