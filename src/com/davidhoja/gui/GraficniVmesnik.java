package com.davidhoja.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.davidhoja.net.Streznik;

public class GraficniVmesnik extends JFrame {

	private JPanel contentPane;
	private JTextField portTextField;
	private JPasswordField gesloField;
	
	private Streznik streznik;
	
//	== PRIMITIVNE SPREMENLJIVKE == //
	private int PORT = 5000;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GraficniVmesnik frame = new GraficniVmesnik(); // Ustvari nov object in zaženi konstuktor ** USTVARI OKNO ** //
					frame.setVisible(true); // ** NAREDI VIDNO **
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	/** KONSTUKTOR ** 
	 * Dodajanje raznih Grafiænih komponent *
	 */
	public GraficniVmesnik() {
		setTitle("Skupna raba - Stre\u017Enik");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 376, 211);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel portLbl = new JLabel("Port stre\u017Enika:");
		portLbl.setBounds(10, 44, 136, 14);
		contentPane.add(portLbl);
		
		portTextField = new JTextField();
		portTextField.setBounds(197, 41, 50, 20);
		contentPane.add(portTextField);
		portTextField.setColumns(10);
		
		JLabel gesloLbl = new JLabel("Geslo: ");
		gesloLbl.setBounds(10, 86, 80, 14);
		contentPane.add(gesloLbl);
		
		gesloField = new JPasswordField();
		gesloField.setBounds(197, 83, 153, 20);
		contentPane.add(gesloField);
		
		JButton btnZaeniStrenik = new JButton("Za\u017Eeni stre\u017Enik");
		btnZaeniStrenik.setBounds(110, 135, 136, 23);
		btnZaeniStrenik.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent arg0) {
				boolean obstajaNapaka = false;
				try{PORT = Integer.parseInt(portTextField.getText());
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Vrednost PORT-a mora biti stevilo!");
					obstajaNapaka = true;
				}
				if(portTextField.getText().length() > 4) {
					JOptionPane.showMessageDialog(null, "V PORT vpisite 4-mestno stevilko");
					obstajaNapaka = true;
				}
				
				if(gesloField.getText().length() < 5) {
					JOptionPane.showMessageDialog(null, "Geslo mora vsebovati vsaj 5 crk/stevilk.");
					obstajaNapaka = true;
				}
				
				if(!obstajaNapaka) {
					streznik = new Streznik(PORT, gesloField.getText());
					
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								GraficniVmesnikStreznik frame = new GraficniVmesnikStreznik(Integer.parseInt(portTextField.getText()), gesloField.getText());
								setVisible(false); // ** Ustvari nov objekt in zaženi konstruktor ** = ODPRE DRUGO OKNO = **
								frame.setVisible(true);
								
								
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
			}
		});
		contentPane.add(btnZaeniStrenik);
		
		JLabel lblKonfigurirajteProgram = new JLabel("Konfigurirajte program:  ");
		lblKonfigurirajteProgram.setBounds(105, 11, 178, 14);
		contentPane.add(lblKonfigurirajteProgram);
		
		
	}
	
}
