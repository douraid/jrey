package com.jrey.view;

import java.util.List;

public class View {

	private String name;
	private List<ViewResource> resources;
	private List<Object> localViewResources;
	private Layout layout = null;

	public View(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ViewResource> getResources() {
		return resources;
	}

	public void setResources(List<ViewResource> resources) {
		this.resources = resources;
	}

	public List<Object> getLocalViewResources() {
		return localViewResources;
	}

	public void setLocalViewResources(List<Object> localViewResources) {
		this.localViewResources = localViewResources;
	}

	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

}
