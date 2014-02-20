package com.jrey.handlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

import com.jrey.util.XMLParser;

public class LayoutHandler extends XMLParser {

	private static LayoutHandler instance = null;

	private LayoutHandler() throws ParserConfigurationException {
		super();

	}

	public static LayoutHandler getInstance() {
		if (instance == null)
			try {
				instance = new LayoutHandler();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
		return instance;
	}

	public HashMap<String, String> getLayoutBlocs(String layoutContent)
			throws IOException {
		HashMap<String, String> layoutBlocs = new HashMap<String, String>();


			Pattern p = Pattern
					.compile("\\Q[region::\\E([a-zA-Z]+)\\Q]\\E(.*?)\\Q[region::end]\\E", Pattern.DOTALL);
			Matcher m = p.matcher(layoutContent);
			while(m.find()) {
				layoutBlocs.put(m.group(1).replace(" ", ""), m.group(2));
			}

		
		return layoutBlocs;
	}
	
	

}
