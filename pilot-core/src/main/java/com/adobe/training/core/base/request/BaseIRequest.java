package com.adobe.training.core.base.request;

import org.apache.sling.api.SlingHttpServletRequest;
/**
 * Base Interface Request
 * @author Thien
 *@since 2018/10/24
 *
 */
public interface BaseIRequest {
	public static final String KEY_WORD = "key_word";
	public static final String LIMIT = "limit";
	public static final String OFFSET = "offset";
	public static final String RESULT = "result";
	public static final String COUNT = "count";
	public static final String ID = "id";
	
	public String getId(SlingHttpServletRequest request);
	public String getKeyWord(SlingHttpServletRequest request);
	public Integer getLimit(SlingHttpServletRequest request);
	public Integer getOffSet(SlingHttpServletRequest request);
}
