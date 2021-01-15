package com.wellsfargo.coms.translationAdder.Readers;

import java.io.File;
import java.util.ResourceBundle;

import com.wellsfargo.coms.translationAdder.languages.LanguagesList.LangFormats;

public class SearchFile {
	
	private SearchText getFileName = new SearchText();
	private String language;
	ResourceBundle rb = ResourceBundle.getBundle("resources/config");
    private String folderPath = rb.getString("rootLocation");
	
	public String runFileReader(String fileName, String currentLang) {
		this.language = currentLang;
		return runSearch(splitFileName(fileName));
	}
	
	private String splitFileName(String fileName) {
		return fileName.split("\\.")[0];
	}

	public String runSearch(String fileName) {
		File found = searchFile(new File(folderPath), fileName +"_"+ language + ".properties");
		return found.getName();
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