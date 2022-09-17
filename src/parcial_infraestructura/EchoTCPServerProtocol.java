package parcial_infraestructura;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class EchoTCPServerProtocol {
	//private static PrintWriter toNetwork;
	private static OutputStream toNetwork;
	private static BufferedReader fromNetwork;

	public static void protocol (Socket socket) throws Exception{

		toNetwork = socket.getOutputStream();
		fromNetwork = new BufferedReader(new InputStreamReader(socket.getInputStream()));

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

//-----------------------------------------------------------------------------------------------------//

		File localFile = new File(archivo_direccion);
		BufferedInputStream fromFile = new BufferedInputStream(new FileInputStream(localFile));

		long size = localFile.length();

		PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
		printWriter.println(archivo_direccion);

		String fecha = generar_fecha();
		String fecha_archivo_mod = archivo_fecha_mod(archivo_direccion);


		byte[] archivo_bytes = fromFile.readAllBytes();
		toNetwork.write(archivo_bytes);

//		byte[] blockToSend = new byte[512];
//		int in;
//		while ((in= fromFile.read(blockToSend)) != -1 )
//		{
//			toNetwork.write(blockToSend , 0, in);
//		}

		toNetwork.write("HTTP/1.1 404 Not Found\r\n".getBytes());
		toNetwork.write("\r\n".getBytes());
		toNetwork.write("<h3> PRUEBA </h3>".getBytes());
		toNetwork.write("\r\n\r\n".getBytes());

//		toNetwork.write("HTTP/1.1 200 OK\r\n".getBytes());
//		toNetwork.write("<h1>PAGINA FUNCIONANDO</h1>".getBytes());
//		toNetwork.write("Server: Titi HTTP Server\r\n".getBytes());
//		toNetwork.write(("Date: " + fecha + "\r\n").getBytes());
//		toNetwork.write(("Last-Modified: " + fecha_archivo_mod + " \r\n").getBytes());
//		toNetwork.write("Contetn-type: image/jpg\r\n".getBytes());
//		toNetwork.write("Content-Length: 11017\r\n".getBytes());
//		toNetwork.write("\r\n\r\n".getBytes());


		toNetwork.flush();
		toNetwork.close();
		fromFile.close();

	}

	private static String generar_fecha() {
		final Date tiempo_actual = new Date();
		final SimpleDateFormat formato_fecha = new SimpleDateFormat("EEE d MMM yyyy hh:mm:ss z");
		formato_fecha.setTimeZone(TimeZone.getTimeZone("GMT"));
		return (formato_fecha.format(tiempo_actual));
	}

	private static String archivo_fecha_mod(String filename) {
		File file = new File(filename);
		final Date fecha_modificacion = new Date(file.lastModified());
		final SimpleDateFormat formato_fecha = new SimpleDateFormat("EEE d MMM yyyy hh:mm:ss z");
		formato_fecha.setTimeZone(TimeZone.getTimeZone("GMT"));
		return (formato_fecha.format(fecha_modificacion));
	}
}