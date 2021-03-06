package uy.edu.fing.redes2017.grupo12;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;


public class ClienteUDP  {

	private DatagramPacket salidaAlCliente;
	private int puertoRemoto;
	private InetAddress dirDestino;
	private DatagramSocket s;
	//private long numPed = 0;
	private volatile long miliscon = 0;
	private volatile boolean pidioFrame = false;

	public ClienteUDP(DatagramSocket s, DatagramPacket recep) throws IOException{
		
		this.s = s;
		puertoRemoto = recep.getPort();
		dirDestino = recep.getAddress();
		miliscon = (new Date()).getTime();
		
	}
	
	public void renewCon(){
		miliscon = (new Date()).getTime();
	}
		
	public Boolean enviarFrame(byte[] bytes) throws IOException{
		
//		ByteArrayOutputStream baso = new ByteArrayOutputStream();
//			
//		DataOutputStream bySend = new DataOutputStream(baso);
//		bySend.writeLong(numPed);
//		bySend.write(bytes);
//		bySend.flush();
//		byte [] sb = baso.toByteArray();
//		salidaAlCliente = new DatagramPacket(sb, sb.length, dirDestino, puertoRemoto);	
//		s.send(salidaAlCliente);
//		numPed++;
//		return true;
		
		salidaAlCliente = new DatagramPacket(bytes, bytes.length, dirDestino, puertoRemoto);
		s.send(salidaAlCliente);
//		numPed++;
		return true;
	
	}

	public int getPuertoRemoto() {
		return puertoRemoto;
	}

	public InetAddress getDirDestino() {
		return dirDestino;
	}
	
	public long getMiliscon() {
		return miliscon;
	}

	public boolean isPidioFrame() {
		return pidioFrame;
	}

	public void setPidioFrame(boolean pidioFrame) {
		this.pidioFrame = pidioFrame;
	}

	public void setMiliscon(long miliscon) {
		this.miliscon = miliscon;
	}

	public void setPuertoRemoto(int puertoRemoto) {
		this.puertoRemoto = puertoRemoto;
	}

	public void setDirDestino(InetAddress dirDestino) {
		this.dirDestino = dirDestino;
	}

}
