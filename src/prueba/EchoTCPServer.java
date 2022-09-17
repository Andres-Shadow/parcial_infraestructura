package prueba;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoTCPServer {
	public static final int PORT = 4200;
	
	private ServerSocket listener;
	private Socket serverSideSocket;
	
	public EchoTCPServer() {
		System.out.println("Echo TCP server is running on port: "+ PORT);
		
	}
	
	private void init() throws IOException {
		listener = new ServerSocket(PORT);
		
		while(true) {
			serverSideSocket = listener.accept();
			EchoTCPServerProtocol.protocol(serverSideSocket);
		}
	}
	
	public static void main(String[] args) throws IOException {
		EchoTCPServer es = new EchoTCPServer();
		es.init();
	}
}
