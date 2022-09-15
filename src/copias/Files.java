package copias;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Files {
    public static void sendFile(String filename, Socket socket) throws Exception{

        File localFile = new File(filename);
        BufferedInputStream fromFile = new BufferedInputStream(new FileInputStream(localFile));

        long size = localFile.length();

        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        printWriter.println(filename);

        OutputStream toNetwork = socket.getOutputStream();

        //BufferedOutputStream toNetwork = new BufferedOutputStream(socket.getOutputStream());


        pause(50);

        String fecha = generar_fecha();
        String fecha_archivo_mod = archivo_fecha_mod(filename);

        String respuesta = ("HTTP/1.1 200 Ok\r\n" +
                "Server: Titi HTTP Server\r\n" +
                "Date: " + fecha + "\r\n"+
                "Last-Modified: " + fecha_archivo_mod + " \r\n" +
                "Contetn-type: image/jpg\r\n" +
                "Content-Length: " + size + "\r\n" +
                "\r\n");

        String prueba = ("HTTP/1.1 200 Ok\r\n" +
                "Server: Titi HTTP Server\r\n" +
                "Date: mié. 07 sept. 2022 20:31:55 GMT\r\n"+
                "Last-Modified: jue. 01 sept. 2022 12:25:40 GMT\r\n" +
                "Contetn-type: image/jpg\r\n" +
                "Content-Length: 11017\r\n");

        toNetwork.write(respuesta.getBytes());

        toNetwork.write("<h1>PAGINA FUNCIONANDO</h1>".getBytes());



        byte[] blockToSend = new byte[512];
        int in;
        while ((in= fromFile.read(blockToSend)) != -1 )
        {
            toNetwork.write(blockToSend , 0, in);
        }


        toNetwork.flush();
        fromFile.close();

        pause(50);
        System.out.println("Archivo enviado :)");
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