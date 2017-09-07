package uy.edu.fing.redes2017.grupo12;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.MatOfByte;

public class SenderFrame extends Thread {

	private ConexionManager conMan;
	private ManagerUDP manUDP;
	//private long nFL = -1;
	//private MatOfByte fs = null;
	
	public SenderFrame(ConexionManager cm, ManagerUDP manUDP)
	{
		conMan = cm;
		this.manUDP = manUDP;
	}
	
	@Override
	public void run() {
		
		while (true){
			enviarFrame();
		}
	
	}

	public void enviarFrame(){
		
		if(conMan.isHayEnvio()){
			
			conMan.setHayEnvio(false);
			MatOfByte fs = conMan.getFrameToSend();
			
			conMan.setFrameReallySended(conMan.getFrameReallySended() + 1);
			
			List<ConexionTCP> conexionesTCPB = new ArrayList<>();
			Integer actCon = 0;
			Integer fin = conMan.getConexionesTCP().size();
			
			while(actCon < fin){
		
				ConexionTCP conexionTCP = conMan.getConexionesTCP().get(actCon);
				boolean b = false;
			  
				try {
					
					b=true;
					if(conexionTCP.esActiva())
							if(conexionTCP.enviarFrame(fs.toArray()))
							  b = false;
				
				} catch (IOException e) {
					e.printStackTrace();
				}
			
				if (b){
					
					conMan.setNumCon(conMan.getNumCon() - 1);
					conexionTCP.cerrar();	
					conexionesTCPB.add(conexionTCP);
				
				}
				actCon++;
			}
		
			
			for (ConexionTCP conexionTCP : conexionesTCPB) 
				conMan.getConexionesTCP().remove(conexionTCP);
			
			Integer actConU = 0;
			Integer finU = manUDP.getDataPackets().size();
			
			while(actConU < finU){
		
				ClienteUDP dg = manUDP.getDataPackets().get(actConU);
			
				if(dg != null){
					
					try {
						dg.enviarFrame(fs.toArray());
					} catch (IOException e) {
						e.printStackTrace();
					}
					actConU++;
				
				}
				
			}
			
		}
		
	}
	
}
