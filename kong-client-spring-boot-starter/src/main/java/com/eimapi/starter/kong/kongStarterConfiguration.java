package com.eimapi.starter.kong;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.eimapi.starter.kong.bean.ApiCandidateSearch;
import com.eimapi.starter.kong.bean.ApiCandidateSearchImpl;
import com.eimapi.starter.kong.bean.KongService;
import com.eimapi.starter.kong.bean.KongServiceImpl;

@Configuration
@EnableConfigurationProperties
@ConditionalOnWebApplication
public class kongStarterConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public KongService starter() {
		return new KongServiceImpl();
	}
	
	@Bean
	@ConditionalOnMissingBean
	public ApiCandidateSearch candidateSearch() {
		return new ApiCandidateSearchImpl();
	}
	
	
	
}
