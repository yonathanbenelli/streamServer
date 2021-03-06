package uy.edu.fing.redes2017.grupo12;

import java.io.IOException;

import org.opencv.core.MatOfByte;

public class SenderFrame extends Thread {

	private ProcessStreaming pS;
	private ConexionManager conMan;

	private volatile Boolean fin=false;
	
	public SenderFrame(ProcessStreaming pS, ConexionManager cm)
	{
		this.pS = pS;
		this.conMan=cm;
		
	}
	
	@Override
	public void run() {
		
		while (!fin){
			enviarFrame();
		}
		
	}
	
	public void fin()
	{

		fin=true;
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("FIN: senderFrame");
		
	}

	public void enviarFrame(){
		
		if(pS.isHayEnvio()){
			
			
			MatOfByte fs = pS.getFrameToSend();
			pS.setHayEnvio(false);
			
			Integer actCon = 0;
			Integer fin = conMan.cantConexionesTCP();
			
			while(actCon < fin){
		
				ConexionTCP conexionTCP = conMan.obtenerConexioneTCP(actCon);
				if(conexionTCP!=null)
				{
					boolean b = true;
				  
						
								if(conexionTCP.enviarFrame(fs.toArray()))
								  b = false;
					
					if (b)
						conMan.quitarConexion(conexionTCP);
				
					
				}
				actCon++;
			}
		
			Integer actConU = 0;
			Integer finU = conMan.cantConexionesUDP();
			
			while(actConU < finU){
				
				ClienteUDP dg = conMan.obtenerConexioneUDP(actConU);
							
				if(dg != null){
				
					try {
						
						if (dg.isPidioFrame()){
							
							dg.enviarFrame(fs.toArray());
							dg.setPidioFrame(false);
						}
						
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				
				}
				actConU++;
			}
			conMan.setFrameReallySended(conMan.getFrameReallySended() + 1);
			conMan.finishSend();
		}
		
	}

	
}
