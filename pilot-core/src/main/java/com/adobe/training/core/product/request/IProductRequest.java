package com.adobe.training.core.product.request;

import org.apache.sling.api.SlingHttpServletRequest;

import com.adobe.training.core.base.request.BaseIRequest;

/**
 * Interface Request
 * @author Thien
 *@since 2018/10/24
 *
 */
public interface IProductRequest extends BaseIRequest {
	public static final String PRICE_MIN = "price_min";
	public static final String PRICE_MAX = "price_max";
	//Field in Model
	public String getName(SlingHttpServletRequest request);
	public String getQuantity(SlingHttpServletRequest request);
	public String getPrice(SlingHttpServletRequest request);
	public String getCompany(SlingHttpServletRequest request);
	public String getDescription(SlingHttpServletRequest request);
	//Expanded field
	public String getMinPrice(SlingHttpServletRequest request);
	public String getMaxPrice(SlingHttpServletRequest request);
	public String getListId(SlingHttpServletRequest request);
}
