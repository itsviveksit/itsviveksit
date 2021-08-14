package com.org.khatabahi.logger.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MaskUtil {
	
	private MaskUtil() {
	}
	
	/**
	 * Mask Contents.
	 * 
	 * @param content
	 * @param pattern
	 * @param group
	 * @param maskConfig
	 * @return
	 */
	public static String mask(String content, Pattern pattern, int group,
			MaskConfiguration maskConfig) {
		
		if (content == null) {
			return null;
		}

		Matcher matcher = pattern.matcher(content);

		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(
					sb,
					matcher.group(1) + maskConfig.maskString(matcher.group(group)));
		}

		matcher.appendTail(sb);

		return sb.toString();
	}
	
	/**
	 * Mask Contents.
	 * 
	 * @param content
	 * @param context
	 * @return
	 */
	public static String mask(String content, MaskContext context) {
		List<MaskContext.Masker> maskers = context.getMaskersList();
		String maskedContent = content;
		
		for (MaskContext.Masker masker : maskers) {
			maskedContent = mask(maskedContent, masker.getPattern(),
					masker.getGroupNumber(), masker.getMaskRule());
		}
		
		return maskedContent;
	}
}