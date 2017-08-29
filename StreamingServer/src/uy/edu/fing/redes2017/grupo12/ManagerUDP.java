package uy.edu.fing.redes2017.grupo12;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.MatOfByte;

public class ManagerUDP extends Thread {

	/**
	 * @param args
	 */
	volatile List<DatagramPacketUDP> dataPackets;
	 DatagramSocket socketServidor;
	 int numCon=0;
	volatile long numeroFrameLoad=0L;
	volatile boolean hayEnvio=false;
	volatile long numFrameSend=-1L;
	volatile MatOfByte frameToSend=null;
	volatile long frameReallySended=0L;
	 
	 public ManagerUDP(DatagramSocket s)
	 {
		 this.socketServidor=s;
	 }
	
	@Override
	public void run()
	{
		dataPackets= new ArrayList<>();
		while(true)
		{
			DatagramPacketUDP u;
			try {
				DatagramPacket p= new DatagramPacket(new byte[1024],1024);
				socketServidor.receive(p);
				u = new DatagramPacketUDP(socketServidor,p);
				numCon++;
				//System.out.println("Nueva peticion UDP: origen "+u.dirDestino+" puerto remoto:"+u.puertoRemoto );
				
				dataPackets.add(u);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
			
	}
	
	
}
