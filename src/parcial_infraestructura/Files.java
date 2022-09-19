package parcial_infraestructura;

import java.nio.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Files {

    public static void pause(int milisecond){
        try{
            Thread.sleep(milisecond);
        }catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}