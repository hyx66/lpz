package com.acooly.epei.service.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import org.apache.shiro.SecurityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.service.CustomerAcctRecordsService;
import com.acooly.epei.service.CustomerAcctService;
import com.acooly.epei.service.CustomerService;
import com.acooly.epei.service.EnchashmentApplyService;
import com.acooly.epei.util.CodeTools;
import com.acooly.epei.util.CodeUtils;
import com.acooly.epei.util.MD5;
import com.acooly.epei.util.WxUtils;
import com.acooly.epei.util.WxpayConfig;
import com.acooly.module.security.domain.User;

import com.acooly.epei.dao.EnchashmentApplyDao;
import com.acooly.epei.domain.Customer;
import com.acooly.epei.domain.CustomerAcct;
import com.acooly.epei.domain.CustomerAcctRecords;
import com.acooly.epei.domain.EnchashmentApply;
import com.acooly.epei.domain.OrderOriginalEnum;

@Service("EnchashmentApplyService")
public class EnchashmentApplyServiceImpl extends EntityServiceImpl<EnchashmentApply, EnchashmentApplyDao> implements EnchashmentApplyService {

	@Autowired
	CustomerService customerService;
	@Autowired
	CustomerAcctService customerAcctService;
	@Autowired
	CustomerAcctRecordsService customerAcctRecordsService;
	
/*	private String fahongbao(EnchashmentApply e) {
		try {
			if(e.getStatus()!=1)return "此次操作无效！";
			KeyStore ks = KeyStore.getInstance("pkcs12");//pkcs12是一种证书格式
			ks.load(new FileInputStream(WxpayConfig.CREDENTIALS), WxpayConfig.MCHI_ID.toCharArray());
			KeyManagerFactory kf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kf.init(ks, WxpayConfig.MCHI_ID.toCharArray());
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(kf.getKeyManagers(), null, new SecureRandom());
			String act_name = "余额提现";//活动名称
			String appid = WxpayConfig.APPID;
			String client_ip = WxpayConfig.SPBILL_CREATE_IP;
			String mch_id = WxpayConfig.MCHI_ID;
			String nonce_str = WxUtils.randomStr();//随机字符串
			String re_openid = e.getOpenId();
			String mch_billno = e.getApplyNo();//商户订单号
			String remark = "客服电话：4008837622";//备注
			String send_name = "乐陪诊";//商户名称
			String total_amount = e.getAmount().multiply(new BigDecimal("100")).toString();//红包金额，单位为分，必须大于100分，小于20000分
			String total_num = "1";//红包发放总人数
			String wishing = "红包有效时间为24小时，请火速领取！ ";//祝福语
			String key = WxpayConfig.PATERNER_KEY;
			String xmlParam = "act_name="+act_name+"&client_ip="+client_ip+"&mch_billno="+mch_billno+"&mch_id="+mch_id+"&nonce_str="+nonce_str+"&re_openid="+re_openid+"&remark="+remark+"&send_name="+send_name+"&total_amount="+total_amount+"&total_num="+total_num+"&wishing="+wishing+"&wxappid="+appid+"&key="+key+"";
			String xml1 = "<xml> <act_name>"+act_name+"</act_name> <wxappid>"+appid+"</wxappid> <client_ip>"+client_ip+"</client_ip> <mch_billno>"+mch_billno+"</mch_billno> <mch_id>"+mch_id+"</mch_id> <nonce_str>"+nonce_str+"</nonce_str> <re_openid>"+re_openid+"</re_openid> <remark>"+remark+"</remark> <send_name>"+send_name+"</send_name> <total_amount>"+total_amount+"</total_amount> <total_num>"+total_num+"</total_num> <wishing>"+wishing+"</wishing>";
			String xml2 = "<sign>"+MD5.MD5Encode(xmlParam).toUpperCase()+"</sign></xml>";
			HttpsURLConnection uc = (HttpsURLConnection)new URL("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack").openConnection();
			uc.setDoOutput(true);
			uc.setRequestMethod("POST");
			uc.setSSLSocketFactory(sslContext.getSocketFactory());
			OutputStream os = uc.getOutputStream();
			String xml = xml1+xml2;
			os.write(xml.getBytes("UTF-8"));
			os.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream(),"UTF-8"));
			StringBuffer sb = new StringBuffer();
			String line = null;
			while((line = br.readLine()) != null){
				sb.append(line);
			}
			br.close();
			Document document = DocumentHelper.parseText(sb.toString());
			Element root = document.getRootElement();
			if(root.elementText("return_code").equals("SUCCESS") && root.elementText("result_code").equals("SUCCESS")){
				return "SUCCESS";
			}else{
					String error = "提现失败...错误代码："+root.elementText("err_code")+"，错误信息："+root.elementText("err_code_des");
				return root.elementText("err_code_des");
				}
		} catch (Exception ex) {
			return "系统出错，请稍后重试！";
		} 
	}*/

