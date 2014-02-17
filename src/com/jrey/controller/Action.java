package com.jrey.controller;

import java.lang.reflect.Method;


import com.jrey.view.View;

public class Action {
	
	private Controller controller;
	private String name;
	private Method method;
	private String url;
	private View view;
	private com.jrey.annotations.Action.Method HTTP_METHOD;
	

	
	public Action(String name,Method method, String url, View view, com.jrey.annotations.Action.Method HTTP_METHOD) {
		super();
		this.name = name;
		this.method = method;
		this.url = url;
		this.view = view;
		this.HTTP_METHOD = HTTP_METHOD;
	}
	
	public Action(){
		
	}
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String URL) {
		this.url = URL;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public com.jrey.annotations.Action.Method getHTTP_METHOD() {
		return HTTP_METHOD;
	}

	public void setHTTP_METHOD(com.jrey.annotations.Action.Method hTTP_METHOD) {
		HTTP_METHOD = hTTP_METHOD;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	
	
	
	

}
