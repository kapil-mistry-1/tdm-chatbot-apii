package com.nordea.tdmchatbotapi.service;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nordea.tdmchatbotapi.model.QueryResult;
import com.nordea.tdmchatbotapi.util.ChatUtil;

@Service("userChatService")
public class UserChatServiceImpl implements UserChatService {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserChatServiceImpl.class);
	private static final boolean TRACE_MODE = false;

	@Override
	public QueryResult getQueryResponse(String chatRequest) {
		String response = "";
		try {

			String resourcesPath = ChatUtil.getResourcesPath();
			LOG.info("The AIML path is:"+ resourcesPath);
			MagicBooleans.trace_mode = TRACE_MODE;
			Bot bot = new Bot("super", resourcesPath);
			Chat chatSession = new Chat(bot);
			bot.brain.nodeStats();

			String request = chatRequest;
			if (MagicBooleans.trace_mode)
				LOG.info("STATE=" + request + ":THAT=" + ((History) chatSession.thatHistory.get(0)).get(0) + ":TOPIC=" + chatSession.predicates.get("topic"));
			response = chatSession.multisentenceRespond(request);
			while (response.contains("&lt;"))
				response = response.replace("&lt;", "<");
			while (response.contains("&gt;"))
				response = response.replace("&gt;", ">");
			LOG.info("Robot response: " + response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		QueryResult result = mapResult(response);
		return result;
	}

	private QueryResult mapResult(String response) {
		QueryResult result = new QueryResult();
		result.setIntermediateQuery(false);
		if (response.contains("||")) {
			result.setIntermediateQuery(true);
			String[] valarray = response.split("\\|\\|");
			System.out.println("***************"+valarray);
			for (int i = 0; i< valarray.length; i++) {
				result.getSubQuestions().add(valarray[i]);
			}
			result.setResponse("");
		} else {
				result.setResponse(response);
		}
		return result;
	}	

}
