package com.org.khatabahi.logger.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class MaskContext {
	
	private List<Masker> maskersList = new ArrayList<Masker>();
	
	private Map<String, String> maskAttributes;
	
	/**
	 * 
	 * @return
	 */
	public List<Masker> getMaskersList() {
		return this.maskersList;
	}
	
	/**
	 * 
	 * @param maskAttributes
	 * @param maskGroup
	 */
	public MaskContext(Map<String, String> maskAttributes, String maskGroup) {
		
		this.maskAttributes = maskAttributes;
		
		String maskersString = maskAttributes.get("mask." + maskGroup);
		
		String[] maskers = StringUtils.split(maskersString, ",");
		
		for (String masker : maskers) {
			String regEx = getVal(masker, ".regular");
			if (StringUtils.isEmpty(regEx)) {
				continue;
			}
			
			String expression = getVal(masker, ".expression");

			if ((StringUtils.isEmpty(regEx)) || (regEx.indexOf("NONE") >= 0)) {
				continue;
			}
			
			int groupNumber = Integer.parseInt(getVal(masker, ".regular.group"));

			this.maskersList.add(new Masker(regEx, groupNumber,	expression));
		}
	}
	
	/**
	 * Get Value.
	 * 
	 * @param masker
	 * @param type
	 * @return
	 */
	private String getVal(String masker, String type) {
		String val = maskAttributes.get("mask." + masker + type);
		
		if (val == null) {
			return "";
		}
		
		return val;
	}

	public class Masker {
		
		private Pattern pattern;
		private int groupNumber;
		private MaskConfiguration maskRule;

		public Pattern getPattern() {
			return this.pattern;
		}

		public int getGroupNumber() {
			return this.groupNumber;
		}

		public MaskConfiguration getMaskRule() {
			return this.maskRule;
		}

		protected Masker(String reg, int groupNumber, String expression) {
			this.pattern = Pattern.compile(reg);
			this.groupNumber = groupNumber;
			this.maskRule = MaskConfiguration.build(expression);
		}
	}
}
