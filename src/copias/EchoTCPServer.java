package copias;

import java.net.ServerSocket;
import java.net.Socket;

public class EchoTCPServer {
		public static final int PORT = 4200;
		private ServerSocket Listener;
		private Socket serverSideSocket;
		public EchoTCPServer(){System.out.println("\n\nSERVER PORT:"  + PORT + "\n\n");}
		private void init() throws Exception{
			Listener = new ServerSocket(PORT);
			while(true){
				serverSideSocket = Listener.accept();
				EchoTCPServerProtocol.protocol(serverSideSocket);
			}
		}
		public static void main(String[] args) throws Exception {
			EchoTCPServer es = new EchoTCPServer();
			es.init();
		}
}
