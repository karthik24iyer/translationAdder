package com.wellsfargo.coms.translationAdder;

import java.io.IOException;

import com.wellsfargo.coms.translationAdder.Readers.SearchFile;
import com.wellsfargo.coms.translationAdder.Readers.SheetReader;
import com.wellsfargo.coms.translationAdder.languages.LanguagesList;

public class App 
{
    public static void main( String[] args )
    {
    	String[] currentLangs;
    	String[][] translationData;
    	// Instantiating Excel Sheet Reader
    	SheetReader sessionData = new SheetReader();
        try {
        	// Reading data from whole sheet
        	sessionData.readSheet();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        finally {
        	System.out.println("Translations Done");
        }
        
      //for(int i=0;i<currentLangs.length && currentLangs[i]!=null; i++) { System.out.print(currentLangs[i] + "\t");}
        //System.out.println();
    }
}
