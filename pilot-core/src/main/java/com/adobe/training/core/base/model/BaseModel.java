package com.adobe.training.core.base.model;

import java.util.Calendar;

import javax.jcr.Node;
import javax.jcr.Session;

/**
 * Base model
 * @author Thien
 * @since 2018/10/24
 * 
 */
public class BaseModel {
	//Messages
	public static final String STATUS = "status";
	public static final String FAILED = "failed";
	public static final String SUCCESSED = "successed";
	//Types
	public static final String TYPE_NT_UNSTRUCTURED = "nt:unstructured";
	public static final String TYPE_SLING_FOLDER = "sling:Folder";
	public static final String TYPE_NT_FOLDER = "nt:folder";
	//Fields
	public static final String ID = "id";
	public static final String CREATED = "jcr:created";
	
	public Node node;
	public Session session;
	
	/**
	 * Get Property with String type
	 * @param key
	 * @param value
	 * 
	 */
	public String getStringProperty(String key) {
		try {
			return node.getProperty(key).getString();
		} catch (Exception e) {
			
		}
		return "";
	}
	
	/**
	 * Set Property with String type
	 * @param key
	 * @param value
	 * 
	 */
	public void setStringProperty(String key, String value) {
		try {
			node.setProperty(key, value);
			session.save();
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * Get Property with Long type
	 * @param key
	 * @param value
	 * 
	 */
	public Long getLongProperty(String key) {
		try {
			return node.getProperty(key).getLong();
		} catch (Exception e) {
			
		}
		return null;
	}
	/**
	 * Set Property with Long type
	 * @param key
	 * @param value
	 * 
	 */
	public void setLongProperty(String key, Long value) {
		try {
			node.setProperty(key, value);
			session.save();
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * Get Property with Double type
	 * @param key
	 * @param value
	 * 
	 */
	public Double getDoubleProperty(String key) {
		try {
			return node.getProperty(key).getDouble();
		} catch (Exception e) {
			
		}
		return null;
	}
	/**
	 * Set Property with Double type
	 * @param key
	 * @param value
	 * 
	 */
	public void setDoubleProperty(String key, Double value) {
		try {
			node.setProperty(key, value);
			session.save();
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * Set Property with Integer type
	 * @param key
	 * @param value
	 * 
	 */
	public void setIntProperty(String key, Integer value) {
		try {
			node.setProperty(key, value);
			session.save();
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * Get Property with Boolean type
	 * @param key
	 * @param value
	 * 
	 */
	public Boolean getBooleanProperty(String key) {
		try {
			return node.getProperty(key).getBoolean();
		} catch (Exception e) {
			
		}
		return null;
	}
	/**
	 * Set Property with Boolean type
	 * @param key
	 * @param value
	 * 
	 */
	public void setBooleanProperty(String key, Boolean value) {
		try {
			node.setProperty(key, value);
			session.save();
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * Get Property with Calendar type
	 * @param key
	 * @param value
	 * 
	 */
	public Calendar getDateProperty(String key) {
		try {
			return node.getProperty(key).getDate();
		} catch (Exception e) {
			
		}
		return null;
	}
	/**
	 * Set Property with Calendar type
	 * @param key
	 * @param value
	 * 
	 */
	public void setCalendarProperty(String key, Calendar value) {
		try {
			node.setProperty(key, value);
			session.save();
		} catch (Exception e) {
			
		}
	}
}
