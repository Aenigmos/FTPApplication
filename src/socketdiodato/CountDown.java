/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketdiodato;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author informatica
 */
public class CountDown extends Thread
{
	private double seconds;
	private ServerClass server;
	
	public CountDown(double seconds, ServerClass server)
	{
		this.seconds = seconds;
		this.server = server;
	}
	
	public void run()
	{
		try {
			for(int i=0; i<seconds; i++ )
			{
				if(!ServerClass.connected)
				{
					Thread.sleep(1000);
					System.out.println("Countdown:"+(seconds-i)+"s");
				}
				else
				{
					break;
				}
			}
			Thread.sleep(1000);
			System.out.println("Connessione scaduta");
			server.connessioneScaduta();
		} catch (InterruptedException ex) {
			
		}
	}
}
