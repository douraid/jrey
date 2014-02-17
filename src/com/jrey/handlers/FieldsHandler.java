package com.jrey.handlers;

import java.lang.reflect.Field;

import com.jrey.exceptions.IllegalInjectionNumberException;
import com.jrey.http.Request;
import com.jrey.http.Response;

public class FieldsHandler {

	private FieldsHandler() {

	}

	public static Request getInjectedRequestField(Object obj)
			throws IllegalArgumentException, IllegalAccessException,
			IllegalInjectionNumberException {
		Request robject = null;
		Field[] fields = obj.getClass().getDeclaredFields();
		int counter = 0;
		for (Field f : fields) {
			f.setAccessible(true);
			if (f.isAnnotationPresent(com.jrey.annotations.InjectedRequest.class)) {
				f.set(obj, new Request(null));
				robject = (Request) f.get(obj);
				counter++;
			}
		}
		if (counter > 1)
			throw new IllegalInjectionNumberException(
					"Injected Request is allowed for one time only per controller");

		return robject;
	}

	public static Response getInjectedResponseField(Object obj)
			throws IllegalArgumentException, IllegalAccessException,
			IllegalInjectionNumberException {
		Response robject = null;
		Field[] fields = obj.getClass().getDeclaredFields();
		int counter = 0;
		for (Field f : fields) {
			f.setAccessible(true);
			if (f.isAnnotationPresent(com.jrey.annotations.InjectedResponse.class)) {
				robject = (Response) f.get(obj);
				counter++;
			}
		}
		if (counter > 1)
			throw new IllegalInjectionNumberException(
					"Injected response is allowed for one time only per controller");
		return robject;
	}

}
