package parcial_infraestructura;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Files {
    public static void sendFile(String filename, Socket socket) throws Exception{
        System.out.println(">> Enviando Archivo : " + filename );
        File localFile = new File(filename);
        BufferedInputStream fromFile = new BufferedInputStream(new FileInputStream(localFile));

        long size = localFile.length();
        System.out.println("Tamaño del Archivo" + size);

        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        printWriter.println(filename);

        printWriter.println("Tamaño archivo: "+ size);

        BufferedOutputStream toNetwork = new BufferedOutputStream(socket.getOutputStream());

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