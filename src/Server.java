import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;



public class Server {
		
	public static void main(String[] args){
		ServerSocket servsock;
		Socket sock = null;
		try{
		servsock = new ServerSocket(5000);
	    File myFile = new File("C:\\Users\\David\\Documents\\index.html");
	    while (sock == null) {
	      sock = servsock.accept();
	      }
	    
	    
	      byte[] mybytearray = new byte[(int) myFile.length()];
	      BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
	      bis.read(mybytearray, 0, mybytearray.length);
	      OutputStream os = sock.getOutputStream();
	      os.write(mybytearray, 0, mybytearray.length);
	      os.flush();
	      sock.close();
	    
	    
		}catch(Exception ex){ex.printStackTrace();}
		}
}