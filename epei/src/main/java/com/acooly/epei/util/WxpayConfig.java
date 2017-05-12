package com.acooly.epei.util;

import org.springframework.context.annotation.Configuration;

@Configuration
public class WxpayConfig {
	private static final String configUrl="pay.wxpay.properties";

	public static final String APPID= GetPropertiesValue.getValue(configUrl, "appId");//appid
	public static final String APPSECRET=GetPropertiesValue.getValue(configUrl, "appSecret");//appsecret
	public static final String PATERNER_KEY=GetPropertiesValue.getValue(configUrl, "paternerKey");// 商户支付密钥
	public static final String MCHI_ID=GetPropertiesValue.getValue(configUrl, "mch_id");//商户号
	public static final String SPBILL_CREATE_IP=GetPropertiesValue.getValue(configUrl, "spbill_create_ip");//支付IP地址
	public static final String SIGNTYPE="MD5";//加密方式
	public static final String DEVICE_INFO=GetPropertiesValue.getValue(configUrl, "device_info");//微信支付分配的终端设备号
	public static final String RECHARGE_NOTIFY_URL=GetPropertiesValue.getValue(configUrl, "recharge_notify_url");//回调地址
	public static final String ORDER_PAY_NOTIFY_URL=GetPropertiesValue.getValue(configUrl, "order_pay_notify_url");//回调地址
	public static final String TRADE_TYPE_JS=GetPropertiesValue.getValue(configUrl, "trade_type_js");//交易类型
	public static final String INPUT_CHARSET=GetPropertiesValue.getValue(configUrl, "input_charset");//编码
	public static final String PREPAY_ID_URL=GetPropertiesValue.getValue(configUrl, "prepay_id_url");//统一支付接口
	public static final String CREDENTIALS=GetPropertiesValue.getValue(configUrl, "credentials");//p12证书存放路径
	public static final String YUMING=GetPropertiesValue.getValue(configUrl, "yuming");//域名
	public static final String WEIDIAN_APPKEY=GetPropertiesValue.getValue(configUrl, "weidian_appkey");//微店appkey
	public static final String WEIDIAN_SECRET=GetPropertiesValue.getValue(configUrl, "weidian_secret");//微店secret
	
}
