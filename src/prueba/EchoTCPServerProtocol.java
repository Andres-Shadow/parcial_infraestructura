package prueba;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;

public class EchoTCPServerProtocol {
	private static OutputStream toNetwork;
	private static BufferedReader fromNetwork;
	
	public static void protocol(Socket socket) throws IOException {
		createStreams(socket);
		
		String message="";
		
		while(true) {
			String linea = fromNetwork.readLine();

//			System.out.println("CORRIENDO EL WHILE");

			try{
				if(linea.equals(null) || linea.equals("")){
					break;
				}
				else{
					message += linea + "\n";
				}
			}catch(NullPointerException e){
				e.printStackTrace();
				System.out.println("Vacio mor");
				break;
			}
			
		}

		System.out.println("[Server] From client:  \n"+message);

		
		String archivo = message.split(" ")[1];
		System.out.println("ARCHIVO:"+archivo);
		
		String rutaServidorArchivo = "archivos" + message.split(" ")[1];
		System.out.println("ARCHIVO ruta servidor:"+rutaServidorArchivo);
		System.out.println();
		
		String test = "MENSAJE DE RESPUESTA\r\n";

//		System.out.println(""+socket.toString());


//		toNetwork.write("HTTP/1.1 200 OK\r\n".getBytes());
//		toNetwork.write("\r\n".getBytes());
//		toNetwork.write("<h1>Chimbita mor</h1>".getBytes());
//		toNetwork.write("\r\n\r\n".getBytes());
//		toNetwork.flush();
//		System.out.println("CONECCION DEL CLIENTE CERRADA");
//		toNetwork.close();
		
		rutaServidorArchivo = "archivos/pagina.html";
//		rutaServidorArchivo = "archivos/";
		File archivoBuscar = new File(rutaServidorArchivo);
		

//		if(rutaServidorArchivo.equals("archivos/") || rutaServidorArchivo == "archivos/"){
		if(true){
			System.out.println("NO SE PIDE QUE ARCHIVO BUSCAR");

			String mensaje = "HTTP/1.1 200 OK\r\n"+
					"Server: Carlos HTTP Server\r\n"+
					"Date: mi�. 07 sept. 2022 20:31:55 GMT\r\n"+
					"Last-Modified: "+archivoBuscar.lastModified() + "\r\n"+
//					"Content-type: image/jpeg\r\n"+
//					"Content-length: 11017\r\n"+
					"\r\n";

			toNetwork.write(mensaje.getBytes());

//			toNetwork.write("HTTP/1.1 200 OK\r\n".getBytes());
//			toNetwork.write("Server: \r\n".getBytes());
//			toNetwork.write("\r\n".getBytes());

//			archivoBuscar.getBytes;



//			toNetwork.write("<h1>Chimbita mor</h1>".getBytes());

//			byte[] contenido = Files.readAllBytes(archivoBuscar.toPath());
//			toNetwork.write(contenido);

			
			BufferedInputStream fromFile = new BufferedInputStream(new FileInputStream(archivoBuscar));
		    
		    byte[] blockToSend = new byte[512];
		    int in;
		    while ((in= fromFile.read(blockToSend)) != -1 )
		    {
		        toNetwork.write(blockToSend , 0, in);
		    }


//			BufferedInputStream fromFile = new BufferedInputStream(new FileInputStream(archivoBuscar));
//			byte[] arr = new byte[(int)archivoBuscar.length()];
//			fromFile.read(arr);
//			toNetwork.write(arr);
//			fromFile.close();


//
//			long size = archivoBuscar.length();
//		    System.out.println("size : " + size);
//
////		    pause(50);
//
//		    byte[] blockToSend = new byte[512];
//		    int in;
//		    while ((in= fromFile.read(blockToSend)) != -1 )
//		    {
//		        toNetwork.write(blockToSend , 0, in);
//		    }
			
			toNetwork.write("\r\n\r\n".getBytes());
			toNetwork.flush();
			System.out.println("CONECCION DEL CLIENTE CERRADA");
			toNetwork.close();


		}
		else if (archivoBuscar.exists()){
			System.out.println("EL  ARCHIVO EXISTE");




		}
		else{
			System.out.println("EL ARCHIVO NO EXISTE");
			
			toNetwork.write("HTTP/1.1 404 Not Found\r\n".getBytes());
			toNetwork.write("\r\n".getBytes());
			toNetwork.write("<h1> PRUEBA </h1>".getBytes());
			toNetwork.write("\r\n\r\n".getBytes());
			toNetwork.flush();
			System.out.println("CONECCION DEL CLIENTE CERRADA");
			toNetwork.close();
			
		}


//		socket.getOutputStream().write(test.getBytes());
//		socket.getOutputStream().write("\r\n".getBytes());
//		socket.getOutputStream().flush();
//		socket.getOutputStream().close();



//		rutaServidorArchivo = "archivos/NotFound.html";
//
//		try {
//			Files.sendFile(rutaServidorArchivo, socket);
//		} catch (Exception e) {e.printStackTrace();}


//		toNetwork.println(test);
//		toNetwork.write(test);
//
//		toNetwork.println("MENSAJE DE RESPUESTA\\r\\n");
//		toNetwork.write("MEASNJNSJ NASD RESPUTSSA");
//		toNetwork.println("adasdasd");



//		if(Files.receiveFile(archivo, socket)){
//			System.out.println("EL ARCHIVO EXISTEEEEEEE!!!!!!");
//		}
//		else{
//			System.out.println("EL ARCHINO NO NO NO NO EXITE :C");
//		}






//		String message="";
//		for(int i = 0; i<20; i++) {
//			message += fromNetwork.readLine() + "\n";
//		}

//		System.out.println("[Server] From client:  \n"+message);

//		String message = fromNetwork.readLine();
//		System.out.println("[Server] From client: "+ message);
//
//		String answer = message;

//		toNetwork.println(answer);

//		message = fromNetwork.readLine();
//		System.out.println("[Server] From client: "+ message);
//		message = fromNetwork.readLine();
//		System.out.println("[Server] From client: "+ message);
//		message = fromNetwork.readLine();
//		System.out.println("[Server] From client: "+ message);
		
	}
	
	public static void createStreams(Socket socket) throws IOException {
		toNetwork = socket.getOutputStream();
		fromNetwork = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
}
