package com.jrey.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
		
		ConfigHandler confighandler = new ConfigHandler();
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
			HttpMethodNotAllowedException, ResourceNotFoundException, ParseErrorException, MethodInvocationException, ParserConfigurationException, SAXException, IllegalInjectionNumberException {

		ControllerHandler controllerhandler = new ControllerHandler();
		if (controllers == null)
			controllers = controllerhandler.getControllersList(this.getServletContext().getRealPath("/"));

		
		Controller c = ApplicationProcessor.getControllerByActionUrl(
				request.getPathInfo(), controllers);

		if (c != null) {

			Action a = ApplicationProcessor.getActionByUrlPattern(c,
					request.getPathInfo());

			if (a.getHTTP_METHOD() != com.jrey.annotations.Action.Method.ANY && a.getHTTP_METHOD() != httpMethod) {

					String message = String.format("The HTTP method %s is not allowed for this location",httpMethod.toString());
					response.setContentType("text/html");
					response.setContentLength(message.length());
					response.getWriter().print(message);
					response.getWriter().flush();
				
			}
			
			else{

			Request injReq = new Request(request);
			Response injResp = new Response(response);
			c.setInjectedRequest(injReq);
			c.setInjectedResponse(injResp);
			injReq.setCurrentAction(a);
			injReq.setCurrentController(c);
			

			HashMap<String, String> paramsMap = ActionRouter.getUrlParams(a,
					request.getPathInfo());
			Set<String> keys = paramsMap.keySet();
			Iterator<String> it = keys.iterator();
			String key;
			while (it.hasNext()) {
				key = it.next();
				injReq.addUrlParameter(key, paramsMap.get(key));
			}

			for (Method m : c.getPreExecs()) {
				m.setAccessible(true);
				m.invoke(c.getControllerObject());
			}

			a.getMethod().setAccessible(true);
			
			HashMap<String, Object> viewContextVars = new HashMap<String, Object>();

			
			if (a.getMethod().getReturnType().equals(Void.TYPE))
				a.getMethod().invoke(c.getControllerObject());
			else if(a.getMethod().getReturnType().equals(Map.class) || a.getMethod().getReturnType().equals(HashMap.class)){
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
						injReq.addViewResource(res.getKey(),
								res.getValue());


				}
				

				viewContextVars.put("Request", injReq);
				viewContextVars.put("Response", injResp);
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
		
		else{
			response.setContentType("text/html");
			response.getWriter().print("404 Not Found: The resource you're looking for is not available at this location.");
			response.getWriter().flush();
		}
	}

}