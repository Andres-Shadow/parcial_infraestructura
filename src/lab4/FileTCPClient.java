package lab4;

import java.net.Socket;

public class FileTCPClient {
    public static final int PORT = 4000;
    public static final String SERVER = "localhost";

    private Socket clientSideSocket;

    public FileTCPClient() {
        System.out.println("File TCP Client ...");
    }

    public void init() throws Exception {
        clientSideSocket = new Socket(SERVER, PORT);

        protocol(clientSideSocket);
        clientSideSocket.close();
    }

    public void protocol(Socket socket) throws Exception {
        Files.receiveFile("doc.html", socket);
    }

    public static void main(String[] args) throws Exception {
        FileTCPClient fc = new FileTCPClient();
        fc.init();
    }
}