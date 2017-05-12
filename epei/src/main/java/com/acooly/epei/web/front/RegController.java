package com.acooly.epei.web.front;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.epei.common.Constants;
import com.acooly.epei.common.security.SecurityAccessControlFilter;
import com.acooly.epei.domain.Advertisement;
import com.acooly.epei.domain.Customer;
import com.acooly.epei.domain.RegOriginEnum;
import com.acooly.epei.service.AdvertisementService;
import com.acooly.epei.service.CustomerService;
import com.acooly.epei.util.CodeUtils;
import com.acooly.epei.util.HttpTool;
import com.acooly.epei.util.LoginUserUtils;
import com.acooly.epei.util.SHA1;
import com.acooly.module.security.shiro.exception.InvaildCaptchaException;
import com.acooly.module.sms.ShortMessageSendService;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;

import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 修订记录：
 * liubin 2015-11-09 16:19 创建
 */
@Controller
public class RegController {
	private Logger logger = LoggerFactory.getLogger(RegController.class);

	/** 界面请求的Input-form表单名称 */
	public String captchaInputName = "captcha";

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ShortMessageSendService shortMessageSendService;

	/** Captcha验证服务 */
	@Autowired
	private ImageCaptchaService imageCaptchaService;
	
	@Autowired
	private AdvertisementService advertisementService;
	
	@RequestMapping("index")
	public String index(HttpServletRequest request, Model model){
		if(request.getSession().getAttribute(LoginUserUtils.CURRENT_CUSTOMER_ATTR)!=null){
			return "/front/mobile/index";
		}else{
			String openid = HttpTool.getOpenID(request);
			if(openid!=null){
				Customer customer = customerService.getCustomerByOpenid(openid);
				if(customer!=null && customer.getDeleted() == 0){
					request.getSession().setAttribute(LoginUserUtils.CURRENT_CUSTOMER_ATTR, customer);
					return "/front/mobile/index";
				}else{
					model.addAttribute("errorMsg", "请先登录");
					return "/front/mobile/login";
				}
			}else{
				model.addAttribute("errorMsg", "请先登录");
				return "/front/mobile/login";
			}
			
		}
	}
	
	@RequestMapping("queryAdvertisement")
	@ResponseBody
	public List<Advertisement> queryAdvertisement(int deleted){
		return advertisementService.findByDeletedReorderByPriority();
	}
	
	@RequestMapping("mobileValidCode")
	@ResponseBody
	public JsonResult getMobileValidCode(HttpServletRequest request,String mobile,Integer type){
		JsonResult result = new JsonResult();
		try{
			Assert.hasText(mobile,"手机号码不能为空");
//			checkCaptcha(request);	验证图片验证码  因为没有图片验证 所以不需要
			Customer customer = customerService.findCustomerByUsername(mobile);
			//如果用户已经存在 不发送短信 直接返回
			if(type == null){
				if(customer != null){
					if(customer.getDeleted() == 0){
						result.setCode("USER_EXISTS");
						result.setSuccess(false);
						result.setMessage("用户名:"+mobile+"已存在,您可以找回密码.");
					}
					else{
						result.setSuccess(false);
						result.setMessage("账户已被锁定,请拨打:"+Constants.SERVICE_TEL +" 联系客服人员处理.");
					}
					return result;
				}
			}
			//找回密码
			else{
				if(customer == null){
					result.setSuccess(false);
					result.setMessage("用户名不存在,请确认用户名无误.");
					return result;
				}
			}

			Map<String,Object> existAttrs = (Map<String, Object>) request.getSession().getAttribute(mobile);
			if(null != existAttrs){
				Long oldExpiredTime = (Long) existAttrs.get(Constants.EXPIRED_TIME);
				if(new Date().getTime() < oldExpiredTime){
					result.setSuccess(true);
					result.setMessage("已发送");
					return  result;
				}
			}
			String mobileValidCode = CodeUtils.getRandCode(6);
			Long expiredDateMilis = new Date().getTime()+(60*1000*2);

			Map<String,Object> attrs  = new HashMap<>();
			attrs.put(Constants.MOBILE_VALID_CODE, mobileValidCode);
			attrs.put(Constants.EXPIRED_TIME,expiredDateMilis);

			request.getSession().setAttribute(mobile,attrs);

			Map<String,Object> smsData = new HashMap<>();
			smsData.put("captcha",mobileValidCode);
			smsData.put("userName",mobile);
			if(null != type){
				shortMessageSendService.sendByTemplateAsync(mobile,Constants.SMS_RESET_PASSWORD_TMP,smsData);
			}
			else{
				shortMessageSendService.sendByTemplateAsync(mobile,Constants.SMS_REG_TMP,smsData);
			}

			result.setSuccess(true);
			result.setMessage("发送成功");

		}
		catch (InvaildCaptchaException ie){
			logger.error("发送注册验证码出错,",ie);
			result.setMessage("验证码输入错误,请重新输入");
			result.setCode("CAPTCHA");
			result.setSuccess(false);
		}
		catch ( Exception e){
			logger.error("发送注册验证码出错,",e);
			result.setMessage("短信发送失败");
			result.setSuccess(false);
		}
		return  result;
	}


