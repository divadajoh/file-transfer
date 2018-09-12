import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Klient {
	
	public Klient() throws UnknownHostException, IOException {
		Socket socket = new Socket("127.0.0.1",5000);
		
		PrintWriter pw = new PrintWriter(socket.getOutputStream());

		Scanner s = new Scanner(System.in);
		String message = "";
				
		while(message != "end") {
			message = s.next();
			if(message != "") {
			System.out.println(message);
			pw.println(message);
			pw.flush();
		}
			}
	}
	
	public static void main(String[] args) {
		try {
			new Klient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
