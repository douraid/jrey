package com.jrey.view;

import java.util.HashMap;



public class Layout {
	
	public static Layout LAYOUT_UNDEFINED = new Layout();
	public static Layout NO_LAYOUT = new Layout();
	

	private String filePath;
	private HashMap<String, String> blocs;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public HashMap<String, String> getBlocs() {
		return blocs;
	}

	public void setBlocs(HashMap<String, String> blocs) {
		this.blocs = blocs;
	}

}
