package com.adobe.training.core.base.request;

import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

import com.day.cq.search.result.SearchResult;

/**
 * Base Request
 * @author Thien
 *@since 2018/10/24
 *
 */
public class BaseRequest implements BaseIRequest{

	@Override
	public String getId(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub
		return request.getParameter(BaseIRequest.ID);
	}

	@Override
	public String getKeyWord(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub
		return request.getParameter(BaseIRequest.KEY_WORD);
	}

	@Override
	public Integer getLimit(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub
		return Integer.parseInt(request.getParameter(BaseIRequest.LIMIT));
	}

	@Override
	public Integer getOffSet(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub
		return Integer.parseInt(request.getParameter(BaseIRequest.OFFSET));
	}
	
	/**
	 * 
	 * @param SlingHttpServletRequest
	 * @param SearchResult
	 * @param List<Object>
	 * @return JSONObject
	 */
	public JSONObject getResults(SlingHttpServletRequest request, SearchResult result, List<Object> list) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(BaseIRequest.COUNT, result.getTotalMatches());
			jsonObject.put(BaseIRequest.OFFSET, getOffSet(request));
			jsonObject.put(BaseIRequest.LIMIT, getLimit(request));
			jsonObject.put(BaseIRequest.RESULT, list);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsonObject;
	}

}
