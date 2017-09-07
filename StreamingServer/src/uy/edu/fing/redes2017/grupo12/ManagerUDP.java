package uy.edu.fing.redes2017.grupo12;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.MatOfByte;

public class ManagerUDP extends Thread {

	/**
	 * @param args
	 */
	volatile List<ClienteUDP> dataPackets;
	DatagramSocket socketServidor;
	int numCon=0;
	volatile long numeroFrameLoad=0L;
	volatile boolean hayEnvio=false;
	volatile long numFrameSend=-1L;
	volatile MatOfByte frameToSend=null;
	volatile long frameReallySended=0L;
	 
	public ManagerUDP(DatagramSocket s){
		this.socketServidor=s;
	}
	
	@Override
	public void run()
	{
		dataPackets= new ArrayList<>();
		while(true)
		{
			ClienteUDP u;
			try {
				DatagramPacket p= new DatagramPacket(new byte[1024],1024);
				socketServidor.receive(p);
				String pedido=new String(p.getData(),0,p.getLength());
				//String[] pd=pedido.split(":");
				if(pedido.equals("inicio"))
				{ 
					u = new ClienteUDP(socketServidor,p);
					numCon++;
					dataPackets.add(u);
					
				}
				else
				{
					ClienteUDP c=buscarCliente(p.getAddress(), p.getPort());
					if(c!=null)
						c.renewCon();
					else
					{//ver si lo meto a prepo en lista de clientes o devuelvo mensaje de error
						
					}
					
				}
				//System.out.println("Nueva peticion UDP: origen "+u.dirDestino+" puerto remoto:"+u.puertoRemoto );
				
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
			
	}
	public ClienteUDP buscarCliente(InetAddress a, int p)
	{
		
		for (ClienteUDP clienteUDP : dataPackets) {
			if(clienteUDP.puertoRemoto==p && clienteUDP.dirDestino.equals(a))
				return clienteUDP;
		}
		return null;
	}
	
}
