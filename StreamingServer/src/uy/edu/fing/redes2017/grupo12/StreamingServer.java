package uy.edu.fing.redes2017.grupo12;


import java.io.File;

import org.opencv.core.Core;


public class StreamingServer {
	static ConexionManager cm;

	static ProcessStreaming pf;
	static SenderFrame sf;
	static StatadisitcThread sT;
	public static void main(String[] args) throws Exception {
		    try {
		   
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		int puertoTCP = new Integer(args[0]);;
		int puertoUDP = new Integer(args[1]);
		String reproducir = args[2];
 		 cm= new ConexionManager(puertoTCP, puertoUDP);
		
		ProcessStreaming pf = new ProcessStreaming(cm, reproducir);
		pf.start();
		sf= new SenderFrame(pf,cm);
		sf.start();
		sT = new StatadisitcThread(cm);
		sT.start();
	
		ShutdownHook stdh2 = new ShutdownHook(cm,pf,sf,sT);
		stdh2.attachShutDownHook();
		System.out.println("Servidor iniciado conexiones activas: 0");
		    } catch (Exception e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		        
		    }
	}
	
}
