package com.eimapi.starter.kong.rest;

import java.util.List;

public class RoutObjectList {

	Object next;
	
	List<RouteObject> data;

	public Object getNext() {
		return next;
	}

	public void setNext(Object next) {
		this.next = next;
	}

	public List<RouteObject> getData() {
		return data;
	}

	public void setData(List<RouteObject> data) {
		this.data = data;
	}
	
	
}

