package com.baosight.iwater.core.security;

import java.util.Collection;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Service;

/**
 * 自己实现的过滤用户请求类，实现在管理界面上为资源分配权限，更加灵活，而不是在配置文件中定义资源权限
 * 本类是自定义的资源权限判定，资源需要的权限和登录用户具有的权限，有一项相同，即判定有权限访问。
 * 
 * @author chaos
 * @email xuzhengchao@baosight.com
 * @date 2016年12月22日上午9:19:14
 */
@Service
public class LightWorkAccessDecisionManager implements AccessDecisionManager {
	private static Logger logger = Logger.getLogger(LightWorkAccessDecisionManager.class);

	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		// 参数说明：
		// authentication是用户登录的时候在LightWorkAuthenticationFilter和LightWorkUserDetailServiceImpl通过loadUserAuthorities()中获取的用户信息，包含该用户被分配的角色、用户组等信息
		// object就是url
		// configAttributes是在LightWorkSecurityMetadataSource中通过getAttributes()这个方法获取url对应的所有权限，如角色、用户组
		String requestUrl = ((FilterInvocation) object).getRequestUrl();
		
		logger.debug("资源'"+requestUrl+"'访问权限判定运行...");
		if (configAttributes == null) {
			return;
		}
		// 所请求的资源拥有的权限(一个资源对多个权限)
		Iterator<ConfigAttribute> iterator = configAttributes.iterator();
		while (iterator.hasNext()) {
			ConfigAttribute configAttribute = iterator.next();
			// 访问所请求资源所需要的权限
			String needPermission = configAttribute.getAttribute();
			// 用户所拥有的权限authentication
			for (GrantedAuthority ga : authentication.getAuthorities()) {
				if (needPermission.equals(ga.getAuthority())) {
					logger.debug("资源'"+requestUrl+"'访问权限判定通过，开始处理该资源访问...");
					return;
				}
			}
		}
		// 没有权限
		logger.debug("资源'"+requestUrl+"'访问权限判定未通过，跳转到其他页面进行访问...");
		throw new AccessDeniedException(" 资源'"+requestUrl+"'没有权限访问！ ");
	}

	public boolean supports(ConfigAttribute attribute) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}

}