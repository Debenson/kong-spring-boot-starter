package com.eimapi.starter.kong.bean.impl;

import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.eimapi.starter.kong.exception.KongStarterException;
import com.eimapi.starter.kong.rest.RouteObject;
import com.eimapi.starter.kong.rest.RoutObjectList;

public abstract class AbstractKongService {

	public static RestTemplate restTemplate = null;

	/**
	 * Get the rest template
	 * 
	 * @return {@link RestTemplate} the rest template
	 */
	protected static RestTemplate getRestTemplate() {
		if (restTemplate == null) {
			restTemplate = new RestTemplate();
		}

		return restTemplate;
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
			ResponseEntity<T> resp = getRestTemplate().getForEntity(url, clazz);
			return resp.getBody();
		} catch (RestClientException e) {
			throw new KongStarterException(e.getMessage(), e.getCause());
		}
	}

	/**
	 * get Kong Route Object List
	 * 
	 * @param url the search URL
	 * @throws KongStarterException if a {@link RestClientException} occur
	 */
	protected List<RouteObject> getKongRouteObjectList(String url) throws KongStarterException {
		try {
			ResponseEntity<RoutObjectList> resp = getRestTemplate().exchange(url, HttpMethod.GET, null, RoutObjectList.class);
			List<RouteObject> objList = resp.getBody().getData();
	
			return objList;
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
			getRestTemplate().delete(url);
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
			ResponseEntity<T> resp = getRestTemplate().postForEntity(url, obj, clazz);
			return resp.getBody();
		} catch (RestClientException e) {
			throw new KongStarterException(e.getMessage(), e.getCause());
		}
	}
}
