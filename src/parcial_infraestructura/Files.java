package parcial_infraestructura;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Files {
    public static void sendFile(String archivo_direccion, Socket socket) throws Exception{

        File localFile = new File(archivo_direccion);
        BufferedInputStream fromFile = new BufferedInputStream(new FileInputStream(localFile));

        long size = localFile.length();

        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        printWriter.println(archivo_direccion);

        OutputStream toNetwork = socket.getOutputStream();

        //BufferedOutputStream toNetwork = new BufferedOutputStream(socket.getOutputStream());


        pause(50);



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