package uy.edu.fing.redes2017.grupo12;


//import java.util.ArrayList;
//import java.util.List;
import java.util.concurrent.TimeUnit;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfInt;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;


public class ProcessStreaming  extends Thread{

	private ConexionManager server;	
	private String origen = "";

	public ProcessStreaming(ConexionManager cm, String origen){
		this.server = cm;
		this.origen = origen;
	}
	
	@Override
	public void run() {
			
		//List<ConexionTCP> conexionesABorrar = new ArrayList<>();
		
		VideoCapture videoCapture ;
		if(origen.equals("0"))
			videoCapture = new VideoCapture(0);
		else
			videoCapture = new VideoCapture(origen);
		
		final Mat mat = new Mat();
		MatOfInt params = new MatOfInt(Highgui.CV_IMWRITE_JPEG_QUALITY, 80);
		MatOfByte bytemat = new MatOfByte();

		while (videoCapture.read(mat)){
			
			if(!origen.equals("0"))
			try {
				
				long milisADetener = (long) (videoCapture.get(5));
				TimeUnit.MILLISECONDS.sleep(milisADetener);
			
			} catch (InterruptedException e) {
				e.printStackTrace();
			};
			
			Highgui.imencode(".jpg", mat, bytemat,params);
			server.setFrameToSend(bytemat);
			server.setNumeroFrameLoad(server.getNumeroFrameLoad() + 1);
			server.setHayEnvio(true);
		  
		}
	}
}
