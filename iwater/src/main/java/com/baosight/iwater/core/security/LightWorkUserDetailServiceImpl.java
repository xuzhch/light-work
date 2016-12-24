package com.baosight.iwater.core.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * User userdetail该类实现 UserDetails 接口，该类在验证成功后会被保存在当前回话的principal对象中
 * 
 * 获得对象的方式：
 * WebUserDetails webUserDetails = (WebUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 * 
 * 或在JSP中：
 * <sec:authentication property="principal.username"/>
 * 
 * 如果需要包括用户的其他属性，可以实现 UserDetails 接口中增加相应属性即可
 * 权限验证类
 * @author chaos
 * @email xuzhengchao@baosight.com
 * @date 2016年12月22日上午9:20:13
 */
@Service
public class LightWorkUserDetailServiceImpl implements UserDetailsService {	
	private static Logger logger = Logger.getLogger(LightWorkUserDetailServiceImpl.class);
	
//	@Autowired
//	private UserDao userDao;
//	@Autowired
//	private ResourcesDao resourcesDao ;
	// 登录验证
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.debug("获取用户'"+username+"'详细信息");
		
		List<UserDetails> users = loadUsersByUsername(username);

		if (users.size() == 0) {
			System.err.println("Query returned no results for user '" + username + "'");

			throw new UsernameNotFoundException("Username '" + username + "' not found");
		}

		UserDetails user = users.get(0); // contains no GrantedAuthority[]

		//获得用户的权限列表，包括角色，用户组列表等
		Set<GrantedAuthority> dbAuthsSet = new HashSet<GrantedAuthority>();
        dbAuthsSet.addAll(loadUserAuthorities(user.getUsername()));
		List<GrantedAuthority> dbAuths = new ArrayList<GrantedAuthority>(dbAuthsSet);
		if (dbAuths.size() == 0) {
			logger.debug("User '" + username
					+ "' has no authorities and will be treated as 'not found'");

			throw new UsernameNotFoundException("Username '" + username + "' not found");
		}

		
		return createUserDetails(username, user, dbAuths);		
	}
	
	/**
	 * Executes the SQL <tt>usersByUsernameQuery</tt> and returns a list of UserDetails
	 * objects. There should normally only be one matching user.
	 */
	protected List<UserDetails> loadUsersByUsername(String username) {
		User userdetail = null;
		if("user".equals(username)){
			userdetail = new User(		
					"user", 
					"$2a$10$vqYy/b.A/ASypttemFVLoeUX50vNiQa8BWOgXeKIXyKFdLM0HaQLu",
					true, 
					true, 
					true,
					true, 
					AuthorityUtils.NO_AUTHORITIES	//用户的权限
				);
		}else if("admin".equals(username)){
			userdetail = new User(		
					"admin", 
					"$2a$10$vqYy/b.A/ASypttemFVLoeUX50vNiQa8BWOgXeKIXyKFdLM0HaQLu",
					true, 
					true, 
					true,
					true, 
					AuthorityUtils.NO_AUTHORITIES	//用户的权限
				);
		}
			
		List<UserDetails> list = new ArrayList<UserDetails>();
		list.add(userdetail);
		return list;
	}


	/**
	 * 取得用户的权限
	 * @param username
	 * @return
	 */
	private Set<GrantedAuthority> loadUserAuthorities(String username) {
		//List<Resources> resources = resourcesDao.getUserResources(String.valueOf(user.getUserId()));
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		if("user".equals(username)){
			authSet.add(new SimpleGrantedAuthority("ROLE_USER"));
		}else if("admin".equals(username)){
			authSet.add(new SimpleGrantedAuthority("ROLE_USER"));
			authSet.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		
//		for (Resources res : resources) {
//			// TODO:ZZQ 用户可以访问的资源名称（或者说用户所拥有的权限） 注意：必须"ROLE_"开头
//			// 关联代码：applicationContext-security.xml
//			// 关联代码：com.huaxin.security.MySecurityMetadataSource#loadResourceDefine
//			authSet.add(new SimpleGrantedAuthority("ROLE_" + res.getResKey()));
//		}
		return authSet;
	}
	
	/**
	 * Can be overridden to customize the creation of the final UserDetailsObject which is
	 * returned by the <tt>loadUserByUsername</tt> method.
	 *
	 * @param username the name originally passed to loadUserByUsername
	 * @param userFromUserQuery the object returned from the execution of the
	 * @param combinedAuthorities the combined array of authorities from all the authority
	 * loading queries.
	 * @return the final UserDetails which should be used in the system.
	 */
	protected UserDetails createUserDetails(String username,
			UserDetails userFromUserQuery, List<GrantedAuthority> combinedAuthorities) {

		return new User(userFromUserQuery.getUsername(), userFromUserQuery.getPassword(),
				userFromUserQuery.isEnabled(), true, true, true, combinedAuthorities);
	}


}