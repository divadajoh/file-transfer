package com.davidhoja.datoteka;
import java.io.File;
import java.util.ArrayList;


public class Datoteka {
	
//	== PRIMITIVNE SPREMENLJIVKE == //
	private String imeDatoteke;
	private String potDoDatoteke;
	private String vrstaDatoteke;
	private long velikostDatoteke;
	private File datoteka;
	
	
	public static  ArrayList<Datoteka> vsebujeDatotekVSkupniRabi = new ArrayList<Datoteka>();
	
	public Datoteka(File f) {
		datoteka = f;
		imeDatoteke = f.getName();
		potDoDatoteke = f.getAbsolutePath();
		
		StringBuilder sb = new StringBuilder(datoteka.getName());
		int indexPike = sb.indexOf(".");
		vrstaDatoteke = sb.delete(0, indexPike).toString();
		
		velikostDatoteke = datoteka.length();
		
	}
	
	

	public String getImeDatoteke() {
		return imeDatoteke;
	}
	
	public String getAbsolutnoPot() {
		return potDoDatoteke;
	}
	
	public File vrniDatoteko() {
		return datoteka;
	}
	
	public String getVrstoDatoteke() {
		return vrstaDatoteke;
	}
	
	public long getVelikostDatoteke() {
		return velikostDatoteke;
	}
	
	public static void dodajDatotekoVSkupnoRabo(Datoteka datoteka) {
		vsebujeDatotekVSkupniRabi.add(datoteka);
	}
	
	
	
}
