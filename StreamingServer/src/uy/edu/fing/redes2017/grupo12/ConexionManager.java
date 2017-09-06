package uy.edu.fing.redes2017.grupo12;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.MatOfByte;

public class ConexionManager extends Thread {

	/**
	 * @param args
	 */
	volatile List<ConexionTCP> conexionesTCP;
	ServerSocket socketServidor ;
	int numCon=0;
	volatile long numeroFrameLoad=0L;
	volatile boolean hayEnvio=false;
	volatile long numFrameSend=-1L;
	volatile MatOfByte frameToSend=null;
	volatile long frameReallySended=0L;
	 
	public ConexionManager(ServerSocket s){
		 this.socketServidor=s;
	}
	
	@Override
	public void run()
	{
		conexionesTCP= new ArrayList<>();
		while(true)
		{
			ConexionTCP t;
			try {
				t = new ConexionTCP(socketServidor.accept());
				numCon++;
				System.out.println("Nueva conexion: puerto remoto:"+t.puertoRemoto);
				
				conexionesTCP.add(t);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
			
	}
	
	
}
