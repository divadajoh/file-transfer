package com.davidhoja.odjemalec;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class PrenasalecDatotek implements Runnable {
	
//	== REFERENÈNE SPREMENLJIVKE == //
	private Socket povezava;
	private Datoteka zacasnaDatoteka;
	private InputStream is = null;
	private FileOutputStream fileOuputStream;
	private BufferedOutputStream bufferedOutputStream;
	
//	== PRIMITIVNE SPREMENLJIVKE == //
	private int filesize, currentTot, prebraniByte;
	
	private String absolutnaPot;
	
	
	public PrenasalecDatotek(Socket povezava, Datoteka zacasnaDatoteka) {
		this.povezava = povezava;
		this.zacasnaDatoteka = zacasnaDatoteka;
		this.absolutnaPot = System.getProperty("user.home") + "\\";
		this.absolutnaPot += zacasnaDatoteka.getImeDatoteke();
		System.out.println("Absolutna pot je" +  this.absolutnaPot);
		
		
		new Thread(this).start();
	}
	
	
	public void run() {
		try{
			byte [] vsebujebyte = new byte [zacasnaDatoteka.getVelikostDatoteke()]; 
			InputStream is = povezava.getInputStream();
			fileOuputStream = new FileOutputStream(absolutnaPot); 
			bufferedOutputStream = new BufferedOutputStream(fileOuputStream); 
			prebraniByte = is.read(vsebujebyte,0,vsebujebyte.length); 
			currentTot = prebraniByte; do { prebraniByte = is.read(vsebujebyte, currentTot, (vsebujebyte.length-currentTot)); 
			if(prebraniByte >= 0) currentTot += prebraniByte; 
			}while(currentTot != zacasnaDatoteka.getVelikostDatoteke()); bufferedOutputStream.write(vsebujebyte, 0 , currentTot); bufferedOutputStream.flush(); 
			System.out.println("I HAVE ENDED!");
			fileOuputStream.close();
			bufferedOutputStream.close();
			
		} catch(IOException ex) {
			ex.printStackTrace();
		}finally {
			
		}
	}

}
