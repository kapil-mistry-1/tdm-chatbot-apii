package com.nordea.tdmchatbotapi.service;

import com.nordea.tdmchatbotapi.model.QueryResult;

public interface UserChatService {
	
	public QueryResult getQueryResponse(String chatRequest);

}
