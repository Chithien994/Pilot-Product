package com.adobe.training.core.base.controller;

import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BaseServlet extends SlingAllMethodsServlet {
	
	//Messages
	public static final String STATUS = "status";
	public static final String FAILED = "failed";
	public static final String EMPTY = "empty";
	public static final String SUCCESSED = "successed";
	private static final long serialVersionUID = 5380306835770821735L;
	private final Logger LOG = LoggerFactory.getLogger(BaseServlet.class);
	
	@SuppressWarnings("unused")
	protected JSONObject checkObject(JSONObject object) {
		if(object==null) {
			try {
				object = new JSONObject();
				object.put(STATUS, EMPTY);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return object;
	}

}
