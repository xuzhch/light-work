package org.zsl.testmybatis;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.AntPathMatcher;

import com.alibaba.fastjson.JSON;
import com.baosight.iwater.system.user.dao.SystemUserMapper;
import com.baosight.iwater.system.user.dao.UserMapper;
import com.baosight.iwater.system.user.pojo.SystemUser;
import com.baosight.iwater.system.user.pojo.User;
import com.baosight.iwater.system.user.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class) // 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })

public class TestMyBatis {
	private static Logger logger = Logger.getLogger(TestMyBatis.class);
	// private ApplicationContext ac = null;
	@Resource
	private IUserService userService = null;
	
	@Resource
	private SystemUserMapper userDao;

	// @Before
	// public void before() {
	// ac = new ClassPathXmlApplicationContext("applicationContext.xml");
	// userService = (IUserService) ac.getBean("userService");
	// }

	@Test
	public void test1() {
		logger.info(Thread.currentThread().getContextClassLoader().getResource(""));
		User user = userService.getUserById(1);
		// System.out.println(user.getUserName());
		// logger.info("值："+user.getUserName());

		logger.info(JSON.toJSONString(user));
	}
	

}
