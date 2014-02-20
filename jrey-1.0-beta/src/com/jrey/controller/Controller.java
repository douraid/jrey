package com.jrey.controller;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import com.jrey.http.Request;
import com.jrey.http.Response;
import com.jrey.view.Layout;

public final class Controller implements Serializable {

	private static final long serialVersionUID = 2476449152393551582L;

	private Class<?> controllerClass;
	private Object controllerObject;
	private List<Action> actions;
	private List<Method> preExecs;
	private Layout layout = null;

	public Controller(Class<?> controllerClass) {
		this.controllerClass = controllerClass;

	}

	public Class<?> getControllerClass() {
		return controllerClass;
	}

	public void setControllerClass(Class<?> controllerClass) {
		this.controllerClass = controllerClass;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public List<Method> getPreExecs() {
		return preExecs;
	}

	public void setPreExecs(List<Method> preExecs) {
		this.preExecs = preExecs;
	}

	public Request getInjectedRequest() throws IllegalArgumentException,
			IllegalAccessException {
		return (Request) getRequestField();
	}

	public void setInjectedRequest(Request injectedRequest)
			throws IllegalArgumentException, IllegalAccessException {

		for (Field f : controllerClass.getDeclaredFields()) {
			f.setAccessible(true);
			if (f.isAnnotationPresent(com.jrey.annotations.InjectedRequest.class)) {
				f.set(controllerObject, injectedRequest);
			}
		}
	}

	public Response getInjectedResponse() throws IllegalArgumentException,
			IllegalAccessException {
		return (Response) getResponseField();
	}

	public void setInjectedResponse(Response injectedResponse)
			throws IllegalArgumentException, IllegalAccessException {
		for (Field f : controllerClass.getDeclaredFields()) {
			f.setAccessible(true);
			if (f.isAnnotationPresent(com.jrey.annotations.InjectedResponse.class)) {
				f.set(controllerObject, injectedResponse);
			}
		}
	}

	public Object getControllerObject() {
		return controllerObject;
	}

	public void setControllerObject(Object controllerObject) {
		this.controllerObject = controllerObject;
	}

	private Request getRequestField() throws IllegalArgumentException,
			IllegalAccessException {
		for (Field f : controllerClass.getDeclaredFields()) {
			f.setAccessible(true);
			if (f.isAnnotationPresent(com.jrey.annotations.InjectedRequest.class)) {
				return (Request) f.get(controllerObject);
			}
		}
		return null;
	}

	private Response getResponseField() throws IllegalArgumentException,
			IllegalAccessException {
		for (Field f : controllerClass.getDeclaredFields()) {
			f.setAccessible(true);
			if (f.isAnnotationPresent(com.jrey.annotations.InjectedResponse.class)) {
				return (Response) f.get(controllerObject);
			}
		}
		return null;
	}

	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

}
