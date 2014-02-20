package com.jrey.handlers;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public class PreExecuteMethodsHandler {

	public static List<Method> getPreExecuteMethods(Class<?> ctrlClass) {
		List<Method> methods = new LinkedList<Method>();
		Method[] objectMethods = ctrlClass.getDeclaredMethods();
		for (Method method : objectMethods) {
			if (method
					.isAnnotationPresent(com.jrey.annotations.PreExecute.class)) {
				methods.add(method);
			}
		}
		return methods;
	}

}
