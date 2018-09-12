package com.davidhoja.net;

import java.util.ArrayList;

public class Konzola {
	
	/*
	 * == UPRAVLJA KONZOLNA SPOROCILA == *
	 * Dodaja sporocila v dinamièno polje
	 * 
	 */
	
	private static ArrayList<String> sporocila = new ArrayList<String>();
	private static int idStevila = 0;
	
	
	public static void dodajSporocilo(String konzolnoSporocilo) {
		sporocila.add(konzolnoSporocilo + " \n");
		idStevila++;
		
	}
	
	public static ArrayList<String> vrniKonzolnaSporocila() {
		return sporocila;
	}

}
