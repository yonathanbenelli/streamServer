package uy.edu.fing.redes2017.grupo12;

public class StatadisitcThread extends Thread {

	ConexionManager conMan;
	
	private volatile Boolean fin=false;
	public StatadisitcThread(ConexionManager cm){
		conMan=cm;
		
	}
	
	@Override
	public void run() {
		
		while (!fin){
			
			try {
			
				Thread.sleep(1000);
				System.out.println("Conexiones TCP activas: " + conMan.cantConexionesTCP()+" Clientes UDP activos: "+conMan.cantConexionesUDP());
				System.out.println("Frames Leidos: " + conMan.getNumeroFrameLoad() + " Frames enviados: " + conMan.getFrameReallySended());
			
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
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
		System.out.println("FIN: staticThread");
	}
	
}
