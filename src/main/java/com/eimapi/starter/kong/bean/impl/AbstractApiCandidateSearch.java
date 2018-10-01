package com.eimapi.starter.kong.bean.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import com.eimapi.starter.kong.bean.ApiCandidateSearch;

/**
 * Abstract class that helps to implements {@link ApiCandidateSearch} interface
 *
 * @author Denys G. Santos
 * @since 0.0.1
 * @version 0.0.1
 */
public abstract class AbstractApiCandidateSearch {

    /**
     * method to get the {@link InetAddress} object
     *
     * @return InetAddress the {@link InetAddress} object with localhost definition
     */
    private InetAddress getInetAddress() {
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.exit(1);
        }
        
		return null;
    }

    /**
     * Method to get the Host IP
     *
     * @return String the host IP
     */
    protected String getIP() {
        return this.getInetAddress().getHostAddress();
    }

    /**
     *  method to get the Hostname
     *
     * @return String the hostname
     */
    protected String getHostName() {
        return this.getInetAddress().getHostName();
    }
    
    /**
     * get the full name of method
     * 
     * @param method the handled method
     * @return {@link String} method full name
     */
    protected String getMethodFullName(HandlerMethod method) {
    	StringBuilder builder = new StringBuilder();
    	
    	builder.append(method.getBeanType().getName());
    	builder.append("_");
    	builder.append(method.getMethod().getName());
    	
    	return builder.toString();
    }
    
    protected String getUris(RequestMappingInfo mapping) {
    	Object[] strArray = mapping.getPatternsCondition().getPatterns().toArray();
    	
    	return Arrays.toString(strArray);
    }
    
   
    
}
