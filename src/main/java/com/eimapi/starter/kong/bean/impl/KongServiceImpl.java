package com.eimapi.starter.kong.bean.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Component;

import com.eimapi.starter.kong.bean.KongService;
import com.eimapi.starter.kong.exception.KongStarterException;
import com.eimapi.starter.kong.rest.RouteObject;
import com.eimapi.starter.kong.rest.ServiceObject;

/**
 * Implementation of Kong service
 * @author dengonca
 *
 */
@Component
@Configuration
public class KongServiceImpl extends AbstractKongService implements KongService {
	private Logger logger = LoggerFactory.getLogger(KongServiceImpl.class);

	private static final String KONG_SERVICE_PATH = "services";
	private static final String KONG_ROUTE_PATH = "routes";
	
	private static final String MODE_REBUILD = "REBUILD";
	private static final String MODE_CREATE = "CREATE";

	@Value("${kong.server.url:http://localhost:8001}")
	private String kongURL;

	@Value("${kong.server.user:}")
	private String user;

	@Value("${kong.server.password:}")
	private String password;
	
	//MODE: REBUILD || CREATE
	@Value("${kong.build.mode:REBUILD}")
	private String mode;
	
	
	@PostConstruct
	public void init() {
		if(user != null && !user.isEmpty()) {
			logger.info("Credential are added to rest template.");
			getRestTemplate().getInterceptors().add(new BasicAuthorizationInterceptor(user, password));
		}
	}
		
	@Override
	public void buildServiceAndRoutes(ServiceObject service) {		
		switch (this.mode) {
			case MODE_CREATE:
				this.createServiceAndRoutes(service);
				break;
			case MODE_REBUILD:
				this.rebuildServiceAndRoutes(service);
				break;
		}
	}
	
	/**
	 * Create all routes mapped on Kong service object
	 * 
	 * @param service the Kong service object
	 * @throws KongStarterException if any error occur
	 */
	private void createRoutes(ServiceObject service) throws KongStarterException {
		for (RouteObject object : service.getRouteObjects()) {
			this.createRoute(object);
		}
	}
	
	/**
	 * Create the Kong service and routes if it not exist
	 *  
	 * @param serviceObject the Kong service Object
	 */
	private void createServiceAndRoutes(ServiceObject serviceObject) {
		try {
			this.createService(serviceObject);
			this.createRoutes(serviceObject);
		} catch (KongStarterException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Rebuild all services and routes. This methods remove the kong service and all routes related 
	 *  
	 * @param serviceObject the {@link ServiceObject} to be rebuild
	 */
	private void rebuildServiceAndRoutes(ServiceObject serviceObject) {
		try {			
			ServiceObject old = this.getService(serviceObject.getName());
			if(old != null) {
				this.removeServiceAndRoutes(old);
			}
			
			ServiceObject objService = this.createService(serviceObject);
			objService.setRouteObjects(serviceObject.getRouteObjects());
			
			for(RouteObject obj: objService.getRouteObjects()) {
				obj.setService(objService);
			}
			
			this.createRoutes(objService);
		} catch (KongStarterException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Remove the service and all her routes
	 * 
	 * @param object the service object
	 * @throws KongStarterException if any error occur
	 */
	public void removeServiceAndRoutes(ServiceObject object) throws KongStarterException {
		String URL_RUOTES = this.getKongURL(this.kongURL, KONG_SERVICE_PATH, object.getName(), KONG_ROUTE_PATH);
		List<RouteObject> routeList = super.getKongRouteObjectList(URL_RUOTES);
		
		for (RouteObject routeObject : routeList) {
			this.removeRoute(routeObject.getId());
		}
		
		this.removeService(object.getName());
	}
	
	/**
	 * @see KongService#createService(ServiceObject)
	 */
	@Override
	public ServiceObject createService(ServiceObject object) throws KongStarterException {			
		String URL = this.getKongURL(this.kongURL, KONG_SERVICE_PATH);
		ServiceObject so = super.createKongObject(URL, object, ServiceObject.class);
				
		logger.info("Kong Service Created: {{}}: {} ", so.getPath(), so.getName());
		
		return so;
	}

	/**
	 * @see KongService#removeService(String)
	 */
	@Override
	public void removeService(String id) throws KongStarterException {
		String URL = this.getKongURL(this.kongURL, KONG_SERVICE_PATH, id);
		
		if(logger.isDebugEnabled()) {
			logger.info("Delete Kong Service: {{}}", id);
		}
		
		super.deleteKongObject(URL);
	}

	/**
	 * @see KongService#createRoute(RouteObject)
	 */
	@Override
	public RouteObject createRoute(RouteObject object) throws KongStarterException {
		String URL = this.getKongURL(this.kongURL, KONG_ROUTE_PATH);
		RouteObject ro = super.createKongObject(URL, object, RouteObject.class);
		
		logger.info("Kong Route Created: {{}}: {} - {}", ro.getPaths(), ro.getProtocols(), ro.getMethods());
		
		return ro;
	}

	/**
	 * @see KongService#removeRoute(String)
	 */
	@Override
	public void removeRoute(String id) throws KongStarterException {
		String URL = this.getKongURL(this.kongURL, KONG_ROUTE_PATH, id);
		
		if(logger.isDebugEnabled()) {
			logger.info("Delete Kong Route: {{}}", id);
		}
		
		super.deleteKongObject(URL);
	}

	/**
	 * @see KongService#getService(String)
	 */
	@Override
	public ServiceObject getService(String name) {
		String URL = this.getKongURL(this.kongURL, KONG_SERVICE_PATH, name);
		
		try {
			return super.getKongObject(URL, ServiceObject.class);
		} catch (KongStarterException e) {
			return null;
		}
	}
}
