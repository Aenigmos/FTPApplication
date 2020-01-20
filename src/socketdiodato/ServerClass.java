/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketdiodato;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author informatica
 */
public class ServerClass 
{
	private int porta = 2000;
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private BufferedReader in,inServer;
	private PrintWriter out;
	private CountDown count;
	private final int SECONDS_COUNTDOWN=30;
	public static boolean connected=false;
	
	public ServerClass()
	{
		startServer();
	}
	
	public void startServer()
	{
		try {
			count = new CountDown(SECONDS_COUNTDOWN,this);
			serverSocket = new ServerSocket(porta);
			count.start();
		} catch (IOException ex) {
			System.err.println("Impossibile creare il socket");
			System.exit(1);
		}
		System.out.println("Echo Server: Server inizializzato!");
		System.out.println("Server Socket: " + serverSocket);
		accettaConnessioni();
	}
	
	public void accettaConnessioni()
	{
		try 
		{
			clientSocket = serverSocket.accept();
			connected = true;
			count.interrupt();
			System.out.println("Connessione accettata: " + clientSocket);
		}
		catch (IOException e) 
		{
			System.err.println("Connessione fallita!");
		}
		startRicezione();
	}
	
	public void startRicezione()
	{
		InputStreamReader isr = null;
		try {
			isr = new InputStreamReader(clientSocket.getInputStream());
			in = new BufferedReader(isr);
			OutputStreamWriter osw = new OutputStreamWriter(clientSocket.getOutputStream());
			BufferedWriter bw = new BufferedWriter(osw);
			out = new PrintWriter(bw, true);
			while (true)
			{
				String str = in.readLine();
				if (str.equals("END"))
				{
					System.out.println("Client disconnesso!");
					connected = false;
					count = new CountDown(SECONDS_COUNTDOWN,this);
					count.start();
					accettaConnessioni();
					break;
				}
				System.out.println("Echo Client: " + str);
				out.println(str);
			} 
		} catch (IOException ex) {
			System.err.println("Errore durante la ricezione dati!"); 
		}
	}
	
	public void chiudiServer()
	{
		try {
			System.out.println("Echo Server: chiusura server...");
			out.close();
			in.close();
			clientSocket.close();
			serverSocket.close();
		} catch (IOException ex) {
			System.err.println("Errore durante la chiusura del server!");
		}
	}
	
	public void connessioneScaduta()
	{
		System.out.println("Echo Server: chiusura server...");
		System.exit(0);
	}
}
