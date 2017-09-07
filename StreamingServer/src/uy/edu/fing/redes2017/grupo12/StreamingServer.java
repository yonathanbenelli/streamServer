package uy.edu.fing.redes2017.grupo12;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.MatOfByte;

public class StreamingServer {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) throws Exception {
		
		// TODO Auto-generated method stub
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		int puerto = 6789;
		int puertoUDP = 9876;
		if (args.length > 0) puerto = new Integer(args[0]);
		
		
		ConexionManager cm= new ConexionManager(new ServerSocket(puerto));
		cm.start();
		ManagerUDP mudp= new ManagerUDP(new DatagramSocket(puertoUDP));
		mudp.start();
		SenderFrame sf= new SenderFrame(cm,mudp);
		sf.start();
		KillUDP ku= new KillUDP(mudp);
		ku.start();
		ProcessStreaming pf=new ProcessStreaming(cm, System.getProperty("user.dir") + "/StreamingServer/src/uy/edu/fing/redes2017/grupo12/friends.mkv");
		pf.start();
		StatadisitcThread sT= new StatadisitcThread(cm);
		sT.start();
		
		System.out.println("Servidor iniciado conexiones activas: 0");
					
	}
	
}
