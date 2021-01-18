package com.wellsfargo.coms.translationAdder.Readers;

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
import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;


public class SearchProperty {
    
	public SearchProperty(String fileName, String filePath, String propertyName, String newTranslation) {
		ResourceBundle rb = ResourceBundle.getBundle("config");
        boolean disablePrompt = Boolean.valueOf(rb.getString("disablePrompt"));
        Properties props = new Properties();
		boolean done=false;
		String oldTranslation;
		File currentTransFile=new File(filePath+"\\"+ fileName);
		System.out.println(fileName+ " found at: " + filePath+fileName);
     	try {
	        if (currentTransFile.isFile()) {
	        	//System.out.println(propertyName + "=" + props.getProperty(propertyName));
	        	//System.out.println(props.propertyNames());
	        
	        	BufferedReader br = new BufferedReader(new InputStreamReader(
            	        new FileInputStream(filePath+"\\"+ fileName), "UTF-8"));
	            String s,in=""; 
	            while((s = br.readLine()) != null) { 
	            	in+=s+"\n";
	            }
	            //System.out.println(in);
	            br.close();
	            br.close();
	            if((in.contains(propertyName+"=")||in.contains(propertyName+" =")) && 
                			!currentTransFile.getName().equals("WriteExcelDemo.java")) {
	            	if(in.contains(propertyName+" = ")) {oldTranslation = in.split(propertyName+" = ")[1].split("\n")[0]; }
	            	else if(in.contains(propertyName+" =")){oldTranslation = in.split(propertyName+" =")[1].split("\n")[0]; }
	            	else {oldTranslation = in.split(propertyName+"=")[1].split("\n")[0]; }
	                System.out.println("-----" + propertyName+"="+oldTranslation + "----- already present in "+ currentTransFile.getName());
	                //FileWriter out = new FileWriter(filePath+fileName);
	                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath+fileName), "UTF-8"));
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