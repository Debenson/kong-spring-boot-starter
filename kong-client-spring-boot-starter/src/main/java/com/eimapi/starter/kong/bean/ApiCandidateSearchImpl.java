package com.eimapi.starter.kong.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.eimapi.starter.kong.ApiModel;

@Component
public class ApiCandidateSearchImpl implements ApiCandidateSearch {

	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;
	
	public List<ApiModel> search(List<String> packages) {

		if(packages == null || packages.isEmpty()) {
			return searchAtAllPackages();
		}
		
		return null;
	}

	private List<ApiModel> searchAtAllPackages() {
		List<ApiModel> model = new ArrayList<ApiModel>();
		
		Map<RequestMappingInfo, HandlerMethod> handlerMethods = this.requestMappingHandlerMapping.getHandlerMethods();

		for (Entry<RequestMappingInfo, HandlerMethod> item : handlerMethods.entrySet()) {
			RequestMappingInfo mapping = item.getKey();
			HandlerMethod method = item.getValue();
			
			if(method.getBeanType().getName().startsWith("org.springframework.")) {
				continue;
			}
			
			for (String urlPattern : mapping.getPatternsCondition().getPatterns()) {
				System.out.println(
						method.getBeanType().getName() + "#" + method.getMethod().getName() + " <-- " + urlPattern);
			}
		}
		
		return model;
	}

}
