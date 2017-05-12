package com.acooly.epei.common.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.acooly.epei.domain.Customer;
import com.acooly.epei.service.CustomerService;
import com.acooly.epei.util.LoginUserUtils;
import com.acooly.epei.util.WechatSignUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.acooly.core.common.web.support.Servlets;
import org.springframework.web.context.support.WebApplicationContextUtils;

//次过滤器主要用来拦截需要用户登录才能进行操作的请求
public class SecurityAccessControlFilter implements Filter {

	private Logger logger = LoggerFactory.getLogger(SecurityAccessControlFilter.class);

	public static final String DEST_URL = "destUrl";



	private String sessionKey = "sessionKey";
	private String loginUrl = "/login";
	private List<String> exclusions = new ArrayList<String>();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String configSessionKey = filterConfig.getInitParameter("sessionKey");
		if (StringUtils.isNotBlank(configSessionKey)) {
			this.sessionKey = configSessionKey;
		}
		this.loginUrl = filterConfig.getInitParameter("loginUrl");
		String ignores = filterConfig.getInitParameter("exclusions");
		if (StringUtils.isNotBlank(ignores)) {
			String[] ignoreArray = ignores.split(",");
			for (int i = 0; i < ignoreArray.length; i++) {
				if (StringUtils.isNotBlank(ignoreArray[i])) {
					exclusions.add(ignoreArray[i]);
				}
			}
		}
		Collections.sort(exclusions);
		Collections.reverse(exclusions);
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		String requestUrl = Servlets.getRequestPath(request);
		PathMatcher pathMatcher = new AntPathMatcher();
		boolean isIgnore = false;
		if (!exclusions.isEmpty()) {
			for (String ignoreUrl : exclusions) {
				isIgnore = pathMatcher.match(ignoreUrl, requestUrl);
				if (isIgnore) {
					break;
				}
			}
		}
		//如果请求地址被此过滤器排除在外  或者  请求的用户已经登录   或者   通过微信自动登录成功   则将求情转交给下一个过滤器
		if (isIgnore || !requiresAuthentication(request, response) || wechatAuthenticated(request)) {
			chain.doFilter(request, response);
			return;
		}
		request.getSession().setAttribute(DEST_URL,requestUrl);
		String queryString = request.getQueryString()==null?"":request.getQueryString();
		response.addHeader("Referer", requestUrl);
		response.sendRedirect(loginUrl+"?needReferer=true&"+queryString);
	}

	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		return getSessionKey(request) == null;
	}

	protected Object getSessionKey(HttpServletRequest request) {
		return request.getSession().getAttribute(sessionKey);
	}

	@Override
	public void destroy() {

	}

	private boolean wechatAuthenticated(HttpServletRequest request){
		try{
			logger.info("Filter进入微信端认证处理......");
			String code = request.getParameter("code");
			String openid = "";
			if(org.apache.commons.lang.StringUtils.isNotBlank(code)){
				openid = (String) request.getSession().getAttribute(code);
				if(org.apache.commons.lang.StringUtils.isBlank(openid)){
					openid = WechatSignUtils.getOpenidByCodeFromWechat(code);
				}
				if(org.apache.commons.lang.StringUtils.isNotBlank(openid)){
					logger.info("Filter中获取用户openid成功,存入session中......");
					request.getSession().setAttribute(code,openid);
					CustomerService customerService = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext
							()).getBean
							(CustomerService.class);
					Customer customer = customerService.getCustomerByOpenid(openid);
					if(customer == null){
						logger.info("Filter根据code:"+code+"获取openid,进行微信端用户登录认证失败!");
						return  false;
					}
					else{
						logger.info("微信端用户登录认证成功!");
						request.getSession().setAttribute(LoginUserUtils.CURRENT_CUSTOMER_ATTR,customer);
						return  true;
					}
				}
				else{
					logger.warn("Filter中根据code:"+code+" 未获取到用户对应的openid.微信自动登录验证失败.");
					return  false;
				}
			}
			else{
				logger.warn("未获取到微信公众平台生成的code.请检查登录菜单url配置.");
				return  false;
			}
		}
		catch (Exception e){
			logger.error("微信端认证出错,错误信息:",e);
			return false;
		}

	}

}
