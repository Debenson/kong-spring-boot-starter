package com.eimapi.starter.kong.bean;

import java.util.List;

import com.eimapi.starter.kong.ApiModel;
import com.eimapi.starter.kong.exception.KongStarterException;

public interface ApiCandidateSearch {

	List<ApiModel> search() throws KongStarterException;
}
