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

		System.out.println("la primera linea es: "+nombre);
		String[] list = nombre.split(" ");
		String archivo =  list[1];


		archivo = archivo.replace("/", "\\");

		String finalArchivo = "root"+archivo;


		System.out.println("el nombre del archivo es: "+finalArchivo);


		Files.sendFile(finalArchivo, socket);


		/**

		HTTP/1.1 200 Ok\r\n
		Server: Carlos HTTP Server\r\n
		Date: mié. 07 sept. 2022 20:31:55 GMT\r\n
		Last-Modified: jue. 01 sept. 2022 12:25:40 GMT\r\n
		Content-type: image/jpeg\r\n
		Content-length: 11017\r\n
		\r\n

		 **/



		System.out.println(message);


	}
}