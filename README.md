# translationAdder
 To auto add translations from excel file  
  
**This application will add translations from a given excel file (in a standard format) to respective .properties file in COMS.**  
  
**NOTE**:  
• Please refer "src\main\java\resources\config.properties" to change required settings, and "Translations.xlsx" to check the format of required input.  
• There are two columns for English in excel file. First for existing word and second for a new translation in English. (If no changes to English, you can keep both columns identical)  
• Refer this [link](https://github.com/karthik24iyer/TranslationAdderJarSupport) for any missing jar support ( which shouldn't be the case if you have coms imported)  
--------------------------------------------------------------------------------------------------------------------------------------  

**The following features are supported by this application -**  

• Adding new translations for existing properties 
• Replacing existing translations ( including English ) with new ones in Excel.  
• Adding unicode characters (eg. \u01234) to add non-native translation letters (like German/French) which display neatly on UI as intended. Unicode is used to keep consistency across all languages.  
• Adding same property-translation to multiple .properties file containing the same property.  
• Manual console prompt to ask before overwriting any existing translation (can be disabled in config file).  
• log4j logging for all translations.  
• All the languages for all regions -'au_AU', 'da_DK', 'de_DE', 'en_GB', 'es_ES', 'es_MX', 'fi_FI', 'fr_CA', 'fr_FR', 'it_IT', 'nl_NL', 'no_NO', 'pt_BR', 'sv_SE', 'zh_CN'.  
• Only .xlsx file support at the moment. Although if original file formattings are correct, .csv and .xls files will also work after changing their format to .xlsx.  
--------------------------------------------------------------------------------------------------------------------------------------  

**How to install, run and check changes -** 
1. Clone the repository
2. Use eclise Import -> Existing Maven projects
3. Edit the required file paths in the given format in config.properties (configWF.properties can be used as template with all paths already given with my UID)
4. Ensure proper formatting of excel is present. Refer Translation.xlsx in home directory
5. Run the app as java application from App.java (translationAdder\src\main\java\com\wellsfargo\coms\translationAdder\App.java)
6. You can check the logs in logging.log in resources. You will see "Translations Done" in logs upon completion.
7. Since changes to .properties file do not directly show in git staging, you can do a Team Synchronise and add them to index(staging) from Synchronise View.
8. Its done. Refer debug solutions below for any issues.
--------------------------------------------------------------------------------------------------------------------------------------  

**The following possible endcases are already covered in the application -**   

• Duplicate property name within same .properties file  
• Any formating issues in .properties file like extra spaces / misplaced "=" etc  
• Properties which already exist in .properties file but are commented will not be tampered, and a new appended entry will be added to file.  
• Case-sensitive letters in excel or .properties file are taken care for.  
--------------------------------------------------------------------------------------------------------------------------------------  

**The following issues might come while using the application -**   

• Unable to build/run the application to due compilation issues:  
Happens if java version in classpath is inconsistent with our eclipse's java version.  
Solution: Please check java build path of this application.  

• Unable to find dependency/plugin from pom.xml:  
Solution: Although version of all dependencies have only been taken from what is used in COMS, if issue still persist, you can refer that dependency/plugin to any exisiting version in your .m2 repo.  
		  Alternatively you can use the jarSupport [link](https://github.com/karthik24iyer/TranslationAdderJarSupport) to get the required jars for this application  
  
• Translation word from excel not found:  
Happens due to inconsistent formatting of words in excel file. For eg. most common issue is presence of "—" instead of "-".  
Can also happen if translation is present as a sentence which has a bad character or extra spaces (excluding prepends and appends which get trimmed in the code already).  
Solution: This can be manually replaced in excel by doing a quick search and replace.  
  
• Locale file (eg. xyz_pt_BR.properties) not found:   
Happens if a translation is given for a particular language in excel file but that locale file is not present in coms resources.  
Solution: You would have to create a new file manually. To ensure auto creation of file does not have any formatting issues, app throws a warning instead of creating a new file.  
  
• Locale file not found even when present:  
Happens if the excel file has incorrect language code in the header. For eg., pt_PT instead of pt_BR.  
Solution: Please ensure all language codes are as in given standard format above in instructions.  
  
• ArrayOutOfBondsException:  
Happens if there are any floating characters outside the given rows/columns in excel file and hence SheetReader fails to read file.  
Solution: Please ensure there aren't.  
  
• Unable to find locations of this application, translations file, or COMS root.  
Solution: Please refer config.properties for a giving the correct file path format.  
--------------------------------------------------------------------------------------------------------------------------------------  
  
**If you find any other bug than stated above, please reach out to me.**  