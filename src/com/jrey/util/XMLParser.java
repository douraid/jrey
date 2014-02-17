package com.jrey.util;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;



import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLParser{
	
	private DocumentBuilderFactory factory;
	private DocumentBuilder parser;
	
	
	public XMLParser() throws ParserConfigurationException{
		factory = DocumentBuilderFactory.newInstance();
		parser = factory.newDocumentBuilder();
	}
	
	public Document parseFromString(String content) throws SAXException, IOException{
		Document document = parser.parse(content);
		document.normalize();
		return document;
	}
	
	public Document parseFromInputStream(InputStream is) throws SAXException, IOException{
		Document document = parser.parse(is);
		document.normalize();
		return document;
	}
}
