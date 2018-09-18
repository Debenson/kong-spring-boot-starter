package com.eimapi.starter.kong;

import java.io.Serializable;
import java.util.Set;

public class ApiModel implements Serializable, Cloneable {
    private String url;

	private String method;

	private Set<String> urlMapping;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Set<String> getUrlMapping() {
        return urlMapping;
    }

    public void setUrlMapping(Set<String> urlMapping) {
        this.urlMapping = urlMapping;
    }

    @Override
    public String toString() {

        StringBuilder urlList = new StringBuilder();
        urlMapping.forEach(url -> {
            urlList.append(url);
            urlList.append(";");
        });

        return "ApiModel{" +
                "url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", urlMapping=" + urlList.toString() +
                '}';
    }
}
