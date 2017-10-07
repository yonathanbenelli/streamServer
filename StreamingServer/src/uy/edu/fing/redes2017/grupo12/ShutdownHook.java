package uy.edu.fing.redes2017.grupo12;

public class ShutdownHook {
	static ConexionManager cm;

	static ProcessStreaming pf;
	static SenderFrame sf;
	static StatadisitcThread sT;
	
public ShutdownHook(ConexionManager cm, ProcessStreaming pf,
			SenderFrame sf, StatadisitcThread sT) {
	this.cm=cm;
	this.sf=sf;
	this.pf=pf;
	this.sT=sT;
		// TODO Auto-generated constructor stub
	}

public void attachShutDownHook() {
	
	
	Runtime.getRuntime().addShutdownHook(new Thread() {
        @Override
        public void run() {
        sT.fin();
        sf.fin();
        pf.fin();
        cm.fin();
        
            System.out.println("exit ......");
        }
    });

}



}