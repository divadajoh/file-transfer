package com.davidhoja.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.davidhoja.datoteka.DatotecniRobot;

public class PosiljateljDatotek {
	
//	== REFEREN�NE SPREMENLJIVKE == //
	private ServerSocket servSock;
	private Socket povezava;
	
// == KONTEJNERJI == //
	private static ArrayList<DatotecniRobot> vsebujePovezave = new ArrayList<DatotecniRobot>();
	
//  == KONSTRUKTOR == //
	public PosiljateljDatotek() {
		try{
			// Ustvarimo nov objekt tipa ServerSocket in mu re�emo naj poslu�a na portu 5001.
			servSock = new ServerSocket(5001); // Port 5001 se bo uporabljal za prena�anje datotek.
		
		} catch(IOException ex) {
			ex.printStackTrace();
			Konzola.dodajSporocilo("Napaka: Pri inicializaciji datote�nega stre�nika.");
		}
		
		while(true) {
			try{
				povezava = servSock.accept();
				
				
				
				vsebujePovezave.add(new DatotecniRobot(povezava)); 
			} catch(IOException ex) {
				ex.printStackTrace();
				Konzola.dodajSporocilo("Napaka: Pri sprejemanju klienta v datote�ni stre�nik.");
			}
		}
		
	}

}
