package uy.edu.fing.redes2017.grupo12;


import java.net.DatagramSocket;
import java.net.ServerSocket;

import org.opencv.core.Core;


public class StreamingServer {

	public static void main(String[] args) throws Exception {
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		int puertoTCP = 6789;
		int puertoUDP = 9876;
		String reproducir = "0";
		if (args.length > 2){ 
			puertoTCP = new Integer(args[0]);
			puertoUDP = new Integer(args[1]);
			reproducir = args[2];
		}
		ConexionManager cm= new ConexionManager(puertoTCP, puertoUDP);
		
		ProcessStreaming pf = new ProcessStreaming(cm, reproducir);
		pf.start();
		
		 SenderFrame sf= new SenderFrame(pf,cm);
			sf.start();
		StatadisitcThread sT = new StatadisitcThread(cm);
		sT.start();
	
		System.out.println("Servidor iniciado conexiones activas: 0");
	/*test codenvy*/				
	}
	
}
