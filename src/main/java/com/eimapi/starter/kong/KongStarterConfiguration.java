package com.eimapi.starter.kong;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.eimapi.starter.kong.bean.ApiCandidateSearch;
import com.eimapi.starter.kong.bean.KongService;
import com.eimapi.starter.kong.bean.impl.ApiCandidateSearchImpl;
import com.eimapi.starter.kong.bean.impl.KongServiceImpl;
import com.eimapi.starter.kong.exception.KongStarterException;
import com.eimapi.starter.kong.rest.ServiceObject;

@Configuration
@EnableConfigurationProperties
@ConditionalOnWebApplication
public class KongStarterConfiguration {
	private Logger logger = LoggerFactory.getLogger(KongStarterConfiguration.class);
	
    @Bean
    @ConditionalOnMissingBean
    public KongService kongService() {
        return new KongServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public ApiCandidateSearch candidateSearch() {
        return new ApiCandidateSearchImpl();
    }


    @PostConstruct
    public void init() throws KongStarterException {
    	logger.info("Starting Kong Configuration Starter");
    	
    	Map<String, ServiceObject> serviceMap = this.candidateSearch().search();
    	
    	serviceMap.forEach((key, value) -> {
    		this.kongService().buildServiceAndRoutes(value);
    	});
    	
    	logger.info("{} APIs processed by Kong service starter", serviceMap.size());
    }
}