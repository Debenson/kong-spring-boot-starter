package com.eimapi.starter.kong.bean.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.eimapi.starter.kong.exception.KongStarterException;
import com.eimapi.starter.kong.rest.RouteObject;

public abstract class AbstractKongService {

	RestTemplate restTemplate = null;

	/**
	 * Get the rest template
	 * 
	 * @return {@link RestTemplate} the rest template
	 */
	protected RestTemplate getRestTemplate() {
		if (restTemplate == null) {
			this.restTemplate = new RestTemplate();
		}

		return this.restTemplate;
	}

	/**
	 * Create the Kong URL to be invoked by rest template
	 * 
	 * @param Array of resource
	 * @return {@link String} the Kong URL
	 */
	protected String getKongURL(String... args) {
		StringBuilder builder = new StringBuilder();

		for (String string : args) {
			builder.append("/");
			builder.append(string);
		}

		return builder.toString().replaceFirst("/", "").replaceAll("[^://]//", "/");
	}

	/**
	 * Get Kong Object
	 * 
	 * @param url   the search URL
	 * @param clazz the object class
	 * @return T the object
	 * @throws KongStarterException if a {@link RestClientException} occur
	 */
	protected <T> T getKongObject(String url, Class<T> clazz) throws KongStarterException {
		try {
			ResponseEntity<T> resp = this.getRestTemplate().getForEntity(url, clazz);
			return resp.getBody();
		} catch (RestClientException e) {
			throw new KongStarterException(e.getMessage(), e.getCause());
		}
	}

	/**
	 * get Kong Route Object List
	 * 
	 * @param url   the search URL
	 * @throws KongStarterException if a {@link RestClientException} occur
	 */
	protected List<RouteObject> getKongRouteObjectList(String url) throws KongStarterException {
		try {
			ResponseEntity<LinkedHashMap> resp = this.getRestTemplate().exchange(url, HttpMethod.GET, null, LinkedHashMap.class);
			
			@SuppressWarnings("unchecked")
			List<LinkedHashMap> obj = (List<LinkedHashMap>) resp.getBody().get("data");
			
			obj.forEach(o -> {
				System.out.println(o);
			});
			
			return null;//resp.getBody();
		} catch (RestClientException e) {
			throw new KongStarterException(e.getMessage(), e.getCause());
		}
	}

	/**
	 * Delete Kong Object
	 * 
	 * @param url the search URL
	 * @throws KongStarterException if a {@link RestClientException} occur
	 */
	protected void deleteKongObject(String url) throws KongStarterException {
		try {
			this.getRestTemplate().delete(url);
		} catch (RestClientException e) {
			throw new KongStarterException(e.getMessage(), e.getCause());
		}
	}

	/**
	 * create Kong Object
	 * 
	 * @param url   the search URL
	 * @param clazz the object class
	 * @return T the object
	 * @throws KongStarterException if a {@link RestClientException} occur
	 */
	protected <T> T createKongObject(String url, T obj, Class<T> clazz) throws KongStarterException {
		try {
			ResponseEntity<T> resp = this.getRestTemplate().postForEntity(url, obj, clazz);
			return resp.getBody();
		} catch (RestClientException e) {
			throw new KongStarterException(e.getMessage(), e.getCause());
		}
	}

}
