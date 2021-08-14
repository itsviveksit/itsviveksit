package com.org.khatabahi.logger;

import org.apache.camel.Exchange;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Encoder;
import org.owasp.esapi.errors.ConfigurationException;
import org.owasp.esapi.errors.ValidationException;
import org.owasp.esapi.reference.DefaultEncoder;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Map;


public final class KhatabahiLogger {

	private Encoder defaultEncoder ;
	
	private org.slf4j.Logger logger;

	private KhatabahiLogger(org.slf4j.Logger logger){
		this.defaultEncoder = DefaultEncoder.getInstance();
		this.logger = logger;
	}
	
	/**
	 * Put contextual information into the MDC.
	 * 
	 * @param key
	 * @param value
	 */
	public static void put(String key, String value) {
		MDC.put(key, value);
	}
	
	/**
	 * Put contextual information into the MDC.
	 * @param map
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void put(Map<String, String> map) {
		Map currentMap = MDC.getCopyOfContextMap();
		
		if ((currentMap != null) && (!currentMap.isEmpty())) {
			map.putAll(currentMap);
		}
		
		MDC.setContextMap(map);
	}
	
	/**
	 * Clear MDC (Mapped Diagnostic Context).
	 */
	public static void clear() {
		MDC.clear();
	}
	
	/**
	 * Get Logger.
	 * @param c
	 * @return
	 */
	public static KhatabahiLogger getLogger(Class<?> c) {
		org.slf4j.Logger logger = LoggerFactory.getLogger(c);
		return new KhatabahiLogger(logger);
	}
	
	/**
	 * Get Logger.
	 * @param s
	 * @return
	 */
	public static KhatabahiLogger getLogger(String s) {
		org.slf4j.Logger logger = LoggerFactory.getLogger(s);
		return new KhatabahiLogger(logger);
	}

	/**
	 * Log input as an Error.
	 * @param s
	 */
	public void error(String s) {
		this.logger.error(cleanMessage(s));
	}
	
	/**
	 * Log input as an Error.
	 * @param s
	 * @param o
	 */
	public void error(String s, Object o) {
		this.logger.error(cleanMessage(s), clean(o));
	}
	
	/**
	 * Log input as an Error.
	 * @param s
	 * @param o
	 */
	public void logEsapi(String s, Object o) {
		this.logger.error(s, o);
	}
	
	/**
	 * Log input (Message & Exception) as an Error.
	 * @param s
	 * @param t
	 */
	public void error(String s, Throwable t) {
		this.logger.error(cleanMessage(s), t);
	}
	
	/**
	 * Log input as an Error (using a message template) + Object array ( which contains values that will be substituted).
	 * @param s
	 * @param os
	 */
	public void error(String s, Object... os) {
		this.logger.error(cleanMessage(s), cleanArray(os));
	}
	
	/**
	 * Log input as a warning.
	 * @param s
	 */
	public void warn(String s) {
		this.logger.warn(cleanMessage(s));
	}
	
	/**
	 * Log input as an warning.
	 * @param s
	 * @param o
	 */
	public void warn(String s, Object o) {
		this.logger.warn(cleanMessage(s), clean(o));
	}
	
	/**
	 * Log input (Message & Exception) as an warning.
	 * @param s
	 * @param t
	 */
	public void warn(String s, Throwable t) {
		this.logger.warn(cleanMessage(s), t);
	}
	
	/**
	 * Log as a warning (using a message template) + Object array ( which contains values that will be substituted).
	 * @param s
	 * @param os
	 */
	public void warn(String s, Object... os) {
		this.logger.warn(cleanMessage(s), cleanArray(os));
	}
	
	/**
	 * Log as a informational message.
	 * @param s
	 */
	public void info(String s) {
		this.logger.info(cleanMessage(s));
	}
	
	/**
	 * Log as a informational message.
	 * @param s
	 * @param o
	 */
	public void info(String s, Object o) {
		this.logger.info(cleanMessage(s), clean(o));
	}
	
	/**
	 * Log input (message & exception) as informational.
	 * @param s
	 * @param t
	 */
	public void info(String s, Throwable t) {
		this.logger.info(cleanMessage(s), t);
	}
	
