package com.adobe.training.core.product.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jcr.Node;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The controller is used to get list companies
 * @author Thien
 * 
 */
public class CompanyController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyController.class);

	private Resource resource;

	/**
	 * This method is used to get list companies
	 * 
	 * @return List<String>
	 */
	public List<String> getCompaniesList(){

		List<String> companiesList = new ArrayList<String>();

		try {
			ResourceResolver resourceResolver = resource.getResourceResolver();
			Resource resourceParent = resourceResolver.getResource("/var/pilot/companies");

			if(resourceParent != null){
				Iterator<Resource> resourceChild = resourceParent.listChildren();

				if (resourceChild.hasNext()){
					Resource currentResource;
					Node currentNode;

					while (resourceChild.hasNext()) {
						currentResource = resourceChild.next();
						currentNode = currentResource.adaptTo(Node.class);

						if(currentNode.hasProperty("name")){
							companiesList.add(currentNode.getProperty("name").getString());
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Cannot get node", e);
		}
		return companiesList;		
	}

	//Set resource
	public void setResource(Resource resource) {
		this.resource = resource;
	}
}