package prueba;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class pruebita {

    public static void main(String[] args) {
        /**

         HTTP/1.1 200 Ok\r\n
         Server: Carlos HTTP Server\r\n
         Date: mié. 07 sept. 2022 20:31:55 GMT\r\n
         Last-Modified: jue. 01 sept. 2022 12:25:40 GMT\r\n
         Content-type: image/jpeg\r\n
         Content-length: 11017\r\n
         \r\n
         **/

        final Date currentTime = new Date();
        final SimpleDateFormat sdf = new SimpleDateFormat("EEE d MMM yyyy hh:mm:ss z");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        System.out.println("Date: " + sdf.format(currentTime) + "\\r\\n\n");


        File log = new File("ultima_modificacion.txt");
        String fecha = sdf.format(currentTime);

        try {
            FileWriter ingresar_modificacion = new FileWriter(log, false);
            ingresar_modificacion.write(fecha);
            ingresar_modificacion.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        try {

            File file = new File("ultima_modificacion.txt");

            BufferedReader obj = new BufferedReader(new FileReader(file));

            String fecha_extraida = obj.readLine();
            System.out.println("Fecha extraida: " + fecha_extraida);
            obj.close();
        } catch (IOException m) {
            m.printStackTrace();
        }


        /**

         System.out.println("Prueba mor!");

         System.out.println("\\n\\r");

         Date date = new Date();
         Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
         date = calendar.getTime();

         System.out.println(date + "\n\n");


         final Date currentTime = new Date();
         final SimpleDateFormat sdf = new SimpleDateFormat("EEE d MMM yyyy hh:mm:ss z");
         sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
         System.out.println("Date: " + sdf.format(currentTime) + "\\r\\n\n");
         **/
    }

}

