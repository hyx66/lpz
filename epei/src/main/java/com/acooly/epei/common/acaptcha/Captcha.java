package com.acooly.epei.common.acaptcha;

//验证码
public class Captcha {

	private String id;
	private String text;
	private long expireTime;

	public Captcha() {
		super();
	}

	public Captcha(String id, String text, long expireTime) {
		super();
		this.id = id;
		this.text = text;
		this.expireTime = expireTime;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Captcha [id=").append(id).append(", text=")
				.append(text).append(", expireTime=").append(expireTime)
				.append("]");
		return builder.toString();
	}

}
