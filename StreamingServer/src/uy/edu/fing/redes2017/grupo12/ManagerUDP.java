package uy.edu.fing.redes2017.grupo12;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;


public class ManagerUDP extends Thread {

	private volatile List<ClienteUDP> dataPackets;
	private DatagramSocket socketServidor;
	private volatile Boolean fin=false;
	
	public ManagerUDP(DatagramSocket s){
		this.socketServidor = s;
		dataPackets = new ArrayList<ClienteUDP>();
		
	}
	
	@Override
	public void run(){
		
		while(!fin){
			

			try {
			
				DatagramPacket p = new DatagramPacket(new byte[1024],1024);
				
				socketServidor.receive(p);
				
				String pedido = new String(p.getData(),0,p.getLength());
				if(pedido.equals("inicio")){
					
					ClienteUDP u = new ClienteUDP(socketServidor,p);
					if (!u.isPidioFrame()){
						u.setPidioFrame(true);
					}
					dataPackets.add(u);
					
				}else if (pedido.equals("renovar")){
					
					ClienteUDP c = buscarCliente(p.getAddress(), p.getPort());
					if(c != null)
						c.renewCon();
					else{
						//TODO ver si lo meto a prepo en lista de clientes o devuelvo mensaje de error
					}	
				
				}else if (pedido.equals("pido frame")){
					
					ClienteUDP c = buscarCliente(p.getAddress(), p.getPort());
					if (c != null){
						if (!c.isPidioFrame())
							c.setPidioFrame(true);
					}else {
						ClienteUDP u = new ClienteUDP(socketServidor,p);
						dataPackets.add(u);
					}
										
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}

	}
	
	public void fin()
	{

		fin=true;
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("FIN: managerUDP");
		
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
