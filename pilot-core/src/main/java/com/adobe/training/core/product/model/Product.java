package com.adobe.training.core.product.model;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.version.VersionException;

import org.apache.sling.commons.json.JSONObject;

import com.adobe.training.core.base.model.BaseModel;

/**
 * Model
 * @author Thien
 *
 */
public class Product extends BaseModel {
	public static final String LIST_PRODUCT = "listProduct";
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
	
	private Node node;
	private Session session;
	
	public Product(Node node, Session session) {
		this.node = node;
		this.session = session;
	}
	
	public Product(Node node) {
		this.node = node;
	}
	
	public String getId() {
		try {
			return node.getProperty(ID).getString();
		} catch (Exception e) {
			
		}
		return "";
	}
	public void setId(String id) {
		try {
			node.setProperty(ID, Long.parseLong(id));
			session.save();
		} catch (Exception e) {
			
		}
	}
	
	public String getName() {
		try {
			return node.getProperty(NAME).getString();
		} catch (Exception e) {
			
		}
		return "";
	}
	public void setName(String name) {
		try {
			node.setProperty(NAME, name);
			session.save();
		} catch (Exception e) {
			
		}
	}
	
	public String getQuantity() {
		try {
			return node.getProperty(QUANTITY).getString();
		} catch (Exception e) {
			
		}
		return "";
	}
	public void setQuantity(String quantity) {
		try {
			node.setProperty(QUANTITY, Integer.parseInt(quantity));
			session.save();
		} catch (Exception e) {
			
		}
	}
	
	public String getPrice() {
		try {
			return node.getProperty(PRICE).getString();
		} catch (Exception e) {
			
		}
		return "";
	}
	public void setPrice(String price) {
		try {
			node.setProperty(PRICE, Long.parseLong(price));
			session.save();
		} catch (Exception e) {
			
		}
	}
	
	public String getCompany() {
		try {
			return node.getProperty(COMPANY).getString();
		} catch (Exception e) {
			
		}
		return "";
	}
	public void setCompany(String company) {
		try {
			node.setProperty(COMPANY, company);
			session.save();
		} catch (Exception e) {
			
		}
	}
	
	public String getDescription() {
		try {
			return node.getProperty(DESCRIPTION).getString();
		} catch (Exception e) {
			
		}
		return "";
	}
	public void setDescription(String description) {
		try {
			node.setProperty(DESCRIPTION, description);
			session.save();
		} catch (Exception e) {
			
		}
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
