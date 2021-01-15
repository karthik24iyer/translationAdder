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
import java.util.ResourceBundle;
import java.util.Scanner;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import com.wellsfargo.coms.translationAdder.languages.LanguagesList.LangFormats;

public class SearchProperty {
    
	public SearchProperty(String fileName, String filePath, String propertyName, String newTranslation) {
		ResourceBundle rb = ResourceBundle.getBundle("resources/config");
        boolean disablePrompt = Boolean.valueOf(rb.getString("disablePrompt"));
		boolean done=false;
		String oldTranslation;
		File currentTransFile=new File(filePath+"\\"+ fileName);
		System.out.println(fileName+ " found at: " + filePath+fileName);
     	try {
	        if (currentTransFile.isFile()) {
	            FileReader reader = new FileReader(filePath+"\\"+ fileName);
	            BufferedReader br = new BufferedReader(reader); 
	            String s,in=""; 
	            while((s = br.readLine()) != null) { 
	            	in+=s+"\n";
	            }
	            //System.out.println(in);
	            br.close();
	            reader.close();
	            if((in.contains(propertyName+"=")||in.contains(propertyName+" =")) && 
                			!currentTransFile.getName().equals("WriteExcelDemo.java")) {
	            	if(in.contains(propertyName+" = ")) {oldTranslation = in.split(propertyName+" = ")[1].split("\n")[0]; }
	            	else if(in.contains(propertyName+" =")){oldTranslation = in.split(propertyName+" =")[1].split("\n")[0]; }
	            	else {oldTranslation = in.split(propertyName+"=")[1].split("\n")[0]; }
	                System.out.println("-----" + propertyName+"="+oldTranslation + "----- already present in "+ currentTransFile.getName());
	                FileWriter out = new FileWriter(filePath+fileName);
		            if(!disablePrompt) {
	                	Scanner myIn = new Scanner(System.in);
		                System.out.println("Do you want to replace it with -----" + propertyName+"="+newTranslation + "-----  ? (y/n) : ");
		                String decide = myIn.nextLine();
		                //myIn.close();
		                switch(decide.toLowerCase()) {
		                	case "y":
		                		in = in.replace(oldTranslation, newTranslation);	                
		                		break;
		                	case "n":
		                		break;
		                }
		            }
		            else {
		            	in = in.replace(oldTranslation, newTranslation);
		            }
	                out.write(in);
	                out.flush();
	                out.close();
	                done=true;
	            }
	            
	            if(!done) {
	            	System.out.println("Writing New -----" + propertyName+"="+newTranslation + "----- in "+ currentTransFile.getName());
	            	BufferedWriter out = new BufferedWriter(new FileWriter(filePath+fileName, true)); 
        			out.write(propertyName+"="+newTranslation+"\n"); 
        			out.close(); 
	            }		
	        }
     	}
     	catch(Exception e){
        	//System.out.print("Cant find " + propertyName);
            e.printStackTrace();
        }
    }	
}    