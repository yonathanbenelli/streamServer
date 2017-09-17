package uy.edu.fing.redes2017.grupo12;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KillUDP extends Thread {

	private volatile List<ClienteUDP> dataPackets;
	private ManagerUDP mudp;
	 
	public KillUDP(ManagerUDP mudp){
		this.mudp = mudp;
	}
	
	@Override
	public void run(){
		
		while(true){
			
			dataPackets = mudp.getDataPackets();
			List<ClienteUDP> dPB = new ArrayList<ClienteUDP>() ;
			long timeact = (new Date()).getTime();
			if(dataPackets != null && dataPackets.size() > 0){
				
				for (ClienteUDP clienteUDP : dataPackets){
				
					if(clienteUDP != null)
						if(timeact-clienteUDP.getMiliscon() > 90000) 
							dPB.add(clienteUDP);
			
				}
				
				for (ClienteUDP clienteUDP : dPB) {
					dataPackets.remove(clienteUDP);
				}
			
			}
				
		}
			
	}
	
}
