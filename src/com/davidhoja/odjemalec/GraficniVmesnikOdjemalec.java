package com.davidhoja.odjemalec;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.davidhoja.gui.KonzolnoOkno;
import com.davidhoja.net.Konzola;

public class GraficniVmesnikOdjemalec extends JFrame {

//  == INSTANÈNE SPREMENLJIVKE == //
	private JPanel contentPane;
	private JPanel datotekePanel;
	private JGumb izbranGumb;
	
	private Random random;
	
//	== KONTEJNERJI == //
	private ArrayList<JGumb> vsebujeGumbe = new ArrayList<JGumb>();
	
	private Odjemalec odjemalec;


	public GraficniVmesnikOdjemalec(Odjemalec odjemalec) {
		setTitle("Skupna raba datotek - Odjemalec");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 450, 446);
		setAlwaysOnTop(true);
	
		
		JMenuBar meniBar = new JMenuBar();
		setJMenuBar(meniBar);
		
		JMenu meniMeni = new JMenu("MENI");
		meniBar.add(meniMeni);
		
		odjemalec.podajOcetovoInstanco(this);
		
		JMenuItem osveziMeniItem = new JMenuItem("Osve\u017Ei datoteke.");
		osveziMeniItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				for(JGumb trenutniGumb : vsebujeGumbe) {
					datotekePanel.remove(trenutniGumb);
					datotekePanel.repaint();
				}
				datotekePanel.setVisible(false);
				vsebujeGumbe.clear();
				Datoteka.vsebujeDatoteke.clear();
				odjemalec.posljiSporocilo("osveziDatoteke");
			}
		});
		meniMeni.add(osveziMeniItem);
		
		
		JMenuItem konzolaMeni = new JMenuItem("Prika\u017Ei konzolo dogajanja.");
		konzolaMeni.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				KonzolnoOkno okno = new KonzolnoOkno(Konzola.vrniKonzolnaSporocila());
				// == USTVARI NOVO KONZOLNO OKNO IN RAZREDU PODAJ SPOROÈILA == //
			}
		});
		meniMeni.add(konzolaMeni);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		datotekePanel = new JPanel();
		datotekePanel.setBackground(SystemColor.inactiveCaptionBorder);
		datotekePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		datotekePanel.setBounds(10, 11, 424, 328);
		contentPane.add(datotekePanel);
		datotekePanel.setLayout(null);
		
		JButton btnPrenesiDatoteko = new JButton("PRENESI ");
		btnPrenesiDatoteko.setBounds(162, 363, 125, 23);
		btnPrenesiDatoteko.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				odjemalec.posljiSporocilo("zahtevamDatoteko" + izbranGumb.vrniDatoteko().getImeDatoteke());
			}
		});
		
		this.odjemalec = odjemalec;
		
		random = new Random();
		
		contentPane.add(btnPrenesiDatoteko);
		ustvariGumbeDatotek();
		
		
		
	}
	
	private void uniciGumbe() {
		for(JGumb trenutniGumb : vsebujeGumbe) {
			datotekePanel.remove(trenutniGumb);
			vsebujeGumbe.remove(trenutniGumb);
			trenutniGumb = null;
		}
	}
	
	
	// ** SPODNJA FUNKCIJA DINAMIÈNO USTVARI GUMBE GLEDE NA NJIHOVO ŠTEVILO
	// ** IN DODA DOLOÈENO RELACIJO MED GUMBI IN DATOTEKAMI, DA LAHKO KASNEJE LAŽJE IDENTIFICIRAMO DATOTEKE.
	public void ustvariGumbeDatotek(){
		int xPos = 10;
		int yPos = 10;
		int xDelta = 140;
		int yDelta = 100;
		int sirina = 120;
		int dolzina = 80;
		
		int stevecGumbov = 1;
		
		for(int i = 0; i <= Datoteka.vsebujeDatoteke.size() - 1; i++) {
			JGumb zacasniGumb = new JGumb(Datoteka.vsebujeDatoteke.get(i).getImeDatoteke());
			zacasniGumb.podajDatoteko(Datoteka.vsebujeDatoteke.get(i));
			
			// == ZA GUMBE DOLOÈI NAKLJUÈNE BARVE == //
			int rdeca = 1 + random.nextInt(243);
			int zelena = 1 + random.nextInt(243);
			int modra = 1 + random.nextInt(243);
			zacasniGumb.setBackground(new Color(rdeca, zelena, modra));
			zacasniGumb.setBounds(xPos, yPos, sirina, dolzina);
			zacasniGumb.setToolTipText("Ime: " + Datoteka.vsebujeDatoteke.get(i).getImeDatoteke() + "\n"
				                        +"    Velikost: " + Datoteka.vsebujeDatoteke.get(i).getVelikostDatoteke() + " bajtov (b).");
			
			
			zacasniGumb.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					zacasniGumb.klik();
					
					if(zacasniGumb.aliJeGumbKliknjen()) {
						zacasniGumb.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 4));
						izbranGumb = zacasniGumb;
						for(JGumb tempGumb : vsebujeGumbe){ 
							if(zacasniGumb == tempGumb) {
								// NE NAREDI NIÈ
							}else {
								tempGumb.setEnabled(false);
							}
						}
					}else {
						zacasniGumb.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
						for(JGumb tempGumb : vsebujeGumbe){ 
							if(zacasniGumb == tempGumb) {
								// NE NAREDI NIÈ
							}else {
								tempGumb.setEnabled(true);
							}
						}
					}
				}
			});
			
			vsebujeGumbe.add(zacasniGumb);
			
			datotekePanel.add(zacasniGumb);
			
			if(stevecGumbov < 3) {
				xPos += xDelta;
		
			}else {
				System.out.println("Nova vrstica");
				stevecGumbov = 0;
				yPos += yDelta ;
				xPos = 10;
			}
			
			stevecGumbov++;
		}
		
		datotekePanel.setVisible(true);
	}
	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GraficniVmesnikOdjemalec frame = new GraficniVmesnikOdjemalec(new Odjemalec("",5, ""));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