	@RequestMapping("register")
	@ResponseBody
	public JsonResult register(HttpServletRequest request,Customer customer){
		JsonResult result =  new JsonResult();
		try{
			checkCaptcha(request);
			//checkMobileValidCode(request);
			//获取设备类型
			UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("user-agent"));
			DeviceType deviceType = userAgent.getOperatingSystem().getDeviceType();

			if(DeviceType.COMPUTER.equals(deviceType)){
				customer.setRegOrigin(RegOriginEnum.PC.name());
			}
			else{
				customer.setRegOrigin(RegOriginEnum.WECHAT.name());
			}

			Customer cus = customerService.findCustomerByUsername(customer.getReferenceMobile());
			if(customer.getReferenceMobile().trim().equals("")){
				customer.setUserName(customer.getMobile().trim());
				customer.setMobile(customer.getUserName());
				customer.setCustomerHosId(Long.valueOf(-1));
				customerService.createCustomer(customer);
				String openid = HttpTool.getOpenID(request);
				customer.setOpenid(openid);
				result.setSuccess(true);
				result.setMessage("恭喜你,注册成功.");
			} else {
				if(cus != null){
					customer.setUserName(customer.getMobile().trim());
					customer.setMobile(customer.getUserName());
					customer.setReferenceMobile(customer.getReferenceMobile());
					customer.setCustomerHosId(Long.valueOf(-1));
					customerService.createCustomer(customer);
					result.setSuccess(true);
					result.setMessage("恭喜你,注册成功.");
				} else {
					result.setSuccess(false);
					result.setMessage("推荐人不存在或输入有误！");
				}
			}	
		}
		catch (InvaildCaptchaException ie){
			logger.error("注册会员出错,",ie);
			result.setMessage("验证码输入错误,请重新输入");
			result.setCode("CAPTCHA");
			result.setSuccess(false);
		}
		catch (IllegalArgumentException | BusinessException be){
			logger.error("注册会员出错,",be);
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		}
		catch ( Exception e){
			logger.error("注册会员出错,",e);
			result.setSuccess(false);
			result.setMessage("系统出错了.");
		}
		return result;
	}
	
	@RequestMapping("login")
	public String login(HttpServletRequest request,String mobile,String password,Model model){
		logger.info("用户["+maskMobile(mobile)+"]正在登录......");
		String rtnView = "/front/mobile/login";
		try{
			Customer customer = customerService.findCustomerByUsername(mobile);
			if(customer != null){
				if(customer.getDeleted() == 0){
					if(customer.getVipActivated()!=null && customer.getVipActivated()==1){
						logger.error("VIP账号未激活！");
						model.addAttribute("errorMsg","为了您的账号安全，VIP会员首次登录需要重置密码");
						return "/front/mobile/forgotpassword";
					}
					if(customerService.validatePassword(customer,password)){
						String openid = HttpTool.getOpenID(request);
						/*if(openid==null){
							model.addAttribute("errorMsg","登录超时，请重新登录");
							return rtnView;
						}else{
							customer.setOpenid(openid);
						}*/
						customer.setOpenid(openid);
						customerService.update(customer);
						request.getSession().setAttribute(LoginUserUtils.CURRENT_CUSTOMER_ATTR,customer);
						String destUrl = (String) request.getSession().getAttribute(SecurityAccessControlFilter.DEST_URL);
						if(StringUtils.isNotBlank(destUrl) && customer.getCustomerType().equals("0")){
							rtnView  = 	"redirect:"+destUrl;
							request.getSession().setAttribute(SecurityAccessControlFilter.DEST_URL,null);
						}else if(StringUtils.isNotBlank(destUrl) && customer.getCustomerType().equals("1")){
							rtnView = "/front/mobile/index";
						}
						else{
							rtnView  = "/front/mobile/index";
						}
						logger.info("用户["+maskMobile(mobile)+"]登录成功.");
					}
					else{
						model.addAttribute("errorMsg","账号或密码错误");
					}
				}
				else{
					logger.error("当前登录账号["+mobile+"]已经被管理员从后台锁定.");
					model.addAttribute("errorMsg","账户已被锁定,请拨打:"+Constants.SERVICE_TEL +" 联系客服人员处理.");
				}
			}
			else{
				logger.error("用户["+maskMobile(mobile)+"]不存在.");
                model.addAttribute("errorMsg","账号或密码错误");
			}
		}
		catch (Exception e){
			logger.error("用户登录发生异常,",e);
            model.addAttribute("errorMsg","系统出错,请稍后再试.");
		}
		return rtnView;
	}

	@RequestMapping("logout")
	public String logout(HttpServletRequest request){
		try{
			request.getSession().invalidate();
		}
		catch (Exception e){
			logger.error("用户退出发生异常,",e);
		}
		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("user-agent"));
		DeviceType deviceType = userAgent.getOperatingSystem().getDeviceType();

		if(DeviceType.COMPUTER.equals(deviceType)){
			return "/front/mobile/index";
		}
		else{
			return "/front/mobile/index";
		}
	}

    @RequestMapping("resetPassword")
    @ResponseBody
    public JsonResult resetPassword(HttpServletRequest request,Customer customer){
        JsonResult result =  new JsonResult();
        try{
            checkMobileValidCode(request);
            String mobile = request.getParameter("mobile");
            String password = request.getParameter("password");
            Customer persistedCustomer = customerService.findCustomerByUsername(mobile);
			if(persistedCustomer != null){
				if(persistedCustomer.getVipActivated()!=null && persistedCustomer.getVipActivated()==1)persistedCustomer.setVipActivated(2);
				customerService.changePassword(persistedCustomer,password);
				result.setSuccess(true);
				result.setMessage("恭喜你,密码修改成功.");
			}
			else{
				logger.error("根据userName:"+mobile+"获取不到用户信息.");
				result.setSuccess(false);
				result.setMessage("用户名不存在,请确认用户名无误.");
			}
        }
        catch (IllegalArgumentException | BusinessException be){
            logger.error("修改密码出错,",be);
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        }
        catch ( Exception e){
            logger.error("修改密码出错,",e);
            result.setSuccess(false);
            result.setMessage("系统出错了.");
        }
        return result;
    }

    private void checkMobileValidCode(HttpServletRequest request){
		String mobile = request.getParameter("mobile");
		String clientMobileCode = request.getParameter("mobileValidCode");
		Assert.hasText(mobile,"手机号码不能为空");
		Assert.hasText(clientMobileCode,"手机验证码不能为空");
		Map<String,Object> attrs  = (Map<String, Object>) request.getSession().getAttribute(mobile);

		if(attrs == null){
			throw  new BusinessException("手机验证码失效,请重新获取验证码.");
		}

//		String mobileValidCode = (String) attrs.get(Constants.MOBILE_VALID_CODE);
		Long expiredTimeMillis = (Long) attrs.get(Constants.EXPIRED_TIME);

		if(!(clientMobileCode.equals(attrs.get(Constants.MOBILE_VALID_CODE)) && new Date().getTime() < expiredTimeMillis)){
			throw  new BusinessException("手机验证码失效,请重新获取验证码.");
		}

		request.getSession().setAttribute(mobile,null);
	}

	/**
	 * 验证图片验证码
	 *
	 * @param request
	 */
	protected void checkCaptcha(ServletRequest request) {
		String requestCaptcha = request.getParameter(captchaInputName);
		String captchaId = SecurityUtils.getSubject().getSession().getId().toString();
		Boolean captchaPassed = Boolean.TRUE;
		try {
			captchaPassed = imageCaptchaService.validateResponseForID(captchaId, requestCaptcha);
		} catch (CaptchaServiceException e) {
			logger.debug("Validate captcha, Exception -- > InvaildCaptchaException");
			throw new InvaildCaptchaException("captchaId format invaild.");
		}
		logger.debug("Validate captcha, captchaPassed -- > " + captchaPassed);
		if (!captchaPassed) {
			throw new InvaildCaptchaException("captcha invaild.");
		}
	}

	private String maskMobile(String mobile){
		if(StringUtils.isNotBlank(mobile)){
			return mobile.substring(0,3)+"******"+mobile.substring(mobile.length()-4);
		}
		return mobile;
	}

	@RequestMapping(value="basicConf",method=RequestMethod.GET)
	public void basicConf(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String signature = request.getParameter("signature");
		String echostr = request.getParameter("echostr");
		String token = "Qimity";
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String sign = SHA1.getSHA1(token, timestamp, nonce);
		response.setCharacterEncoding("UTF-8");
		if(sign!=null && sign.equals(signature)){
			response.getWriter().print(echostr);
		}else{
			response.getWriter().print(sign);
		}
	}
	
}
