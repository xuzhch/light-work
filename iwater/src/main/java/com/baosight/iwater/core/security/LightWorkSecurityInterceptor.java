package com.baosight.iwater.core.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
/**
 * 核心的InterceptorStatusToken token = super.beforeInvocation(fi);
 * 会调用我们定义的accessDecisionManager:decide(Object object)和securityMetadataSource:getAttributes(Object object)方法。  
 * 还需要提供authenticationManager。
 * 自己实现的过滤用户请求类
 * @author chaos
 * @email xuzhengchao@baosight.com
 * @date 2016年12月22日上午8:47:11
 */

public class LightWorkSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {
	private static Logger logger = Logger.getLogger(LightWorkSecurityInterceptor.class);
	//securityMetadataSource与spring-security.xml里定义的securityMetadataSource对应，
	//其他的两个组件，已经在AbstractSecurityInterceptor定义，通过类型自动进行装配

	private LightWorkSecurityMetadataSource securityMetadataSource;
	
	
	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		FilterInvocation fi = new FilterInvocation(request, response, chain);
		invoke(fi);
	}
	
	private void invoke(FilterInvocation fi) throws IOException, ServletException {
		//fi里面有一个被拦截的url  
        //里面调用InvocationSecurityMetadataSource的getAttributes(Object object)这个方法获取url对应的所有权限，如角色、用户组 
        //再调用AccessDecisionManager的decide方法来校验用户的权限是否足够，用户信息中包含该用户被分配的角色、用户组等信息 
		String requestUrl = fi.getRequestUrl();
		logger.info("资源访问权限验证开始, url为:'"+requestUrl+"'");
		InterceptorStatusToken token = super.beforeInvocation(fi);
		
		//执行下一个拦截器
		try {
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
		} finally {
			super.afterInvocation(token, null);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
	}
	
	public void destroy() {
		
	}

	@Override
	public Class<? extends Object> getSecureObjectClass() {
		//下面的MyAccessDecisionManager的supports方面必须放回true,否则会提醒类型错误
		return FilterInvocation.class;
	}
	
	public void setSecurityMetadataSource(LightWorkSecurityMetadataSource securityMetadataSource) {
		this.securityMetadataSource = securityMetadataSource;
	}
}