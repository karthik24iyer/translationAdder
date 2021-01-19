package com.wellsfargo.coms.translationAdder;

import java.io.IOException;
import org.apache.log4j.Logger;
import com.wellsfargo.coms.translationAdder.Readers.SheetReader;
import com.wellsfargo.coms.translationAdder.languages.LanguagesList;

public class App 
{
    public static void main( String[] args )
    {
    	final Logger logger = Logger.getLogger(App.class.getName());
    	String[] currentLangs;
    	String[][] translationData;
    	// Instantiating Excel Sheet Reader
    	SheetReader sessionData = new SheetReader();
        try {
        	// Reading data from whole sheet
        	sessionData.readSheet();
		} catch (IOException e) {
			e.printStackTrace();
		}
        System.out.println();
        
        //Fetching listed languages in Excel Sheet
        currentLangs = sessionData.getRowHeaderData();
        
        //Fetching listed languages in Excel Sheet
        translationData = sessionData.getSheetData();
        
        //Starting Translation Process !
        try {
        	new RunIterator(currentLangs,translationData);
        	//System.out.println("Translations Done");
        	logger.info("Translations Done");
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
      //for(int i=0;i<currentLangs.length && currentLangs[i]!=null; i++) { System.out.print(currentLangs[i] + "\t");}
        //System.out.println();
    }
}
