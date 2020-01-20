/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketdiodato;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author informatica
 */
public class ClientClass 
{
	private InetAddress addr;
	private Socket socket;
	private BufferedReader in, stdIn;
	private PrintWriter out;
	private int porta = 2000;
	private String ip = "127.0.0.1";
	
	public ClientClass()
	{
		try {
			addr = InetAddress.getByName(ip);
		} catch (UnknownHostException ex) {
			System.err.println("Host sconosciuto!");
			System.exit(1);
		}
		startClient();
	}
	
	public ClientClass(String address)
	{
		try {
			addr = InetAddress.getByName(address);
		} catch (UnknownHostException ex) {
			System.err.println("Host sconosciuto!");
			System.exit(1);
		}
		startClient();
	}
	
	public void startClient()
	{
		try {
			socket = new Socket(addr, porta);
		} catch (IOException ex) {
			System.err.println("Impossibile creare il socket!");
			System.exit(1);
		}
		System.out.println("Echo Client: Client inizializzato!");
		System.out.println("Client Socket: "+ socket);
	}
	
	public void startRicezione()
	{
		InputStreamReader isr = null;
		try {
			isr = new InputStreamReader(socket.getInputStream());
			in = new BufferedReader(isr);
			OutputStreamWriter osw = new OutputStreamWriter( socket.getOutputStream());
			BufferedWriter bw = new BufferedWriter(osw);
			out = new PrintWriter(bw, true);
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			String userInput;
			while (true)
			{
				userInput = stdIn.readLine();
				out.println(userInput);
				if (userInput.equals("END"))
				{
					chiudiClient();
					break;
				}
				System.out.println("Echo: " + in.readLine());
			}
		} catch (IOException ex) {
			System.err.println("Errore durante l'invio dei dati!");
		}
	}
	
	public void chiudiClient()
	{
		try {
			System.out.println("Echo Client: chiusura client...");
			out.close();
			in.close();
			stdIn.close();
			socket.close();
		} catch (IOException ex) {
			System.err.println("Errore durante la chiusura del client!");
		}
	}
}
