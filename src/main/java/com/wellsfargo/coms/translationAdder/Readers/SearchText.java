package com.wellsfargo.coms.translationAdder.Readers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

public class SearchText {
		
		
        private static String filePath ="";
        static int fileCount = 0;   
        String fileName, propertyName, fullFilePath;
        static int lineNumber=0;
        private String existingFilesForCurrentText, ignoreSearchForFiles;
        ResourceBundle rb = ResourceBundle.getBundle("resources/config");
        private String folderPath = rb.getString("rootLocation");
        ResourceBundle rbForFilesIgnore = ResourceBundle.getBundle("resources/ignoreDoNotEdit");
        
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
        		existingFilesForCurrentText = rbForFilesIgnore.getString(currentText);
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
	                    try {
	                        FileReader reader = new FileReader(filePath );
	                        BufferedReader br = new BufferedReader(reader); 
	                        String s; 
	                        while((s = br.readLine()) != null) { 
	                            lineNumber++;
	                            boolean conidtion=(s.contains("="+currentText)||s.contains("= "+currentText)) && isWord(s) && 
	                            		!file.getName().equals("WriteExcelDemo.java") && !file.getName().equals("config.properties") && 
                            			!existingFilesForCurrentText.contains(file.getName()) && !ignoreSearchForFiles.contains(file.getName());
	                            if(conidtion){
	                                    System.out.println(currentText + " is found in "+ file.getName()+ " at " + 
                                    			"line "+lineNumber+"\t"+ "---- "+s.trim()+ " ----\n");
	                                    this.propertyName = s.split("=")[0];
	                                    this.fileName = file.getName();
	                                    this.fullFilePath = file.getCanonicalPath();
	                                    BufferedWriter out = new BufferedWriter(new FileWriter(folderPath+"\\ignoreDoNotEdit.properties", true)); 
	                        			out.write(currentText+"="+file.getName()+"\n"); 
	                        			out.close(); 
	                                    return file.getName();
	                            }                           
	                        } 
	                        reader.close(); 
	                    }
	                    catch(Exception e){
	                    	System.out.print("Cant find " + currentText);
	                        e.printStackTrace();
	                    }
	                    }
	                lineNumber=0;
	
	            } catch (IOException e) {
	            	System.out.print("Cant find " + currentText);
	                e.printStackTrace();
	            }
	         }
			return null;
	       }
        
        public static boolean isWord(String s) {
            return (s.length() > 0 && s.split("\\s+").length <= 2);
        }
    }