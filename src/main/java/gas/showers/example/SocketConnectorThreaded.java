package gas.showers.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;

import gas.showers.apiframeworkmk1.API;
import gas.showers.simplethreads.SimpleProcess;

public class SocketConnectorThreaded extends SimpleProcess {
	public class Cons {
		public Socket socket;
		public PrintWriter out;
		public BufferedReader in;
		
		public Cons(ServerSocket serverSocket) throws IOException {
			socket = serverSocket.accept();
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}
		
		public void close() throws IOException {
			socket.close();
			out.close();
			in.close();
		}
	}
	
	private Cons con;
	private boolean kill;
	private API api;
	public SocketConnectorThreaded(ServerSocket _serverSocket, API _api) throws IOException {
		super("SocketConnectorThreaded", API.logger);
		api = _api;
		con = new Cons(_serverSocket);
		this.start();
		kill = false;
	}

	@Override
	public void abRun() throws Exception {
		String s;
		while ((s = con.in.readLine()) != null) {
			logger.log(Level.FINE, this.getIP()+" sent \""+s+"\"");
			
			if (s.equals("exit"))
				break;
			else if (s.equals("kill")) {
				kill = true;
				break;
			} else {
				con.out.println(api.execute(s));
			}
		}
	}
	
	public Cons getCon() {
		return con;
	}
	public boolean getKill() {
		return kill;
	}
	
	public void close () throws IOException {
		con.close();
	}
	
	public String getIP() {
		return con.socket.getInetAddress().getHostAddress();
	}
}
