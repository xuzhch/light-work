package com.baosight.iwater.system.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baosight.iwater.system.user.dao.SystemUserMapper;
import com.baosight.iwater.system.user.pojo.SystemUser;
import com.baosight.iwater.system.user.service.ISystemUserService;

@Service("systemUserService")
public class SystemUserServiceImpl implements ISystemUserService{
	@Resource
	private SystemUserMapper systemUserDao;

	@Override
	public List<SystemUser> getSystemUserList(SystemUser systemUser) {
		// TODO Auto-generated method stub
		return this.getSystemUserList(systemUser);
	}

	

}
