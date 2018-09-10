package com.eimapi.starter.kong.bean;

import java.util.List;

import com.eimapi.starter.kong.ApiModel;

public interface ApiCandidateSearch {
	List<ApiModel> search(List<String> packages);
}