	/**
	 * Log as a info (using a message template) + Object array ( which contains values that will be substituted).
	 * @param s
	 * @param os
	 */
	public void info(String s, Object... os) {
		this.logger.info(cleanMessage(s), cleanArray(os));
	}

	/**
	 * Log as debug info.
	 * @param s

	 */
	public void debug(String s) {
		this.logger.debug(cleanMessage(s));
	}

	/**
	 * Log as debug info.
	 * @param s
	 * @param o
	 */
	public void debug(String s, Object o) {
		this.logger.debug(cleanMessage(s), clean(o));
	}

	/**
	 * Log input (message & exception) as debug.
	 * @param s
	 * @param t
	 */
	public void debug(String s, Throwable t) {
		this.logger.debug(cleanMessage(s), t);
	}
	
	/**
	 * Log as debug info (using a message template) + Object array ( which contains values that will be substituted).
	 * @param s
	 * @param os
	 */
	public void debug(String s, Object... os) {
		this.logger.debug(cleanMessage(s), cleanArray(os));
	}
	
	/**
	 * Log as trace info.
	 * @param s
	 */
	public void trace(String s) {
		this.logger.trace(cleanMessage(s));
	}
	
	/**
	 * Log as trace info.
	 * @param s
	 * @param o
	 */
	public void trace(String s, Object o) {
		this.logger.trace(cleanMessage(s), clean(o));
	}
	
	/**
	 * Log input (message & exception) as trace information.
	 * @param s
	 * @param t
	 */
	public void trace(String s, Throwable t) {
		this.logger.trace(cleanMessage(s), t);
	}

	/**
	 * Log as trace info (using a message template) + Object array ( which contains values that will be substituted).
	 * @param s
	 */
	public void trace(String s, Object... os) {
		this.logger.trace(cleanMessage(s), cleanArray(os));
	}
	
	/**
	 * 
	 * @param args
	 */
	private Object[] cleanArray(Object... args) {
		
		if (args != null && !args.getClass().isArray()) {
			return args;
		}
		
		String tmp = null;
		if (args != null) {
			for (int i = 0; i < args.length; i++) {			
				if (args[i] instanceof String) {
					tmp = (String) args[i];
					args[i] = cleanMessage(tmp);
				}
			}
		}
		
		return args;
	}
	
	/**
	 * Cleanse String.
	 * 
	 * @param input
	 * @return
	 */
	private Object clean(Object input) {
				
		if (input instanceof String) {
			return cleanMessage((String)input);
		}
		
		return input;
	}
	
    /**
     * Removes newline characters from the provided String then encodes it for HTML before returning the 'clean' version
     * to the caller.
     * 
     * @param message
     *            Original message to clean.
     * @return Cleaned message.
     */
	private final String cleanMessage(String message) {
		KhatabahiLogger LOGGER_ESAPI = getLogger("SPLEsapi");
		
		try {
			ESAPI.validator().getValidInput("input", message,"SPLString", message.length(), true, false);
		} catch (ValidationException e) {
			LOGGER_ESAPI.logEsapi("ValidationException [message]: {}", message);
			message = defaultEncoder.encodeForHTML(message);
		} catch (ConfigurationException e) {
			LOGGER_ESAPI.logEsapi("ConfigurationException [message]: {}", message);
			message = defaultEncoder.encodeForHTML(message);
		} catch(Exception e) {
			LOGGER_ESAPI.logEsapi("Exception [message]: {}", message);
			message = defaultEncoder.encodeForHTML(message);
		}
        
        return message;
    }	
	
	public static void logTotalTime(Exchange exch) {
		if(exch.getProperty("HOST_PROCESS_TIME") != null)  {
			long totalTimeInMilliSec = (long) exch.getProperty("HOST_PROCESS_TIME");
			StringBuilder sb = new StringBuilder();
			sb.append(totalTimeInMilliSec);
			sb.append("ms");
			MDC.put("totalTimeByHost",sb.toString());
		}
	}
	
	public static void removeFrmMDC() {
		MDC.remove("totalTimeByHost");
	}
	

}