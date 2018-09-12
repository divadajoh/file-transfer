package com.davidhoja.odjemalec;

import javax.swing.JButton;

public class JGumb extends JButton {
	private boolean gumbKliknjen;
	private Datoteka datoteka;
	private String naslovGumba;
	
	public JGumb(String naslov) {
		super(naslov);
		naslovGumba = naslov;
	}
	
	public boolean aliJeGumbKliknjen() {
		return gumbKliknjen;
	}
	
	public void klik() {
		if(gumbKliknjen) {
			gumbKliknjen = false;
		}else {
			gumbKliknjen = true;
		}
	}
	
	public void podajDatoteko(Datoteka datoteka) {
		this.datoteka = datoteka;
	}
	
	public Datoteka vrniDatoteko(){ 
		return datoteka;
	}
	
	public String getNaslovGumba() {
		return naslovGumba;
	}
}