	public String payment(EnchashmentApply e) {
		try {
			KeyStore ks = KeyStore.getInstance("pkcs12");//pkcs12是一种证书格式
			ks.load(new FileInputStream(WxpayConfig.CREDENTIALS), WxpayConfig.MCHI_ID.toCharArray());
			KeyManagerFactory kf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kf.init(ks, WxpayConfig.MCHI_ID.toCharArray());
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(kf.getKeyManagers(), null, new SecureRandom());
			String amount = e.getAmount().multiply(new BigDecimal("100")).toString();
			String check_name = "NO_CHECK";
			String desc = "用户可用金额申请提现已到账";
			String mch_appid = WxpayConfig.APPID;
			String mchid = WxpayConfig.MCHI_ID;
			String nonce_str = WxUtils.randomStr();
			String openid = e.getOpenId();
			String partner_trade_no = e.getApplyNo();
			String spbill_create_ip = InetAddress.getLocalHost().getHostAddress();//调用接口的机器Ip地址，但对支付验证没有任何作用，可随意填写一个IP地址
			String sign = "amount="+amount+"&check_name="+check_name+"&desc="+desc+"&mch_appid="+mch_appid+"&mchid="+mchid+"&nonce_str="+nonce_str+"&openid="+openid+"&partner_trade_no="+partner_trade_no+"&spbill_create_ip="+spbill_create_ip+"&key="+WxpayConfig.PATERNER_KEY;
			sign = MD5.MD5Encode(sign).toUpperCase();
			String xml ="<xml>"
						+"<amount>"+amount+"</amount>"
						+"<check_name>"+check_name+"</check_name>"
						+"<desc>"+desc+"</desc>"
						+"<mch_appid>"+mch_appid+"</mch_appid>"
						+"<mchid>"+mchid+"</mchid>"
						+"<nonce_str>"+nonce_str+"</nonce_str>"
						+"<openid>"+openid+"</openid>"
						+"<partner_trade_no>"+partner_trade_no+"</partner_trade_no>"
						+"<spbill_create_ip>"+spbill_create_ip+"</spbill_create_ip>"
						+"<sign>"+sign+"</sign>"
					   + "</xml>";
			HttpsURLConnection uc = (HttpsURLConnection)new URL("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers").openConnection();
			uc.setDoOutput(true);//默认即为true,当不需要读取数据时可以设置为false
			uc.setRequestMethod("POST");
			uc.setSSLSocketFactory(sslContext.getSocketFactory());
			OutputStream os = uc.getOutputStream();
			os.write(xml.getBytes("UTF-8"));
			os.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream(),"UTF-8"));
			StringBuffer sb = new StringBuffer();
			String line = null;
			while((line = br.readLine()) != null){
				sb.append(line);
			}
			br.close();
			Document document = DocumentHelper.parseText(sb.toString());
			Element root = document.getRootElement();
			if(root.elementText("return_code").equals("SUCCESS") && root.elementText("result_code").equals("SUCCESS")){
				return "SUCCESS";
			}else{
				return root.elementText("err_code_des");
			}
		} catch (Exception ex) {
			return "系统出错，请稍后重试！";
		}
    }
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void transfer(EnchashmentApply e){
		//修改申请状态
		if(e.getStatus()!=1)throw new BusinessException("该申请已经审批过了，请勿重复提交！");
		User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
		e.setAuditorId(loginUser.getId());
		e.setAuditorName(loginUser.getRealName());
		e.setEndTime(new Date());
		e.setStatus(2);
		update(e);
		
		//修改账户余额
		CustomerAcct c = customerAcctService.getCustomerAcctByCustomerId(e.getCustomerId());
		BigDecimal availableAmount = new BigDecimal(CodeTools.dncode(c.getAvailableAmount(), e.getCustomerId().toString()));
		BigDecimal balance = new BigDecimal(CodeTools.dncode(c.getBalance(), e.getCustomerId().toString()));
		if(availableAmount.compareTo(e.getAmount())==-1)throw new BusinessException("该用户可用金额不足！");
		if(balance.compareTo(e.getAmount())==-1)throw new BusinessException("该用户余额不足");
		String newAvailableAmount = CodeTools.encode(availableAmount.subtract(e.getAmount()).toString(), e.getCustomerId().toString());
		String newBalance = CodeTools.encode(balance.subtract(e.getAmount()).toString(), e.getCustomerId().toString());
		c.setAvailableAmount(newAvailableAmount);
		c.setBalance(newBalance);
		customerAcctService.update(c);
		
		//添加账户变更明细
		CustomerAcctRecords customerAcctRecords = new CustomerAcctRecords();
		Customer customer = customerService.get(e.getCustomerId());
		customerAcctRecords.setCustomerId(customer.getId());
		customerAcctRecords.setCustomerMobile(customer.getMobile());
		customerAcctRecords.setCustomerName(customer.getName());
		customerAcctRecords.setTitle("余额提现");
		customerAcctRecords.setDataType(2);
		customerAcctRecords.setOutNo(e.getApplyNo());
		customerAcctRecords.setRechargeAmount(new BigDecimal(0));
		customerAcctRecords.setRecordsNo(CodeUtils.getOrderNo(OrderOriginalEnum.getByCode("PC")));
		customerAcctRecords.setRechargeChannle(0);
		customerAcctRecords.setRechargeStatus(0);
		customerAcctRecords.setSpendAmount(e.getAmount());
		customerAcctRecords.setSpendType(3);
		customerAcctRecords.setSpendStatus(1);
		customerAcctRecords.setSpendChannel(1);
		customerAcctRecords.setMemo("用户申请提现的金额已转账至其微信账户上");
		customerAcctRecords.setDateYmd(new SimpleDateFormat("yyyyMMdd").format(new Date()));
		customerAcctRecords.setBalance(new BigDecimal(CodeTools.dncode(c.getBalance(), e.getCustomerId().toString())));
		customerAcctRecordsService.save(customerAcctRecords);
		
		//调用微信付款接口
		String result = payment(e);
		if(!result.equals("SUCCESS"))throw new BusinessException(result);
	}

	@Override
	public List<EnchashmentApply> queryByCustomerId(Long id) {
		Map<String, Object> params = new HashMap<>();
		params.put("EQ_customerId", id);
		List<EnchashmentApply> enchashmentApplyList = query(params, null);
		return enchashmentApplyList;
	}
	
}
