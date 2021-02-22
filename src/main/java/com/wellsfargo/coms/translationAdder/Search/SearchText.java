package com.wellsfargo.coms.translationAdder.Search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.log4j.Logger;

import com.wellsfargo.coms.translationAdder.App;
import  com.wellsfargo.coms.translationAdder.Languages.LanguagesList;

public class SearchText {
		
		final Logger logger = Logger.getLogger(App.class.getName());
        private static String filePath ="";
        static int fileCount = 0;
        int wordCount=0;
        String fileName, propertyName, fullFilePath;
        static int lineNumber=0;
        private String existingFilesForCurrentText, ignoreSearchForFiles;
        ResourceBundle rb = ResourceBundle.getBundle("config");
        private String folderPath = rb.getString("rootLocation");
        private String[] languagesList = LanguagesList.langFormats;
        ArrayList<String> fileNameList= new ArrayList<String>();
        ArrayList<String> filePathList= new ArrayList<String>();
        //private String ignoreFilesPath = rb.getString("ignoreFilesPath");
        //ResourceBundle rbForFilesIgnore = ResourceBundle.getBundle("resources/ignoreDoNotEdit");
        Properties props = new Properties();
        
        public String getFileName() {
        	return this.fileName;
        }
        public String getPropertyName() {
        	return this.propertyName;
        }
        public ArrayList<String> getFullFilePath() {
        	//return this.fullFilePath;
        	return this.filePathList;
        }

        public ArrayList<String> getFileNamesForText(String currentText, String hCFileName) {
        	try {
        		existingFilesForCurrentText = props.toString();
        		//existingFilesForCurrentText = rbForFilesIgnore.getString(currentText);
        	}
        	catch (MissingResourceException e) {
        		existingFilesForCurrentText="";
        	}
        	try {
        		ignoreSearchForFiles = rb.getString("IgNoReAlL");
        	}
        	catch (MissingResourceException e) {
        		ignoreSearchForFiles="";
        	}
    		
	        File dir = new File(folderPath);
	        List<File> files =(List<File>) FileUtils.listFiles( dir, TrueFileFilter.INSTANCE, DirectoryFileFilter.DIRECTORY);
	
	        for (File file : files) {
	            try {
	                filePath=file.getCanonicalPath();
	                	
	                if (file.isFile()) {
	                    //System.out.println(file.getName());
	                    fileName=file.getName();
	                    if((hCFileName!=null && !fileName.equals(hCFileName)) || 
	                    		!fileName.contains(".properties") || ignoreSearchForFiles.contains(fileName) || 
	                    		existingFilesForCurrentText.contains(currentText+"="+fileName)) 
	                    { continue; }
	                    else if(getLangCondition(fileName)) { continue; }
	                    try {
	                    	wordCount=0;
	                        FileReader reader = new FileReader(filePath);
	                        BufferedReader br = new BufferedReader(reader); 
	                        String s,value; 
	                        while((s = br.readLine()) != null) { 
	                        	try {
	                        		value = s.split("=")[1].toLowerCase().trim();
	                        	}
	                        	catch(ArrayIndexOutOfBoundsException e) {
	                        		value = s.toLowerCase();
	                        	}
	                            lineNumber++;
	                            boolean condition=(value.equals(currentText.toLowerCase())||value.equals(" "+currentText.toLowerCase()));
	                            if(condition){	                      
                                    this.propertyName = s.split("=")[0];
                                    this.fileName = file.getName();
                                    this.fullFilePath = file.getCanonicalPath();
                                    filePathList.add(file.getCanonicalPath());
                                   // BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ignoreFilesPath), "UTF-8"));
                                    props.put(currentText.toLowerCase(), file.getName());
                	            	//props.store(out,null);
                        			//out.close();
                        			if (wordCount>1) {
	    	                        	//System.out.print("Duplicate property found: " + " in "+ file.getName()+ " at " + 
	                                			//"line "+lineNumber+"\t"+ "---- "+s.trim()+ " ----\n");
                        				logger.info("Duplicate property found: " + " in "+ file.getName()+ " at " + 
	                                			"line "+lineNumber+"\t"+ "---- "+s.trim()+ " ----\n");
	    	                        }
                        			else {
                            			//System.out.println(currentText + " is found in "+ file.getName()+ " at " + 
                            			//		"line "+lineNumber+"\t"+ "---- "+s.trim()+ " ----\n");
                        				logger.info("\""+currentText+"\"" + " is found in "+ file.getName()+ " at " + 
                            					"line "+lineNumber+"\t"+ "---- "+s.trim()+ " ----\n");
                        			}
                        			wordCount++;                                   
	                            }                           
	                        }	                        
	                        br.close();
	                        if(wordCount!=0) { 
	                        	fileNameList.add(file.getName());
	                        	
	                        	//return file.getName(); 
	                        	}
	                    }
	                    catch(Exception e){
	                        e.printStackTrace();
	                    }
	                    }
	                lineNumber=0;
	
	            } catch (IOException e) {
	            	//System.out.print("Cant find " + currentText);
	                //e.printStackTrace();
	            	logger.error("Cant find " + "\""+currentText+"\"" + ", ", e);
	            }
	         }
	        return fileNameList;
			//return null;
	       }
        
        public static boolean isWord(String s) {
            return (s.length() > 0 && s.split("\\s+").length <= 2);
        }
        private boolean getLangCondition(String fileName) {
        	int k=0; boolean langCondition=false;
            while(k<languagesList.length && langCondition!=true) {
            	langCondition = langCondition || fileName.contains(languagesList[k]);
            	k++;
            }
            return langCondition;
        }
    }