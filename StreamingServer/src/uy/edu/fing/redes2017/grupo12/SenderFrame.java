package uy.edu.fing.redes2017.grupo12;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.MatOfByte;

public class SenderFrame extends Thread {

	ConexionManager conMan;
	ManagerUDP manUDP;
	long nFL=-1;
	MatOfByte fs=null;
	
	public SenderFrame(ConexionManager cm, ManagerUDP manUDP)
	{
		conMan=cm;
		this.manUDP=manUDP;
	}
	
	@Override
	public void run() {
		
		while (true){
			enviarFrame();
		}
	
	}

	public void enviarFrame(){
		
		if(conMan.hayEnvio){
			
			conMan.hayEnvio=false;
			MatOfByte fs=conMan.frameToSend;
			
			conMan.frameReallySended++;
			
			List<ConexionTCP> conexionesTCPB= new ArrayList<>();
			Integer actCon=0;
			Integer fin=conMan.conexionesTCP.size();
			
			while(actCon < fin){
		
				ConexionTCP conexionTCP=conMan.conexionesTCP.get(actCon);
				boolean b=false;
			  
				try {
					
					b=true;
					if(conexionTCP.esActiva())
							if(conexionTCP.enviarFrame(fs.toArray()))
							  b=false;
				
				} catch (IOException e) {
					
				}
			
				if (b){
					
					conMan.numCon--;
					conexionTCP.cerrar();	
					conexionesTCPB.add(conexionTCP);
				
				}
				actCon++;
			}
		
			
			for (ConexionTCP conexionTCP : conexionesTCPB) 
				conMan.conexionesTCP.remove(conexionTCP);
			
			Integer actConU=0;
			Integer finU=manUDP.dataPackets.size();
			List<DatagramPacketUDP> dPUDPB= new ArrayList<>();
			
			
			while(actConU < finU)
			{
		
				DatagramPacketUDP dg=manUDP.dataPackets.get(actConU);
			
				if(dg!=null){
					
					try {
					
						dg.enviarFrame(fs.toArray());
						dPUDPB.add(dg);
				
					} catch (IOException e) {
					
					}
					actConU++;
				
				}
			}
			
			for (DatagramPacketUDP d : dPUDPB) 
				manUDP.dataPackets.remove(d);
			
			
		}
		
	}
	
}
