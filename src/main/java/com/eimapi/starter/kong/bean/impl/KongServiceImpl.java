package com.eimapi.starter.kong.bean.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.eimapi.starter.kong.bean.KongService;
import com.eimapi.starter.kong.exception.KongStarterException;
import com.eimapi.starter.kong.rest.RouteObject;
import com.eimapi.starter.kong.rest.ServiceObject;

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
	
	//MODE: REBUILD || UPDATE || CREATE 
	@Value("${kong.build.mode:REBUILD}")
	private String mode;
		

	/*@Override
	public void addService(ApiObject api) {

		HttpEntity<ApiObject> apiEntity = new HttpEntity<>(api);
		ResponseEntity<String> addAPIResp = null;

		ResponseEntity<String> respo = hasService(api.getName());
		if (respo != null) {
			try {
				JSONObject jObj = new JSONObject(respo.getBody());
				String id = jObj.getString("id");

				getRestTemplate().delete(kongURL + "/apis/" + id);

				logger.info("The old APIs [" + api.getUris() + "] was removed from Kong.");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		try {
			addAPIResp = getRestTemplate().postForEntity((kongURL + "/apis"), apiEntity, String.class);
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			System.exit(1);
		}

		if (addAPIResp.getStatusCode().equals(HttpStatus.CREATED)) {
			logger.info("The APIs [" + api.getUris() + "] was created at Kong.");
		}
	}*/

	/*public ResponseEntity<String> hasService(String name) {
		try {
			return getRestTemplate().getForEntity(this.kongURL + "/apis/" + name, String.class);
		} catch (HttpClientErrorException e) {
			return null;
		}
	}*/

	// -----------------------------

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
			
			this.createService(serviceObject);
			this.createRoutes(serviceObject);
		} catch (KongStarterException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/*private void createServiceIfNotExists(ServiceObject serviceObject) {
		ServiceObject serviceObjectFoud = this.getService(serviceObject.getName());
		
		if(serviceObjectFoud != null && serviceObjectFoud.getId() != null) {
			//check by route
			List<RouteObject> routeObjects = this.getRoutes(serviceObjectFoud);
			
			return;
		}
		
		try {
			this.createService(serviceObject);
		} catch (KongStarterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		RouteObject[] routeObjects = serviceObject.getRouteObjects(); 

		for (RouteObject routeObject : routeObjects) {
			try {
				this.createRoute(routeObject);
			} catch (KongStarterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}*/
	
	public void removeServiceAndRoutes(ServiceObject object) throws KongStarterException {
		String URL_RUOTES = this.getKongURL(this.kongURL, KONG_SERVICE_PATH, object.getName(), KONG_ROUTE_PATH);
		List<RouteObject> routeList = super.getKongRouteObjectList(URL_RUOTES);
		
		for (RouteObject routeObject : routeList) {
			this.removeRoute(routeObject.getId());
		}
		
		this.removeService(object.getName());
	}
	
	@Override
	public ServiceObject createService(ServiceObject object) throws KongStarterException {
		String URL = this.getKongURL(this.kongURL, KONG_SERVICE_PATH);
		return super.createKongObject(URL, object, ServiceObject.class);
	}

	@Override
	public void removeService(String id) throws KongStarterException {
		String URL = this.getKongURL(this.kongURL, KONG_SERVICE_PATH, id);
		super.deleteKongObject(URL);
	}

	@Override
	public RouteObject createRoute(RouteObject object) throws KongStarterException {
		String URL = this.getKongURL(this.kongURL, KONG_ROUTE_PATH);
		return super.createKongObject(URL, object, RouteObject.class);
	}

	@Override
	public void removeRoute(String id) throws KongStarterException {
		String URL = this.getKongURL(this.kongURL, KONG_ROUTE_PATH, id);
		super.deleteKongObject(URL);
	}

	@Override
	public List<RouteObject> getRoutes(ServiceObject serviceObject) throws KongStarterException {
		// TODO Auto-generated method stub
		return null;
	}

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
