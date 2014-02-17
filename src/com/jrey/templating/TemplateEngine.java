package com.jrey.templating;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.xml.sax.SAXException;

import com.jrey.controller.Action;
import com.jrey.handlers.ConfigHandler;
import com.jrey.handlers.LayoutHandler;
import com.jrey.view.Layout;

public class TemplateEngine {

	private VelocityContext context;
	private Template template;
	private final VelocityEngine vcengine = new VelocityEngine();

	private static TemplateEngine instance = null;

	private TemplateEngine() {

	}

	public static TemplateEngine getInstance() {
		if (instance == null)
			instance = new TemplateEngine();
		return instance;
	}

	public String getTemplateOutput(String webRoot, String templatePath,
			HashMap<String, Object> viewResources, Action action)
			throws ResourceNotFoundException, ParseErrorException,
			MethodInvocationException, ParserConfigurationException,
			SAXException, IOException {

		Properties props = new Properties();
		ConfigHandler config = new ConfigHandler();
		props.put("file.resource.loader.path", webRoot);

		vcengine.init(props);
		context = new VelocityContext();
		StringWriter output = new StringWriter();
		String mainContent = "";

		Set<String> rkeys = viewResources.keySet();
		Iterator<String> keys = rkeys.iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			context.put(
					key,
					viewResources.get(key).getClass()
							.cast(viewResources.get(key)));
		}

		String layoutPath = null;

		if (action.getView().getLayout() != null) {
			if (!action.getView().getLayout().equals(Layout.NO_LAYOUT)){
				layoutPath = (config.getConfiguration().getViewsPath() + "/" + action
						.getView().getLayout().getFilePath())
						.replace("//", "/");
			
			}
		}

		else if (action.getController().getLayout() != null) {
			if (!action.getController().getLayout().equals(Layout.NO_LAYOUT)){
				layoutPath = (config.getConfiguration().getViewsPath() + "/" + action
						.getController().getLayout().getFilePath()).replace(
						"//", "/");
			}

		}

		else if (config.getConfiguration().getDefaultLayout() != null) {
			
			if(action.getController().getLayout()!=null){
			if (action.getController().getLayout().equals(Layout.NO_LAYOUT)) {

				layoutPath = null;
			}
			}
			
			else if(action.getView().getLayout()!=null){
			if (action.getView().getLayout().equals(Layout.NO_LAYOUT)) {
				layoutPath = null;
			}
			}
			else
				layoutPath = (config.getConfiguration().getViewsPath() + "/" + config
						.getConfiguration().getDefaultLayout()).replace("//",
						"/");
			
			}
			



		if (layoutPath != null) {
			template = vcengine.getTemplate(layoutPath);
			template.merge(context, output);
			String layoutContent = output.toString();
			output = new StringWriter();
			template = vcengine.getTemplate(templatePath);
			template.merge(context, output);
			String viewContent = output.toString();

			HashMap<String, String> layoutBlocs = LayoutHandler.getInstance()
					.getLayoutBlocs(layoutContent);
			HashMap<String, String> viewBlocs = LayoutHandler.getInstance()
					.getLayoutBlocs(viewContent);
			Iterator<String> bkeys = layoutBlocs.keySet().iterator();

			String tempKey;
			while (bkeys.hasNext()) {

				tempKey = bkeys.next();

				if (viewBlocs.containsKey(tempKey)) {

					layoutContent = layoutContent
							.replaceAll(
									String.format(
											"\\Q[region::%s]\\E[^\\Q[region::end]\\E]*\\Q[region::end]\\E",
											tempKey), layoutBlocs.get(tempKey)
											+ viewBlocs.get(tempKey));

				}
			}

			mainContent = layoutContent
					.replaceAll(
							"\\Q[region::\\E.*\\Q]\\E[^\\Q[region::end]\\E]*\\Q[region::end]\\E",
							"");
			;
		} else {
			template = vcengine.getTemplate(templatePath);
			output = new StringWriter();
			template.merge(context, output);
			mainContent = output.toString();
		}

		return mainContent;

	}
}