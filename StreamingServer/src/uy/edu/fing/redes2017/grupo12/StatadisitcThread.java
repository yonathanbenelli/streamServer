package uy.edu.fing.redes2017.grupo12;

public class StatadisitcThread extends Thread {

	ConexionManager conMan;
	
	public StatadisitcThread(ConexionManager cm){
		conMan=cm;
	}
	
	@Override
	public void run() {
		
		while (true){
			
			try {
			
				Thread.sleep(1000);
				System.out.println("Conexiones TCP activas: " + conMan.cantConexionesTCP()+" Clientes UDP activos: "+conMan.cantConexionesUDP());
				System.out.println("Frames Leidos: " + conMan.getNumeroFrameLoad() + " Frames enviados: " + conMan.getFrameReallySended());
			
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	
	}
	
}
