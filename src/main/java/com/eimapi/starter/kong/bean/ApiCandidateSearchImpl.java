package com.eimapi.starter.kong.bean;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.eimapi.starter.kong.exception.KongStarterException;
import com.eimapi.starter.kong.rest.ApiObject;

/**
 * Implementation of ApiCandidateSearch
 *
 * @author Denys G. Santos
 * @since 0.0.1
 * @version 0.0.1
 */
@Component
public class ApiCandidateSearchImpl extends AbstractApiCandidateSearch implements ApiCandidateSearch {

	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;

	@Value("${kong.service.url:http://localhost:8001}")
	private String kongURL;

	@Value("${kong.mapping.host:localhost}")
	private String kongHost;
	
	@Value("${server.protocol:http}")
	private String serverProtocol;
	
	@Value("${server.address:localhost}")
	private String serverAddres;

	@Value("${server.port:8080}")
	private String serverPort;
	
	/**
	 * @see ApiCandidateSearch#search()
	 */
	@Override
	public Map<String, ApiObject> search() throws KongStarterException {
		Map<RequestMappingInfo, HandlerMethod> handlerMethods = this.requestMappingHandlerMapping.getHandlerMethods();
		
		//Filter to exclude Spring frameworks beans
		Predicate<Object> springFrameworkExcludeFilter = new Predicate<Object>() {
	        @Override
	        public boolean test(Object item) {
	        	@SuppressWarnings("unchecked")
				Entry<RequestMappingInfo, HandlerMethod> entry = (Entry<RequestMappingInfo, HandlerMethod>) item;
	            return !entry.getValue().getBeanType().getName().startsWith("org.springframework");
	        }
	    };
	    
	    return handlerMethods.entrySet().stream()
	    		.filter(springFrameworkExcludeFilter)
				.collect(
					Collectors.toMap(
						item -> getMethodFullName(item.getValue()),
						item -> getApiObject(item.getValue(), item.getKey()),
						(item1, item2) -> item1
					)
				);
	    	    
	  /*  handlerMethods = handlerMethods.entrySet().stream()
	    		.filter(springFrameworkExcludeFilter)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	    
	    
	    
	    
	    return null;*/
	}
	
	
	private ApiObject getApiObject(HandlerMethod method, RequestMappingInfo mapping) {
		StringBuilder builder = new StringBuilder();
		builder.append(this.serverProtocol);
		builder.append("://");
		builder.append(this.serverAddres);
		builder.append(":");
		builder.append(this.serverPort);
		
		Object[] arrayPath = mapping.getPatternsCondition().getPatterns().toArray();
		
		String path = Arrays.toString(arrayPath);
		path = path.substring(1, path.length() -1);
		
		builder.append(arrayPath[0].toString());
				
		return new ApiObject(
					getMethodFullName(method),
					this.kongHost,
					builder.toString(),
					path
				);
	}

}
