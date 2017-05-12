package com.acooly.epei.common.acaptcha;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acooly.core.utils.Dates;

public class SimpleTextCaptchaService implements CaptchaService {

	private static Logger logger = LoggerFactory.getLogger(SimpleTextCaptchaService.class);

	public static final int DEFAULT_VALIDTY_SECOND = 180;

	protected Map<String, Captcha> store = new ConcurrentHashMap<String, Captcha>();
	private int capacity = 10000;
	private int cleanThreshold = 8000;

	private String wordRange = "0123456789";
	private int wordSize = 6;

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

		Captcha c = store.get(captchaId);
		if (c == null) {
			logger.info("验证码不存在. captchaId:{}", captchaId);
			return false;
		}

		boolean isValid = false;
		if (expired(c, System.currentTimeMillis())) {
			logger.debug("验证码过期.[ 有效期:{}] captchaId:{}",
					Dates.format(new Date(c.getExpireTime()), Dates.DATETIME_NOT_SEPARATOR), captchaId);
			isValid = false;
		} else {
			isValid = captcha.equals(c.getText());
			if (isValid) {
				logger.debug("验证码错误.[ 正确验证码:{}，请求验证码:{}] captchaId:{}", c.getText(), captcha, captchaId);
			}
		}
		store.remove(captchaId);
		return isValid;
	}

	@Override
	public boolean exist(String captchaId, boolean remove) {
		boolean exist = false;
		Captcha captcha = store.get(captchaId);
		if (captcha != null) {
			if (!expired(captcha, System.currentTimeMillis())) {
				exist = true;
			} else {
				store.remove(captchaId);
			}
		}
		if (exist && remove) {
			store.remove(captchaId);
		}
		return exist;
	}

	protected Captcha newCaptcha(String id, int validitySecond) {
		if (capacity < this.store.size()) {
			throw new RuntimeException("captcha: the store capacity overflow");
		}
		clear();
		String text = RandomStringUtils.random(wordSize, wordRange);
		if (StringUtils.isBlank(id)) {
			id = DigestUtils.md5Hex(System.currentTimeMillis() + text);
		}
		Captcha captcha = new Captcha(id, text, System.currentTimeMillis() + DEFAULT_VALIDTY_SECOND * 1000);
		this.store.put(captcha.getId(), captcha);
		return captcha;
	}

	protected void clear() {
		if (this.store.size() < cleanThreshold) {
			return;
		}
		long now = System.currentTimeMillis();
		for (Map.Entry<String, Captcha> entry : this.store.entrySet()) {
			if (expired(entry.getValue(), now)) {
				store.remove(entry.getKey());
			}
		}
	}

	protected boolean expired(Captcha c, long now) {
		if (c == null)
			return false;
		return now > c.getExpireTime();
	}

	public void clearAll() {
		this.store.clear();
	}

	public Map<String, Captcha> getStore() {
		return store;
	}

	public void setStore(Map<String, Captcha> store) {
		this.store = store;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getCleanThreshold() {
		return cleanThreshold;
	}

	public void setCleanThreshold(int cleanThreshold) {
		this.cleanThreshold = cleanThreshold;
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
