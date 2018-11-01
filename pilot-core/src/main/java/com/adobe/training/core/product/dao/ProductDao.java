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
import com.adobe.training.core.base.request.BaseIRequest;
import com.adobe.training.core.product.controller.ProductServlet;
import com.adobe.training.core.product.model.Product;
import com.adobe.training.core.product.request.IProductRequest;
import com.adobe.training.core.product.request.ProductRequest;
import com.day.cq.commons.jcr.JcrUtil;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;

/**
 * Data processing of a model.
 * @author Thien
 * @since 2018/10/24
 *
 */

@SuppressWarnings("unused")
public class ProductDao extends ProductRequest{
	
	private final Logger LOG = LoggerFactory.getLogger(ProductDao.class);
	
	/**
	 * Used to add product
	 * @param SlingHttpServletRequest
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
	 * @param SlingHttpServletRequest
	 * @return JSONObject
	 */
	public JSONObject editProduct(SlingHttpServletRequest request){
		JSONObject jsonObject = null;
		String id = getId(request);

		try {
			ResourceResolver resourceResolver = request.getResourceResolver();
			Resource resource = resourceResolver.getResource(Product.PRODUCT_PATH + id);

			if (resource != null) {
				Session session = resourceResolver.adaptTo(Session.class);
				Node currentNode = resource.adaptTo(Node.class);
				Product product = new Product(currentNode, session);
				jsonObject = setProduct(request, id, product);
			}
		} catch (Exception e) {
			
		}
		return jsonObject;
	}
	
	/**
	 * Used to delete product by id
	 * @param SlingHttpServletRequest
	 * @param id
	 * @return JSONObject
	 */
	public JSONObject deleteProduct(SlingHttpServletRequest request, String id){
		ResourceResolver resourceResolver = request.getResourceResolver();
		Resource resource = resourceResolver.getResource(Product.PRODUCT_PATH + id);
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
	 * Used to delete product by list id
	 * @param SlingHttpServletRequest
	 * @return JSONObject
	 */
	public JSONObject deleteProducts(SlingHttpServletRequest request){
		String[] listID = getListId(request).split(",");
		JSONObject jsonObject = new JSONObject();
		try {
			for(int i = 0; i < listID.length; i++){
				deleteProduct(request,listID[i]);
			}
			jsonObject.put(Product.STATUS, Product.SUCCESSED);
		} catch (Exception e) {
			
		}
		return jsonObject;
	}
	
	/**
	 * Used to get list products by key work
	 * @param SlingHttpServletRequest
	 * @param QueryBuilder
	 * @return JSONObject
	 */
	public JSONObject getProducts(SlingHttpServletRequest request, QueryBuilder builder){
		ResourceResolver resourceResolver = request.getResourceResolver();
		Session session = resourceResolver.adaptTo(Session.class);
		Map<String, String> criteriaMap = new HashMap<String, String>();

		// Get list products by key work and price
		List<Object> products = new ArrayList<Object>();
		String keyWord = getKeyWord(request);
		
		criteriaMap.put("path", Product.PARENT_PATH);
		criteriaMap.put("type", Product.TYPE_NT_UNSTRUCTURED);
		criteriaMap.put("group.and", "true");
		criteriaMap.put("1_group.p.or", "true");
		criteriaMap.put("1_group.1_property", Product.NAME);
		criteriaMap.put("1_group.1_property.value", "%" + keyWord + "%");
		criteriaMap.put("1_group.1_property.operation", "like");
		criteriaMap.put("1_group.2_property", Product.COMPANY);
		criteriaMap.put("1_group.2_property.value", "%" + keyWord + "%");
		criteriaMap.put("1_group.2_property.operation", "like");
		criteriaMap.put("1_group.3_property", Product.DESCRIPTION);
		criteriaMap.put("1_group.3_property.value", "%" + keyWord + "%");
		criteriaMap.put("1_group.3_property.operation", "like");
		criteriaMap.put("2_group.rangeproperty.property", "price");
		criteriaMap.put("2_group.rangeproperty.lowerBound", getMinPrice(request));
		criteriaMap.put("2_group.rangeproperty.lowerOperation", ">=");
		criteriaMap.put("2_group.rangeproperty.upperBound", getMaxPrice(request));
		criteriaMap.put("2_group.rangeproperty.upperOperation", "<=");
		criteriaMap.put("p.guessTotal", "true");
		criteriaMap.put("orderby", "path");
		criteriaMap.put("orderby.sort", "desc");
		criteriaMap.put("p.offset", String.valueOf(getOffSet(request)));
		criteriaMap.put("p.limit", String.valueOf(getLimit(request)));
		Query query = builder.createQuery(PredicateGroup.create(criteriaMap), session);
		SearchResult result = query.getResult();

		if (result.getTotalMatches() > 0){
			Iterator<Node> nodesIterator = result.getNodes();
			while (nodesIterator.hasNext()) {
				Node currentNode = nodesIterator.next();
				products.add(new Product(currentNode).getProductTypeJSONObject());
			}
		}
		return 	getResults(request, result, products);
	}

	/**
	 * Used to get a product by id
	 * @param SlingHttpServletRequest
	 * @return JSONObject
	 */
	public JSONObject getProductById(SlingHttpServletRequest request){
		ResourceResolver resourceResolver = request.getResourceResolver();
		Session session = resourceResolver.adaptTo(Session.class);
		Resource resource = resourceResolver.getResource(Product.PRODUCT_PATH 
				+ getId(request));
		if (resource == null) return null;

		Node currentNode = resource.adaptTo(Node.class);
		return new Product(currentNode).getProductTypeJSONObject();
		
	}
	
	/**
	 * Create node
	 * @param SlingHttpServletRequest
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
		
		long id = (new Date()).getTime();
		Node currentNode = JcrUtil.createPath(Product.PRODUCT_PATH + id,
				Product.TYPE_NT_UNSTRUCTURED, session);
		Product product = new Product(currentNode, session);
		return setProduct(request, String.valueOf(id), product);
	}
	
	/**
	 * Used to set a product
	 * @param SlingHttpServletRequest
	 * @param id
	 * @param Product
	 * @return JSONObject
	 */
	private JSONObject setProduct(SlingHttpServletRequest request, String id, Product product) {
		product.setId(id);
		product.setName(getName(request));
		product.setQuantity(getQuantity(request));
		product.setPrice(getPrice(request));
		product.setCompany(getCompany(request));
		product.setDescription(getDescription(request));
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
