package uy.edu.fing.redes2017.grupo12;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.MatOfByte;

public class ConexionManager extends Thread {

	//volatile te permite actualizar las variables compartidas por los threads de forma inmediata.
	private volatile List<ConexionTCP> conexionesTCP;
	private volatile long numeroFrameLoad=0L;
	private volatile boolean hayEnvio=false;
	private volatile long numFrameSend=-1L;
	private volatile MatOfByte frameToSend=null;
	private volatile long frameReallySended=0L;
	
	private ServerSocket socketServidor ;
	private int numCon = 0;
	
	public ConexionManager(ServerSocket s){
		 this.socketServidor=s;
	}
	
	@Override
	public void run(){
		
		conexionesTCP= new ArrayList<ConexionTCP>();
		while(true){
			
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

	public List<ConexionTCP> getConexionesTCP() {
		return conexionesTCP;
	}

	public long getNumeroFrameLoad() {
		return numeroFrameLoad;
	}

	public boolean isHayEnvio() {
		return hayEnvio;
	}

	public long getNumFrameSend() {
		return numFrameSend;
	}

	public MatOfByte getFrameToSend() {
		return frameToSend;
	}

	public long getFrameReallySended() {
		return frameReallySended;
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

	public void setNumeroFrameLoad(long numeroFrameLoad) {
		this.numeroFrameLoad = numeroFrameLoad;
	}

	public void setHayEnvio(boolean hayEnvio) {
		this.hayEnvio = hayEnvio;
	}

	public void setNumFrameSend(long numFrameSend) {
		this.numFrameSend = numFrameSend;
	}

	public void setFrameToSend(MatOfByte frameToSend) {
		this.frameToSend = frameToSend;
	}

	public void setFrameReallySended(long frameReallySended) {
		this.frameReallySended = frameReallySended;
	}

	public void setSocketServidor(ServerSocket socketServidor) {
		this.socketServidor = socketServidor;
	}

	public void setNumCon(int numCon) {
		this.numCon = numCon;
	}
		
}
