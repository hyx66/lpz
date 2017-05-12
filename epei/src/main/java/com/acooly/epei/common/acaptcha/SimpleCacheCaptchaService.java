package com.acooly.epei.common.acaptcha;

import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acooly.module.caches.simple.SimpleCacheService;

public class SimpleCacheCaptchaService implements CaptchaService {

	private static Logger logger = LoggerFactory.getLogger(SimpleCacheCaptchaService.class);

	public static final int DEFAULT_VALIDTY_SECOND = 180;

	private String wordRange = "0123456789";
	private int wordSize = 6;

	@Resource(name = "memerySimpleCacheService")
	private SimpleCacheService memerySimpleCacheService;

	@Override
	public Captcha getCaptcha() {
		return getCaptcha(DEFAULT_VALIDTY_SECOND);
	}

	@Override
	public Captcha getCaptcha(int validitySecond) {
		return getCaptcha(null, validitySecond);
	}

	@Override
	public Captcha getCaptcha(String captchaId) {
		return getCaptcha(captchaId, DEFAULT_VALIDTY_SECOND);
	}

	@Override
	public Captcha getCaptcha(String captchaId, int validitySecond) {
		return newCaptcha(captchaId, validitySecond);
	}

	@Override
	public boolean verifyCaptcha(String captchaId, String captcha) {
		if (StringUtils.isBlank(captcha)) {
			logger.info("验证码为空. captchaId:{}", captchaId);
			return false;
		}

		String captchaText = getSimpleCacheService().get(captchaId);
		if (captchaText == null) {
			logger.info("验证码不存在或过期. captchaId:{}", captchaId);
			return false;
		} else {
			return captchaText.equals(captcha);
		}
	}

	@Override
	public boolean exist(String captchaId, boolean remove) {

		if (getSimpleCacheService().get(captchaId) != null) {
			if (remove) {
				getSimpleCacheService().delete(captchaId);
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 创建新验证码
	 * 
	 * @param id
	 * @param validitySecond
	 * @return
	 */
	protected Captcha newCaptcha(String id, int validitySecond) {
		String value = RandomStringUtils.random(wordSize, wordRange);
		if (StringUtils.isBlank(id)) {
			id = UUID.randomUUID().toString();
		}
		Captcha captcha = new Captcha(id, value, System.currentTimeMillis() + validitySecond * 1000);
		getSimpleCacheService().set(id, value, validitySecond);
		return captcha;
	}

	/**
	 * 获取缓存服务
	 * 
	 * @return
	 */
	protected SimpleCacheService getSimpleCacheService() {
		return memerySimpleCacheService;
	}

	public String getWordRange() {
		return wordRange;
	}

	public void setWordRange(String wordRange) {
		this.wordRange = wordRange;
	}

	public int getWordSize() {
		return wordSize;
	}

	public void setWordSize(int wordSize) {
		this.wordSize = wordSize;
	}

}
