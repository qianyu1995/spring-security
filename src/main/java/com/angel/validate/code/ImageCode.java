package com.angel.validate.code;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @author 陈明
 * @date 2019/9/5 14:14
 */
public class ImageCode
{
	private BufferedImage image;
	
	private String code;
	
	private LocalDateTime expireTime;
	
	public ImageCode(BufferedImage image, String code, int expireIn) {
		this.image = image;
		this.code = code;
		this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
	}
	
	public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
		this.image = image;
		this.code = code;
		this.expireTime = expireTime;
	}
	
	boolean isExpire() {
		return LocalDateTime.now().isAfter(expireTime);
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public LocalDateTime getExpireTime() {
		return expireTime;
	}
	
	public void setExpireTime(LocalDateTime expireTime) {
		this.expireTime = expireTime;
	}
}
