package com.acooly.epei.util;

import com.acooly.core.utils.mapper.JsonMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;

/**
 * 修订记录：
 * liubin 2015-11-11 15:36 创建
 */
public class WechatSignUtils {

	private static Logger logger = LoggerFactory.getLogger(WechatSignUtils.class);

	// 与接口配置信息中的Token要一致
	private static final String ACCESS_TOKEN = "CorwHBx25XwvJuhUWonBG2Z4TFzQGteQ";

	private static final String APP_ID = "wxe7b1c705a3d8fe8c";

	private static final String APP_SECRET = "3bf428de596f0f89966eebc2c2693e59";

	private static final String OPENID_URL = "https://api.weixin.qq" +
											 ".com/sns/oauth2/access_token?appid="+APP_ID+"&secret="+APP_SECRET
											 +"&code=";

	private static  final  String GRANT_TYPE = "&grant_type=authorization_code";


	/**

	 * 验证签名

	 *

	 * @param signature

	 * @param timestamp

	 * @param nonce

	 * @return

	 */

	public static boolean checkSignature(String signature, String timestamp, String nonce) {

		if(StringUtils.isBlank(signature) || StringUtils.isBlank(timestamp) || StringUtils.isBlank(nonce)){
			return false;
		}

		String[] params = {ACCESS_TOKEN,timestamp,nonce};

		Arrays.sort(params);
		String paramsStr = StringUtils.join(params);

		byte[] digestParams = DigestUtils.getDigest("SHA-1").digest(paramsStr.getBytes());

		paramsStr = byteToStr(digestParams);
		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信

		logger.info("Signature String :" + paramsStr+"check result:" +paramsStr.equals(signature.toUpperCase()));
		return paramsStr.equals(signature.toUpperCase());

	}

	public static String getOpenidByCodeFromWechat(String code){
		logger.info("根据code:{}获取用户openid......",new String[]{code});
		String getUrl = OPENID_URL+code+GRANT_TYPE;
		String openid = "";
		try {
			String responseBody = Request.Get(getUrl)
					.connectTimeout(1000)
					.socketTimeout(1000)
					.execute().returnContent().asString();
			Map<String,String> result =  JsonMapper.nonEmptyMapper().fromJson(responseBody, Map.class);
			openid = result.get("openid");
			if(StringUtils.isBlank(openid)){
				logger.error("根据code:["+code+"]获取openid失败,错误信息:"+result.get("errmsg"));
			}
		} catch (Exception e) {
			logger.error("获取微信用户openid出错,异常信息:",e);
		}
		return openid;
	}

	public static String getOpenidByCode(String code){
		logger.info("根据code:{}获取用户openid......",new String[]{code});
		String getUrl = OPENID_URL+code+GRANT_TYPE;
		String openid = "";
		try {
			//先从session中获取,如果已经获取过openid的话会在session中找到
			Object openidObj = RequestContextHolderUtils.getSession().getAttribute(code);
			if(null != openidObj){
				openid = (String) RequestContextHolderUtils.getSession().getAttribute(code);
			}
			if(StringUtils.isBlank(openid)){
				String responseBody = Request.Get(getUrl)
						.connectTimeout(1000)
						.socketTimeout(1000)
						.execute().returnContent().asString();
				Map<String,String> result =  JsonMapper.nonEmptyMapper().fromJson(responseBody, Map.class);
				openid = result.get("openid");
				if(StringUtils.isBlank(openid)){
					logger.error("根据code:["+code+"]获取openid失败,错误信息:"+result.get("errmsg"));
				}
			}
		} catch (Exception e) {
			logger.error("获取微信用户openid出错,异常信息:",e);
		}
		return openid;
	}

	public static void main(String[] args){
		String code = "021fdb63eb66e2f285a437c4e737f11u";
		System.out.println(getOpenidByCode(code));
	}


	/**

	 * 将字节数组转换为十六进制字符串

	 *

	 * @param byteArray

	 * @return

	 */

	private static String byteToStr(byte[] byteArray) {

		String strDigest = "";

		for (int i = 0; i < byteArray.length; i++) {

			strDigest += byteToHexStr(byteArray[i]);

		}

		return strDigest;

	}

	/**

	 * 将字节转换为十六进制字符串

	 *

	 * @param mByte

	 * @return

	 */

	private static String byteToHexStr(byte mByte) {

		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

		char[] tempArr = new char[2];

		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];

		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);

		return s;

	}

}
