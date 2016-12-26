package com.baosight.iwater.core.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

public class LightWorkAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	private static Logger logger = Logger.getLogger(LightWorkAuthenticationFilter.class);

	public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
	public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

	private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
	private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
	private boolean postOnly = false;
	
	/**
	 * 登录成功后跳转的地址
	 */
	private String successUrl = "/index.html";
	/**
	 * 登录失败后跳转的地址
	 */
	private String errorUrl = "/login.html";
	/**
	 * 过滤器拦截的登录验证地址
	 */
	private static String filterProcessesUrl = "/login";

	// ~ Constructors
	// ===================================================================================================

	public LightWorkAuthenticationFilter() {
		super(new AntPathRequestMatcher(filterProcessesUrl, "POST"));
	}
	
	/**
	 * 自定义表单参数的name属性，默认是 j_username 和 j_password
	 * 定义登录成功和失败的跳转地址
	 */
	public void init() {
		System.err.println("信息: 水利水务LightWork平台用户验证已启用...");
		// 验证成功，跳转的页面
		SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
		successHandler.setDefaultTargetUrl(successUrl);
		this.setAuthenticationSuccessHandler(successHandler);

		// 验证失败，跳转的页面
		SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();
		failureHandler.setDefaultFailureUrl(errorUrl);
		this.setAuthenticationFailureHandler(failureHandler);
	}

	// ~ Methods
	// ========================================================================================================

	/* 验证失败则抛出AuthenticationServiceException
	 * @see org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter#attemptAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {		
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		String username = obtainUsername(request);
		String password = obtainPassword(request);
		logger.debug("开始用户登录验证，username:"+username +", password:"+password);

		if (username == null) {
			username = "";
			throw new AuthenticationServiceException("用户名为空！ ");
		}

		if (password == null) {
			password = "";
		}

		username = username.trim();
		//“password”加密后为$2a$10$vqYy/b.A/ASypttemFVLoeUX50vNiQa8BWOgXeKIXyKFdLM0HaQLu

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);
		
		
		//此处调用getAuthenticationManager的authenticate方法验证用户名和密码，当supports方法返回true时执行authenticate方法
		Authentication auth = null;  
		HttpSession session = request.getSession(true); 		
        try {                
        	//进行用户名和密码验证
            auth = this.getAuthenticationManager().authenticate(authRequest);             
            if (auth == null || !auth.isAuthenticated()) {  //验证失败
            	session.setAttribute("__login_error", "__login_error");  
            } else  {  //验证成功
            	session.setAttribute("USER_INFO", username);  //TODO 此处可从数据库查询出用户信息放入session中              
            }  
            
        } catch (AuthenticationException e) {  
        	session.setAttribute("__login_error", "__login_error");  
            throw e;  
        }  

		return auth;
	}

	/**
	 * Enables subclasses to override the composition of the password, such as
	 * by including additional values and a separator.
	 * <p>
	 * This might be used for example if a postcode/zipcode was required in
	 * addition to the password. A delimiter such as a pipe (|) should be used
	 * to separate the password and extended value(s). The
	 * <code>AuthenticationDao</code> will need to generate the expected
	 * password in a corresponding manner.
	 * </p>
	 *
	 * @param request
	 *            so that request attributes can be retrieved
	 *
	 * @return the password that will be presented in the
	 *         <code>Authentication</code> request token to the
	 *         <code>AuthenticationManager</code>
	 */
	protected String obtainPassword(HttpServletRequest request) {
		return request.getParameter(passwordParameter);
	}

	/**
	 * Enables subclasses to override the composition of the username, such as
	 * by including additional values and a separator.
	 *
	 * @param request
	 *            so that request attributes can be retrieved
	 *
	 * @return the username that will be presented in the
	 *         <code>Authentication</code> request token to the
	 *         <code>AuthenticationManager</code>
	 */
	protected String obtainUsername(HttpServletRequest request) {
		return request.getParameter(usernameParameter);
	}

	/**
	 * Provided so that subclasses may configure what is put into the
	 * authentication request's details property.
	 *
	 * @param request
	 *            that an authentication request is being created for
	 * @param authRequest
	 *            the authentication request object that should have its details
	 *            set
	 */
	protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	/**
	 * Sets the parameter name which will be used to obtain the username from
	 * the login request.
	 *
	 * @param usernameParameter
	 *            the parameter name. Defaults to "username".
	 */
	public void setUsernameParameter(String usernameParameter) {
		Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
		this.usernameParameter = usernameParameter;
	}

	/**
	 * Sets the parameter name which will be used to obtain the password from
	 * the login request..
	 *
	 * @param passwordParameter
	 *            the parameter name. Defaults to "password".
	 */
	public void setPasswordParameter(String passwordParameter) {
		Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
		this.passwordParameter = passwordParameter;
	}

	/**
	 * Defines whether only HTTP POST requests will be allowed by this filter.
	 * If set to true, and an authentication request is received which is not a
	 * POST request, an exception will be raised immediately and authentication
	 * will not be attempted. The <tt>unsuccessfulAuthentication()</tt> method
	 * will be called as if handling a failed authentication.
	 * <p>
	 * Defaults to <tt>true</tt> but may be overridden by subclasses.
	 */
	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	public final String getUsernameParameter() {
		return usernameParameter;
	}

	public final String getPasswordParameter() {
		return passwordParameter;
	}
	
	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getErrorUrl() {
		return errorUrl;
	}

	public void setErrorUrl(String errorUrl) {
		this.errorUrl = errorUrl;
	}

	public static String getFilterProcessesUrl() {
		return filterProcessesUrl;
	}
}
