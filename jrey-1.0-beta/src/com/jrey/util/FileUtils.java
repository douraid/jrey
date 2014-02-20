package com.jrey.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {

	public static String getFileContent(String filepath) throws IOException{
		File f = new File(filepath);
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String line;
		String fileContent = "";
		while((line=reader.readLine())!=null){
			fileContent += line;
		}
		reader.close();
		return fileContent;
	}
}
