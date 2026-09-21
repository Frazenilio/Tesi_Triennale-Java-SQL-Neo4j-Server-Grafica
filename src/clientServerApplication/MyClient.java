package clientServerApplication;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * Classe per gestire il client
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class MyClient {
	
	public static void main(String[] args) {
		String serverAddress = "localhost";
		int port = 4999; 
		
		try (Socket socket = new Socket(serverAddress, port);
		     ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
		     ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {
			runClient(socket, outputStream, inputStream);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	/**
	 * Metodo che gestisce l'operazione del client
	 * @param socket socket che collega il client al server
	 * @param out output di oggetti
	 * @param input input di oggetti
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @since TESI CLIENT-SERVER
	 */
	public static void runClient(Socket socket, ObjectOutputStream out, ObjectInputStream input) throws FileNotFoundException, IOException {
		ClientApp app = new ClientApp(socket, out, input);
		app.main();
	}
}
