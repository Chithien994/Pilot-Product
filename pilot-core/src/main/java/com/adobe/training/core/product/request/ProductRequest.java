package com.adobe.training.core.product.request;

import org.apache.sling.api.SlingHttpServletRequest;

import com.adobe.training.core.base.request.BaseRequest;
import com.adobe.training.core.product.model.Product;

/**
 * Product Request
 * @author Thien
 *@since 2018/10/24
 *
 */
public class ProductRequest extends BaseRequest implements IProductRequest{

	@Override
	public String getName(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub
		return request.getParameter(Product.NAME);
	}

	@Override
	public String getQuantity(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub
		return request.getParameter(Product.QUANTITY);
	}

	@Override
	public String getPrice(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub
		return request.getParameter(Product.PRICE);
	}

	@Override
	public String getCompany(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub
		return request.getParameter(Product.COMPANY);
	}

	@Override
	public String getDescription(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub
		return request.getParameter(Product.DESCRIPTION);
	}

	@Override
	public String getMinPrice(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub
		return request.getParameter(IProductRequest.PRICE_MIN);
	}

	@Override
	public String getMaxPrice(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub
		return request.getParameter(IProductRequest.PRICE_MAX);
	}

	@Override
	public String getListId(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub
		return request.getParameter(Product.LIST_ID);
	}
	

}
