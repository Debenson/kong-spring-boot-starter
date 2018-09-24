package com.eimapi.starter.kong.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(content = Include.NON_NULL)
public class RouteObject {

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
}