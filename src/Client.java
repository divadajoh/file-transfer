import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;


public class Client {
	public static void main(String[] args) throws IOException { 
		int filesize=2022386; int prebraniByte; int currentTot = 0;
		Socket socket = new Socket("127.0.0.1",5000); 
		byte [] vsebujebyte = new byte [filesize]; 
		InputStream is = socket.getInputStream();
		FileOutputStream fos = new FileOutputStream("C:\\Users\\David\\Desktop\\dreksi.html"); 
		BufferedOutputStream bos = new BufferedOutputStream(fos); 
		System.out.println(is.read() + "sdad ");
		prebraniByte = is.read(vsebujebyte,0,vsebujebyte.length); 
		System.out.println("s a " +prebraniByte);
		System.out.println();
		currentTot = prebraniByte; do { prebraniByte = is.read(vsebujebyte, currentTot, (vsebujebyte.length-currentTot)); 
		System.out.println(currentTot);
		if(prebraniByte >= 0) currentTot += prebraniByte;}
		while(prebraniByte > -1); bos.write(vsebujebyte, 0 , currentTot); bos.flush(); }

	 // V WHILE  bos.close(); socket.close(); 

}
