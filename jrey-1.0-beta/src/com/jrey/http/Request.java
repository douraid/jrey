package com.jrey.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.jrey.application.ApplicationProcessor;
import com.jrey.controller.Action;
import com.jrey.controller.ActionRouter;
import com.jrey.controller.Controller;
import com.jrey.exceptions.NoSuchActionNameException;
import com.jrey.exceptions.NoSuchUrlParameterException;

public class Request {

	private HashMap<String, Object> viewsResources = new HashMap<String, Object>();
	private HashMap<String, String> urlParameters = new HashMap<String, String>();
	public HttpServletRequest servletRequest;
	private Action currentAction;
	private Controller currentController;

	public Request(HttpServletRequest request) {
		servletRequest = request;
	}

	public void addViewResource(String key, Object value) {
		viewsResources.put(key, value);
	}

	public Object getViewResource(String key) {
		return viewsResources.get(key);

	}

	public Object getViewResource(String key, Class<?> type) {
		return type.cast(viewsResources.get(key));

	}

	public void addUrlParameter(String key, String value) {
		urlParameters.put(key, value);
	}

	public String getUrlParameter(String key) {
		return urlParameters.get(key);
	}

	public String getCurrentAction() {
		return currentAction.getName();
	}

	public String getCurrentController() {
		return currentController.getControllerClass().getName();
	}

	public void setCurrentAction(Action currentAction) {
		this.currentAction = currentAction;
	}

	public void setCurrentController(Controller currentController) {
		this.currentController = currentController;
	}

	public String generateUrl(String actionName, Map<String, String> params)
			 {
		try{
		Action a = ApplicationProcessor.findActionByName(actionName);
		if (a == null)
			throw new NoSuchActionNameException(actionName);

		String url = a.getUrl();
		List<HashMap<String, String>> actionParams = ActionRouter
				.getUrlParams(a);

		HashMap<String, String> actionParamsKeys = new HashMap<String, String>();
		for (HashMap<String, String> p : actionParams) {
			actionParamsKeys.put(p.get("parameter"), null);
		}

		Iterator<String> keys = params.keySet().iterator();
		String key;
		while (keys.hasNext()) {
			key = keys.next();
			if (!actionParamsKeys.containsKey(key))
				throw new NoSuchUrlParameterException(key);
			url = url.replace(":" + key, params.get(key));
		}
		return (servletRequest.getContextPath() + "/" + servletRequest.getServletPath() + "/" + url
				.replace("*", "")).replaceAll("/+", "/");
		
		}
		catch(Exception e){ e.printStackTrace(); }
		return null;
	}
	
	public String generateUrl(String actionName){
		return this.generateUrl(actionName,new HashMap<String,String>());
	}

	public String generateUrl(String actionName, String[] keys, String[] values){
		if (keys.length != values.length)
			throw new IllegalArgumentException(
					"The keys and the values arrays must have the same size");
		HashMap<String, String> params = new HashMap<String, String>();
		for (int c = 0; c < keys.length; params.put(keys[c], values[c]), c++);
		return generateUrl(actionName,params);
	}

	public AsyncContext getAsyncContext() {
		return servletRequest.getAsyncContext();
	}

	public String getCharacterEncoding() {
		return servletRequest.getCharacterEncoding();
	}

	public int getContentLength() {
		return servletRequest.getContentLength();
	}

	public String getContentType() {
		return servletRequest.getContentType();

	}

	public ServletInputStream getInputStream() throws IOException {
		return servletRequest.getInputStream();
	}

	public String getLocalAddr() {
		return servletRequest.getLocalAddr();

	}

	public String getLocalName() {

		return servletRequest.getLocalName();
	}

	public int getLocalPort() {
		return servletRequest.getLocalPort();
	}

	public Locale getLocale() {
		return servletRequest.getLocale();
	}

	public Enumeration<Locale> getLocales() {
		return servletRequest.getLocales();
	}

	public String getParameter(String arg0) {
		return servletRequest.getParameter(arg0);
	}

	public Map<String, String[]> getParameterMap() {
		return servletRequest.getParameterMap();

	}

	public Enumeration<String> getParameterNames() {
		return servletRequest.getParameterNames();

	}

