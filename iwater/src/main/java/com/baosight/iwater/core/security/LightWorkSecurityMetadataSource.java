package com.baosight.iwater.core.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

/**
 * 加载资源与权限的对应关系
 * 
 * @author chaos
 * @email xuzhengchao@baosight.com
 * @date 2016年12月22日上午9:19:55
 */
@Service
public class LightWorkSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	private static Logger logger = Logger.getLogger(LightWorkSecurityMetadataSource.class);
	
	private static Map<String, Collection<ConfigAttribute>> map = new HashMap<String, Collection<ConfigAttribute>>();
	private static Map<String, String> urlResource = new HashMap<String, String>();
	private AntPathMatcher matcher = null;

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		//TODO 确认此处功能
		Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
		return atts;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

	/*
	 * 初始化用户权限，为了简便操作没有从数据库获取 实际操作可以从数据库中获取所有资源路径url所对应的权限
	 */
	private void loadAllResourceData() {
		this.setMatcher(new AntPathMatcher());// 用来匹配访问资源路径
		Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
		atts.add(new SecurityConfig("ROLE_ADMIN"));
		map.put("/user/showUser3*", atts);

		Collection<ConfigAttribute> attsno = new ArrayList<ConfigAttribute>();
		attsno.add(new SecurityConfig("ROLE_ADMIN"));
		attsno.add(new SecurityConfig("ROLE_USER"));
		map.put("/user/showUser2/**", attsno);

		Collection<ConfigAttribute> attsno1 = new ArrayList<ConfigAttribute>();
		attsno1.add(new SecurityConfig("ROLE_ADMIN"));
		attsno1.add(new SecurityConfig("ROLE_USER"));
		map.put("/user/showUser1/**", attsno1);
		
		Collection<ConfigAttribute> attsno2 = new ArrayList<ConfigAttribute>();
		attsno2.add(new SecurityConfig("ROLE_ADMIN"));
		attsno2.add(new SecurityConfig("ROLE_USER"));
		map.put("/seedstartermng", attsno2);
	}

	// 返回所请求资源所需要的权限
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {		
		if (map == null || map.isEmpty()) {
			this.loadAllResourceData();
		}
		
		String requestUrl = ((FilterInvocation) object).getRequestUrl();			
		
		// 循环资源路径，当访问的Url和资源路径url匹配时，返回该Url所需要的权限
		logger.debug("从注册资源中获取资源'"+requestUrl+"'具有的权限...");
		for (Iterator<Map.Entry<String, Collection<ConfigAttribute>>> iter = map.entrySet().iterator(); iter
				.hasNext();) {
			Map.Entry<String, Collection<ConfigAttribute>> entry = iter.next();
			String url = entry.getKey();
			boolean isMatch = matcher.match(url, requestUrl);
			logger.debug("'"+requestUrl+"' match '"+url+"'，"+isMatch);
			if (isMatch) {
				return map.get(url);
			}
		}

		// 未注册的资源，默认设置为需要admin权限
		logger.debug("从配置文件中获取资源'"+requestUrl+"'具有的权限...");
		for (Iterator<Map.Entry<String, String>> iter = urlResource.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<String, String> entry = iter.next();
			String url = entry.getKey();
			String role = entry.getValue();
			boolean isMatch = matcher.match(url, requestUrl);
			logger.debug("'"+requestUrl+"' match '"+url+"'，"+isMatch);

			if (isMatch) {
				if ("IS_AUTHENTICATED_ANONYMOUSLY".equalsIgnoreCase(role)) {
					// 不受权限控制
					logger.debug("资源'"+requestUrl+"'无需验证授权，可匿名直接访问...");
					return null;
				} else {
					// 有多个权限的用“,”隔开，如ROLE_USER,ROLE_ADMIN
					Collection<ConfigAttribute> attsno = new ArrayList<ConfigAttribute>();
					String[] roles = role.split("[,]");
					for (String r : roles) {
						attsno.add(new SecurityConfig(r));
					}
					return attsno;
				}
			}
		}

		// 未注册的资源默认设置为需要admin权限
		logger.warn("待获取资源权限的url'"+requestUrl+"'无匹配项。解决方法：1、资源未注册，请注册资源，2、资源已注册，但匹配有问题，请重新注册此资源！3、ADMIN权限可临时访问！");
		Collection<ConfigAttribute> attsno = new ArrayList<ConfigAttribute>();
		attsno.add(new SecurityConfig("ROLE_ADMIN"));
		return attsno;
	}

	public AntPathMatcher getMatcher() {
		return matcher;
	}

	public void setMatcher(AntPathMatcher matcher) {
		this.matcher = matcher;
	}

	public static Map<String, String> getUrlResource() {
		return urlResource;
	}

	public static void setUrlResource(Map<String, String> urlResource) {
		LightWorkSecurityMetadataSource.urlResource = urlResource;
	}

}