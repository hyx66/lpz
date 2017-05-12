package com.acooly.epei.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.epei.domain.ServiceItem;
import com.acooly.epei.domain.Vip;
import com.acooly.epei.service.ServiceItemService;
import com.acooly.epei.service.VipService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/manage/epei/vip")
public class VipManagerController extends AbstractJQueryEntityController<Vip, VipService> {

	@Autowired
	private VipService vipService;

	@Autowired
	private ServiceItemService serviceItemService;

	@Override
	protected void onCreate(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("serviceItems", serviceItemService.getAll());
	}
	
	@Override
	protected void onEdit(HttpServletRequest request, HttpServletResponse response, Model model, Vip entity) {
		if(entity.getDiscount() != null && !JSONObject.fromObject(entity.getDiscount()).isNullObject()){
			JSONObject json = JSONObject.fromObject(entity.getDiscount());
			List<ServiceItem> serviceItems = new ArrayList<ServiceItem>();
			for(ServiceItem serviceItem : serviceItemService.getAll()){
				if(json.get(serviceItem.getCode())!=null){
					JSONObject item = JSONObject.fromObject(json.get(serviceItem.getCode()));
					serviceItem.setCount((Integer) item.get("count"));
				}
				serviceItems.add(serviceItem);
			}
			model.addAttribute("serviceItems", serviceItems);
		}else{
			model.addAttribute("serviceItems", serviceItemService.getAll());
		}
	}

	@Override
	protected Vip onSave(HttpServletRequest request, HttpServletResponse response, Model model, Vip entity,
			boolean isCreate) throws Exception {
		if(vipService.getAll()!=null)throw new BusinessException("目前暂不支持添加多个VIP级别");
		JSONObject json = new JSONObject();
		for(ServiceItem serviceItem : serviceItemService.getAll()){
			JSONObject item = new JSONObject();
			String count = request.getParameter(serviceItem.getCode()+"count");
			String price = request.getParameter(serviceItem.getCode()+"price");
			if(StringUtils.isNotBlank(price)){
				item.put("price", new BigDecimal(price));
			}
			if(StringUtils.isNotBlank(count)){
				item.put("count", Long.parseLong(count));
			}
			if(!item.isEmpty()){
				json.put(serviceItem.getCode(), item);
			}
		}
		if(!json.isEmpty()){
			entity.setDiscount(json.toString());
		}else{
			entity.setDiscount(null);
		}
		return entity;
	}
	
	@Override
	protected void onShow(HttpServletRequest request, HttpServletResponse response, Model model, Vip entity)throws Exception{
		if(StringUtils.isNotBlank(entity.getDiscount())){
			JSONObject json = JSONObject.fromObject(entity.getDiscount());
			List<ServiceItem> serviceItems = new ArrayList<ServiceItem>();
			if(!json.isNullObject()){
				for(Object object : json.keySet()){
					JSONObject item = json.getJSONObject((String) object);
					ServiceItem serviceItem = serviceItemService.findByCode((String) object);
					if(serviceItem != null){
						serviceItem.setCount((Integer) item.get("count"));
						serviceItems.add(serviceItem);
					}
				}
			}
			model.addAttribute("serviceItems",serviceItems);
		}
	}
	
}
