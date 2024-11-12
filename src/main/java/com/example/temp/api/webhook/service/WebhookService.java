package com.example.temp.api.webhook.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

@Service
public interface WebhookService {

	void createLabelPrint(HttpServletRequest request, HttpServletResponse response, Map<String, Object> jsonHeader, Map<String, Object> jsonData) throws Exception;

	void updateTrackingInfo(HttpServletRequest request, HttpServletResponse response, Map<String, Object> jsonHeader, Map<String, Object> jsonData) throws Exception;

}
