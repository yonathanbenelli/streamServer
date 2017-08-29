package uy.edu.fing.redes2017.grupo12;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfInt;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;



public class ProcessStreaming  extends Thread{

	ConexionManager server;	
	String origen="";

	public ProcessStreaming(ConexionManager cm, String origen)
	{
		this.server=cm;
		this.origen=origen;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
			
			List<ConexionTCP> conexionesABorrar= new ArrayList<>();
		
			VideoCapture videoCapture ;
			if(origen.equals("0"))
			  videoCapture = new VideoCapture(0);
			else
			{
				 videoCapture = new VideoCapture();
				 videoCapture.open(origen);
			}
			// videoCapture.open("test.mp4");
		
		
		
		final Mat mat=new Mat();
		int frames=0;
		final long startTime=System.currentTimeMillis();
		
		MatOfInt params=new MatOfInt(Highgui.CV_IMWRITE_JPEG_QUALITY, 80);
		MatOfByte bytemat = new MatOfByte();
		Boolean b=false;
		while (videoCapture.read(mat)) {
			
		  Highgui.imencode(".jpg", mat, bytemat,params);
		  server.frameToSend=bytemat;
		  server.numeroFrameLoad++;
		  server.hayEnvio=true;
		  
		}
	}
}
