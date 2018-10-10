package com.eimapi.starter.kong.bean.impl;

import com.eimapi.starter.kong.bean.KongService;
import com.eimapi.starter.kong.exception.KongStarterException;
import com.eimapi.starter.kong.rest.RouteObject;
import com.eimapi.starter.kong.rest.ServiceObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Implementation of Kong service
 *
 * @author Denys G. Santos
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
@Configuration
public class KongServiceImpl extends AbstractKongService implements KongService {
    private static final String KONG_SERVICE_PATH = "services";
    private static final String KONG_ROUTE_PATH = "routes";
    private static final String MODE_REBUILD = "REBUILD";
    private static final String MODE_CREATE = "CREATE";
    private Logger logger = LoggerFactory.getLogger(KongServiceImpl.class);
    @Value("${kong.server.url:http://localhost:8001}")
    private String kongURL;

	/*
	TODO: create the possibility to use authentication at kong aministration API

	@Value("${kong.server.user:}")
	private String user;

	@Value("${kong.server.password:}")
	private String password;*/

    //MODE: REBUILD || CREATE
    @Value("${kong.build.mode:REBUILD}")
    private String mode;


    @PostConstruct
    public void init() {
        if (logger.isDebugEnabled()) {
            if (mode.equals(MODE_CREATE) || mode.equals(MODE_REBUILD)) {
                logger.debug("'{}' Kong build mode selected", mode);
            } else {
                logger.debug("No one entry to mode '{}'. Using the default Kong build mode '{}'.", mode, MODE_CREATE);
            }
        }

		/*
		 TODO: This code just will be used when kong administration authentication will be used

		if(user != null && !user.isEmpty()) {
			if(logger.isDebugEnabled()) {
				logger.debug("Using basic authentication. Adding kong API administration credentials.");
			}
			
			getRestTemplate().getInterceptors().add(new BasicAuthorizationInterceptor(user, password));
		}*/
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
            default:
                this.createServiceAndRoutes(service);
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
        createRoutes(service, false);
    }

    /**
     * Create all routes mapped on Kong service object
     *
     * @param service the Kong service object
     * @throws KongStarterException if any error occur
     */
    private void createRoutes(ServiceObject service, boolean checkBeforeCreate) throws KongStarterException {
        String URL_RUOTES = this.getKongURL(this.kongURL, KONG_SERVICE_PATH, service.getName(), KONG_ROUTE_PATH);
        List<RouteObject> routeList = super.getKongRouteObjectList(URL_RUOTES);

        for (RouteObject object : service.getRouteObjects()) {
            if (checkBeforeCreate) {
                if (routeList.contains(object)) {
                    continue;
                }
            }

            this.createRoute(object);
        }
    }

    /**
     * Create the Kong service and routes if it not exist
     *
     * @param serviceObject the Kong service Object
     */
    private void createServiceAndRoutes(ServiceObject serviceObject) {
        ServiceObject objService = this.getService(serviceObject.getName());

        try {
            if (objService == null) {
                objService = this.createService(serviceObject);
            }

            for (RouteObject obj : serviceObject.getRouteObjects()) {
                obj.setService(objService);
            }

            this.createRoutes(serviceObject, true);
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
            if (old != null) {
                this.removeServiceAndRoutes(old);
            }

            ServiceObject objService = this.createService(serviceObject);
            objService.setRouteObjects(serviceObject.getRouteObjects());

            for (RouteObject obj : objService.getRouteObjects()) {
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

        if (logger.isDebugEnabled()) {
            logger.debug("Kong Service Created: {{}}: {} ", so.getPath(), so.getName());
        }

        return so;
    }

    /**
     * @see KongService#removeService(String)
     */
    @Override
    public void removeService(String id) throws KongStarterException {
        String URL = this.getKongURL(this.kongURL, KONG_SERVICE_PATH, id);

        if (logger.isDebugEnabled()) {
            logger.debug("Delete Kong Service: {{}}", id);
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

        if (logger.isDebugEnabled()) {
            logger.debug("Kong Route Created: {{}}: {} - {}", ro.getPaths(), ro.getProtocols(), ro.getMethods());
        }

        return ro;
    }

    /**
     * @see KongService#removeRoute(String)
     */
    @Override
    public void removeRoute(String id) throws KongStarterException {
        String URL = this.getKongURL(this.kongURL, KONG_ROUTE_PATH, id);

        if (logger.isDebugEnabled()) {
            logger.debug("Delete Kong Route: {{}}", id);
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
