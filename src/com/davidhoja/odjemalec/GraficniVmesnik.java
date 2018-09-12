package com.davidhoja.odjemalec;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class GraficniVmesnik extends JFrame {

//	== INSTANÈNE SPREMENLJIVKE == //
	private JPanel contentPane;
	private JTextField ipTextField;
	private JTextField portTextField;
	private JPasswordField gesloField;
	
	private Odjemalec odjemalec;
	private JTextField lokacijaTextField;
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GraficniVmesnik frame = new GraficniVmesnik();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public GraficniVmesnik() {
		setTitle("Skupna raba datotek - Odjemalec");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 356, 325);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel ipLbl = new JLabel("IP Naslov stre\u017Enika:");
		ipLbl.setBounds(23, 59, 115, 14);
		contentPane.add(ipLbl);
		
		ipTextField = new JTextField();
		ipTextField.setBounds(190, 56, 134, 20);
		contentPane.add(ipTextField);
		ipTextField.setColumns(10);
		
		JLabel naslovLbl = new JLabel("ODJEMALEC");
		naslovLbl.setBounds(147, 11, 134, 14);
		contentPane.add(naslovLbl);
		
		JLabel mejaLbl = new JLabel("");
		mejaLbl.setBounds(0, 36, 378, 2);
		mejaLbl.setBorder(BorderFactory.createLineBorder(Color.black));
		contentPane.add(mejaLbl);
		
		JLabel portLbl = new JLabel("PORT stre\u017Enika:");
		portLbl.setBounds(23, 98, 94, 14);
		contentPane.add(portLbl);
		
		portTextField = new JTextField();
		portTextField.setBounds(190, 95, 134, 20);
		contentPane.add(portTextField);
		portTextField.setColumns(10);
		
		JLabel gesloLbl = new JLabel("Geslo za dostop:");
		gesloLbl.setBounds(23, 214, 115, 14);
		contentPane.add(gesloLbl);
		
		gesloField = new JPasswordField();
		gesloField.setBounds(190, 211, 134, 20);
		gesloField.setBackground(Color.LIGHT_GRAY);
		contentPane.add(gesloField);
		
		JButton poveziSeButton = new JButton("POVE\u017DI SE");
		poveziSeButton.setFocusable(true);
		poveziSeButton.setBounds(123, 263, 103, 23);
		poveziSeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				int PORT = 5000;
				boolean jeNapaka = false;
				
				try{PORT = Integer.parseInt(portTextField.getText());
				}catch(NumberFormatException ex){ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "PORT mora biti številka!");
					jeNapaka = true;
				}
				
				if(ipTextField.getText().equals("") || gesloField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Nobeno polje ne sme biti prazno!");
					jeNapaka = true;
				}
				
				
				if(!jeNapaka) {
					try {
						odjemalec = new Odjemalec(ipTextField.getText(), PORT, gesloField.getText());
						
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									Thread.sleep(1000);
									if(odjemalec.getUporabnikVpisan()){
									GraficniVmesnikOdjemalec frame  = new GraficniVmesnikOdjemalec(odjemalec);
									frame.setVisible(true);
									setVisible(false);
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				

				
			
			}
		});
		contentPane.add(poveziSeButton);
		
		JLabel mejaSpodajLbl = new JLabel("");
		mejaSpodajLbl.setBounds(0, 250, 378, 2);
		mejaSpodajLbl.setBorder(BorderFactory.createLineBorder(Color.black));
		contentPane.add(mejaSpodajLbl);

		
		poveziSeButton.requestFocus();
	}
}
