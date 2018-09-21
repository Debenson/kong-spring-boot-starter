package com.eimapi.starter.kong.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
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
	private Logger logger = LoggerFactory.getLogger(KongServiceImpl.class);

	RestTemplate restTemplate = null;

	@Value("${kong.service.url:http://localhost:8001}")
	private String kongURL;

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

		
		ResponseEntity<String> respo = hasService(api.getName());
		if (respo != null) {
			try {
				JSONObject jObj =  new JSONObject(respo.getBody());
				String id = jObj.getString("id");
				
				getRestTemplate().delete(kongURL + "/apis/" + id);
				
				logger.info("The old APIs [" + api.getUris() + "] was removed from Kong.");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		try {
			addAPIResp = getRestTemplate().postForEntity((kongURL + "/apis"), apiEntity, String.class);
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			System.exit(1);
		}

		if (addAPIResp.getStatusCode().equals(HttpStatus.CREATED)) {
			logger.info("The APIs [" + api.getUris() + "] was created at Kong.");
		}
	}

	public ResponseEntity<String> hasService(String name) {
		try {
			return getRestTemplate().getForEntity(this.kongURL + "/apis/" + name, String.class);
		} catch (HttpClientErrorException e) {
			return null;
		}
	}
}
