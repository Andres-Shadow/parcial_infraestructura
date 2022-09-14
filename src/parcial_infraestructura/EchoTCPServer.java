package parcial_infraestructura;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoTCPServer {

	
		public static final int PORT = 4200;
		
		private ServerSocket Listener;
		private Socket serverSideSocket;
	
		
		public EchoTCPServer(){
			System.out.println("Echo TCP server is running on port:"  + PORT);
		}
		
		private void init() throws Exception{

			Listener = new ServerSocket(PORT);

			

			while(true){


				serverSideSocket = Listener.accept();



				EchoTCPServerProtocol.protocol(serverSideSocket);
				System.out.println("[Cliente puerto]:"  + serverSideSocket.getPort());
				System.out.println("[Cliente IP]:"  + serverSideSocket.getInetAddress());
			}
		}
		
		public static void main(String[] args) throws Exception {
			EchoTCPServer es = new EchoTCPServer();
			es.init();
		}
	

}
