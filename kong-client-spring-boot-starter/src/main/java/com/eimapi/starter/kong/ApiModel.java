package com.eimapi.starter.kong;

import java.util.List;

public class ApiModel {

	private String id;
	
	private String method;
	
	private List<String> urlMapping;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public List<String> getUrlMapping() {
		return urlMapping;
	}

	public void setUrlMapping(List<String> urlMapping) {
		this.urlMapping = urlMapping;
	}
}
