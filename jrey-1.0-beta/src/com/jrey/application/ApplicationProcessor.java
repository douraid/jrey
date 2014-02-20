package com.jrey.application;

import java.util.List;

import com.jrey.controller.Action;
import com.jrey.controller.ActionRouter;
import com.jrey.controller.Controller;
import com.jrey.controller.MainController;

public class ApplicationProcessor {

	public static Controller getControllerByActionUrl(String urlPattern,
			List<Controller> clist) {
		Controller controller = null;
		for (Controller c : clist) {
			List<Action> actionList = c.getActions();
			for (Action a : actionList) {

				if (ActionRouter.isUrlForAction(urlPattern, a)) {
					controller = c;
					break;
				}
			}
		}
		return controller;
	}


	public static Action getActionByUrlPattern(Controller c, String urlPattern) {
		Action action = null;
		for (Action a : c.getActions()) {
			if (ActionRouter.isUrlForAction(urlPattern, a)) {
				action = a;
				break;
			}
		}
		return action;
	}

	public static Action findActionByName(String actionName) {
		for (Controller c : MainController.controllers) {
			for (Action a : c.getActions()) {
				if (a.getName().equals(actionName))
					return a;
			}
		}
		return null;
	}

}
