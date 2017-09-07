package uy.edu.fing.redes2017.grupo12;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Date;

import javax.xml.crypto.KeySelector.Purpose;



public class ClienteUDP  {

	DatagramPacket recepcion;
	DatagramPacket salidaAlCliente;
	int puertoRemoto;
	InetAddress dirDestino;
	DatagramSocket s;
	
	long numPed=0;
	long miliscon=0;

	public ClienteUDP(DatagramSocket s, DatagramPacket recep) throws IOException{
		this.s=s;
		this.recepcion=recep;
		
		puertoRemoto=recep.getPort();
		dirDestino=recep.getAddress();
		
		miliscon= (new Date()).getTime();
		
		
	}
	public void renewCon(){
		miliscon= (new Date()).getTime();
	}
		
	public Boolean enviarFrame(byte[] bytes) throws IOException{
		
		ByteArrayOutputStream baso= new ByteArrayOutputStream();
		
		DataOutputStream bySend= new DataOutputStream(baso);
		bySend.writeLong(numPed);
		bySend.write(bytes);
		bySend.flush();
		byte [] sb=baso.toByteArray();
		salidaAlCliente=new DatagramPacket(sb, sb.length, dirDestino, puertoRemoto);
		s.send(salidaAlCliente);
		numPed++;
		return true;
	
	}
	

	
}
