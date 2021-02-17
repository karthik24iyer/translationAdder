package com.wellsfargo.coms.translationAdder;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;

import org.apache.commons.io.FilenameUtils;

import com.wellsfargo.coms.translationAdder.Search.SearchFile;
import com.wellsfargo.coms.translationAdder.Search.SearchProperty;
import com.wellsfargo.coms.translationAdder.Search.SearchText;

public class RunIterator {
	
	final Logger logger = Logger.getLogger(App.class.getName());
	
	private String currentEnWord;
	private String standardEngFileName;
	ArrayList<String> standardEngFileNameList = new ArrayList<String>();
	private String currentTranslationFileName;
	private String currentPropertyName;
	private String engFileLocation;
	ArrayList<String> engFileLocationList = new ArrayList<String>();
	private SearchFile sf = new SearchFile();
	SearchText st = new SearchText();
	ResourceBundle rb = ResourceBundle.getBundle("config");
	//ResourceBundle rbForFilesIgnore = ResourceBundle.getBundle("resources/ignoreDoNotEdit");
	String ignoreFilesPath = rb.getString("ignoreFilesPath");
	String getHCFileName = rb.getString("searchOnlyInFile");
	
	public RunIterator(String[] currentLangs, String[][] translationData) {
				
		for(int i=0;i<(translationData[i].length) && translationData[i][0]!=null;i++) {

			// Fetching English translation for each corresponding word
			this.currentEnWord = translationData[i][0].trim();
			if(triggerEnSearch(currentEnWord)==null) {
				//System.out.println("\""+currentEnWord+"\" (old) word not found in properties files\n");
				logger.info("\""+currentEnWord+"\" word not found in properties files\n");
				continue;
			}
			int k=0;
			while(k<standardEngFileNameList.size()) {
				this.standardEngFileName = standardEngFileNameList.get(k);
				this.engFileLocation = engFileLocationList.get(k);
				triggerSearch(translationData[i][1].trim(),"eng");
				k++;
			}

			for(int j=2;j<currentLangs.length && currentLangs[j]!=null; j++) {
				//System.out.print(translationData[i][j]+"\t");
				if(translationData[i][j]==null||translationData[i][j].isEmpty()) {
					//System.out.println("Translations not present for \""+currentEnWord+"\" in " + currentLangs[j] + "\n");
					logger.info("Translations not present for \""+currentEnWord+"\" in " + currentLangs[j] + "\n");
					continue;
				}
				k=0;
				while(k<standardEngFileNameList.size()) {
					this.standardEngFileName = standardEngFileNameList.get(k);
					this.engFileLocation = engFileLocationList.get(k);
					triggerSearch(translationData[i][j].trim(),currentLangs[j].trim());
					k++;
				}
				
				
			}
			standardEngFileNameList.clear();
			engFileLocationList.clear();
			System.out.println();
		}
	}
	
	public String triggerEnSearch(String enWord) {
		//System.out.print(st.getFileNameForText(enWord)+"\t"+st.getPropertyName()+"\n");
		if(getHCFileName!=null && getHCFileName.contains(".properties")) {
			this.standardEngFileNameList = st.getFileNamesForText(enWord,getHCFileName);
		}
		else {
			this.standardEngFileNameList = st.getFileNamesForText(enWord,null);
		}			
		if(standardEngFileNameList.isEmpty()) {
			return null;
		}
		this.currentPropertyName = st.getPropertyName();
		this.engFileLocationList = st.getFullFilePath();
		return "";
	}
	
	public void triggerSearch(String transWord, String currentLang) {
			this.currentTranslationFileName = sf.runFileReader(standardEngFileName,currentLang);
			//System.out.println(currentTranslationFileName+"\t"+C:\\"+FilenameUtils.getPath(engFileLocation)+"\t"+currentPropertyName+"\t"+transWord);
			new SearchProperty(currentTranslationFileName,"C:\\"+FilenameUtils.getPath(engFileLocation),currentPropertyName.trim(), transWord);
	}
}