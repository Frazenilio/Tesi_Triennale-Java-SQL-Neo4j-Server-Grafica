package clientServerApplication;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import errori.ErroreDatabaseNotWorking;
import generateResponseChainOfResponsibility.GenerateAddComprensorio;
import generateResponseChainOfResponsibility.ResponseGeneratorInterface;
import logica.Gestore;
import logica.ModelService;
import requestPackage.Request;
import responsePackage.Response;

/**
 * Classe per gestire il server
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class MyServer {

	private static int port = 4999;
	
	private static Gestore gestore;
	
	public static void main(String[] args) {
		
		try {
			gestore = new Gestore();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ErroreDatabaseNotWorking e) {
			e.printStackTrace();
		}
		
		try (ServerSocket serverSocket = new ServerSocket(port)) {
		    System.out.println("Server in ascolto sulla porta: " + port);

		    while (true) {
		        Socket clientSocket = serverSocket.accept();
		        System.out.println("Connessione client accettata da: " + clientSocket.getInetAddress());

		        new Thread(() -> gestisciConnessioneClient(clientSocket, gestore)).start();
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}

	}
	
	/**
	 * Metodo per gestire le richieste del client
	 * @param clientSocket socket del client con cui comunicare
	 * @param gestore model da cui attingere per i dati
	 * @since TESI CLIENT-SERVER
	 */
	private static void gestisciConnessioneClient(Socket clientSocket, ModelService gestore) {
	    try (ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
	    		ObjectOutputStream  output = new ObjectOutputStream(clientSocket.getOutputStream())) {

	    	ResponseGeneratorInterface handler = new GenerateAddComprensorio();
	    	do {
	    		
	    		try {
	    			
					Request request = (Request) input.readObject();
					Response response = handler.generateResponse(gestore, request);
					output.writeObject(response);
					output.flush();
					output.reset();
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
	    		
	    	}while(true);
	    } catch (IOException e) {
	        System.err.println("Exit");
	    } finally {
	        try {
	            clientSocket.close();
	        } catch (IOException e) {
	        	System.out.println("client uscito");
	            e.printStackTrace();
	        }
	    }
	}
	
}
