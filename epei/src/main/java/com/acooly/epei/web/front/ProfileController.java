package com.acooly.epei.web.front;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.epei.domain.Customer;
import com.acooly.epei.service.CustomerService;
import com.acooly.epei.util.LoginUserUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * 修订记录：
 * liubin 2015-11-05 17:53 创建
 */
@Controller
@RequestMapping("profile")
public class ProfileController {

    private Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private CustomerService customerService;

	@RequestMapping("mycenter")
	public String mycenter(){
		return "/front/mobile/mycenter";
	}

	@RequestMapping("adminCenter")
	public String adminCenter(){
		return "/front/mobile/adminCenter";
	}

    @RequestMapping("myinfo")
    public String myInfo(Model model){
        model.addAttribute("entity",LoginUserUtils.getCustomer());
        return "/front/mobile/myinfo";
    }
    
    @RequestMapping("admininfo")
    public String adminInfo(Model model){
        model.addAttribute("entity",LoginUserUtils.getCustomer());
        return "/front/mobile/admininfo";
    }

	@RequestMapping(value = "changePassword",method = RequestMethod.GET)
	public String changePassword(Model model){
		model.addAttribute("entity",LoginUserUtils.getCustomer());
		return "/front/mobile/changePassword";
	}
	
	@RequestMapping(value = "adminChangePassword",method = RequestMethod.GET)
	public String adminChangePassword(Model model){
		model.addAttribute("entity",LoginUserUtils.getCustomer());
		return "/front/mobile/adminChangePassword";
	}

    @RequestMapping("changePassword")
    @ResponseBody
    public JsonResult changePassword(String oldpassword,String password,String repassword,HttpServletRequest request){
        JsonResult result = new JsonResult();
        result.setSuccess(false);
        try{
			//修改密码
			if(password.equals(repassword)){
				Customer pCustomer = LoginUserUtils.getCustomer();
				if(customerService.validatePassword(pCustomer,oldpassword)){
					customerService.changePassword(pCustomer,password);
					result.setSuccess(true);
					result.setMessage("密码已修改,请重新登录");
					//session失效掉,重新登录
					request.getSession().setAttribute(LoginUserUtils.CURRENT_CUSTOMER_ATTR,null);
				}
				else{
					logger.error("修改密码出错:旧密码输入错误.");
					result.setMessage("旧密码不正确,请重新输入旧密码");
				}
			}
			else{
				logger.error("修改密码出错:两次输入的新密码不一致.");
				result.setMessage("两次输入的新密码不一致.");
			}
        }
        catch (Exception e){
            logger.error("修改用户密码出错,",e);
            result.setMessage("出错了,请稍后再试.");
        }

        return result;
    }

	@RequestMapping("changeMyInfo")
	@ResponseBody
	public JsonResult changeMyInfo(HttpServletRequest request,Customer customer){
		JsonResult result = new JsonResult();
		result.setSuccess(false);
		try{
			Customer pCustomer = LoginUserUtils.getCustomer();
			customer.setUserName(customer.getMobile());
			customerService.checkUnique(customer);
			copyCustomerInfoNotNull(customer,pCustomer);
			customerService.updateCustomer(pCustomer);
			result.setSuccess(true);
			result.setMessage("信息修改成功.");
		}
		catch (BusinessException be){
			result.setMessage(be.getMessage());
		}
		catch (Exception e){
			logger.error("修改用户信息出错,",e);
			result.setMessage("出错了,请稍后再试.");
		}

		return result;
	}
	
	@RequestMapping("modifyMyInfo")
	@ResponseBody
	public JsonResult modifyMyInfo(HttpServletRequest request){
		JsonResult result = new JsonResult();
		result.setSuccess(false);
		try{
			Customer customer = LoginUserUtils.getCustomer();
			customer.setName(request.getParameter("name"));
			customer.setIdCard(request.getParameter("idCard"));
			customer.setAddress(request.getParameter("address"));
			customer.setBirthday(request.getParameter("birthday"));
			customer.setSex(request.getParameter("sex"));
			customer.setDegreeOfEducation(request.getParameter("degreeOfEducation"));
			customer.setEmail(request.getParameter("email"));
			customer.setEmergencyContactNumber(request.getParameter("emergencyContactNumber"));
			customer.setEmergencyContactPerson(request.getParameter("emergencyContactPerson"));
			customer.setEmergencyContactRelationship(request.getParameter("emergencyContactRelationship"));
			customer.setFamily(request.getParameter("family"));
			customer.setMaritalStatus(request.getParameter("maritalStatus"));
			customer.setNativePlace(request.getParameter("nativePlace"));
			customer.setPhoneNumber(request.getParameter("phoneNumber"));
			customer.setProfession(request.getParameter("profession"));
			customer.setReceiveInfo(request.getParameter("receiveInfo"));
			customerService.update(customer);
			result.setSuccess(true);
			result.setMessage("信息修改成功.");
		}
		catch (BusinessException be){
			result.setMessage(be.getMessage());
		}
		catch (Exception e){
			logger.error("修改用户信息出错,",e);
			result.setMessage("出错了,请稍后再试.");
		}

		return result;
	}
	
	private void  copyCustomerInfoNotNull(Customer orignal,Customer dest){
		if(StringUtils.isNotBlank(orignal.getMobile())){
			dest.setMobile(orignal.getMobile());
			dest.setUserName(orignal.getMobile());
		}

		if(StringUtils.isNotBlank(orignal.getName())){
			dest.setName(orignal.getName());
		}

		if(StringUtils.isNotBlank(orignal.getIdCard())){
			dest.setIdCard(orignal.getIdCard());
		}

		if(StringUtils.isNotBlank(orignal.getPassword())){
			dest.setPassword(orignal.getPassword());
		}

	}

}
