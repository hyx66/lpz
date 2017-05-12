package com.acooly.epei.web.front;

import com.acooly.epei.util.WechatSignUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 修订记录：
 * liubin 2015-11-11 14:52 创建
 */
@Controller
@RequestMapping("wechat")
public class WechatController {

	private Logger logger = LoggerFactory.getLogger(WechatController.class);

	/**
	 * 供微信服务端调用,验证当前服务器身份的真实性
	 *
	 * @param timestamp
	 * @param nonce
	 * @param signature
	 * @param echostr
	 * @return
	 */
	@RequestMapping("checkSign")
	public void checkSign(String timestamp, String nonce, String signature, String echostr,
						 HttpServletResponse response) {
		logger.info("进行微信验证,timestamp:"+timestamp+",nonce:"+nonce+",sign:"+signature+",echostr:"+echostr);
		if(WechatSignUtils.checkSignature(signature,timestamp,nonce)){
			try {
				response.getWriter().print(echostr);
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
	}
}
