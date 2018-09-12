package com.davidhoja.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.davidhoja.net.Konzola;

public class KonzolnoOkno extends JFrame{
	
//	== INSTANÈNE SPREMENLJIVKE == //
	JPanel glavniPanel;
	JEditorPane editorPane;
	JScrollPane scrollPane;
	
	
	// == KONSTRUKTOR, KI DOBI REFERENCO DO DINAMIÈNEGA POLJA, KI VSEBUJE KONZOLNA SPOROCILA == //
	public KonzolnoOkno(ArrayList<String> konzolnaSporocila)  {
		super();
		this.setTitle("KONZOLA DOGAJANJA ");
		this.setSize(300,550);
		this.setResizable(false);
		
		glavniPanel = new JPanel();
		glavniPanel.setSize(new Dimension(this.getWidth(), this.getHeight()));
		
		String vsebujeSporocila = "<b> <p style=\"text-align:center;\"> KONZOLA </p> </b> <br>";
		vsebujeSporocila += spremeniPoljeVString(konzolnaSporocila);
		
		editorPane = new JEditorPane("text/html", vsebujeSporocila); // Ustvari editorPane in dodaj sporoèila.
		editorPane.setBounds(10, 10, getWidth() - 20, getHeight()- 40);
		editorPane.setEditable(false);
		
		scrollPane = new JScrollPane(editorPane);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Vedno pokaži vertikalni ScrollPane.
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); // Pokaži horizontalni scrollpane le ko potrebuješ.
	    scrollPane.setPreferredSize(new Dimension(editorPane.getWidth(), editorPane.getHeight()));
			
		glavniPanel.add(scrollPane);
		
		
		add(glavniPanel);
		setVisible(true);
	}
	
	
	// == FUNKCIJA, KI SPREMENI POLJE V STRING. - Za lažjo uporabo kasneje. == //
	private String spremeniPoljeVString(ArrayList<String> dinamicnoPolje) {
		String zaVrnit = "";
		int indexKonzole = 0;
		
		for(String trenutniString : dinamicnoPolje) {
			indexKonzole++;
			// Formatirnaje oz. oblikovanje sporoèil. Za novo vrstico se uporablja <br> html tag, ker za JEditorPane uporabljam html.
			if(trenutniString != ""){zaVrnit +=  " " + indexKonzole + ". " + trenutniString + " <br>";}
		}
		
		return zaVrnit;
	}
	
	public static void main(String[] args) {
		new KonzolnoOkno(new ArrayList<String>());
	}
	

}
