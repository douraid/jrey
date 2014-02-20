package com.jrey.exceptions;

@SuppressWarnings("serial")
public class NoSuchActionNameException extends Exception{

	public NoSuchActionNameException(String action_name){
		super("No action found with the following name: " + action_name);
	}
	
}
