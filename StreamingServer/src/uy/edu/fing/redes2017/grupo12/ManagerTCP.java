package uy.edu.fing.redes2017.grupo12;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class ManagerTCP extends Thread {

	//volatile te permite actualizar las variables compartidas por los threads de forma inmediata.
	private volatile List<ConexionTCP> conexionesTCP;
	private int numCon = 0;

	private volatile Boolean fin=false;
		
	private ServerSocket socketServidor ;
	
	public ManagerTCP(ServerSocket s){
		 this.socketServidor=s;
		 
		 
	}
	
	@Override
	public void run(){
		
		conexionesTCP= new ArrayList<ConexionTCP>();
		while(!fin){
			
			ConexionTCP t;
			try{
				
				t = new ConexionTCP(socketServidor.accept());
				numCon++;
				System.out.println("Nueva conexion: puerto remoto:" + t.getPuertoRemoto());
				conexionesTCP.add(t);
			
			} catch (IOException e){
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
		for (ConexionTCP conexionTCP : conexionesTCP) {
			conexionTCP.cerrar();
		}
		System.out.println("FIN: managerTCP");
		
	}

	public List<ConexionTCP> getConexionesTCP() {
		return conexionesTCP;
	}

	

	public ServerSocket getSocketServidor() {
		return socketServidor;
	}

	public int getNumCon() {
		return numCon;
	}

	public void setConexionesTCP(List<ConexionTCP> conexionesTCP) {
		this.conexionesTCP = conexionesTCP;
	}


	public void setSocketServidor(ServerSocket socketServidor) {
		this.socketServidor = socketServidor;
	}

	public void setNumCon(int numCon) {
		this.numCon = numCon;
	}
		
}
