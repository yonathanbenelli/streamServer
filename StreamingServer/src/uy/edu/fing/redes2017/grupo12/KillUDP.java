package uy.edu.fing.redes2017.grupo12;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.opencv.core.MatOfByte;

public class KillUDP extends Thread {

	/**
	 * @param args
	 */
	volatile List<ClienteUDP> dataPackets;
	ManagerUDP mudp;
	 
	public KillUDP(ManagerUDP mudp){
		this.mudp=mudp;
	}
	
	@Override
	public void run()
	{
		
		while(true)
		{
			dataPackets=mudp.dataPackets;
			List<ClienteUDP> dPB= new ArrayList<ClienteUDP>() ;
			long timeact=(new Date()).getTime();
			if(dataPackets!=null && dataPackets.size()>0)
			for (ClienteUDP clienteUDP : dataPackets) {
				if(timeact-clienteUDP.miliscon>90000) 
					dPB.add(clienteUDP);
			}
			
			for (ClienteUDP clienteUDP : dPB) {
				dataPackets.remove(clienteUDP);
			}
			
		}
			
	}
	
	
}
