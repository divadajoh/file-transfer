package com.davidhoja.datoteka;

import java.io.InputStream;
import java.net.Socket;


public class DatotecniRobot implements Runnable {
	
//	== PRIMITIVNE SPREMENLIVKE == //
	private String uporabniskoIme;
	
	private boolean IMA_DOVOLJENJE;
	
// == REFERENÈNE SPREMENLJIVKE == //
	private Socket povezava;
	private InputStream inputStream;
	
	
	public DatotecniRobot(Socket povezava) {
		this.povezava = povezava;
		
		StringBuilder sb = new StringBuilder(povezava.getInetAddress().toString());
		uporabniskoIme = sb.deleteCharAt(0).toString();
		
		IMA_DOVOLJENJE = false; 
		
		new Thread(this).start();
	}
	
	public void run() {
		
	}
	
	
//	== GETTERJI IN SETTERJI == //
	
	public String getUporabniskoIme() {
		return uporabniskoIme;
	}

}
