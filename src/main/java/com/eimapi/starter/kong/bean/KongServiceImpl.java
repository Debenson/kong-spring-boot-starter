package com.eimapi.starter.kong.bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.eimapi.starter.kong.rest.ApiObject;

@Component
@Configuration
public class KongServiceImpl implements KongService {

	RestTemplate restTemplate = null;


	private RestTemplate getRestTemplate() {
		if (restTemplate == null) {
			this.restTemplate = new RestTemplate();
		}
		
		return this.restTemplate;
	}

	@Override
	public void addService(ApiObject api) {
		
		HttpEntity<ApiObject> apiEntity = new HttpEntity<>(api);
		ResponseEntity<String> addAPIResp = null;

		try {
			addAPIResp = getRestTemplate().postForEntity("http://192.168.80.83:8001/apis", apiEntity, String.class);
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
		}

		if (!addAPIResp.getStatusCode().equals(HttpStatus.CREATED)) {
			System.err.println("\n\n\terro\n\n");
		}
	}
}
