package parcial_infraestructura;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class EchoTCPServerProtocol {
	
	private static PrintWriter toNetwork;
	private static BufferedReader fromNetwork;
	
	public static void protocol (Socket socket) throws Exception{
		
		//createStreams(socket);
		fromNetwork = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		toNetwork = new PrintWriter(socket.getOutputStream(), true);

		
		String message = "";

		ArrayList<String> renglones =  new ArrayList<>();


		String renglon="";
		String nombre = " ";
		do{
			renglon = fromNetwork.readLine();
			if(nombre.equalsIgnoreCase(" ")){
				nombre = renglon;
				System.out.println("Nombre lleno");
				System.out.println(nombre + "Este el el nombre, perro");
			}
			System.out.println(renglon);
			renglones.add(renglon);

		}while(!renglon.equals(""));

		toNetwork.write("HTTP/1.1 200 OK\r\n");
		toNetwork.write("\r\n");
		System.out.println("la primera linea es: "+nombre);
		String[] list = nombre.split(" ");
		String archivo =  list[1];


		archivo = archivo.replace("/", "\\");

		String finalArchivo = "root"+archivo;


		System.out.println("el nombre del archivo es: "+finalArchivo);


		Files.sendFile(finalArchivo, socket);


		System.out.println(message);

	}
}
