package com.wellsfargo.coms.translationAdder.Search;

import java.io.File;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.wellsfargo.coms.translationAdder.App;

public class SearchFile {
	
	final Logger logger = Logger.getLogger(App.class.getName());
	private String language;
	ResourceBundle rb = ResourceBundle.getBundle("config");
    private String folderPath = rb.getString("rootLocation");
	
	public String runFileReader(String fileName, String currentLang) {
		this.language = currentLang;
		return runSearch(splitFileName(fileName));
	}
	
	private String splitFileName(String fileName) {
		return fileName.split("\\.")[0];
	}

	public String runSearch(String fileName) {
		try {
			if(language.equals("eng")) {
				File found = searchFile(new File(folderPath), fileName + ".properties");
				return found.getName();
			}
			else {
				File found = searchFile(new File(folderPath), fileName +"_"+ language + ".properties"); 
				return found.getName();
			}
			
		}
		catch(Exception e) {
			logger.error("Warning: Locale file "+fileName +"_"+ language + ".properties"+" not present for this translation, ",e);
			//System.out.println("Warning: Locale file "+fileName +"_"+ language + ".properties"+" not present for this translation");
			//e.printStackTrace();
		}
		return null;
	}

	public File searchFile(File file, String search) {
		if (file.isDirectory()) {
			File[] arr = file.listFiles();
			for (File f : arr) {
				File found = searchFile(f, search);
				if (found != null)
					return found;
			}
		} else {
			if (file.getName().equals(search)) {
				//System.out.println(file.getName() + "and search is = " + search);
				return file;
			}
		}
		return null;
	}
}