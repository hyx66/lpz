package com.acooly.epei.common.acaptcha;

public interface CaptchaService {

	Captcha getCaptcha();

	Captcha getCaptcha(int validitySecond);
	
	Captcha getCaptcha(String captchaId);

	Captcha getCaptcha(String captchaId, int validitySecond);

	boolean verifyCaptcha(String captchaId, String captcha);

	boolean exist(String captchaId, boolean remove);

}
