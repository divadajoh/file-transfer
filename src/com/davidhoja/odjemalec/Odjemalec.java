package com.davidhoja.odjemalec;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

import com.davidhoja.net.Konzola;

public class Odjemalec implements Runnable {
	
//	== PRIMITIVNE SPREMENLJIVKE == //
	private String IP;
	private int PORT;
	private String gesloZaDostop;
	private String prejetoSporocilo;
	private boolean podatkiDatotekPrihajajo;
	private boolean datotekaPrihaja;
	private boolean uporabnikVpisan;
	
	

	
	
//	== INSTANÈNE SPREMENLJIVKE == //
	private Socket povezava;
	private Socket datotecnaPovezava;
	private PrintWriter pisalec;
	private BufferedReader bralec;
	private InputStream is;
	
	private Datoteka zacasnaDatoteka;
	private GraficniVmesnikOdjemalec graficniVmesnikOdjemalec;
	
//	== KONTEJNERJI == //
	byte [] vsebujeByte;
	
	
// == TA RAZRED JE NAMENJEN IZMENJAVANU SPOROÈIL Z SERVERJOM. TA RAZRED JE GLAVNI RAZRED PRI KLIENTU. == //
	
//  == KONSTRUKTOR == //
	public Odjemalec(String IP, int PORT, String gesloZaDostop) throws IOException {
		this.IP = IP;
		this.PORT = PORT;
		this.gesloZaDostop = gesloZaDostop;
		
		boolean NAPAKA = false;
		
		try {
			povezava = new Socket(IP, PORT);
			datotecnaPovezava = new Socket(IP, (PORT + 1));
		}catch(Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, 
			"Pri povezavi z strežnikom je prišlo do napake!\n"
			+ "\n1. Preverite podatke, ki ste jih vpisali."
			+ "\n2. Preverite, da je strežnik vzpostavljen."
			+ "\n3. Preverite, da ste povezani v omrežje." 
			,"NAPAKA!", 1);
			NAPAKA = true;
		}
		
		if(NAPAKA == false) {
			bralec = new BufferedReader(
			new InputStreamReader(povezava.getInputStream()));
			
			is = datotecnaPovezava.getInputStream();
			
			pisalec = new PrintWriter(povezava.getOutputStream());
			
			new Thread(this).start();
			
			
			posljiSporocilo("gesloje" + gesloZaDostop);
		}
		
	}
	
	
	
//	== PODPROCES == //
	public void run()  {
		try {
			
			while((prejetoSporocilo = bralec.readLine()) != null) {
				if(prejetoSporocilo.equals("pravilnoGeslo")) {
					uporabnikVpisan = true;
				}else if(prejetoSporocilo.equals("napacnoGeslo")) {
					JOptionPane.showMessageDialog(null, "DOSTOP ZAVRNJEN!\n"
							+ "Vpisali ste napaèno geslo.","DOSTOP ZAVRNJEN!",1);
					uporabnikVpisan = false;
				}
				
				if(prejetoSporocilo.contains("podatkiDatotekPrihajajo")) {
					podatkiDatotekPrihajajo = true;
				
				}
				
				preveriSporocilo(prejetoSporocilo);
				
				if(prejetoSporocilo != "") {
					Konzola.dodajSporocilo("Server pravi : " + prejetoSporocilo);
				}
				
				
			}
			
			
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	
//	== FUNCKIJA, KI OBRAVNAVA POŠILJANJE SPOROCIL == //
	public void posljiSporocilo(String sporocilo){
		pisalec.println(sporocilo);
		pisalec.flush();
		
		Konzola.dodajSporocilo("Serverju posiljam sporocilo: " + sporocilo);
		
	}

	
//	== PREVERJA SPOROCILA ZA KLJUÈNE BESEDE == //
	
	private void preveriSporocilo(String prejetoSporocilo) throws IOException{
		StringBuilder sb = new StringBuilder(prejetoSporocilo);
		if(prejetoSporocilo.equals("podatkiDatotekPrihajajo")){
			podatkiDatotekPrihajajo = true;
		}
		
		if(prejetoSporocilo.equals("konecPodatkovODatotekah")) {
			podatkiDatotekPrihajajo = false;
			if(graficniVmesnikOdjemalec != null) {
				graficniVmesnikOdjemalec.ustvariGumbeDatotek();
			}
		}
		
		
		if(podatkiDatotekPrihajajo){
			
			if(prejetoSporocilo.contains("imeDatoteke")) {
				zacasnaDatoteka = new Datoteka();
				String imeDatoteke = sb.delete(0, "imeDatoteke".length()).toString();
				zacasnaDatoteka.setImeDatoteke(imeDatoteke);
			}
		
			if(prejetoSporocilo.contains("vrstaDatoteke")) {
				String vrstaDatoteke = sb.delete(0, "vrstaDatoteke".length()).toString();
				zacasnaDatoteka.setVrstaDatoteke(vrstaDatoteke);
			}
		
			if(prejetoSporocilo.contains("velikostDatoteke")) {
				String velikostDatoteke = sb.delete(0, "velikostDatoteke".length()).toString();
				zacasnaDatoteka.setVelikostDatoteke(velikostDatoteke);
				Datoteka.dodajDatoteko(zacasnaDatoteka);
				zacasnaDatoteka = null;
			}
			
		}
		
		
		if(prejetoSporocilo.contains("posiljamDatoteko")) {
			StringBuilder stringbuilder = new StringBuilder(prejetoSporocilo);
			String imeDatoteke = stringbuilder.delete(0, "posiljamDatoteko".length()).toString();
			for(Datoteka tempDatoteka : Datoteka.vsebujeDatoteke) {
				if(tempDatoteka.getImeDatoteke().equals(imeDatoteke)) {
					zacasnaDatoteka = tempDatoteka;
					break;
				}
			}
			
			new PrenasalecDatotek(datotecnaPovezava, zacasnaDatoteka);

			datotekaPrihaja = true;
		}else if(prejetoSporocilo.equals("konecPosiljanjaDatoteke")) {
			datotekaPrihaja = false;
		}
		
	}
	
	
	public boolean getUporabnikVpisan() {
		return uporabnikVpisan;
	}
	
	public void setDatotekaPrihaja(boolean datotekaPrihaja) {
		this.datotekaPrihaja = datotekaPrihaja;
	}
	
	public void podajOcetovoInstanco(GraficniVmesnikOdjemalec graficniVmesnikOdjemalec) {
		this.graficniVmesnikOdjemalec = graficniVmesnikOdjemalec;
	}

}
