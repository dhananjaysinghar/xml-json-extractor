package com.test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class Test {

	private static final String SOAP_REQUEST_BODY = "Body";

	public static void main(String[] args) throws Exception {

		String path = "/home/dhananjaya/eclipse-workspace/xml-json-extractor/src/main/resources/input.xml";
		
		String contents = new String(Files.readAllBytes(Paths.get(path)));
		System.out.println(extractXml(contents));

	}

	@SuppressWarnings("unchecked")
	public static String extractXml(String request) throws Exception {
		XmlMapper mapper = new XmlMapper();
		Map<String, Object> obj = mapper.readValue(request, Map.class);
		ObjectWriter writer = new ObjectMapper().writerWithDefaultPrettyPrinter();
		return obj.containsKey(SOAP_REQUEST_BODY) ? getSoapBody(obj, writer) : writer.writeValueAsString(obj);
	}

	/**
	 * Get the Body content in-case soap envelope
	 * 
	 * @param obj
	 * @param writer
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private static String getSoapBody(Map<String, Object> obj, ObjectWriter writer) throws Exception {
		Map<String, Object> data = (Map<String, Object>) obj.get(SOAP_REQUEST_BODY);
		String key = (String) data.keySet().toArray()[0];
		return writer.writeValueAsString(data.get(key));
	}

}
