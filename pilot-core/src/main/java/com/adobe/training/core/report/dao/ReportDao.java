package com.adobe.training.core.report.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.json.JSONObject;

import com.adobe.training.core.base.model.BaseModel;
import com.adobe.training.core.product.model.Product;
import com.adobe.training.core.report.request.ReportRequest;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;

/**
 * 
 * @author Thien
 *
 */
public class ReportDao extends ReportRequest{
	/**
	 * Used to get products by key word
	 * @param SlingHttpServletRequest
	 * @param QueryBuilder
	 * @return JSONObject
	 */
	public JSONObject getReport(SlingHttpServletRequest request, QueryBuilder builder) {
		ResourceResolver resourceResolver = request.getResourceResolver();
		Session session = resourceResolver.adaptTo(Session.class);
		Map<String, String> criteriaMap = new HashMap<String, String>();
		List<Object> reports = new ArrayList<Object>();
		String keyWord = getKeyWord(request);
		
		criteriaMap.put("path", Product.REPORT_PATH);
		criteriaMap.put("property", "jcr:primaryType");
		criteriaMap.put("property.value", "nt:file");
		criteriaMap.put("1_property", BaseModel.CREATED);
		criteriaMap.put("1_property.value", "%" + keyWord + "%");
		criteriaMap.put("1_property.operation", "like");
		criteriaMap.put("orderby", "@jcr:created");
		criteriaMap.put("orderby.sort", "desc");
		criteriaMap.put("p.offset", String.valueOf(getOffSet(request)));
		criteriaMap.put("p.limit", String.valueOf(getLimit(request)));
		Query query = builder.createQuery(PredicateGroup.create(criteriaMap), session);
		SearchResult result = query.getResult();
		try {	
			if (result.getTotalMatches() > 0){
				Iterator<Node> nodeIterator = result.getNodes();
				Node currentNode;
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

				while (nodeIterator.hasNext()) {
					currentNode = nodeIterator.next();
					String currentDate = currentNode.getProperty(BaseModel.CREATED).getString().substring(0, 10);
					String value = simpleDateFormat.format(simpleDateFormat2.parse(currentDate));
					JSONObject json = new JSONObject();
					json.put("value", value);
					json.put("date", currentDate);
					reports.add(json);
				}
			}
		} catch (Exception e) {
		}
		return getResults(request, result, reports);
	}

}
