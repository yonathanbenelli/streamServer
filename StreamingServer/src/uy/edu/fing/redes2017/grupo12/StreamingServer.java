package uy.edu.fing.redes2017.grupo12;


import java.net.DatagramSocket;
import java.net.ServerSocket;

import org.opencv.core.Core;


public class StreamingServer {

	public static void main(String[] args) throws Exception {
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		int puerto = 6789;
		int puertoUDP = 9876;
		String reproducir = new String();
		if (args.length > 0){ 
			puerto = new Integer(args[0]);
			reproducir = args[1];
		}
		
		ConexionManager cm = new ConexionManager(new ServerSocket(puerto));
		cm.start();
		ManagerUDP mudp = new ManagerUDP(new DatagramSocket(puertoUDP));
		mudp.start();
		SenderFrame sf = new SenderFrame(cm,mudp);
		sf.start();
		KillUDP ku = new KillUDP(mudp);
		ku.start();
		ProcessStreaming pf = new ProcessStreaming(cm, reproducir);
		pf.start();
		StatadisitcThread sT = new StatadisitcThread(cm);
		sT.start();
	
		System.out.println("Servidor iniciado conexiones activas: 0");
					
	}
	
}
