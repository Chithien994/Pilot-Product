package com.adobe.training.core.product.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFormatException;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.jcr.api.SlingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.felix.scr.annotations.Reference;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;

import com.adobe.training.core.base.controller.BaseServlet;
import com.adobe.training.core.product.controller.ProductServlet;
import com.adobe.training.core.product.model.Product;
import com.day.cq.commons.jcr.JcrUtil;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;

@SuppressWarnings("unused")
public class ProductDao {
	
	private final Logger LOG = LoggerFactory.getLogger(ProductDao.class);
	
	/**
	 * Used to add product
	 * @param request
	 * @return JSONObject
	 */
	public JSONObject addProduct(SlingHttpServletRequest request) {
		try {
			return createNode(request);
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Used to edit product
	 * @param request
	 * @return JSONObject
	 */
	public JSONObject editProduct(SlingHttpServletRequest request){
		JSONObject jsonObject = null;
		String productID = request.getParameter(Product.PRODUCT_ID);

		try {
			ResourceResolver resourceResolver = request.getResourceResolver();
			Resource resource = resourceResolver.getResource(Product.PRODUCT_PATH + productID);

			if (resource != null) {
				Session session = resourceResolver.adaptTo(Session.class);
				Node currentNode = resource.adaptTo(Node.class);
				Product product = new Product(currentNode, session);
				jsonObject = setProduct(request, productID, product);
			}
		} catch (Exception e) {
			
		}
		return jsonObject;
	}
	
	/**
	 * Used to delete product by id
	 * @param request
	 * @return JSONObject
	 */
	public JSONObject deleteProduct(SlingHttpServletRequest request){
		String productID = request.getParameter(Product.PRODUCT_ID);
		ResourceResolver resourceResolver = request.getResourceResolver();
		Resource resource = resourceResolver.getResource(Product.PRODUCT_PATH + productID);
		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put(Product.STATUS, Product.FAILED);
			if (resource != null) {
				Session session = resourceResolver.adaptTo(Session.class);
				Node currentNode = resource.adaptTo(Node.class);
				currentNode.remove();
				session.save();
				jsonObject.put(Product.STATUS, Product.SUCCESSED);
			}		
		} catch (Exception e) {
			
		}
		return jsonObject;
	}
	
	/**
	 * Used to get list products by key work
	 * @param request
	 * @return JSONObject
	 */
	public JSONObject getProducts(SlingHttpServletRequest request, QueryBuilder builder){
		ResourceResolver resourceResolver = request.getResourceResolver();
		Session session = resourceResolver.adaptTo(Session.class);
		Map<String, String> criteriaMap = new HashMap<String, String>();

		// Get list products by key work and price
		List<Object> productsList = new ArrayList<Object>();
		String keywork = request.getParameter("keywork");
		String minprice = request.getParameter("price_min");
		String maxprice = request.getParameter("price_max");

		criteriaMap.put("path", Product.PARENT_PATH);
		criteriaMap.put("type", Product.TYPE_NT_UNSTRUCTURED);
		criteriaMap.put("group.and", "true");
		criteriaMap.put("1_group.p.or", "true");
		criteriaMap.put("1_group.1_property", Product.NAME);
		criteriaMap.put("1_group.1_property.value", "%" + keywork + "%");
		criteriaMap.put("1_group.1_property.operation", "like");
		criteriaMap.put("1_group.2_property", Product.COMPANY);
		criteriaMap.put("1_group.2_property.value", "%" + keywork + "%");
		criteriaMap.put("1_group.2_property.operation", "like");
		criteriaMap.put("1_group.3_property", Product.DESCRIPTION);
		criteriaMap.put("1_group.3_property.value", "%" + keywork + "%");
		criteriaMap.put("1_group.3_property.operation", "like");
		criteriaMap.put("2_group.rangeproperty.property", "price");
		criteriaMap.put("2_group.rangeproperty.lowerBound", minprice);
		criteriaMap.put("2_group.rangeproperty.lowerOperation", ">=");
		criteriaMap.put("2_group.rangeproperty.upperBound", maxprice);
		criteriaMap.put("2_group.rangeproperty.upperOperation", "<=");
		criteriaMap.put("orderby", "path");
		criteriaMap.put("orderby.sort", "desc");
		criteriaMap.put("p.limit", "-1");
		Query query = builder.createQuery(PredicateGroup.create(criteriaMap), session);
		SearchResult result = query.getResult();

		if (result.getTotalMatches() > 0){
			Iterator<Node> nodesIterator = result.getNodes();
			while (nodesIterator.hasNext()) {
				Node currentNode = nodesIterator.next();
				productsList.add(new Product(currentNode).getProductTypeJSONObject());
			}
		}
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("keyworkStore", keywork);
			jsonObject.put("priceMin", minprice);
			jsonObject.put("priceMax", maxprice);
			jsonObject.put("result", productsList);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 	jsonObject;
	}

	/**
	 * Used to get a product by id
	 * @param request
	 * @return JSONObject
	 */
	public JSONObject getProductById(SlingHttpServletRequest request){
		ResourceResolver resourceResolver = request.getResourceResolver();
		Session session = resourceResolver.adaptTo(Session.class);
		Resource resource = resourceResolver.getResource(Product.PRODUCT_PATH 
				+ request.getParameter(Product.PRODUCT_ID));
		if (resource == null) return null;

		Node currentNode = resource.adaptTo(Node.class);
		return new Product(currentNode).getProductTypeJSONObject();
		
	}
	
	/**
	 * Create node
	 * @param request
	 * @return JSONObject
	 * @throws RepositoryException
	 */
	private JSONObject createNode(SlingHttpServletRequest request) throws RepositoryException {			
		ResourceResolver resourceResolver = request.getResourceResolver();
		Session session = resourceResolver.adaptTo(Session.class);
		Resource resource = resourceResolver.getResource(Product.PARENT_PATH);

		if (resource == null) {
			resource = resourceResolver.getResource(Product.PARENT_PARENT_PATH);
			if (resource == null) {
				JcrUtil.createPath(Product.PARENT_PARENT_PATH, Product.TYPE_NT_FOLDER, session);
				session.save();
			}
			JcrUtil.createPath(Product.PARENT_PATH, Product.TYPE_SLING_FOLDER, session);
			session.save();
		}
		
		long productID = (new Date()).getTime();
		Node currentNode = JcrUtil.createPath(Product.PRODUCT_PATH + productID,
				Product.TYPE_NT_UNSTRUCTURED, session);
		Product product = new Product(currentNode, session);
		return setProduct(request, String.valueOf(productID), product);
	}
	
	/**
	 * Used to set a product
	 * @param request
	 * @param productID
	 * @param product
	 * @return JSONObject
	 */
	private JSONObject setProduct(SlingHttpServletRequest request, String productID, Product product) {
		product.setId(productID);
		product.setName(request.getParameter(Product.NAME));
		product.setQuantity(request.getParameter(Product.QUANTITY));
		product.setPrice(request.getParameter(Product.PRICE));
		product.setCompany(request.getParameter(Product.COMPANY));
		product.setDescription(request.getParameter(Product.DESCRIPTION));
		JSONObject object = product.getProductTypeJSONObject();
		try {
			if(object==null) {
				object = new JSONObject();
				object.put(Product.STATUS, Product.FAILED);
			}else {
				object.put(Product.STATUS, Product.SUCCESSED);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}
	
}
