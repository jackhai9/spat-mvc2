package cn.hofan.spat.mvc2.route;


import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import cn.hofan.spat.mvc2.BeatContext;

/**
 * 提供用于定义路由及获取路由相关信息。
 * 
 * @author renjun (jun.ren@gmail.com)
 *
 */
public class BeatRouter {
	
	private Set<ActionInfo> actions = new LinkedHashSet<ActionInfo>();
	
	private PathMatcher pathMatcher = new AntPathMatcher();
	
	public void addMapping(ActionInfo action) {
		actions.add(action);
	}
	
	public RouteResult route(BeatContext beat) {
		String url = beat.getClient().getRelativeUrl(); //beat.getRelativeUrl();

	    String bestPathMatch = null;
	    ActionInfo bestAction = null;
	    
	    // 精确匹配
	    for(ActionInfo action : actions){
	    	for (String registeredPath : action.getMappedPatterns()) { 
	    		if (StringUtils.equals(url, registeredPath))
	    			if (action.matchRequestMethod(beat)){
		    	    	bestPathMatch = registeredPath;
		    	    	bestAction = action;
		    	    }
	    	}
	    }
	    
	    // 正则匹配
	    if (bestPathMatch == null)
	    	for(ActionInfo action : actions){
			    for (String registeredPath : action.getMappedPatterns()) {  
			        if (pathMatcher.match(registeredPath, url) &&  
			                (bestPathMatch == null || bestPathMatch.length() < registeredPath.length())) {
			    	    if (action.matchRequestMethod(beat)){
			    	    	bestPathMatch = registeredPath;
			    	    	bestAction = action;
			    	    }
			        } 
			    }
		    }
		
	    if (bestPathMatch == null) return null;
	    
		RouteResult match = new RouteResult();
		//match.isExactMatch = false;
		match.action = bestAction;
		match.beat = beat;
		
		//TODO: 预判断下，可以提高效率
		if (match.action.getParamNames().length > 0)
			match.urlParams = pathMatcher.extractUriTemplateVariables(bestPathMatch, url);
		
		
		return match;
	    
	}


}
