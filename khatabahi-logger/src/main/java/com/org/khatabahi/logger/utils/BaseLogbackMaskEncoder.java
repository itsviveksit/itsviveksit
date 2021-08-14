package com.org.khatabahi.logger.utils;

import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.org.khatabahi.logger.KhatabahiLogger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public abstract class BaseLogbackMaskEncoder extends PatternLayoutEncoder {

	private static final KhatabahiLogger LOGGER = KhatabahiLogger.getLogger(BaseLogbackMaskEncoder.class);
	
	private static final String MASK_PROPERTIES = "mask.properties";
	private static final String MASK_LOGREQUEST = "logrequest";
	public static final String MASK_FLAG = "mask";
	
	private static MaskContext maskContext;

	protected abstract String getCountry();

	/*
	 * (non-Javadoc)
	 * @see ch.qos.logback.core.encoder.LayoutWrappingEncoder#doEncode(java.lang.Object)
	 */
	public void doEncode(ILoggingEvent event) throws IOException {
		String txt = this.layout.doLayout(event);
		txt = maskContent(txt);
		this.outputStream.write(convertToBytes(txt));
		this.outputStream.flush();
	}
	
	/**
	 * Convert String to Bytes
	 * @param s
	 * @return
	 */
	private byte[] convertToBytes(String s) {
		if (getCharset() == null) {
			return s.getBytes();
		}
		
		try {
			return s.getBytes(getCharset().name());
		} catch (UnsupportedEncodingException e) {
		}
		
		throw new IllegalStateException(
				"An existing charset cannot possibly be unsupported.");
	}
	
	/**
	 * Mask Content before logging into file.
	 * 
	 * @param content
	 * @return
	 */
	private String maskContent(String content) {
		if (shouldMaskContents()) {
			initMaskIfNecessary();
			content = MaskUtil.mask(content, maskContext);
		}
	
		return content;
	}
	
	/**
	 * Check if the contents should be masked or not.
	 * @return
	 */
	private boolean shouldMaskContents() {
		Map<String, String> maskAttrs = getMaskAttributes();
		
		if (maskAttrs != null) {
			String mask = maskAttrs.get(MASK_FLAG);
			
			if (mask != null && mask.equalsIgnoreCase("true")) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Get Mask Attributes.
	 * @return
	 */
	private Map<String, String> getMaskAttributes(){

		HashMap<String, String> maskAttrs = null;
		
		try {
			Properties maskProperties = loadMaskProperties();
			
			maskAttrs = new HashMap<String, String>();

			CollectionUtils.mergePropertiesIntoMap(maskProperties, maskAttrs);
		
		} catch (IOException e) {
			LOGGER.error("Error occurred while retrieving the mask attributes {}", e);
		}
		
		return maskAttrs;
	}
	
	/**
	 * Load Mask Properties.
	 * 
	 * @return
	 * @throws IOException
	 */
	private Properties loadMaskProperties() throws IOException {
		Resource resource = new ClassPathResource(MASK_PROPERTIES);
		return PropertiesLoaderUtils.loadProperties(resource);
	}
	
	/**
	 * Initialize Mask Properties.
	 */
	private void initMaskIfNecessary() {
		if (maskContext == null) {
			maskContext = new MaskContext(getMaskAttributes(), MASK_LOGREQUEST);
		}
	}
}