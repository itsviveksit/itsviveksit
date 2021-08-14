package com.org.khatabahi.logger.utils;

import org.apache.commons.lang3.StringUtils;

public final class MaskConfiguration {

	private String type;
	private int number;
	private char maskCharacter = '*';
	private int fixLegnth = 4;
	
	/**
	 * Default Constructor.
	 * @param config
	 */
	private MaskConfiguration(String config) {
		
		if (config != null) {
			if (config.indexOf(',') > 0) {
				this.maskCharacter = config.charAt(config.indexOf(',') + 1);
				config = config.substring(0, config.indexOf(','));
			}
			
			if (config.startsWith("EL")) {
				this.number = Integer.valueOf(config.substring(2)).intValue();
				this.type = "EL";
			
			} else if (config.startsWith("ER")) {
				this.number = Integer.valueOf(config.substring(2)).intValue();
				this.type = "ER";
			
			} else if (config.startsWith("L")) {
				this.type = "L";
				this.number = Integer.valueOf(config.substring(1)).intValue();
			
			} else if (config.startsWith("R")) {
				this.type = "R";
				this.number = Integer.valueOf(config.substring(1)).intValue();
			
			} else if (config.equals("NONE")) {
				this.type = "NONE";
			
			} 
		
		} else {
			this.type = "ALL";
		}
	}
	
	/**
	 * Mask Contents.
	 * @param content
	 * @return
	 */
	public String maskString(String content) {
		StringBuilder sb = new StringBuilder();
		int contentLength = content.length();
		
		if ("EL".equals(this.type)) {
			if (this.number > contentLength) {
				sb.append(content);
			} else {
				sb.append(content.substring(0, this.number));
				sb.append(getMaskString(contentLength - this.number));
			}
		
		} else if ("ER".equals(this.type)) {
			if (this.number > contentLength) {
				sb.append(content);
			} else {
				sb.append(getMaskString(content.length() - this.number));
				sb.append(content.substring(contentLength - this.number));
			}
		
		} else if ("L".equals(this.type)) {
			if (this.number > contentLength) {
				sb.append(getMaskString(contentLength));
			} else {
				sb.append(getMaskString(this.number));
				sb.append(content.substring(this.number));
			}
		
		} else if ("R".equals(this.type)) {
			if (this.number > contentLength) {
				sb.append(getMaskString(contentLength));
			} else {
				sb.append(content.substring(0, contentLength - this.number));
				sb.append(getMaskString(this.number));
			}
		
		} else if ("NONE".equals(this.type)) {
			sb.append(content);
		
		} else if ("ALL".equals(this.type)) {
			sb.append(getMaskString(this.fixLegnth));
		}
		
		return sb.toString();
	}

	private String getMaskString(int length) {
		return StringUtils.repeat(this.maskCharacter + "", length);
	}
	
	/**
	 * 
	 * @param config
	 * @return
	 */
	public static MaskConfiguration build(String config) {
		return new MaskConfiguration(config);
	}
}
