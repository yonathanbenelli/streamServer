package uy.edu.fing.redes2017.grupo12;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class ConexionTCP   {

	private Socket socketConexion;
	private DataOutputStream salidaAlCliente;
	private int puertoRemoto;

	public ConexionTCP(Socket s) throws IOException{
		
		this.socketConexion = s;
		this.socketConexion. setSoLinger(true,1);
		salidaAlCliente = new DataOutputStream(this.socketConexion.getOutputStream());
		puertoRemoto = this.socketConexion.getPort();
	
	}
	
	public Boolean esActiva(){	
		return this.socketConexion != null && !this.socketConexion.isClosed() ;
	}

	public void cerrar(){
		
		if(this.socketConexion != null && this.esActiva()){
			
			try {
				
				this.socketConexion.close();
			} catch (IOException e){
				e.printStackTrace();
			};
		
		}
		System.out.println("Cierre de conexion: puerto remoto:" + puertoRemoto);
	
	}
	
	public Boolean enviarFrame(byte[] bytes) {
	
		if(this.esActiva()){
			
			try {
				salidaAlCliente.writeInt(bytes.length);
				
				salidaAlCliente.write(bytes);
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			} // write length of the message
			
			
			
		}
		return false;
	
	}

	public int getPuertoRemoto() {
		return puertoRemoto;
	}

	public void setPuertoRemoto(int puertoRemoto) {
		this.puertoRemoto = puertoRemoto;
	}

}
