package com.wellsfargo.coms.translationAdder.Search;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.log4j.Logger;

import com.wellsfargo.coms.translationAdder.App;

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
        private String ignoreFilesPath = rb.getString("ignoreFilesPath");
        //ResourceBundle rbForFilesIgnore = ResourceBundle.getBundle("resources/ignoreDoNotEdit");
        Properties props = new Properties();
        
        
        public String getFileName() {
        	return this.fileName;
        }
        public String getPropertyName() {
        	return this.propertyName;
        }
        public String getFullFilePath() {
        	return this.fullFilePath;
        }

        public String getFileNameForText(String currentText) {
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
	                    if(!fileName.contains(".properties") || existingFilesForCurrentText.contains(currentText+"="+fileName) || fileName.contains("_au_AU.properties") 
	                    		|| fileName.contains("_en_GB.properties")|| ignoreSearchForFiles.contains(fileName)) { continue; }
	                    try {
	                    	wordCount=0;
	                        FileReader reader = new FileReader(filePath);
	                        BufferedReader br = new BufferedReader(reader); 
	                        String s,value; 
	                        while((s = br.readLine()) != null) { 
	                        	try {
	                        		value = s.split("=")[1].toLowerCase();
	                        	}
	                        	catch(ArrayIndexOutOfBoundsException e) {
	                        		value = s.toLowerCase();
	                        	}
	                            lineNumber++;
	                            boolean condition=(value.equals(currentText.toLowerCase())||value.contains(" "+currentText.toLowerCase()));
	                            if(condition){	                      
                                    this.propertyName = s.split("=")[0];
                                    this.fileName = file.getName();
                                    this.fullFilePath = file.getCanonicalPath();
                                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ignoreFilesPath), "UTF-8"));
                                    props.put(currentText, file.getName());
                	            	props.store(out, "");
                        			out.close();
                        			if (wordCount>1) {
	    	                        	//System.out.print("Duplicate property found: " + " in "+ file.getName()+ " at " + 
	                                			//"line "+lineNumber+"\t"+ "---- "+s.trim()+ " ----\n");
                        				logger.info("Duplicate property found: " + " in "+ file.getName()+ " at " + 
	                                			"line "+lineNumber+"\t"+ "---- "+s.trim()+ " ----\n");
	    	                        }
                        			else {
                            			//System.out.println(currentText + " is found in "+ file.getName()+ " at " + 
                            			//		"line "+lineNumber+"\t"+ "---- "+s.trim()+ " ----\n");
                        				logger.info(currentText + " is found in "+ file.getName()+ " at " + 
                            					"line "+lineNumber+"\t"+ "---- "+s.trim()+ " ----\n");
                        			}
                        			wordCount++;                                   
	                            }                           
	                        }
	                        
	                        br.close();
	                        if(wordCount!=0) { return file.getName(); }
	                    }
	                    catch(Exception e){
	                        e.printStackTrace();
	                    }
	                    }
	                lineNumber=0;
	
	            } catch (IOException e) {
	            	//System.out.print("Cant find " + currentText);
	                //e.printStackTrace();
	            	logger.error("Cant find " + currentText + ", ", e);
	            }
	         }
			return null;
	       }
        
        public static boolean isWord(String s) {
            return (s.length() > 0 && s.split("\\s+").length <= 2);
        }
    }