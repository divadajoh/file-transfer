package com.davidhoja.gui;

import java.io.File;

/*
 * Uporabil sem dinami�nost Jave in si sposodil njen razred JButton
 * in dodal dolo�ene funkcionalnosti, ki jih potrebujem.
 */

import javax.swing.JButton;

import com.davidhoja.odjemalec.Datoteka;

public class JGumb extends JButton{
	
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
	
	// Spremeni stanje gumba.
	public void klik() {
		if(gumbKliknjen) {
			gumbKliknjen = false;
		}else {
			gumbKliknjen = true;
		}
	}
	
	// Slu�i vzpostavljanju dolo�enih relacij med Datoteka-mi in JGumb-i.
	public void podajDatoteko(Datoteka datoteka) {
		this.datoteka = datoteka;
	}
	
	
	public Datoteka vrniDatoteko(){ 
		return datoteka;
	}
	
	public String getNaslovGumba( ){
		return naslovGumba;
	}

}
