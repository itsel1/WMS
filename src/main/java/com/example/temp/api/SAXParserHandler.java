package com.example.temp.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXParserHandler extends DefaultHandler {
	
	
	private Stack<String> elementStack = new Stack<>();
	private Map<String, String> dataMap = new HashMap<>();
	

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        elementStack.push(qName);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        elementStack.pop();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String value = new String(ch, start, length).trim();
        if (!value.isEmpty()) {
            String key = elementStack.peek();
            dataMap.put(key, value);
        }
    }

    public Map<String, String> getDataMap() {
        return dataMap;
    }
	/*
	private Attributes tagAttributes;
	private String startTagName;
	private String endTagName;
	private String tagData;
	private String[] attrQNames;
	private String[] attrValues;
	
	private StringBuffer buffer = new StringBuffer();
	private HashMap<String, String> elementMap = new HashMap<>();

	public SAXParserHandler() {
		super();
	}

	public void startElement(String uri, String name, String qname, Attributes attr) throws SAXException {
		startTagName = qname;
		tagAttributes = attr;
		attrQNames = new String[tagAttributes.getLength()];
		attrValues = new String[tagAttributes.getLength()];
		
		for (int i = 0; i < tagAttributes.getLength(); i++) {
			attrQNames[i] = tagAttributes.getQName(i).trim();
			attrValues[i] = tagAttributes.getValue(i).trim();
		}
		
		buffer.setLength(0);
	}
	
	public void characters(char[] ch, int start, int len) throws SAXException {
		buffer.append(ch, start, len);
	}
	
	public void endElement(String uri, String name, String qname) {
		endTagName = qname;
		tagData = buffer.toString().trim();
		elementMap.put(qname, tagData);
	}
	
	 public HashMap<String, String> getElementMap() {
		 return elementMap;
	 }
*/
}
