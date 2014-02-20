package com.jrey.handlers;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import com.jrey.controller.Action;
import com.jrey.controller.Controller;
import com.jrey.util.FileUtils;
import com.jrey.view.Layout;

abstract class ActionHandler {

	public static List<Action> getActionList(Object controller, Controller parent, String webRoot)
			throws IOException {
		Class<?> C = controller.getClass();

		List<Action> actions = new LinkedList<Action>();
		for (Method method : C.getDeclaredMethods()) {

			if (method.isAnnotationPresent(com.jrey.annotations.Action.class)) {

				Action action = new Action();
				action.setController(parent);
				action.setName(method.getAnnotation(
						com.jrey.annotations.Action.class).name());
				action.setMethod(method);
				action.setHTTP_METHOD(method.getAnnotation(
						com.jrey.annotations.Action.class).method());
				action.setUrl(method.getAnnotation(
						com.jrey.annotations.Action.class).url());
				if (method.isAnnotationPresent(com.jrey.annotations.View.class)) {
					action.setView(ViewHandler.buildView(method
							.getAnnotation(com.jrey.annotations.View.class)));
					if (method
							.isAnnotationPresent(com.jrey.annotations.Layout.class)) {

						Layout vlayout = new Layout();
						vlayout.setFilePath(method.getAnnotation(
								com.jrey.annotations.Layout.class).path());

						vlayout.setBlocs(LayoutHandler.getInstance()
								.getLayoutBlocs(
										FileUtils.getFileContent((webRoot + vlayout
												.getFilePath()).replaceAll("/+", "/"))));

						action.getView().setLayout(vlayout);
					}
					else if(method.isAnnotationPresent(com.jrey.annotations.NoLayout.class)){
						action.getView().setLayout(Layout.NO_LAYOUT);
					}
				}
				actions.add(action);
			}

		}
		return actions;
	}
}
