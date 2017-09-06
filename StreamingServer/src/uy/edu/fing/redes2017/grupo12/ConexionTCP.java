package uy.edu.fing.redes2017.grupo12;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.crypto.KeySelector.Purpose;
;


public class ConexionTCP   {

	Socket socketConexion;
	DataOutputStream salidaAlCliente;
	int puertoRemoto;

	public ConexionTCP(Socket s) throws IOException{
		
		this.socketConexion=s;
		salidaAlCliente = new DataOutputStream(this.socketConexion.getOutputStream());
		puertoRemoto=this.socketConexion.getPort();
	
	}
	
	public Boolean esActiva(){	
		return this.socketConexion!=null && !this.socketConexion.isClosed();
	}

	public void cerrar(){
		
		if(this.socketConexion!=null && this.esActiva())
			try {
				this.socketConexion.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		System.out.println("Cierre de conexion: puerto remoto:"+puertoRemoto);
	
	}
	
	public Boolean enviarFrame(byte[] bytes) throws IOException{
	
		if(this.esActiva())
		{
			
//			salidaAlCliente = new DataOutputStream(this.socketConexion.getOutputStream());

			salidaAlCliente.writeInt(bytes.length); // write length of the message
			salidaAlCliente.write(bytes);
			//salidaAlCliente.flush();
			return true;
		}
		return false;
	
	}

}
