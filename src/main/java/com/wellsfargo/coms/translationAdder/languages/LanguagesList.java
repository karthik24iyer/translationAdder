package com.wellsfargo.coms.translationAdder.languages;

import com.wellsfargo.coms.translationAdder.Readers.SheetReader;

public class LanguagesList {
	
	private String[] langHeader;
	
	public enum LangFormats {
		au_AU, da_DK, de_DE, en_GB, es_ES, es_MX, fi_FI, fr_CA, fr_FR,
			it_IT, nl_NL, no_NO, pt_BR, sv_SE, zh_CN;
	}
	
	public String[] getCurrentLangs(SheetReader sessionData) {
		langHeader = sessionData.getLanguageHeader();
		//for(int i=0;i<langHeader.length && langHeader[i]!=null; i++) { System.out.print(langHeader[i] + "\t");}
		return langHeader;
	}
	
}