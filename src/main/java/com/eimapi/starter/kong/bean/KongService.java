package com.eimapi.starter.kong.bean;

import java.util.List;

import com.eimapi.starter.kong.exception.KongStarterException;
import com.eimapi.starter.kong.rest.RouteObject;
import com.eimapi.starter.kong.rest.ServiceObject;

public interface KongService {
	

	void buildServiceAndRoutes(ServiceObject service);
	
	/**
	 * Create new Kong Service 
	 * 
	 * @param object the {@link ServiceObject}
	 * @return {@link ServiceObject} response of query execution
	 */
	ServiceObject createService(ServiceObject object) throws KongStarterException;
	
	/**
	 * remove kong service
	 * 
	 * @param id the service ID
	 */
	void removeService(String id) throws KongStarterException;
	
	/**
	 * Create new Kong Route
	 * 
	 * @param object the {@link RouteObject}
	 * @return {@link RouteObject} response of query execution
	 */
	RouteObject createRoute(RouteObject object) throws KongStarterException;
	
	/**
	 * remove kong route
	 * 
	 * @param id the route ID
	 */
	void removeRoute(String id) throws KongStarterException;
	
	/**
	 * get routes from service
	 * 
	 * @param serviceObject
	 * @return
	 * @throws KongStarterException
	 */
	List<RouteObject> getRoutes(ServiceObject serviceObject) throws KongStarterException;
	
	/**
	 * Get service from name
	 * 
	 * @param name the service name
	 * @return ServiceObject the kong service object 
	 * @throws KongStarterException if any error occur
	 */
	ServiceObject getService(String name);
}
