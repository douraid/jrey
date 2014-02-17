package com.jrey.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.xml.sax.SAXException;

import com.jrey.application.AppConfig;
import com.jrey.application.ApplicationProcessor;
import com.jrey.exceptions.HttpMethodNotAllowedException;
import com.jrey.exceptions.IllegalInjectionNumberException;
import com.jrey.handlers.ConfigHandler;
import com.jrey.handlers.ControllerHandler;
import com.jrey.handlers.ViewHandler;
import com.jrey.http.Request;
import com.jrey.http.Response;
import com.jrey.templating.TemplateEngine;
import com.jrey.view.ViewResource;

public class MainController extends HttpServlet {



	private static final long serialVersionUID = 5218431671365220161L;

	@ApplicationScoped
	public static List<Controller> controllers;

	@ApplicationScoped
	private AppConfig appConfig;

	public MainController() throws ParserConfigurationException, SAXException,
			IOException, IllegalInjectionNumberException {
		super();
		ControllerHandler controllerhandler = new ControllerHandler();
		ConfigHandler confighandler = new ConfigHandler();
		if (controllers == null)
			controllers = controllerhandler.getControllersList();
		if (appConfig == null)
			appConfig = confighandler.getConfiguration();

	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {
			doProcess(req, resp, com.jrey.annotations.Action.Method.GET);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			doProcess(req, resp, com.jrey.annotations.Action.Method.POST);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {
			doProcess(req, resp, com.jrey.annotations.Action.Method.PUT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {
			doProcess(req, resp, com.jrey.annotations.Action.Method.DELETE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {
			doProcess(req, resp, com.jrey.annotations.Action.Method.HEAD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {
			doProcess(req, resp, com.jrey.annotations.Action.Method.OPTIONS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doTrace(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {
			doProcess(req, resp, com.jrey.annotations.Action.Method.TRACE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doProcess(HttpServletRequest request,
			HttpServletResponse response,
			com.jrey.annotations.Action.Method httpMethod)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, ServletException, IOException,
			HttpMethodNotAllowedException, ResourceNotFoundException, ParseErrorException, MethodInvocationException, ParserConfigurationException, SAXException {

		Controller c = ApplicationProcessor.getControllerByActionUrl(
				request.getPathInfo(), controllers);

		if (c != null) {

			Action a = ApplicationProcessor.getActionByUrlPattern(c,
					request.getPathInfo());

			if (a.getHTTP_METHOD() != com.jrey.annotations.Action.Method.ANY) {
				if (a.getHTTP_METHOD() != httpMethod) {
					throw new HttpMethodNotAllowedException(
							httpMethod.toString());
				}
			}

			c.setInjectedRequest(new Request(request));
			c.setInjectedResponse(new Response(response));
			c.getInjectedRequest().setCurrentAction(a);
			c.getInjectedRequest().setCurrentController(c);

			HashMap<String, String> paramsMap = ActionRouter.getUrlParams(a,
					request.getPathInfo());
			Set<String> keys = paramsMap.keySet();
			Iterator<String> it = keys.iterator();
			String key;
			while (it.hasNext()) {
				key = it.next();
				c.getInjectedRequest().addUrlParameter(key, paramsMap.get(key));
			}

			for (Method m : c.getPreExecs()) {
				m.setAccessible(true);
				m.invoke(c.getControllerObject());
			}

			a.getMethod().setAccessible(true);
			
			HashMap<String, Object> viewContextVars = new HashMap<String, Object>();

			
			if (a.getMethod().getReturnType().equals(Void.TYPE))
				a.getMethod().invoke(c.getControllerObject());
			else if(a.getMethod().getReturnType().equals(HashMap.class)){
				@SuppressWarnings("unchecked")
				HashMap<String,Object> actionReturn = (HashMap<String, Object>) a.getMethod().getReturnType().cast(a.getMethod().invoke(c.getControllerObject()));
				Iterator<String> keyset = actionReturn.keySet().iterator();
				String varkey;
				while(keyset.hasNext()){
					varkey = keyset.next();
					viewContextVars.put(varkey, actionReturn.get(varkey));
				}
			}

			if (a.getView() != null) {

				ViewHandler.setViewResources(a, c.getControllerObject());
				for (ViewResource res : a.getView().getResources()) {
					if (c.getInjectedRequest() != null) {
						c.getInjectedRequest().addViewResource(res.getKey(),
								res.getValue());

					}

				}
				

				viewContextVars.put("Request", c.getInjectedRequest());
				viewContextVars.put("Response", c.getInjectedRequest());
				viewContextVars.put("Session", request.getSession());
				
				String webRoot = this.getServletContext().getRealPath("/");
					
				String output = TemplateEngine.getInstance().getTemplateOutput(
						webRoot, (appConfig.getViewsPath() + a.getView().getName())
						.replace("//", "/"), viewContextVars,a);

				response.setContentType("text/html");
				response.setContentLength(output.length());
				response.getWriter().print(output);
				response.getWriter().flush();

			}

		}
	}

}