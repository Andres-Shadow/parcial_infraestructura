package copias;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class EchoTCPServerProtocol {
	//private static PrintWriter toNetwork;
	private static OutputStream toNetwork;
	private static BufferedReader fromNetwork;

	public static void protocol (Socket socket) throws Exception{

		fromNetwork = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		toNetwork = socket.getOutputStream();
		String linea="";
		String archivo_nombre = " ";

		do{
			linea = fromNetwork.readLine();
			if(archivo_nombre.equalsIgnoreCase(" ")){
				archivo_nombre = linea;
			}
			System.out.println(linea);
		}while(!linea.equals(""));

		String[] list = archivo_nombre.split(" ");
		String archivo =  list[1];
		archivo = archivo.replace("/", "\\");
		String archivo_direccion = "root" + archivo;

		Files.sendFile(archivo_direccion, socket);

	}
}