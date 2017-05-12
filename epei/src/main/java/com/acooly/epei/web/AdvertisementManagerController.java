package com.acooly.epei.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.epei.domain.Advertisement;
import com.acooly.epei.service.AdvertisementService;
 /** 
 * Date： 2017-3-13 下午6:35:06
 * @author acooly code Generator
 */
@Controller
@RequestMapping(value = "/manage/epei/advertisement")
public class AdvertisementManagerController extends AbstractJQueryEntityController<Advertisement,AdvertisementService>{
	@Autowired
	private AdvertisementService advertisementService;
	
	private Logger logger = LoggerFactory.getLogger(AdvertisementManagerController.class);
	
	
	
	@RequestMapping("cancelAdvertisement")
	@ResponseBody
	public JsonResult cancelAdvertisement(Long id){
		JsonResult result  = new JsonResult();
		
		try{
			Advertisement  advertisement = advertisementService.findById(id);
			if(advertisement != null){
				advertisement.setDeleted(1);
				advertisementService.update(advertisement);
				result.setMessage("广告已锁定");
			}
			else{
				result.setSuccess(false);
				result.setMessage("该广告不存在,试试刷新页面.");
			}
		}
		catch (Exception e ){
			logger.error("锁定广告发生异常,",e);
			result.setSuccess(false);
			result.setMessage("系统出错啦,请稍后再试.");
		}
		return  result;
	}

	@RequestMapping("recoverAdvertisement")
	@ResponseBody
	public JsonResult recoverAdvertisement(Long id){
		JsonResult result  = new JsonResult();
		try{
			Advertisement  advertisement = advertisementService.get(id);
			if(advertisement != null){
				advertisement.setDeleted(0);
				advertisementService.update(advertisement);
				result.setMessage("广告已解锁");
			}
			else{
				result.setSuccess(false);
				result.setMessage("该广告不存在,试试刷新页面.");
			}
		}
		catch (Exception e ){
			logger.error("解锁广告发生异常,",e);
			result.setSuccess(false);
			result.setMessage("系统出错啦,请稍后再试.");
		}
		return  result;
	}
	

	@RequestMapping({"theAdvertisement"})
	public String show(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			model.addAllAttributes(referenceData(request));
			AbstractEntity entity = loadEntity(request);
			if (entity == null) {
				throw new RuntimeException("LoadEntity failure.");
			}
			model.addAttribute(getEntityName(), entity);
			model.addAttribute("advertisements",advertisementService.findById(entity.getId()));
		} catch (Exception e) {
			logger.warn(getExceptionMessage("show", e), e);
			handleException("查看", e, request);
		}
		return getShowView();
	}
}
