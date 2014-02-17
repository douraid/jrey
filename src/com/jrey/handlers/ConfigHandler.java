package com.jrey.handlers;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.jrey.application.AppConfig;
import com.jrey.util.XMLParser;

public class ConfigHandler extends XMLParser {

	private AppConfig configuration = null;

	public ConfigHandler() throws ParserConfigurationException {
		super();

	}

	public AppConfig getConfiguration() throws SAXException, IOException {
		if (configuration == null) {
			Document doc = parseFromInputStream(this.getClass()
					.getClassLoader()
					.getResourceAsStream("./META-INF/jrey-meta.xml"));
			NodeList nodelist = doc.getElementsByTagName("config");

			Node elem = nodelist.item(0);
			if (elem == null)
				return configuration;

			String viewsPath = "/";
			String defaultLayout = null;

			Element e = (Element) elem;
			if (e.getElementsByTagName("views-root-path").item(0) != null)
				viewsPath = e.getElementsByTagName("views-root-path").item(0)
						.getTextContent();
			
			if (e.getElementsByTagName("default-layout").item(0) != null)
				defaultLayout = e.getElementsByTagName("default-layout").item(0)
						.getTextContent();

			configuration = new AppConfig();
			configuration.setViewsPath(("/" + viewsPath + "/").replace("//",
					"/"));
			configuration.setDefaultLayout(defaultLayout);

		}
		return configuration;
	}

}
