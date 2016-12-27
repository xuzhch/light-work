package com.baosight.iwater.system.user.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baosight.iwater.system.user.dao.UserMapper;
import com.baosight.iwater.system.user.pojo.User;
import com.baosight.iwater.system.user.service.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService {
	@Resource
	private UserMapper userDao;
	public User getUserById(int userId) {
		// TODO Auto-generated method stub  
		return this.userDao.selectByPrimaryKey(userId);
	}

}
