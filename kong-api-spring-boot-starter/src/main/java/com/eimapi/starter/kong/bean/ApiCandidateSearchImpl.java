package com.eimapi.starter.kong.bean;

import com.eimapi.starter.kong.ApiModel;
import com.eimapi.starter.kong.exception.KongStarterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

    @Value("${kong.service.host-style:IP")
    private String kongHostStyle;

    @Value("${kong.service.host:localhost")
    private String host;


    /**
     * @see ApiCandidateSearch#search()
     */
    @Override
    public List<ApiModel> search() throws KongStarterException {
        List<ApiModel> model = new ArrayList<ApiModel>();

        Map<RequestMappingInfo, HandlerMethod> handlerMethods = this.requestMappingHandlerMapping.getHandlerMethods();

        for (Entry<RequestMappingInfo, HandlerMethod> item : handlerMethods.entrySet()) {
            RequestMappingInfo mapping = item.getKey();
            HandlerMethod method = item.getValue();

            //grant that the scanner cannot scan the spring framework packages
            if (method.getBeanType().getName().startsWith("org.springframework")) {
                continue;
            }

            ApiModel apiModel = new ApiModel();

            apiModel.setMethod(method.getMethod().getName());
            apiModel.setUrl(this.getURL());
            apiModel.setUrlMapping(mapping.getPatternsCondition().getPatterns());

            model.add(apiModel);

        }

        return model;
    }

    /**
     * Get the URL to be mapped at the kong
     *
     * @return String the url
     */
    private String getURL() throws KongStarterException {
        switch (this.kongHostStyle) {
            case "IP":
                return super.getIP();
            case "HOSTNAME":
                return super.getHostName();
            case "CUSTOM":
                return this.host;
            default:
                return "localhost";
        }
    }
}
