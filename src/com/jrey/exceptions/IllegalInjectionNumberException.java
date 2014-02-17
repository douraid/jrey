package com.jrey.exceptions;

public class IllegalInjectionNumberException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public IllegalInjectionNumberException(){
		super();
	}
	
	public IllegalInjectionNumberException(String message){
		super(message);
	}

}
