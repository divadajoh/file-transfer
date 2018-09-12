package com.davidhoja.net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Streznik implements Runnable {
//	== PRIMITIVNE SPREMENLJIVKE == //
	int PORT;
	String gesloZaDostop;
	
//	== INSTAN�NE SPREMENLJIVKE == //
	private ServerSocket servSocket;
	private ServerSocket datotecniServSocket;
	private Socket povezava;
	private Socket datotecnaPovezava;
	
//	== KONTEJNERJI == //
	private static ArrayList<Uporabnik> vsebujeUporabnike = new ArrayList<Uporabnik>();
	
//	== KONSTRUKTOR == //
	public Streznik(int PORT, String gesloZaDostop) {
		this.PORT = PORT;
		this.gesloZaDostop = gesloZaDostop;
		
		new Thread(this).start();
	}
	
//	== PODPROCES == //
	public void run() {
		try {
			servSocket = new ServerSocket(PORT); // Ustvari ServerSocket objeckt in mu podaj PORT kot argument.
			datotecniServSocket= new ServerSocket(PORT + 1); // PORT za datote�no povezavo je vedno +1 ve� kot port za sporo�ila.
			
			while(true){ // Loopaj v neskon�nost.
			povezava = servSocket.accept(); // Ko je na voljo povezava jo sprejmi.
			datotecnaPovezava = datotecniServSocket.accept(); // Ko je na voljo datote�na poveza jo sprejmi.
			
			Uporabnik novUporabnik = new Uporabnik(povezava); //Ko je povezava prejeta ustvari nov objeckt tipa Uporabnik.
			vsebujeUporabnike.add(novUporabnik); // Dodaj novUporabnik v dinami�no polje vsebujeUporabnike.
			novUporabnik.podajDatotecnoPovezavo(datotecnaPovezava); //Podaj mu datote�no povezavo.
			
			novUporabnik.setGesloZaDostop(gesloZaDostop); //Nastavi geslo za dostop.
			
			Konzola.dodajSporocilo("Povezava: Nov uporabnik se je pravkar povezal."); // Dodajanje sporo�il v konzolo.
			}
		} catch(IOException ex) { // �e gre kaj narobe izpi�i StackTrace(Drevo funkcij, kot so bila postavljena na Stack-u)
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Server: Prislo je do napake!"); // In �e neko sporocilo.
		}
	}
	

}
