package com.eimapi.starter.kong.rest;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonInclude(content = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteObject {
	private Logger logger = LoggerFactory.getLogger(RouteObject.class);

	@JsonInclude(content = Include.NON_NULL)
	private String id;
	
	private String[] protocols;
	private String[] methods;
	private String[] paths;
	private String[] hosts;
	
	private boolean strip_path = true;
    private boolean preserve_host = false;
    
    private Integer regex_priority;
	
	private ServiceObject service;
	
	public RouteObject(String[] protocols, String[] methods, String[] paths, ServiceObject serviceObject) {
		super();
		this.protocols = protocols;
		this.methods = methods;
		this.paths = paths;
		this.service = serviceObject;
	}

	public RouteObject() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String[] getProtocols() {
		return protocols;
	}

	public void setProtocols(String[] protocols) {
		this.protocols = protocols;
	}

	public String[] getMethods() {
		return methods;
	}

	public void setMethods(String[] methods) {
		this.methods = methods;
	}

	public String[] getPaths() {
		return paths;
	}

	public void setPaths(String[] paths) {
		this.paths = paths;
	}

	public String[] getHosts() {
		return hosts;
	}

	public void setHosts(String[] hosts) {
		this.hosts = hosts;
	}

	public boolean isStrip_path() {
		return strip_path;
	}

	public void setStrip_path(boolean strip_path) {
		this.strip_path = strip_path;
	}

	public boolean isPreserve_host() {
		return preserve_host;
	}

	public void setPreserve_host(boolean preserve_host) {
		this.preserve_host = preserve_host;
	}

	public Integer getRegex_priority() {
		return regex_priority;
	}

	public void setRegex_priority(Integer regex_priority) {
		this.regex_priority = regex_priority;
	}

	public ServiceObject getService() {
		return service;
	}

	public void setService(ServiceObject service) {
		this.service = service;
	}
	
	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		String value = new String();
		
		try {
			value = mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			logger.error("JSON Convertion Error: \n" + e.getMessage());
		}
		
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(hosts);
		result = prime * result + Arrays.hashCode(methods);
		result = prime * result + Arrays.hashCode(paths);
		result = prime * result + Arrays.hashCode(protocols);
		result = prime * result + ((service == null) ? 0 : service.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RouteObject other = (RouteObject) obj;
		if (!Arrays.equals(hosts, other.hosts))
			return false;
		if (!Arrays.equals(methods, other.methods))
			return false;
		if (!Arrays.equals(paths, other.paths))
			return false;
		if (!Arrays.equals(protocols, other.protocols))
			return false;
		
		return true;
	}
	
	
}