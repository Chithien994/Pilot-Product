package com.adobe.training.core.report.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.Session;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.training.core.product.model.Product;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;

/**
 * The Servlet is used to get reports
 * @author Thien
 *
 */
@SlingServlet(paths = "/bin/report", methods = "GET")
public class ReportServlet extends SlingSafeMethodsServlet {

	private static final long serialVersionUID = 8748669978536860121L;

	private final Logger LOGGER = LoggerFactory.getLogger(ReportServlet.class);

	@Reference
	private QueryBuilder builder;

	/**
	 * This method is used to get list report
	 */
	protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws IOException {
		response.setHeader("Content-Type", "application/json");
		JSONObject jsonObject = new JSONObject();
		List<Object> reportsList = new ArrayList<Object>();

		try {
			ResourceResolver resourceResolver = request.getResourceResolver();
			Session session = resourceResolver.adaptTo(Session.class);
			Map<String, String> criteriaMap = new HashMap<String, String>();

			criteriaMap.put("path", Product.REPORT_PATH);
			criteriaMap.put("property", "jcr:primaryType");
			criteriaMap.put("property.value", "nt:file");
			criteriaMap.put("orderby", "@jcr:created");
			criteriaMap.put("orderby.sort", "desc");
			Query query = builder.createQuery(PredicateGroup.create(criteriaMap), session);
			SearchResult result = query.getResult();

			if (result.getTotalMatches() > 0){
				Iterator<Node> nodeIterator = result.getNodes();

				Node currentNode;
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

				while (nodeIterator.hasNext()) {
					currentNode = nodeIterator.next();
					String currentDate = currentNode.getProperty("jcr:created").getString().substring(0, 10);
					String value = simpleDateFormat.format(simpleDateFormat2.parse(currentDate));
					JSONObject json = new JSONObject();
					json.put("value", value);
					json.put("date", currentDate);
					reportsList.add(json);
				}
			} else {
				reportsList = null;
			}
			jsonObject.put("result", reportsList);
		} catch (Exception e) {
			LOGGER.error("Cannot get report", e);
		}
		response.getWriter().print(jsonObject.toString());
	}
}
