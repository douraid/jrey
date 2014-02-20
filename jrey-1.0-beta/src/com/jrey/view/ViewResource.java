package com.jrey.view;

public class ViewResource {

	private Class<?> RClass;
	private String key;
	private Object value;
	
	public ViewResource(String key, Object value, Class<?> RClass) {
		super();
		this.key = key;
		this.value = value;
		this.RClass = RClass;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Class<?> getRClass() {
		return RClass;
	}

	public void setRClass(Class<?> rClass) {
		RClass = rClass;
	}
	
	
	
}
