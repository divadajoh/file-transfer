package com.davidhoja.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.davidhoja.datoteka.Datoteka;

public class GraficniVmesnikStreznik extends JFrame {
// == INSTANÈNE SPREMENLJIVKE == //
	private JPanel contentPane;
	private File datoteka;
	private JFileChooser fc;
	
// == PRIMITIVNE SPREMENLJIVKE == //
	private int PORT;
	private String geslo;
	private int trenutnaVisina = 80;
	private int trenutnaVisinaGumbov = 30;
	private boolean odstraniGumb = false;
	private int steviloGumbov = 0;
	
//  == KONTEJNERJI == //
	private ArrayList<JGumb> vsebujeGumbe = new ArrayList<JGumb>();
	
	public GraficniVmesnikStreznik(int PORT, String geslo) {
		setTitle("Skupna raba datotek - Stre\u017Enik");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 322, 102);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel labelEna = new JLabel("Dodajte datoteke v skupno rabo:");
		labelEna.setBounds(10, 22, 192, 14);
		contentPane.add(labelEna);
		
		fc = new JFileChooser();
		
		
		// ** OMOGOÈA IZBIRANJE DATOTEKE ** //
		JButton dodajBtn = new JButton("Dodaj");
		dodajBtn.setBounds(198, 18, 89, 23);
		dodajBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if(steviloGumbov < 9){
					int vrnjenaVrednost = fc.showOpenDialog(GraficniVmesnikStreznik.this);
				
					if(vrnjenaVrednost == JFileChooser.APPROVE_OPTION) {
						datoteka = fc.getSelectedFile();
						dodajDatoteko();
					}
					
					steviloGumbov++;
					
				}else {
					JOptionPane.showMessageDialog(null, "Dodate lahko le 9 datotek ne enkrat!", "OPOZORILO", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		contentPane.add(dodajBtn);
		
		this.PORT = PORT;
		this.geslo = geslo;
	}
	
	// ** USTVARI NOV GUMB, KI PREDSTAVLJA DATOTEKO, KOT GRAFIÈNO KOMPONENTO ** //
	private void dodajDatoteko() {
		contentPane.setVisible(true);
		trenutnaVisina+=70;
		JLabel mejaLabel = new JLabel("");
		mejaLabel.setBounds(0,50,400,2);
		contentPane.add(mejaLabel);
		mejaLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		JLabel trenutniLabel = new JLabel("Datoteke v skupni rabi:");
		trenutniLabel.setBounds(90,60,200,20);
		contentPane.add(trenutniLabel);
		setSize(322, trenutnaVisina);
		
		trenutnaVisinaGumbov+= 60;
		JGumb tempGumb = new JGumb(datoteka.getName()); 
		tempGumb.setBounds(80, trenutnaVisinaGumbov, 150, 30);
		tempGumb.setBackground(Color.DARK_GRAY);
		tempGumb.setForeground(Color.white);
		tempGumb.addActionListener(new ActionListener() {
			
			/*
			 * * SPODAJ JE NAVEDENA KODA, KI ODSTRANJUJE DATOTEKO GLEDE NA
			 * * GUMB, KI GA UPORABNIK KLIKNE.
			 */

			@Override
			public void actionPerformed(ActionEvent arg0) { // Ta funkcija se izvede ko nekdo klikne na gumb.
				if(odstraniGumb) { //Èe odstranjuješ gumbe.
					tempGumb.setVisible(false); //Naredi gumb ne-viden.
					vsebujeGumbe.remove(tempGumb);// Odstrani ga z polja, ki vsebuje gumbe.
					trenutnaVisinaGumbov -=60; //Dinamièna nastavitev položaja
					trenutnaVisina -= 70; //Dinamièna nastavitev položaja
					setSize(getWidth(), trenutnaVisina); //Dinamièna nastavitev položaja/velikosti.
					
					Datoteka zaOdstranitev = null; //Usvari spremenljivko in ji daj vrednost null.. niè.
					
					for(Datoteka trenutnaDatoteka : Datoteka.vsebujeDatotekVSkupniRabi) { //Loopaj skozi vse datoteke, ki so dodane.
						if(trenutnaDatoteka.getImeDatoteke().equals(tempGumb.getNaslovGumba())) { //Najdi pravo datoteko glede na to kateri gumb je uporabnik kliknil.
							zaOdstranitev = trenutnaDatoteka; //Podaj vrednost zgoraj ustvarjeni spremenljivki. - Z tem se izognemo morebitni ConcurrentModificationException napaki.
						}
					}
					
					if(zaOdstranitev != null) { // Èe smo zgoraj našli datoteko oz. ni null.
						Datoteka.vsebujeDatotekVSkupniRabi.remove(zaOdstranitev); // Odstrani datoteko iz dinamiènega polja.
					}
					
					steviloGumbov--;
				}
				
			}
			
		});
		
		// ** SPODNJI ANONIMNI NOTRANJI ZARED SLUŽKI ZGOLJ MENJAVANJU IKON NA GUMBU OZ. DODA TEXT "Odstrani?"
		tempGumb.addMouseListener(new MouseListener() {
			String imeGumba = "";
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				tempGumb.setText(imeGumba);
				odstraniGumb = false;
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				imeGumba = tempGumb.getText();
				tempGumb.setText("Odstrani? ");
				odstraniGumb = true;
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		vsebujeGumbe.add(tempGumb);
		contentPane.add(tempGumb);
		
		
		Datoteka.dodajDatotekoVSkupnoRabo(new Datoteka(datoteka));
	}
	
	
	private void dodajDatoteko(String imeDatoteke) {
		trenutnaVisina+=70;
		JLabel mejaLabel = new JLabel("");
		mejaLabel.setBounds(0,50,400,2);
		contentPane.add(mejaLabel);
		mejaLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		JLabel trenutniLabel = new JLabel("Datoteke v skupni rabi:");
		trenutniLabel.setBounds(90,60,200,20);
		contentPane.add(trenutniLabel);
		setSize(322, trenutnaVisina);
		
		trenutnaVisinaGumbov+= 60;
		JGumb tempGumb = new JGumb(imeDatoteke); 
		tempGumb.setBounds(80, trenutnaVisinaGumbov, 150, 30);
		tempGumb.setBackground(Color.DARK_GRAY);
		tempGumb.setForeground(Color.white);
		tempGumb.addActionListener(new ActionListener() {
			
			/*
			 * * SPODAJ JE NAVEDENA KODA, KI ODSTRANJUJE DATOTEKO GLEDE NA
			 * * GUMB, KI GA UPORABNIK KLIKNE.
			 */

			@Override
			public void actionPerformed(ActionEvent arg0) { // Ta funkcija se izvede ko nekdo klikne na gumb.
				if(odstraniGumb) { //Èe odstranjuješ gumbe.
					tempGumb.setVisible(false); //Naredi gumb ne-viden.
					vsebujeGumbe.remove(tempGumb);// Odstrani ga z polja, ki vsebuje gumbe.
					trenutnaVisinaGumbov -=60; //Dinamièna nastavitev položaja
					trenutnaVisina -= 70; //Dinamièna nastavitev položaja
					setSize(getWidth(), trenutnaVisina); //Dinamièna nastavitev položaja/velikosti.
					
					Datoteka zaOdstranitev = null; //Usvari spremenljivko in ji daj vrednost null.. niè.
					
					for(Datoteka trenutnaDatoteka : Datoteka.vsebujeDatotekVSkupniRabi) { //Loopaj skozi vse datoteke, ki so dodane.
						if(trenutnaDatoteka.getImeDatoteke().equals(tempGumb.getNaslovGumba())) { //Najdi pravo datoteko glede na to kateri gumb je uporabnik kliknil.
							zaOdstranitev = trenutnaDatoteka; //Podaj vrednost zgoraj ustvarjeni spremenljivki. - Z tem se izognemo morebitni ConcurrentModificationException napaki.
						}
					}
					
					if(zaOdstranitev != null) { // Èe smo zgoraj našli datoteko oz. ni null.
						Datoteka.vsebujeDatotekVSkupniRabi.remove(zaOdstranitev); // Odstrani datoteko iz dinamiènega polja.
					}
					
					for(JGumb trenutniGumb : vsebujeGumbe) {
						vsebujeGumbe.remove(trenutniGumb);
						trenutnaVisina = 80;
						trenutnaVisinaGumbov = 40;
					}
					
					ponovnoUstvariGumbe();
				}
				
			}
			
		});
		
		// ** SPODNJI ANONIMNI NOTRANJI ZARED SLUŽKI ZGOLJ MENJAVANJU IKON NA GUMBU OZ. DODA TEXT "Odstrani?"
		tempGumb.addMouseListener(new MouseListener() {
			String imeGumba = "";
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				tempGumb.setText(imeGumba);
				odstraniGumb = false;
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				imeGumba = tempGumb.getText();
				tempGumb.setText("Odstrani? ");
				odstraniGumb = true;
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		vsebujeGumbe.add(tempGumb);
		contentPane.add(tempGumb);
		
		
		Datoteka.dodajDatotekoVSkupnoRabo(new Datoteka(datoteka));
	}
	
	private void ponovnoUstvariGumbe() {
		for(JGumb trenutniGumb : vsebujeGumbe) {
			String imeDatoteke = trenutniGumb.getNaslovGumba();
			
			dodajDatoteko(imeDatoteke);
		}
		
		contentPane.setVisible(true);
	}
	


}
