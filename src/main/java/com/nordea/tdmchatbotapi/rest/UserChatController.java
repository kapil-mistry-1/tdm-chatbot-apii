package com.nordea.tdmchatbotapi.rest;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.alicebot.ab.Bot;
import org.alicebot.ab.MagicBooleans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import com.nordea.tdmchatbotapi.model.QueryResult;
import com.nordea.tdmchatbotapi.service.UserChatService;
import com.nordea.tdmchatbotapi.util.ChatUtil;

@CrossOrigin
@RestController
public class UserChatController {

	private static final boolean TRACE_MODE = false;
	static String botName = "super";
	private static final Logger LOG = LoggerFactory.getLogger(UserChatController.class);

	@Autowired
	private UserChatService userChatService;

	// Example: http://localhost:8090/chatresponse/"how to get production data"
	@CrossOrigin
	@RequestMapping("/chatresponse/{query}/**")
	public QueryResult getQueryResponse(@PathVariable String query, HttpServletRequest request) {
		final String path = request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE).toString();
		final String bestMatchingPattern = request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString();

	    String arguments = new AntPathMatcher().extractPathWithinPattern(bestMatchingPattern, path);

	    String userQuery;
	    if (null != arguments && !arguments.isEmpty()) {
	    	userQuery = query + '/' + arguments;
	    } else {
	    	userQuery = query;
	    }
	    
		LOG.info("The request sent by the user is:" + userQuery);
		return userChatService.getQueryResponse(userQuery);
	}	
	

	@PostConstruct
	public void doLog() {
		try {
			String resourcesPath = ChatUtil.getResourcesPath();
			MagicBooleans.trace_mode = TRACE_MODE;
			Bot bot = new Bot("super", resourcesPath);
			bot.writeAIMLFiles();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
