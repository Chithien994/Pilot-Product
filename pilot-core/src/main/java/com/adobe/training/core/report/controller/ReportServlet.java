package com.adobe.training.core.report.controller;

import java.io.IOException;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.commons.json.JSONObject;

import com.adobe.training.core.base.controller.BaseServlet;
import com.adobe.training.core.report.dao.ReportDao;
import com.day.cq.search.QueryBuilder;

/**
 * The Servlet is used to get reports
 * @author Thien
 *
 */
@SlingServlet(paths = "/bin/report", methods = "GET")
public class ReportServlet extends BaseServlet {

	private static final long serialVersionUID = 8748669978536860121L;

	@Reference
	private QueryBuilder builder;
	
	private ReportDao reportDao = new ReportDao();

	/**
	 * This method is used to get list report
	 */
	protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws IOException {
		response.setHeader("Content-Type", "application/json");
		JSONObject jsonObject = reportDao.getReport(request, builder);
		jsonObject = checkObject(jsonObject);
		response.getWriter().print(jsonObject);
	}
}
