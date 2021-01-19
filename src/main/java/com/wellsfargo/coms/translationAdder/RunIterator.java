package com.wellsfargo.coms.translationAdder;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Properties;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;

import org.apache.commons.io.FilenameUtils;

import com.wellsfargo.coms.translationAdder.Search.SearchFile;
import com.wellsfargo.coms.translationAdder.Search.SearchProperty;
import com.wellsfargo.coms.translationAdder.Search.SearchText;
import com.wellsfargo.coms.translationAdder.languages.LanguagesList.LangFormats;

public class RunIterator {
	
	final Logger logger = Logger.getLogger(App.class.getName());
	
	private String currentEnWord;
	private String standardEngFileName;
	private String currentTranslationFileName;
	private String currentPropertyName;
	private String engFileLocation;
	private SearchFile sf = new SearchFile();
	SearchText st = new SearchText();
	ResourceBundle rb = ResourceBundle.getBundle("config");
	//ResourceBundle rbForFilesIgnore = ResourceBundle.getBundle("resources/ignoreDoNotEdit");
	 String ignoreFilesPath = rb.getString("ignoreFilesPath");
	
	public RunIterator(String[] currentLangs, String[][] translationData) {
				
		for(int i=0;i<(translationData[i].length) && translationData[i][0]!=null;i++) {
			try {       
				Properties props = new Properties();
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ignoreFilesPath), "UTF-8"));
				//BufferedWriter out = new BufferedWriter(new FileWriter(ignoreFilesPath)); 
				props.clear();
				props.store(out, "");
				}
			catch (IOException e) {
				logger.error("Cant find ignoreDoNotEdit.properties, ",e);
				//System.out.print("Cant find ignoreDoNotEdit.properties");
	            //e.printStackTrace();
	        }
			// Fetching English translation for each corresponding word
			this.currentEnWord = translationData[i][0].trim();
			if(triggerEnSearch(currentEnWord)==null) {
				//System.out.println("\""+currentEnWord+"\" (old) word not found in properties files\n");
				logger.info("\""+currentEnWord+"\" word not found in properties files\n");
				continue;
			}
			triggerSearch(translationData[i][1].trim(),"eng");
			for(int j=2;j<currentLangs.length && currentLangs[j]!=null; j++) {
				//System.out.print(translationData[i][j]+"\t");
				if(translationData[i][j]==null||translationData[i][j].isEmpty()) {
					//System.out.println("Translations not present for \""+currentEnWord+"\" in " + currentLangs[j] + "\n");
					logger.info("Translations not present for \""+currentEnWord+"\" in " + currentLangs[j] + "\n");
					continue;
				}
				triggerSearch(translationData[i][j].trim(),currentLangs[j].trim());
				
			}
			System.out.println();
		}
	}
	
	public String triggerEnSearch(String enWord) {
		//System.out.print(st.getFileNameForText(enWord)+"\t"+st.getPropertyName()+"\n");
		this.standardEngFileName = st.getFileNameForText(enWord);
		if(standardEngFileName==null) {
			return null;
		}
		this.currentPropertyName = st.getPropertyName();
		this.engFileLocation = st.getFullFilePath();
		return "";
	}
	
	public void triggerSearch(String transWord, String currentLang) {
			this.currentTranslationFileName = sf.runFileReader(standardEngFileName,currentLang);
			//System.out.println(currentTranslationFileName+"\t"+C:\\"+FilenameUtils.getPath(engFileLocation)+"\t"+currentPropertyName+"\t"+transWord);
			new SearchProperty(currentTranslationFileName,"C:\\"+FilenameUtils.getPath(engFileLocation),currentPropertyName, transWord);
	}
}