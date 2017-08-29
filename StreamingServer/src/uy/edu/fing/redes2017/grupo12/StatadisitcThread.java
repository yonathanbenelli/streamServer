package uy.edu.fing.redes2017.grupo12;

public class StatadisitcThread extends Thread {

	ConexionManager conMan;
	
	public StatadisitcThread(ConexionManager cm)
	{
		conMan=cm;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true)
		{
			try {
				Thread.sleep(1000);
				System.out.println("Conexiones activas:"+conMan.numCon);
				System.out.println("Frames Leidos:"+conMan.numeroFrameLoad +" Frames enviados:" +conMan.frameReallySended);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	
	}


	
}
