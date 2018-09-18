package com.eimapi.starter.kong.bean;

import java.util.List;

import com.eimapi.starter.kong.ApiModel;

public interface KongService {
	
	void addService(List<ApiModel> apiList);
	
	//void addRoute()
}