	public String[] getParameterValues(String arg0) {
		return servletRequest.getParameterValues(arg0);
	}

	public String getProtocol() {
		return servletRequest.getProtocol();
	}

	public BufferedReader getReader() throws IOException {
		return servletRequest.getReader();
	}

	@SuppressWarnings("deprecation")
	public String getRealPath(String arg0) {
		return servletRequest.getRealPath(arg0);
	}

	public String getRemoteAddr() {
		return servletRequest.getRemoteAddr();
	}

	public String getRemoteHost() {
		return servletRequest.getRemoteHost();
	}

	public int getRemotePort() {
		return servletRequest.getRemotePort();
	}

	public String getScheme() {
		return servletRequest.getScheme();
	}

	public String getServerName() {
		return servletRequest.getServerName();
	}

	public int getServerPort() {
		return servletRequest.getServerPort();
	}

	public boolean isAsyncStarted() {
		return servletRequest.isAsyncStarted();
	}

	public boolean isAsyncSupported() {
		return servletRequest.isAsyncSupported();
	}

	public boolean isSecure() {
		return servletRequest.isSecure();
	}

	public void setCharacterEncoding(String arg0)
			throws UnsupportedEncodingException {
		servletRequest.setCharacterEncoding(arg0);
	}

	public AsyncContext startAsync() {
		return servletRequest.startAsync();
	}

	public AsyncContext startAsync(ServletRequest arg0, ServletResponse arg1) {
		return servletRequest.startAsync(arg0, arg1);
	}

	public boolean authenticate(HttpServletResponse arg0) throws IOException,
			ServletException {
		return servletRequest.authenticate(arg0);

	}

	public String getAuthType() {
		return servletRequest.getAuthType();
	}

	public String getContextPath() {
		return servletRequest.getContextPath();
	}

	public Cookie[] getCookies() {
		return servletRequest.getCookies();
	}

	public long getDateHeader(String arg0) {
		return servletRequest.getDateHeader(arg0);
	}

	public String getHeader(String arg0) {
		return servletRequest.getHeader(arg0);
	}

	public Enumeration<String> getHeaderNames() {
		return servletRequest.getHeaderNames();
	}

	public Enumeration<String> getHeaders(String arg0) {
		return servletRequest.getHeaders(arg0);
	}

	public int getIntHeader(String arg0) {
		return servletRequest.getIntHeader(arg0);
	}

	public String getMethod() {
		return servletRequest.getMethod();
	}

	public Part getPart(String arg0) throws IOException, IllegalStateException,
			ServletException {

		return servletRequest.getPart(arg0);
	}

	public Collection<Part> getParts() throws IOException,
			IllegalStateException, ServletException {

		return servletRequest.getParts();
	}

	public String getPathInfo() {
		return servletRequest.getPathInfo();
	}

	public String getPathTranslated() {
		return servletRequest.getPathTranslated();
	}

	public String getQueryString() {
		return servletRequest.getQueryString();
	}

	public String getRemoteUser() {
		return servletRequest.getRemoteUser();
	}

	public String getRequestURI() {
		return servletRequest.getRequestURI();
	}

	public StringBuffer getRequestURL() {
		return servletRequest.getRequestURL();
	}

	public String getRequestedSessionId() {
		return servletRequest.getRequestedSessionId();
	}

	public String getServletPath() {
		return servletRequest.getServletPath();
	}

	public HttpSession getSession() {
		return servletRequest.getSession();
	}

	public HttpSession getSession(boolean arg0) {
		return servletRequest.getSession(arg0);
	}

	public Principal getUserPrincipal() {
		return servletRequest.getUserPrincipal();
	}

	public boolean isRequestedSessionIdFromCookie() {
		return servletRequest.isRequestedSessionIdFromCookie();

	}

	public boolean isRequestedSessionIdFromURL() {
		return servletRequest.isRequestedSessionIdFromURL();
	}

	public boolean isRequestedSessionIdValid() {
		return servletRequest.isRequestedSessionIdValid();
	}

	public boolean isUserInRole(String arg0) {
		return servletRequest.isUserInRole(arg0);
	}

	public void login(String arg0, String arg1) throws ServletException {
		servletRequest.login(arg0, arg1);
	}

	public void logout() throws ServletException {
		servletRequest.logout();
	}

}
