package uy.edu.fing.redes2017.grupo12;


import java.util.concurrent.TimeUnit;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfInt;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;


public class ProcessStreaming  extends Thread{

	private String origen = "";
	
	private volatile Boolean fin=false;
	

	private volatile MatOfByte frameToSend=null;
	
	private volatile boolean hayEnvio=false;
	private ConexionManager cm;
	VideoCapture videoCapture ;
	
	public ProcessStreaming(ConexionManager cm, String origen){
		this.cm=cm;
		this.origen = origen;
		
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
		videoCapture.release();
		System.out.println("FIN: processStreaming");
		
	}
	
	@Override
	public void run() {
		
		while (!fin){
			
		
			
			if(origen.equals("0"))
				videoCapture = new VideoCapture(0);
			else
				videoCapture = new VideoCapture(origen);
			
			final Mat mat = new Mat();
			MatOfInt params = new MatOfInt(Highgui.IMWRITE_JPEG_QUALITY, 80);
			MatOfByte bytemat = new MatOfByte();
	
			while ( !fin && videoCapture.read(mat)){
				if(!origen.equals("0"))
				{
						try {
							
							long fps = (long) (videoCapture.get(5));
							
							Long tini=System.currentTimeMillis();
							Highgui.imencode(".jpg", mat, bytemat,params);
							Long tfin=System.currentTimeMillis();
							Long tiempo_espera=(1000/fps)-(tfin-tini);
							
							if(tiempo_espera>0)
							TimeUnit.MILLISECONDS.sleep(tiempo_espera);
							
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
				}
				else
				{
					Highgui.imencode(".jpg", mat, bytemat,params);
				}
				cm.setNumeroFrameLoad(cm.getNumeroFrameLoad()+1);
	
				if(!hayEnvio)
					setFrameToSend(bytemat);
				
				hayEnvio=true;
				
			}
			
		}
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	
	public boolean isHayEnvio() {
		return hayEnvio;
	}

	public void setHayEnvio(boolean hayEnvio) {
		this.hayEnvio = hayEnvio;
	}

	public MatOfByte getFrameToSend() {
		
		return frameToSend;
	}

	public void setFrameToSend(MatOfByte frameToSend) {
		this.frameToSend = frameToSend;
	}
	
}
