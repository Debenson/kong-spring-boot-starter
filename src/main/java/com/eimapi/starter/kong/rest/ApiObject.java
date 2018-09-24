package com.eimapi.starter.kong.rest;

@Deprecated
public class ApiObject {

	private String name;
    private String hosts;
    private String upstream_url;
    private String uris;
    
    public ApiObject() {
		// TODO Auto-generated constructor stub
	}

    public ApiObject(String name, String hosts, String upstream_url, String uris) {
        this.name = name;
        this.hosts = hosts;
        this.upstream_url = upstream_url;
        this.uris = uris;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHosts() {
		return hosts;
	}

	public void setHosts(String hosts) {
		this.hosts = hosts;
	}

	public String getUpstream_url() {
		return upstream_url;
	}

	public void setUpstream_url(String upstream_url) {
		this.upstream_url = upstream_url;
	}

	public String getUris() {
		return uris;
	}

	public void setUris(String uris) {
		this.uris = uris;
	}
}
