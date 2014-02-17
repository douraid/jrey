package com.jrey.handlers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import com.jrey.controller.Action;
import com.jrey.view.View;
import com.jrey.view.ViewResource;

public class ViewHandler {

	private ViewHandler() {

	}

	public static View buildView(com.jrey.annotations.View a) {
		View instance = new View(a.path());
		return instance;
	}

	public static List<ViewResource> getViewResources(Method method,
			Object repObject) throws IllegalArgumentException,
			IllegalAccessException {
		List<ViewResource> resources = new LinkedList<ViewResource>();
		Field[] fields = repObject.getClass().getDeclaredFields();


		for (Field f : fields) {

			if (f.isAnnotationPresent(com.jrey.annotations.ViewResource.class)) {

				com.jrey.annotations.ViewResource va = f
						.getAnnotation(com.jrey.annotations.ViewResource.class);
				f.setAccessible(true);
				ViewResource resource = new ViewResource(va.key(),
						f.get(repObject), repObject.getClass());
				resources.add(resource);

			}
		}
		return resources;
	}
	
	public static void setViewResources(Action a, Object cobject){
		try {
			a.getView().setResources(
					ViewHandler
							.getViewResources(a.getMethod(), cobject));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	


}
