package com.acooly.epei.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.service.LxyOrderBaseService;
import com.acooly.epei.util.HttpTool;
import com.acooly.epei.util.WxpayConfig;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.acooly.epei.dao.GlobalVariableDao;
import com.acooly.epei.dao.LxyOrderBaseDao;
import com.acooly.epei.domain.GlobalVariable;
import com.acooly.epei.domain.LxyOrderBase;
import com.acooly.epei.domain.LxyOrderItem;

@Service("lxyOrderBaseService")
public class LxyOrderBaseServiceImpl extends EntityServiceImpl<LxyOrderBase, LxyOrderBaseDao>
		implements LxyOrderBaseService {
	
	@Autowired
	private GlobalVariableDao globalVariableDao;

	public List<LxyOrderBase> jsonformatObject(JSONObject json) {
		List<LxyOrderBase> lxyOrderBaseList = new ArrayList<LxyOrderBase>();
		JSONObject result = json.getJSONObject("result");
		JSONArray orderList = result.getJSONArray("orders");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (Object object : orderList) {
			// buyer_info
			JSONObject order = (JSONObject) object;
			JSONObject buyer = order.getJSONObject("buyer_info");
			LxyOrderBase lxyOrderBase = getEntityDao().findByOrderId(order.getString("order_id"));
			if (lxyOrderBase == null) {
				lxyOrderBase = new LxyOrderBase();
			}
			lxyOrderBase.setAddress(buyer.getString("address"));
			lxyOrderBase.setCity(buyer.getString("city"));
			lxyOrderBase.setName(buyer.getString("name"));
			lxyOrderBase.setPhone(buyer.getString("phone"));
			lxyOrderBase.setPost(buyer.getString("post"));
			lxyOrderBase.setProvince(buyer.getString("province"));
			lxyOrderBase.setRegion(buyer.getString("region"));
			lxyOrderBase.setSelfAddress(buyer.getString("self_address"));
			// order_detail
			lxyOrderBase.setBuyerNote(order.getString("buyer_note"));
			if (StringUtils.isNotBlank(order.getString("express_fee"))) {
				lxyOrderBase.setExpressFee(new BigDecimal(order.getString("express_fee")));
			}
			lxyOrderBase.setExpressNo(order.getString("express_no"));
			lxyOrderBase.setExpressType(order.getString("express_type"));
			lxyOrderBase.setFphone(order.getString("f_phone"));
			lxyOrderBase.setFsellerId(order.getString("f_seller_id"));
			lxyOrderBase.setFshopName(order.getString("f_shop_name"));
			lxyOrderBase.setGroupStatus(order.getInt("group_status"));
			lxyOrderBase.setIsWeiOrder(order.getInt("is_wei_order"));
			lxyOrderBase.setOrderId(order.getString("order_id"));
			lxyOrderBase.setSellerNote(order.getString("seller_note"));
			lxyOrderBase.setStatus(order.getString("status"));
			lxyOrderBase.setStatus2(order.getString("status2"));
			if (StringUtils.isNotBlank(order.getString("total"))) {
				lxyOrderBase.setTotal(new BigDecimal(order.getString("total")));
			}
			if (StringUtils.isNotBlank(order.getString("time"))) {
				try {
					lxyOrderBase.setTime(dateFormat.parse(order.getString("time")));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			lxyOrderBaseList.add(lxyOrderBase);
		}
		return lxyOrderBaseList;
	}

	public LxyOrderBase jsonFormatEntity(JSONObject json) {
		JSONObject result = json.getJSONObject("result");
		JSONObject buyer = result.getJSONObject("buyer_info");
		JSONArray items = result.getJSONArray("items");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		LxyOrderBase lxyOrderBase = getEntityDao().findByOrderId(result.getString("order_id"));
		if (lxyOrderBase == null) {
			lxyOrderBase = new LxyOrderBase();
			List<LxyOrderItem> orderItemList = new ArrayList<LxyOrderItem>();
			for (Object object : items) {
				JSONObject item = (JSONObject) object;
				LxyOrderItem lxyOrderItem = new LxyOrderItem();	
				lxyOrderItem.setSkuId(item.getString("sku_id"));
				lxyOrderItem.setSkuTitle(item.getString("sku_title"));
				lxyOrderItem.setItemId(item.getString("item_id"));
				lxyOrderItem.setItemName(item.getString("item_name"));
				lxyOrderItem.setMerchantCode(item.getString("merchant_code"));
				lxyOrderItem.setUrl(item.getString("url"));
				lxyOrderItem.setImg(item.getString("img"));
				if (StringUtils.isNotBlank(item.getString("price"))) {
					lxyOrderItem.setPrice(new BigDecimal(item.getString("price")));
				}
				lxyOrderItem.setQuantity(item.getInt("quantity"));
				if (StringUtils.isNotBlank(item.getString("total_price"))) {
					lxyOrderItem.setTotalPrice(new BigDecimal(item.getString("total_price")));
				}
				orderItemList.add(lxyOrderItem);
			}
			lxyOrderBase.setItem(orderItemList);
		}
		lxyOrderBase.setBuyerId(buyer.getString("buyer_id"));
		lxyOrderBase.setAddress(buyer.getString("address"));
		lxyOrderBase.setCity(buyer.getString("city"));
		lxyOrderBase.setName(buyer.getString("name"));
		lxyOrderBase.setPhone(buyer.getString("phone"));
		lxyOrderBase.setPost(buyer.getString("post"));
		lxyOrderBase.setProvince(buyer.getString("province"));
		lxyOrderBase.setRegion(buyer.getString("region"));
		lxyOrderBase.setSelfAddress(buyer.getString("self_address"));
		// order_detail
		lxyOrderBase.setBuyerNote(result.getString("note"));
		if (StringUtils.isNotBlank(result.getString("express_fee"))) {
			lxyOrderBase.setExpressFee(new BigDecimal(result.getString("express_fee")));
		}
		lxyOrderBase.setExpressNo(result.getString("express_no"));
		lxyOrderBase.setExpressType(result.getString("express_type"));
		lxyOrderBase.setFphone(result.getString("f_phone"));
		lxyOrderBase.setFsellerId(result.getString("f_seller_id"));
		lxyOrderBase.setFshopName(result.getString("f_shop_name"));
		lxyOrderBase.setGroupStatus(result.getInt("group_status"));
		lxyOrderBase.setIsWeiOrder(result.getInt("is_wei_order"));
		lxyOrderBase.setOrderId(result.getString("order_id"));
		lxyOrderBase.setSellerNote(result.getString("express_note"));
		lxyOrderBase.setStatus(result.getString("status"));
		lxyOrderBase.setStatus2(result.getString("status2"));
		if (StringUtils.isNotBlank(result.getString("total"))) {
			lxyOrderBase.setTotal(new BigDecimal(result.getString("total")));
		}
		if (StringUtils.isNotBlank(result.getString("add_time"))) {
			try {
				lxyOrderBase.setTime(dateFormat.parse(result.getString("add_time")));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return lxyOrderBase;
	}

	public static ArrayList<String> jsonFormatArrayList(JSONObject json) {
		ArrayList<String> list = new ArrayList<String>();
		JSONObject result = json.getJSONObject("result");
		String orderIdsString = result.getString("order_ids").replace("[", "").replace("]", "").replaceAll("\"", "");
		if(StringUtils.isNotBlank(orderIdsString)){
			String[] orderIds = orderIdsString.split(",");
			if(orderIds.length>0){
				for (String orderId : orderIds) {
					list.add(orderId);
				}
			}
		}
		return list;
	}

	@Override
	@Transactional
	public void dataSynchronization(Date date) {
		String access_token = getWeidianAccessToken();
		int page_num = 1;
		int page_size = 50;
		int total_num = 0;
		List<String> orderIdsList = new ArrayList<String>();
		List<LxyOrderBase> lxyOrderBaseList = new ArrayList<LxyOrderBase>();
		do {
			String dateString = new SimpleDateFormat("yyyy-MM-dd").format(date);
			JSONObject json = HttpTool.getWeidianOrderIdList(page_num, page_size, dateString, access_token);
			List<String> orderIds = jsonFormatArrayList(json);
			if(!orderIds.isEmpty()){
				orderIdsList.addAll(orderIds);
			}
			total_num = orderIds.size();
			page_num++;
		} while (total_num == page_size);
		if(orderIdsList.isEmpty()){
			for (String orderId : orderIdsList) {
				JSONObject orderDetail = HttpTool.getWeidianOrderDetail(orderId, access_token);
				LxyOrderBase lxyOrderBase = jsonFormatEntity(orderDetail);
				if(lxyOrderBase.getStatus().equals("pay")){
					lxyOrderBaseList.add(lxyOrderBase);
				}
			}
			saves(lxyOrderBaseList);
		}
	}
	
	@Override
	public synchronized String getWeidianAccessToken() {
		String access_token = null;
		GlobalVariable globalVariable = globalVariableDao.findByParam("weidian_access_token");
		if (globalVariable != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String today = dateFormat.format(new Date());
			String updateTime = dateFormat.format(globalVariable.getUpdateTime());
			if (today.equals(updateTime)) {
				access_token = globalVariable.getValue();
			} else {
				access_token = HttpTool.getWeidianAccessToken(WxpayConfig.WEIDIAN_APPKEY, WxpayConfig.WEIDIAN_SECRET);
				globalVariable.setValue(access_token);
				globalVariableDao.update(globalVariable);
			}
		} else {
			access_token = HttpTool.getWeidianAccessToken(WxpayConfig.WEIDIAN_APPKEY, WxpayConfig.WEIDIAN_SECRET);
			globalVariable = new GlobalVariable();
			globalVariable.setParam("weidian_access_token");
			globalVariable.setValue(access_token);
			globalVariable.setMemo("微店access_token，有效期为25小时");
			globalVariableDao.save(globalVariable);
		}
		return access_token;
	}

	@Override
	public void setEntityTheme(LxyOrderBase entity) {
		String theme = "";
		for(LxyOrderItem item : entity.getItem()){
			if(item.getItemName()!=null){
				theme =item.getItemName()+"(" ;
			}
		}
		if(entity.getHospitalName()!=null){
			theme = theme+entity.getHospitalName();
		}
		for(LxyOrderItem item : entity.getItem()){
			if(item.getSkuTitle()!=null){
				theme = theme+item.getSkuTitle()+")";
			}
			
		}
		entity.setTheme(theme);
	}

}
