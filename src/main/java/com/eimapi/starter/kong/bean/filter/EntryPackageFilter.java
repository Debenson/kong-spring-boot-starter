package com.eimapi.starter.kong.bean.filter;

import java.util.Map.Entry;
import java.util.function.Predicate;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

/**
 * Filter for package name. This filter exclude all classes that the package starts with org.springframework.
 * 
 * @author Denys G. Santos
 * @see 0.0.2
 * @version 0.0.1
 */
public class EntryPackageFilter implements Predicate <Entry<RequestMappingInfo, HandlerMethod>> {
	private String[] packageArray;
	private static final String SPRING_FRAMEWORK = "org.springframework.";
	
	public EntryPackageFilter(String ... packageName) {
		this.packageArray = packageName;
	}
	
	@Override
	public boolean test(Entry<RequestMappingInfo, HandlerMethod> entry) {
		String packageName = entry.getValue().getBeanType().getName();

		if(this.packageArray != null && this.packageArray.length > 0) {
			for (String string : packageArray) {
				if (packageName.startsWith(string)) {
					return false;
				}
			}
		}
		
        return !packageName.startsWith(SPRING_FRAMEWORK);
	}
}
