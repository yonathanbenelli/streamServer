package uy.edu.fing.redes2017.grupo12;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

//import org.opencv.core.MatOfByte;

public class ManagerUDP extends Thread {

	private volatile List<ClienteUDP> dataPackets;
	//private volatile long numeroFrameLoad = 0L;
	//private volatile boolean hayEnvio = false;
	//private volatile long numFrameSend = -1L;
	//private volatile MatOfByte frameToSend = null;
	//private volatile long frameReallySended = 0L;
	private DatagramSocket socketServidor;
	//private int numCon = 0;

	public ManagerUDP(DatagramSocket s){
		this.socketServidor = s;
	}
	
	@Override
	public void run(){
		
		dataPackets = new ArrayList<>();
		while(true){
			
			ClienteUDP u;
			try {
				
				DatagramPacket p = new DatagramPacket(new byte[1024],1024);
				socketServidor.receive(p);
				String pedido = new String(p.getData(),0,p.getLength());
				if(pedido.equals("inicio")){
					
					u = new ClienteUDP(socketServidor,p);
					//numCon++;
					dataPackets.add(u);
					
				} else{
					
					ClienteUDP c = buscarCliente(p.getAddress(), p.getPort());
					if(c != null)
						c.renewCon();
					else{
						//TODO ver si lo meto a prepo en lista de clientes o devuelvo mensaje de error
					}
					
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
			
	}
	
	public ClienteUDP buscarCliente(InetAddress a, int p){
		
		for (ClienteUDP clienteUDP : dataPackets) {
			
			if(clienteUDP.getPuertoRemoto() == p && clienteUDP.getDirDestino().equals(a))
				return clienteUDP;
		
		}
		return null;
	}

	public List<ClienteUDP> getDataPackets() {
		return dataPackets;
	}

	public void setDataPackets(List<ClienteUDP> dataPackets) {
		this.dataPackets = dataPackets;
	}
	
}
