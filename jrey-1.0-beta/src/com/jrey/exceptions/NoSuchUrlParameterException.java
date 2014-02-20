package com.jrey.exceptions;

@SuppressWarnings("serial")
public class NoSuchUrlParameterException extends Exception {
	
		public NoSuchUrlParameterException(String param){
			super("The requested action URL does not contain a parameter with the following name:" + param );
		}
}
