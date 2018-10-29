package com.adobe.training.core.product.controller;

import java.io.IOException;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.commons.json.JSONObject;

import com.adobe.training.core.base.controller.BaseServlet;
import com.adobe.training.core.product.dao.ProductDao;
import com.adobe.training.core.product.model.Product;
import com.day.cq.search.QueryBuilder;

/**
 * The servlet is used to add, edit, delete and search product
 * @author Thien
 * 
 */
@SlingServlet(paths = "/bin/servlet/product", methods = { "GET", "POST", "DELETE", "PUT" })
public class ProductServlet extends BaseServlet{
	
	@Reference
	private QueryBuilder builder;
	
	private static final String SEARCH = "search";

	private static final long serialVersionUID = -7054668461323865426L;
	
	private ProductDao productDao = new ProductDao();
	
	/**
	 * This method is used to get a product by id or get list products by key work
	 */
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		response.setHeader("Content-Type", "application/json");
		JSONObject object;
		if (request.getParameter(SEARCH) != null) {
			object = productDao.getProducts(request, builder);
		}else {
			object = productDao.getProductById(request);
		}
		object = checkObject(object);
		response.getWriter().print(object.toString());
	}
	
	/**
	 * This method is used to add a product
	 */
	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		response.setHeader("Content-Type", "application/json");
		JSONObject object = null;
		if (request.getParameter(Product.LIST_PRODUCT) == null){
			object = productDao.addProduct(request);
		}
		object = checkObject(object);
		response.getWriter().print(object.toString());
	}

	/**
	 * This method is used to delete a product
	 */
	@Override
	protected void doDelete(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		response.setHeader("Content-Type", "application/json");
		JSONObject object = null;
		object = checkObject(productDao.deleteProduct(request));
		response.getWriter().print(object.toString());
	}


	/**
	 * This method is used to edit a product
	 */
	@Override
	protected void doPut(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		response.setHeader("Content-Type", "application/json");
		JSONObject object = null;
		object = checkObject(productDao.editProduct(request));
		response.getWriter().print(object.toString());
	}
	
}
