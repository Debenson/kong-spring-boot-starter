package com.eimapi.starter.kong.bean.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.eimapi.starter.kong.bean.ApiCandidateSearch;
import com.eimapi.starter.kong.bean.filter.EntryPackageFilter;
import com.eimapi.starter.kong.exception.KongStarterException;
import com.eimapi.starter.kong.rest.RouteObject;
import com.eimapi.starter.kong.rest.ServiceObject;

/**
 * Implementation of ApiCandidateSearch
 *
 * @author Denys G. Santos
 * @since 0.0.1
 * @version 0.0.1
 */
@Component
public class ApiCandidateSearchImpl extends AbstractApiCandidateSearch implements ApiCandidateSearch {
	private Logger logger = LoggerFactory.getLogger(ApiCandidateSearch.class);
	
	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;

	//server properties
	@Value("${server.protocol:http}")
	private String serverProtocol;

	@Value("${server.address:}")
	private String serverAddres;

	@Value("${server.port:8080}")
	private String serverPort;
	
	//kong service properties
	@Value("${kong.model.service.connect_timeout:60000}")
	private Integer connect_timeout;
	
	@Value("${kong.model.service.read_timeout:60000}")
	private Integer read_timeout;
	
	@Value("${kong.model.service.write_timeout:60000}")
	private Integer write_timeout;
	
	@Value("${kong.model.service.retries:5}")
	private Integer retries;
	

	//kong route properties
	@Value("${kong.model.route.strip_path:true}")
	private boolean strip_path;
	
	@Value("${kong.model.route.preserve_host:false}")
	private boolean preserve_host;

	@Value("${kong.model.route.regex_priority:0}")
    private Integer regex_priority;
	
	
	@PostConstruct
	public void init() {
		if(serverAddres == null || serverAddres.isEmpty()) {
			try {
				InetAddress ia =InetAddress.getLocalHost();
				serverAddres = ia.getHostAddress();
			} catch (UnknownHostException e) {
				logger.error("Error when try to recovery server address. Set it 'localhost'");
				serverAddres = "localhost";
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("Server address set as: {}", serverAddres);
		}

	}
	
	
	/**
	 * @see ApiCandidateSearch#search()
	 */
	@Override
	public Map<String, ServiceObject> search() throws KongStarterException {
		Map<RequestMappingInfo, HandlerMethod> handlerMethods = this.requestMappingHandlerMapping.getHandlerMethods();
		
		if(logger.isDebugEnabled()) {
			logger.debug("Start mapping Kong services and routes");
		}
		
		EntryPackageFilter packageFilter = new EntryPackageFilter();

		Map<String, ServiceObject> map = handlerMethods.entrySet().stream()
				.filter(packageFilter)
				.collect(
						Collectors.toMap(
								item -> getMethodFullName(item.getValue()),
								item -> getServiceObject(item.getValue(), item.getKey()), 
								(item1, item2) -> item1
						)
				);
		
		if(logger.isDebugEnabled()) {
			logger.debug("{} APIs mapped.", map.values().size());
		}
		
		return map;
	}

	/**
	 * 
	 * @param method
	 * @param mapping
	 * @return
	 */
	protected ServiceObject getServiceObject(@NotNull HandlerMethod method, @NotNull RequestMappingInfo mapping) {

		String[] paths = this.getPaths(mapping);

		ServiceObject serviceObject = new ServiceObject(super.getMethodFullName(method), this.serverProtocol,
				this.serverAddres, new Integer(this.serverPort), paths[0]);
		
		serviceObject.setRead_timeout(this.read_timeout);
		serviceObject.setWrite_timeout(this.write_timeout);
		serviceObject.setRetries(this.retries);
		serviceObject.setConnect_timeout(this.connect_timeout);

		serviceObject.setRouteObjects(this.getRouteArray(mapping, paths, serviceObject));
		
		return serviceObject;
	}

	
	private RouteObject[] getRouteArray(@NotNull RequestMappingInfo mapping, String[] paths, ServiceObject serviceObject) {
		Set<RequestMethod> methodSet = mapping.getMethodsCondition().getMethods();
		String[] methods = {"GET"};
		
		if(methodSet != null && methodSet.size() > 0) {
			methods = new String[methodSet.size()];
			methods = methodSet.stream().map(rm -> rm.toString()).toArray(String[]::new);
		}
		
		RouteObject routeObject = new RouteObject(this.serverProtocol.split(","), methods, paths, serviceObject);
		
		routeObject.setPreserve_host(this.preserve_host);
		routeObject.setRegex_priority(this.regex_priority);
		routeObject.setStrip_path(this.strip_path);
		
		RouteObject[] routeObjects = {routeObject};
		
		return routeObjects;
	}
	
	
	private String[] getPaths(@NotNull RequestMappingInfo mapping) {
		Object[] objectArray = mapping.getPatternsCondition().getPatterns().toArray();

		String[] pathArray = { "/" };

		if (objectArray == null || objectArray.length > 0) {
			return Arrays.copyOf(objectArray, objectArray.length, String[].class);
		}

		return pathArray;
	}
}
