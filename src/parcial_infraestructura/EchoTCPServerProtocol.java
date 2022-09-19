package parcial_infraestructura;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.nio.file.Files;


public class EchoTCPServerProtocol {

    private static OutputStream toNetwork;
    private static BufferedReader fromNetwork;

    public static void protocol(Socket socket) throws Exception {

        createStreams(socket);

        String linea = "";
        String archivo_nombre = " ";

        do {
            linea = fromNetwork.readLine();
            if (archivo_nombre.equalsIgnoreCase(" ")) {
                archivo_nombre = linea;
            }
            System.out.println(linea);
        } while (!linea.equals(""));

        String[] list = archivo_nombre.split(" ");
        String archivo = list[1];
        archivo = archivo.replace("/", "\\");
        String archivo_direccion = "root" + archivo;

		System.out.println("----------------> " + archivo_direccion);

//-----------------------------------------------------------------------------------------------------//

        File localFile = new File(archivo_direccion);


        if (archivo_direccion.equalsIgnoreCase("root\\")) {

            //ARCHIVO NO ESPECIFICADO

			System.out.println("START - CONNECT :)");

            File foundFile = new File("root/conected.html");

            String servidor = "Titi HTTP Server";
            String fecha = generar_fecha();
            String fecha_archivo_mod = archivo_fecha_mod(archivo_direccion);
            String contentType = Files.probeContentType(foundFile.toPath());
            long size = foundFile.length();

            String encabezados = "HTTP/1.1 200 Ok\r\n" +
                    "Server: " + servidor + "\r\n" +
                    "Date: " + fecha + "\r\n" +
                    "Last-Modified: " + fecha_archivo_mod + "\r\n" +
                    "Contetn-type: " + contentType + "\r\n" +
                    "Content-length: " + size + "\r\n"
                    + "\r\n";

            sendFile(foundFile, encabezados);

			System.out.println("END - CONNECT :)");

        } else if (localFile.exists()) {

            //ARCHIVO EXISTENTE

			File foundFile = new File("root/found.html");

			String servidor = "Titi HTTP Server";
			String fecha = generar_fecha();
			String fecha_archivo_mod = archivo_fecha_mod(archivo_direccion);
			String contentType = Files.probeContentType(foundFile.toPath());
			long size = foundFile.length();

			String encabezados = "HTTP/1.1 200 Ok\r\n" +
					"Server: " + servidor + "\r\n" +
					"Date: " + fecha + "\r\n" +
					"Last-Modified: " + fecha_archivo_mod + "\r\n" +
					"Contetn-type: " + contentType + "\r\n" +
					"Content-length: " + size + "\r\n"
					+ "\r\n";

			sendFile(foundFile, encabezados);

			pause(50);



			//-------------------------------------------------------------------------------------

			System.out.println("START - FILE FOUND");

             servidor = "Titi HTTP Server";
             fecha = generar_fecha();
             fecha_archivo_mod = archivo_fecha_mod(archivo_direccion);
             contentType = Files.probeContentType(localFile.toPath());
             size = localFile.length();

             encabezados = "HTTP/1.1 200 Ok\r\n" +
                    "Server: " + servidor + "\r\n" +
                    "Date: " + fecha + "\r\n" +
                    "Last-Modified: " + fecha_archivo_mod + "\r\n" +
                    "Contetn-type: " + contentType + "\r\n" +
                    "Content-length: " + size + "\r\n"
                    + "\r\n";

            sendFile(localFile, encabezados);

			System.out.println("END - FILE FOUND");

        } else {

			//ARCHIVO NO ENCONTRADO

            System.out.println("START - NOT FOUND :(");

            File notFile = new File("root/notfound.html");

            String servidor = "Titi HTTP Server";
            String fecha = generar_fecha();
            String contentType = Files.probeContentType(notFile.toPath());
            long size = notFile.length();

            String encabezados = "HTTP/1.1 404 Not Found\r\n" +
                    "Server: " + servidor + "\r\n" +
                    "Date: " + fecha + "\r\n" +
                    "Content-type: " + contentType + "\r\n" +
                    "Content-Length: " + size + "\r\n"
                    + "\r\n\r\n";

            sendFile(notFile, encabezados);

            System.out.println("END - NOT FOUND ENVIADO :)");
        }
    }


    private static void createStreams(Socket socket) throws IOException {
        toNetwork = socket.getOutputStream();
        fromNetwork = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public static void sendFile(File file, String mensaje) {
        try {

            toNetwork.write(mensaje.getBytes());

            BufferedInputStream buffer_file = new BufferedInputStream(new FileInputStream(file));

            byte[] blockToSend = new byte[512];
            int in;
            while ((in = buffer_file.read(blockToSend)) != -1) {
                toNetwork.write(blockToSend, 0, in);
            }

            toNetwork.write("\r\n\r\n".getBytes());
            toNetwork.flush();
            toNetwork.close();

        } catch (Exception e) {
            System.out.println();
        }
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

	public static void pause(int milisecond){
		try{
			Thread.sleep(milisecond);
		}catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}