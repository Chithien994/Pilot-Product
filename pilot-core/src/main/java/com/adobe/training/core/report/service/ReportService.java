package com.adobe.training.core.report.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.PropertyUnbounded;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.apache.sling.commons.scheduler.Scheduler;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.training.core.product.model.Product;
import com.day.cq.commons.jcr.JcrUtil;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.search.Query;

@Component(immediate = true, metatype = true,
label = "Report Service")
@Service(value = Runnable.class)
@Property(name = "scheduler.expression", value = "0 * * ? * *") // Every minute
public class ReportService implements Runnable {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportService.class);
	@Reference
	private Scheduler scheduler;

	@Reference
	private SlingRepository repository;
	
	@Reference
	private ResourceResolverFactory resolverFactory;

	@Reference
	private QueryBuilder builder;
	
	@Property(label = "Path", description = "Report path", value = Product.PARENT_PATH)
	public static final String REPORT_PATH = "reportPath";
	private String reportPath;
	
	@Property(unbounded = PropertyUnbounded.ARRAY, cardinality = 10, label = "Properties", description = "Report properties",
			value = { Product.ID, Product.NAME })
	public static final String REPORT_PROPERTIES = "reportProperties";
	private String[] reportProperties;
	
	protected void activate(ComponentContext componentContext){
		configure(componentContext.getProperties());
		exportFile();
	}
	protected void configure(Dictionary<?, ?> properties) {
		this.reportPath = PropertiesUtil.toString(properties.get(REPORT_PATH), null);
		this.reportProperties = PropertiesUtil.toStringArray(properties.get(REPORT_PROPERTIES));
	}

	@Override
	public void run() {
		LOGGER.info("running now");	
		exportFile();
	}
	
	/**
	 * The method is used to export file report csv
	 * 
	 * @param reportPath
	 * @param reportProperties
	 */
	private void exportFile() {
		try {

			// Get session admin
			Session session = repository.login(new SimpleCredentials("admin",
				      "admin".toCharArray()),"crx.default");
            
			StringBuffer stringBuffer = new StringBuffer();

			// Get list properties add header file csv
			for (int i = 0; i < this.reportProperties.length; i++) {
				stringBuffer.append(this.reportProperties[i] + ",");
			}

			// Delete the last ","
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
			List<Map<String,String>> productsList = this.getProductsList();

			if(productsList != null){

				for (Map<String, String> product : productsList) {
					stringBuffer.append(System.getProperty("line.separator"));

					for (int i = 0; i < this.reportProperties.length; i++) {
						stringBuffer.append(product.get(this.reportProperties[i]) + ",");
					}
					stringBuffer.deleteCharAt(stringBuffer.length() - 1);
				}
			}
			InputStream inputStream = new ByteArrayInputStream(stringBuffer.toString().getBytes(StandardCharsets.UTF_8));
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
			String time = simpleDateFormat.format(new Date());
			String fileName = "report_" + time + ".csv";

			if (!session.nodeExists(Product.REPORT_PATH)) {

				if (!session.nodeExists(Product.PARENT_PARENT_PATH)) {
					JcrUtil.createPath(Product.PARENT_PARENT_PATH, Product.TYPE_NT_FOLDER, session);
					session.save();
				}
				JcrUtil.createPath(Product.REPORT_PATH, Product.TYPE_SLING_FOLDER, session);
				session.save();
			}
			Node reportNode = session.getNode(Product.REPORT_PATH);
			JcrUtils.putFile(reportNode, fileName, "text/csv;charset=UTF-8", inputStream);
			session.save();
			inputStream.close();
		} catch (Exception e) {
			LOGGER.error("Cannot export report", e);
		}
	}

	/**
	 * This method is used to get list product with properties
	 * 
	 * @param reportPath
	 * @param reportProperties
	 * @return List<Map<String,String>>
	 */
	private List<Map<String,String>> getProductsList() {

		List<Map<String,String>> productsList = new ArrayList<Map<String,String>>();

		try {
			Session session = repository.login(new SimpleCredentials("admin",
				      "admin".toCharArray()),"crx.default");
			Map<String, String> criteriaMap = new HashMap<String, String>();

			criteriaMap.put("path", this.reportPath);
			criteriaMap.put("type", Product.TYPE_NT_UNSTRUCTURED);
			criteriaMap.put("orderby", "path");
			criteriaMap.put("p.limit", "-1");
			Query query = builder.createQuery(PredicateGroup.create(criteriaMap), session);
			SearchResult result = query.getResult();

			if (result.getTotalMatches() > 0){
				Iterator<Node> nodeIterator = result.getNodes();
				Node currentNode;

				while (nodeIterator.hasNext()) {
					currentNode = nodeIterator.next();
					Map<String, String> productMap = new HashMap<String, String>();

					for (int i = 0; i < this.reportProperties.length; i++) {

						if (currentNode.hasProperty(this.reportProperties[i])) {
							productMap.put(this.reportProperties[i], currentNode.getProperty(this.reportProperties[i]).getString());
						} else {
							productMap.put(this.reportProperties[i], "");
						}
					}
					productsList.add(productMap);
				}
			} else {
				productsList = null;
			}
		} catch (Exception e) {
			LOGGER.error("Cannot get node", e);
		}
		return productsList;
	}
	private Session loginService() {
		// TODO Auto-generated method stub
		return null;
	}
}