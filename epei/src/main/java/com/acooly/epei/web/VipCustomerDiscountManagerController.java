package com.acooly.epei.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.epei.domain.ServiceItem;
import com.acooly.epei.domain.VipCustomerDiscount;
import com.acooly.epei.service.ServiceItemService;
import com.acooly.epei.service.VipCustomerDiscountService;

import net.sf.json.JSONObject;

import com.acooly.core.common.web.AbstractJQueryEntityController;

@Controller
@RequestMapping(value = "/manage/epei/vipCustomerDiscount")
public class VipCustomerDiscountManagerController
		extends AbstractJQueryEntityController<VipCustomerDiscount, VipCustomerDiscountService> {

	@Autowired
	private VipCustomerDiscountService vipCustomerDiscountService;

	@Autowired
	ServiceItemService serviceItemService;

	@Override
	protected void onShow(HttpServletRequest request, HttpServletResponse response, Model model,
			VipCustomerDiscount entity) throws Exception {
		if (StringUtils.isNotBlank(entity.getDiscount())) {
			JSONObject json = JSONObject.fromObject(entity.getDiscount());
			List<ServiceItem> serviceItems = new ArrayList<ServiceItem>();
			if(!json.isNullObject()){
				for (Object object : json.keySet()) {
					JSONObject item = json.getJSONObject((String) object);
					ServiceItem serviceItem = serviceItemService.findByCode((String) object);
					if (serviceItem != null) {
						serviceItem.setCount((Integer) item.get("count"));
						serviceItems.add(serviceItem);
					}
				}
			}
			model.addAttribute("serviceItems", serviceItems);
		}
	}

}
