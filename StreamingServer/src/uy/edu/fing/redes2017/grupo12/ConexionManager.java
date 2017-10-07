package uy.edu.fing.redes2017.grupo12;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class ConexionManager {

	private ManagerUDP mudp;
	private ManagerTCP mtcp;
	private KillUDP ku;
	private List<ConexionTCP> lCAB;
	private  long frameReallySended=0L;
	private  long numeroFrameLoad=0L;
	
	public ConexionManager(int puertoTCP, int puertoUDP) throws IOException {
			
		mtcp = new ManagerTCP(new ServerSocket(puertoTCP));
		mtcp.start();
		mudp = new ManagerUDP(new DatagramSocket(puertoUDP));
		mudp.start();
		ku = new KillUDP(mudp);
		ku.start();	
		
	}
	
	public void fin()
	{
		
		mtcp.fin();
		mudp.fin();
		ku.fin();
		System.out.println("FIN: conexionManager");
	}
	public void quitarConexion(ConexionTCP conexionTCP) {
		if(lCAB==null)
			lCAB= new ArrayList<ConexionTCP>();
		lCAB.add(conexionTCP);
	}
	
	public Integer cantConexionesUDP() {
		// TODO Auto-generated method stub
		if(this.mudp.getDataPackets()!=null)
			return this.mudp.getDataPackets().size();
		return 0;
	}
	
	public ClienteUDP obtenerConexioneUDP(Integer actCon) {
		// TODO Auto-generated method stub
		if(this.mudp.getDataPackets()!=null && this.mudp.getDataPackets().size()>actCon)
			return this.mudp.getDataPackets().get(actCon);
		return null;
	}
	
	public ConexionTCP obtenerConexioneTCP(Integer actCon) {
		// TODO Auto-generated method stub
		if(this.mtcp.getConexionesTCP()!=null && this.mtcp.getConexionesTCP().size()>actCon)
			return this.mtcp.getConexionesTCP().get(actCon);
		return null;
	}
	
	public Integer cantConexionesTCP() {
		// TODO Auto-generated method stub
		if(this.mtcp.getConexionesTCP()!=null)
			return this.mtcp.getConexionesTCP().size();
		return 0;
	}
	
	public void finishSend() {
		// TODO Auto-generated method stub
		if(lCAB!=null && lCAB.size()>0)
			for (ConexionTCP c : lCAB) {
				
				mtcp.getConexionesTCP().remove(c);
				c.cerrar();
			}
		
		lCAB= new ArrayList<ConexionTCP>();
	}
	
	public long getFrameReallySended() {
		return frameReallySended;
	}
	
	public void setFrameReallySended(long frameReallySended) {
		this.frameReallySended = frameReallySended;
	}
	
	public long getNumeroFrameLoad() {
		return numeroFrameLoad;
	}
	
	public void setNumeroFrameLoad(long numeroFrameLoad) {
		this.numeroFrameLoad = numeroFrameLoad;
	}
	
}
