package parcial_infraestructura;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Files {
    public static void sendFile(String filename, Socket socket) throws Exception{
        System.out.println(">> Enviando Archivo : " + filename );
        File localFile = new File(filename);
        BufferedInputStream fromFile = new BufferedInputStream(new FileInputStream(localFile));

        System.out.println(fromFile + "  Este es el fromfile");

        long size = localFile.length();
        System.out.println("Tamaño del Archivo: " + size);

        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        printWriter.println(filename);

        printWriter.println("Tamaño archivo: "+ size);

        BufferedOutputStream toNetwork = new BufferedOutputStream(socket.getOutputStream());

        System.out.println("Hasta aca");
        System.out.println(toNetwork + " Este es el toNetwork");

        pause(50);

        byte[] blockToSend = new byte[512];
        int in;
        while ((in= fromFile.read(blockToSend)) != -1 )
        {
            System.out.println(blockToSend + " bloq");
            System.out.println(in + " in");
            //toNetwork.write(blockToSend , 0, in);
        }

        final Date currentTime = new Date();
        final SimpleDateFormat sdf = new SimpleDateFormat("EEE d MMM yyyy hh:mm:ss z");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        System.out.println("GMT time: " + sdf.format(currentTime));

        //actualizar_Fecha_mod(sdf.format(currentTime));

        String fecha_modificacion = extraer_fecha_mod();

        /**

         HTTP/1.1 200 Ok\r\n
         Server: Carlos HTTP Server\r\n
         Date: mié. 07 sept. 2022 20:31:55 GMT\r\n
         Last-Modified: jue. 01 sept. 2022 12:25:40 GMT\r\n
         Content-type: image/jpeg\r\n
         Content-length: 11017\r\n
         \r\n

         **/

        String mensaje = ("HTTP/1.1 200 Ok\\r\\n\n");
        mensaje += ("Server: Titi HTTP Server\\r\\n\\n");
        mensaje += ("Date: "+(sdf.format(currentTime) + "\\r\\n\n"));
        mensaje += ("Last-Modified: " + fecha_modificacion + "\\r\\n\n");






        //toNetwork.flush();
        fromFile.close();

        pause(50);
        System.out.println("Archivo enviado :)");
    }

    private static String extraer_fecha_mod() {
        String fecha_extraida = null;
        try {

            File file = new File("ultima_modificacion.txt");

            BufferedReader obj = new BufferedReader(new FileReader(file));

            fecha_extraida = obj.readLine();
            System.out.println("Fecha extraida: " + fecha_extraida);
            obj.close();
        } catch (IOException m) {
            m.printStackTrace();
        }

        return fecha_extraida;
    }

    private static void actualizar_Fecha_mod(String fecha) {
        File log = new File("ultima_modificacion.txt");
        try {
            FileWriter ingresar_modificacion = new FileWriter(log, false);
            ingresar_modificacion.write(fecha);
            ingresar_modificacion.close();
            System.out.println("Servidor Actualizado :)");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void pause(int milisecond){
        try{
            Thread.sleep(milisecond);
        }catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }


    public static String receiveFile(String folder, Socket socket) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        BufferedInputStream fromNetwork = new BufferedInputStream(socket.getInputStream());

        String filename = reader.readLine();
        filename = folder + File.separator + filename;

        BufferedOutputStream toFile = new BufferedOutputStream(new FileOutputStream(filename));

        System.out.println(">> Recibiendo Archivo: "+ filename);

        String sizeString = reader.readLine();

        long size = Long.parseLong(sizeString.split(": ")[1]);

        System.out.println("Tamaño del archivo: "+ size);

        byte[] blockToReceive= new byte[512];
        int in;
        long remainder = size;
        while((in = fromNetwork.read(blockToReceive)) != -1) {
            toFile.write(blockToReceive, 0, in);
            remainder -= in;
            if(remainder == 0){
                break;
            }
        }

        pause(50);
        toFile.flush();
        toFile.close();
        System.out.println("Archivo Recibido: ");

        return filename;
    }
}