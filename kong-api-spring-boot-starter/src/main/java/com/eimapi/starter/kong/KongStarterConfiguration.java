package com.eimapi.starter.kong;

import com.eimapi.starter.kong.bean.ApiCandidateSearch;
import com.eimapi.starter.kong.bean.ApiCandidateSearchImpl;
import com.eimapi.starter.kong.bean.KongService;
import com.eimapi.starter.kong.bean.KongServiceImpl;
import com.eimapi.starter.kong.exception.KongStarterException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@EnableConfigurationProperties
@ConditionalOnWebApplication
public class KongStarterConfiguration {

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
        this.kongService().addService(this.candidateSearch().search());
    }
}