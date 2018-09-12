package com.davidhoja.net;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import com.davidhoja.datoteka.Datoteka;

public class Uporabnik implements Runnable {
//	== INSTANÈNE SPREMENLJIVKE == //
	private Socket povezava;
	private Socket datotecnaPovezava;
	private BufferedReader br;
	private PrintWriter pisalec;
	private Datoteka zacasnaDatoteka;
	
//	== PRIMITIVNE SPREMENLJIVKE == //
	private String prejetoSporocilo;
	private String naslovUporabnika;
	private String gesloZaDostop;
	
	private boolean DOVOLJENJE_ZA_DOSTOP_DO_DATOTEK;
	
	
// == KONTERJNERJI == //
	byte[] vsebujeByteDatotek;
	
	
//  == KONSTRUKTOR == //
	public Uporabnik(Socket povezava) throws IOException{
		this.povezava = povezava;
		br = new BufferedReader( // Object za branje bufferejev- zaèanih pomnilikov.
				new InputStreamReader(povezava.getInputStream()));  // Objekt za branje povezave.
		
		pisalec = new PrintWriter(povezava.getOutputStream()); // Objekt za pisanje po povezavi.
		
		String naslovClienta = povezava.getInetAddress().toString();
		StringBuilder sb = new StringBuilder(naslovClienta); // Razred za izvajanje doloæenih operacij an Stringih.
		sb.deleteCharAt(0); // Izbišemo prvi char, ker dobim vrnjen ip kot npr: /127.0.0.1 in zato izbrišemo /.
		
		naslovUporabnika = sb.toString(); //Spremeni nazaj v tip String. 
		
		DOVOLJENJE_ZA_DOSTOP_DO_DATOTEK = false; //Na zaèetku uporabnik seveda nima dovoljenja za dostop.
		
		// Zaženi nov podproces  oz. nit.
		new Thread(this).start();
	}

	
//	== FUNKCIJA, KI OMOGOCA POSILJANJE SPOROCIL UPORABNIKU == //
	public void posljiSporocilo(String sporocilo) {
		pisalec.println(sporocilo);
		pisalec.flush();
		
		Konzola.dodajSporocilo("Uporabniku: " + naslovUporabnika + " pošiljam sporoèilo: " + sporocilo);
	}
	
//	== PREJEMA SPOROCILA OD ODJEMALCA == //
	public void run() {
		try {
			
			/*
			 * Program spodaj ugotavlja kaj mu klient pošilja
			 * in se glede na sporoèila primerno odzove.
			 */
			while((prejetoSporocilo = br.readLine()) != null) {
				
				if(prejetoSporocilo.contains("gesloje")) {
					StringBuilder sb = new StringBuilder(prejetoSporocilo);
					sb.delete(0, "gesloje".length());
					prejetoSporocilo = sb.toString();
					
					if(prejetoSporocilo.equals(gesloZaDostop)) {
						Konzola.dodajSporocilo(naslovUporabnika + " "
						+ "je pravilno vpisal geslo!\nSedaj lahko dostopa do datotek.");
						
						posljiSporocilo("pravilnoGeslo");
						DOVOLJENJE_ZA_DOSTOP_DO_DATOTEK = true;
						posljiPodatkeDatotek();
					}else {
						posljiSporocilo("napacnoGeslo");
						DOVOLJENJE_ZA_DOSTOP_DO_DATOTEK = false;
					}
				
				} else if(prejetoSporocilo.contains("zahtevamDatoteko")) {
					StringBuilder sb = new StringBuilder(prejetoSporocilo);
					prejetoSporocilo = sb.delete(0, "zahtevamDatoteko".length()).toString();
					
					if(DOVOLJENJE_ZA_DOSTOP_DO_DATOTEK) {
						posljiDatoteko(prejetoSporocilo);
					}
				} else if(prejetoSporocilo.contains("osveziDatoteke")) {
					posljiPodatkeDatotek();
				}
				
				if(prejetoSporocilo != "") {
					Konzola.dodajSporocilo(naslovUporabnika  + " : " + prejetoSporocilo);
				}
			}
		} catch (IOException e) {
			
		}
	}
	
	
//	== FUNCKIJA NAMENJENA POSREDOVANJU PODATKOV O DATOTEKAH ODJEMALCEM == //
	private void posljiPodatkeDatotek() {
		posljiSporocilo("podatkiDatotekPrihajajo");
		for(Datoteka zacasnaDatoteka : Datoteka.vsebujeDatotekVSkupniRabi) {
			posljiSporocilo("imeDatoteke" + zacasnaDatoteka.getImeDatoteke());
			posljiSporocilo("vrstaDatoteke" + zacasnaDatoteka.getVrstoDatoteke());
			posljiSporocilo("velikostDatoteke" + String.valueOf
						(zacasnaDatoteka.getVelikostDatoteke()));
		}
		posljiSporocilo("konecPodatkovODatotekah");
	}
	
//	== POSLJE DATOTEKO UPORABNIKU == // 
	
	private void posljiDatoteko(String imeDatoteke){
		
		for(Datoteka zacasnaDatoteka : Datoteka.vsebujeDatotekVSkupniRabi) {
			if(imeDatoteke.equals(zacasnaDatoteka.getImeDatoteke())) {
				posljiSporocilo("posiljamDatoteko"+zacasnaDatoteka.getImeDatoteke());
				this.zacasnaDatoteka = zacasnaDatoteka;
				break;
			}
		}
		
		
		try {
			vsebujeByteDatotek = new byte[(int) zacasnaDatoteka.getVelikostDatoteke()];
			FileInputStream fin = new FileInputStream(zacasnaDatoteka.getAbsolutnoPot());
			BufferedInputStream bin = new BufferedInputStream(fin);
			bin.read(vsebujeByteDatotek,0,vsebujeByteDatotek.length);
			OutputStream os = datotecnaPovezava.getOutputStream();
			os.write(vsebujeByteDatotek,0,vsebujeByteDatotek.length);
			os.flush();
//			datotecnaPovezava.close();
			bin.close();
		} catch(Exception ex) {
			ex.printStackTrace();
			Konzola.dodajSporocilo("Napaka: Pri pošiljanju datoteke.");
		}
	}
	

	
	public void setGesloZaDostop(String gesloZaDostop) {
		this.gesloZaDostop = gesloZaDostop;
	}
	
	public void podajDatotecnoPovezavo(Socket datotecnaPovezava) {
		this.datotecnaPovezava = datotecnaPovezava;
	}
	
	
// == NOTRANJI RAZRED, KI JE ZADOLŽEN ZA POŠILJANJE DATOTEK == //
	class PosiljateljDatotek implements Runnable {
		
		// == REFERENÈNE SPREMENLJIVKE == //
		
		// == PRIMITIVNE SPREMENLJIVKE == //
		
		
		public PosiljateljDatotek(Socket datotecnaPovezava) {
			
		}
		
		public void run() {
			
		}
	}
	
}
