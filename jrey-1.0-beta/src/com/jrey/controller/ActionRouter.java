package com.jrey.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.jrey.util.PatternLookup;
import com.jrey.util.PatternLookup.PatternMatch;

public class ActionRouter {

	public static List<HashMap<String, String>> getUrlParams(Action a) {
		List<HashMap<String, String>> params = new LinkedList<HashMap<String, String>>();
		List<PatternMatch> mlist = PatternLookup.getMatches(":[a-zA-Z0-9]+",
				Matcher.quoteReplacement(a.getUrl()));
		
		String prevSeparator = null;
		String nextSeparator = null;
		int csp = 0;
		HashMap<String, String> param;

		Iterator<PatternMatch> it = mlist.iterator();
		try{
		it.next();
	
		for (PatternMatch match : mlist) {
			param = new HashMap<String, String>();
			prevSeparator = match.getHolder().substring(csp,match.getStartPos());
			if(it.hasNext()){
				PatternMatch next = it.next();
				nextSeparator = match.getHolder().substring(match.getEndPos(),next.getStartPos());

			}
			
			else{
				nextSeparator = match.getHolder().substring(match.getEndPos());
			}
			
			param.put("prevSeparator",prevSeparator);
			param.put("parameter", match.getText().replace(":", ""));
			param.put("nextSeparator", nextSeparator);
			csp = match.getEndPos();
			params.add(param);
		}

		}
		catch(NoSuchElementException e){}
		return params;
	}

	public static boolean isUrlForAction(String requestUrl, Action a) {
		
		String actionUrl = String.format("%s" + a.getUrl().replaceAll(":[a-zA-Z0-9]+", "[^\\/]+").replaceAll("\\*", ".*") +"%s","/*","/*");
		Pattern p = Pattern.compile(actionUrl);
		Matcher m = p.matcher(Matcher.quoteReplacement(requestUrl));
		if (m.matches())
			return true;
		return false;

	}
	
	
	public static HashMap<String,String> getUrlParams(Action a, String url){
		HashMap<String,String> paramsMap = new HashMap<String,String>();
		Pattern p;
		Matcher m;
		int regionStart = 0;
		for(HashMap<String,String> map:getUrlParams(a)){
			String region = url.substring(regionStart);
			String pattern = "("+map.get("prevSeparator")+")([^\\/]+)" + "(" + map.get("nextSeparator") + ")";
			p = Pattern.compile(pattern);
			m = p.matcher(region);
			if(m.find()){
			paramsMap.put(map.get("parameter"), m.group(2));
		    regionStart = m.end(2);
			}
		}
		
		
		return paramsMap;
	}

}
