package com.eimapi.starter.kong.bean;

import java.util.Map;

import com.eimapi.starter.kong.exception.KongStarterException;
import com.eimapi.starter.kong.rest.ApiObject;
import com.eimapi.starter.kong.rest.ServiceObject;

/**
 * Interface to define the API candidate search
 * 
 * @author Denys G. Santos
 * @since 0.0.1
 * @version 0.0.1
 */
public interface ApiCandidateSearch {

	/**
	 * Search by all APIs that exist on the microservice
	 *  
	 * @return {@link Map} of {@link ApiObject}
	 * @throws KongStarterException if any error occurs
	 */
	Map<String, ServiceObject> search() throws KongStarterException;
	
}
