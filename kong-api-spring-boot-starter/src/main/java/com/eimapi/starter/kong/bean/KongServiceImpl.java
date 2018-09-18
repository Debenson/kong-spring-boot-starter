package com.eimapi.starter.kong.bean;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.eimapi.starter.kong.ApiModel;

@Component
public class KongServiceImpl implements KongService {

	@PostConstruct
	public void init() {
		//check kong url and connections
	}

	public void addService(List<ApiModel> apiList) {
		// TODO Auto-generated method stub

		apiList.forEach(api -> System.out.println(api.toString()));

	}

	
	
}
