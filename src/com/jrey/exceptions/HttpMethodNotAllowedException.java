package com.jrey.exceptions;

public class HttpMethodNotAllowedException extends Exception {

	private static final long serialVersionUID = 1142760131259627672L;

	public HttpMethodNotAllowedException(String method) {
		super(String.format(
				"The HTTP method %s is not allowed for this location", method));
	}
}
