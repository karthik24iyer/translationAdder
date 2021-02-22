package com.wellsfargo.coms.translationAdder.Readers;

import java.io.IOException;

public abstract class ExcelReader {  
	
	public abstract String[] getLanguageHeader();
    
    public abstract String[] getRowHeaderData();
    
    public abstract String[][] getSheetData();
    
    public abstract int readSheet() throws IOException;
}  