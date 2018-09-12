package com.davidhoja.odjemalec;

import java.util.ArrayList;

public class Datoteka {
	
//	== PRIMITIVNE SPREMENLJIVKE == //
	private String imeDatoteke;
	private String vrstaDatoteke;
	private String velikostDatoteke;
	
	
// == KONTEJNERJI == //
	public static ArrayList<Datoteka> vsebujeDatoteke = new ArrayList<Datoteka>();
	
	
//	== GETTERJI IN SETTERJI == //
	public String getImeDatoteke() {
		return imeDatoteke;
	}

	public void setImeDatoteke(String imeDatoteke) {
		this.imeDatoteke = imeDatoteke;
	}

	public String getVrstaDatoteke() {
		return vrstaDatoteke;
	}

	public void setVrstaDatoteke(String vrstaDatoteke) {
		this.vrstaDatoteke = vrstaDatoteke;
	}

	public int getVelikostDatoteke() {
		int velDatoteke = 0;
		try {
			velDatoteke = Integer.parseInt(velikostDatoteke);
		} catch(NumberFormatException ex) {
			ex.printStackTrace();
		}
		
		return velDatoteke;
	}

	public void setVelikostDatoteke(String velikostDatoteke) {
		this.velikostDatoteke = velikostDatoteke;
	}
	
	public static void dodajDatoteko(Datoteka datoteka) {
		vsebujeDatoteke.add(datoteka);
	}
	

}
