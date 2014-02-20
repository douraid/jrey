package com.jrey.handlers;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.jrey.controller.Controller;
import com.jrey.exceptions.IllegalInjectionNumberException;
import com.jrey.util.FileUtils;
import com.jrey.util.XMLParser;
import com.jrey.view.Layout;

public class ControllerHandler extends XMLParser {

	public ControllerHandler() throws ParserConfigurationException {
		super();
	}

	private List<Controller> controllersList;

	private void fetchControllersList(String webRoot) throws IllegalInjectionNumberException, ParserConfigurationException {
		List<Controller> clist = new LinkedList<Controller>();
		try {
			Document doc = parseFromInputStream(this.getClass()
					.getClassLoader()
					.getResourceAsStream("./META-INF/jrey-meta.xml"));
			NodeList nodelist = doc.getElementsByTagName("controller");
			for (int c = 0; c < nodelist.getLength(); c++) {
				Node elem = nodelist.item(c);

				if (elem.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) elem;
					String c_class = e.getElementsByTagName("class").item(0)
							.getTextContent();
					Class<?> cls = Class.forName(c_class);
					Constructor<?> builder = cls.getConstructor();
					Object cobject = builder.newInstance();
					cobject = cls.cast(cobject);

					Controller controller = new Controller(cls);
					controller.setControllerObject(cobject);
					controller.setActions(ActionHandler.getActionList(cobject,controller, webRoot));
					controller.setPreExecs(PreExecuteMethodsHandler
							.getPreExecuteMethods(cls));
					controller.setInjectedRequest(FieldsHandler
							.getInjectedRequestField(cobject));
					controller.setInjectedResponse(FieldsHandler
							.getInjectedResponseField(cobject));

					if (cobject.getClass().isAnnotationPresent(
							com.jrey.annotations.Layout.class)) {
						Layout clayout = new Layout();
						clayout.setFilePath(cobject
								.getClass()
								.getAnnotation(
										com.jrey.annotations.Layout.class)
								.path());

						clayout.setBlocs(LayoutHandler.getInstance()
								.getLayoutBlocs(
										FileUtils.getFileContent((webRoot + "/" +(new ConfigHandler()).getConfiguration().getViewsPath() +clayout
												.getFilePath()).replaceAll("/+", "/"))));
						controller.setLayout(clayout);
					}
					else if(cobject.getClass().isAnnotationPresent(com.jrey.annotations.NoLayout.class)){
						controller.setLayout(Layout.NO_LAYOUT);
					}

					clist.add(controller);
				}
			}

		} catch (SAXException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		} catch (SecurityException e1) {

			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {

			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {

			e1.printStackTrace();
		} catch (InstantiationException e1) {

			e1.printStackTrace();
		} catch (IllegalAccessException e1) {

			e1.printStackTrace();
		} catch (InvocationTargetException e1) {

			e1.printStackTrace();
		}
		this.controllersList = clist;
	}

	public List<Controller> getControllersList(String webRoot)
			throws IllegalInjectionNumberException, ParserConfigurationException {
		if (controllersList == null) {
			this.fetchControllersList(webRoot);
		}
		return controllersList;
	}

}
