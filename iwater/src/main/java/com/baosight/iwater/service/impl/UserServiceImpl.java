package com.baosight.iwater.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baosight.iwater.dao.UserMapper;
import com.baosight.iwater.pojo.User;
import com.baosight.iwater.service.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService {
	@Resource
	private UserMapper userDao;
	public User getUserById(int userId) {
		// TODO Auto-generated method stub  
		return this.userDao.selectByPrimaryKey(userId);
	}

}
