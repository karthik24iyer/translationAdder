package com.wellsfargo.coms.translationAdder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ResourceBundle;

import org.apache.commons.io.FilenameUtils;

import com.wellsfargo.coms.translationAdder.Readers.SearchFile;
import com.wellsfargo.coms.translationAdder.Readers.SearchProperty;
import com.wellsfargo.coms.translationAdder.Readers.SearchText;
import com.wellsfargo.coms.translationAdder.languages.LanguagesList.LangFormats;

public class RunIterator {
	
	private String currentEnWord;
	private String standardEngFileName;
	private String currentTranslationFileName;
	private String currentPropertyName;
	private String engFileLocation;
	private SearchFile sf = new SearchFile();
	SearchText st = new SearchText();

	public RunIterator(String[] currentLangs, String[][] translationData) {
		try {
			ResourceBundle rb = ResourceBundle.getBundle("resources/config");
	        String ignoreFilesPath = rb.getString("ignoreFilesPath");
	        System.out.println(ignoreFilesPath);
			BufferedWriter out = new BufferedWriter(new FileWriter(ignoreFilesPath)); 
			out.write(""); 
			out.close(); 
			}
		catch (IOException e) {
        	System.out.print("Cant find ignoreDoNotEdit.properties");
            e.printStackTrace();
        }
		
		for(int i=0;i<(translationData[i].length) && translationData[i][0]!=null;i++) {
			// Fetching English translation for each corresponding word
			this.currentEnWord = translationData[i][0];
			if(triggerEnSearch(currentEnWord)==null) {
				System.out.println("Translations already done for \""+currentEnWord+"\"\n");
				continue;
			}
			for(int j=1;j<currentLangs.length && currentLangs[j]!=null; j++) {
				//System.out.print(translationData[i][j]+"\t");
				if(translationData[i][j]==null||translationData[i][j].isEmpty()) {
					System.out.println("Translations not present for \""+currentEnWord+"\" in " + currentLangs[j] + "\n");
					continue;
				}
				triggerSearch(translationData[i][j],currentLangs[j]);
				
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