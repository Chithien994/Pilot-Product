package com.adobe.training.core.product.model;

import javax.jcr.Node;
import javax.jcr.Session;

import org.apache.sling.commons.json.JSONObject;

import com.adobe.training.core.base.model.BaseModel;

/**
 * Model
 * @author Thien
 *@since 2018/10/24
 *
 */
public class Product extends BaseModel {
	public static final String LIST_ID = "list_id";
	//Path
	public static final String PARENT_PARENT_PATH 	= "/var/pilot";
	public static final String PARENT_PATH 			= "/var/pilot/products";
	public static final String PRODUCT_PATH 		= "/var/pilot/products/product_";
	public static final String REPORT_PATH 			= "/var/pilot/reports";
	//Fields
	public static final String NAME 		= "name";
	public static final String QUANTITY 	= "quantity";
	public static final String PRICE 		= "price";
	public static final String COMPANY 		= "company";
	public static final String DESCRIPTION 	= "description";
	public static final String PRODUCT_ID 	= "productID";
	
	
	public Product(Node node, Session session) {
		this.node = node;
		this.session = session;
	}
	
	public Product(Node node) {
		this.node = node;
	}
	
	public String getId() {
		return getStringProperty(ID);
	}
	
	public void setId(String id) {
		setLongProperty(ID, Long.parseLong(id));
	}
	
	public String getName() {
		return getStringProperty(NAME);
	}
	
	public void setName(String name) {
		setStringProperty(NAME, name);
	}
	
	public String getQuantity() {
		return getStringProperty(QUANTITY);
	}
	public void setQuantity(String quantity) {
		setIntProperty(QUANTITY, Integer.parseInt(quantity));
	}
	
	public String getPrice() {
		return getStringProperty(PRICE);
	}
	
	public void setPrice(String price) {
		setLongProperty(PRICE, Long.parseLong(price));
	}
	
	public String getCompany() {
		return getStringProperty(COMPANY);
	}
	public void setCompany(String company) {
		setStringProperty(COMPANY, company);
	}
	
	public String getDescription() {
		return getStringProperty(DESCRIPTION);
	}
	
	public void setDescription(String description) {
		setStringProperty(DESCRIPTION, description);
	}
	
	/**
	 * Get a product type JSONObject
	 * @param node
	 * @return JSONObject
	 */
	@SuppressWarnings("unused")
	public JSONObject getProductTypeJSONObject() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(Product.ID, getId());
			jsonObject.put(Product.NAME, getName());
			jsonObject.put(Product.QUANTITY, getQuantity());
			jsonObject.put(Product.PRICE, getPrice());
			jsonObject.put(Product.COMPANY, getCompany());
			jsonObject.put(Product.DESCRIPTION, getDescription());
		}  catch (Exception e) {
			jsonObject = null;
		}
		return jsonObject;
	}
	
}
