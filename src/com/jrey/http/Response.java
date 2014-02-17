package com.jrey.http;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


public class Response{
	private HttpServletResponse servletResponse;
	
	public Response(HttpServletResponse response) {
		servletResponse = response;
		
	}


	
	public void flushBuffer() throws IOException {
		servletResponse.flushBuffer();
	}

	
	public int getBufferSize() {
		return servletResponse.getBufferSize();
	}

	
	public String getCharacterEncoding() {
		
		return servletResponse.getCharacterEncoding();
	}

	
	public String getContentType() {
		
		return servletResponse.getContentType();
	}

	
	public Locale getLocale() {
		
		return servletResponse.getLocale();
	}

	
	public ServletOutputStream getOutputStream() throws IOException {
		
		return servletResponse.getOutputStream();
	}

	
	public PrintWriter getWriter() throws IOException {
		
		return servletResponse.getWriter();
	}

	
	public boolean isCommitted() {
		
		return servletResponse.isCommitted();
	}

	
	public void reset() {
		servletResponse.reset();
	}

	
	public void resetBuffer() {
		servletResponse.resetBuffer();
		
	}

	
	public void setBufferSize(int arg0) {
		servletResponse.setBufferSize(arg0);
	}

	
	public void setCharacterEncoding(String arg0) {
		servletResponse.setCharacterEncoding(arg0);
	}

	
	public void setContentLength(int arg0) {
		servletResponse.setContentLength(arg0);
		
	}

	
	public void setContentType(String arg0) {
		servletResponse.setContentType(arg0);
		
	}

	
	public void setLocale(Locale arg0) {
		servletResponse.setLocale(arg0);
		
	}

	
	public void addCookie(Cookie arg0) {
		servletResponse.addCookie(arg0);
		
	}

	
	public void addDateHeader(String arg0, long arg1) {
		servletResponse.addDateHeader(arg0, arg1);
		
	}

	
	public void addHeader(String arg0, String arg1) {
		servletResponse.addHeader(arg0, arg1);
		
	}

	
	public void addIntHeader(String arg0, int arg1) {
		servletResponse.addIntHeader(arg0, arg1);
		
	}

	
	public boolean containsHeader(String arg0) {
		
		return servletResponse.containsHeader(arg0);
	}

	
	public String encodeRedirectURL(String arg0) {
		
		return servletResponse.encodeRedirectURL(arg0);
	}


	
	public String encodeURL(String arg0) {
		
		return servletResponse.encodeURL(arg0);
	}


	
	public String getHeader(String arg0) {
		
		return servletResponse.getHeader(arg0);
	}

	
	public Collection<String> getHeaderNames() {
		
		return servletResponse.getHeaderNames();
	}

	
	public Collection<String> getHeaders(String arg0) {
		
		return servletResponse.getHeaders(arg0);
	}

	
	public int getStatus() {
		
		return servletResponse.getStatus();
	}

	
	public void sendError(int arg0) throws IOException {
		servletResponse.sendError(arg0);
		
	}

	
	public void sendError(int arg0, String arg1) throws IOException {
		servletResponse.sendError(arg0,arg1);
		
	}

	
	public void sendRedirect(String arg0) throws IOException {
		servletResponse.sendRedirect(arg0);
		
	}

	
	public void setDateHeader(String arg0, long arg1) {
		servletResponse.setDateHeader(arg0, arg1);
	}

	
	public void setHeader(String arg0, String arg1) {
		servletResponse.setHeader(arg0, arg1);
	}

	
	public void setIntHeader(String arg0, int arg1) {
		servletResponse.setIntHeader(arg0, arg1);
		
	}

	
	public void setStatus(int arg0) {
		servletResponse.setStatus(arg0);
		
	}
	
	

	
	

}
