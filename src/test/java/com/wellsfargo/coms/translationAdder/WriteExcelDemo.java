package com.wellsfargo.coms.translationAdder;

import java.io.File;  
import java.io.FileOutputStream;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell; 
import org.apache.poi.ss.usermodel.Row;  

public class WriteExcelDemo 
{
    public static void main(String[] args) 
    {
    	ResourceBundle rb = ResourceBundle.getBundle("resources/config");
        String location = rb.getString("xcelLocation");
    	//String location = "C:\\Users\\Karthik\\eclipse-workspace\\translationAdder\\src\\\\main\\java\\resources\\tmpTranslations.xlsx";
        XSSFWorkbook workbook = new XSSFWorkbook(); 
        XSSFSheet sheet = workbook.createSheet("translationTest");
          
        Map<String, Object[]> data = new TreeMap<String, Object[]>();     
        data.put("1", new Object[] {"en", "au_AU", "en_GB"});
        data.put("2", new Object[] {"Hello", "Hey There", "Hoy Thore"});
        data.put("3", new Object[] {"Water","Woter", "Wotor"});

        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset)
        {
            Row row = sheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr)
            {
               Cell cell = row.createCell(cellnum++);
               if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
            }
        }
        try
        {
            FileOutputStream out = new FileOutputStream(new File(location));
            workbook.write(out);
            out.close();
            System.out.println("Translations.xlsx written successfully on disk.");
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}