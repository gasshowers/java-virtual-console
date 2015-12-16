package gas.showers.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.logging.Level;

import org.json.JSONArray;
import org.json.JSONObject;

import gas.showers.apiframeworkmk1.*;

public class APIv1 extends API {
	public static final int MAX_CONNECTIONS = 25;
	public static API api;

	public APIv1() {
		super();
		// set up commands
		APIInterface temp = null;
		//this.commands.put(temp.command(), temp);
		temp = new SingleInstanceCommandExample(this);
		this.commands.put(temp.command(), temp);
		
		//re-add help so we get the entire list
		temp = new Help(this);
		this.commands.put(temp.command(), temp);
	}

	public static void main(String args[]) throws IOException {
		api = new APIv1();

		if (args.length == 0) {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String s = "";
			do {
				System.out.print("\nEnter command (q to exit):");
				s = br.readLine();
				if (!s.equals("q"))
					System.out.println(api.execute(s));
			} while (!s.equals("q"));
		} else {
			ArrayList<SocketConnectorThreaded> socks = new ArrayList<SocketConnectorThreaded>();
			boolean kill = false;

			int portNumber = Integer.parseInt(args[0]);
			API.logger.log(Level.INFO, "Listening on " + portNumber);
			ServerSocket serverSocket = null;
			SocketManager mgr = null;
			try {
				serverSocket = new ServerSocket();
				// InetSocketAddress ad = new
				// InetSocketAddress(InetAddress.getLocalHost(), portNumber);
				serverSocket.bind(new InetSocketAddress("0.0.0.0", portNumber));

				mgr = new SocketManager(serverSocket, socks, MAX_CONNECTIONS, api);
				mgr.start();
				// set up threads which wait for connection, and execute
				// commands on the API.
				while (true) {
					if (kill || !mgr.getRunning()) {
						break;
					}

					// remove any finished threads
					for (int x = 0; x < socks.size(); x++) {
						if (!socks.get(x).getRunning()) {
							if (socks.get(x).getKill())
								kill = true;
							socks.get(x).close();
							socks.remove(x);
							x--;
						}
					}

					try {
						Thread.sleep(50);
					} catch (Exception e) {
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				serverSocket.close();
				for (SocketConnectorThreaded sock : socks) {
					sock.close();
				}
			}
		}

		// exit all threads
		api.stop();
	}
	
	/**
	 * Designed to be a better method to run commands, without them hanging, and to take into account ccme.
	 * 
	 * REQUIRES CONSISTENT HANDLING OF THE ERROR (APIArg..newCCME())
	 * 
	 * @param command
	 * @return
	 */
	public static String run(String command) {
		String ccme = APIArgumentException.newCCME().toString();
		JSONObject com = null;
		
		while (true) {
			String ret = api.execute(command);
			try {
				JSONArray me = new JSONArray(ret);
				return me.toString();
			} catch (Exception e) {}
			com = new JSONObject(ret);
			//exit if we dont have an exception, or the exception is not ccme
			if (!com.has("javaexception"))
				break;
			
			if (!com.getString("javaexception").equals(ccme))
				break;
			
			//sleep for a bit if it fails. Sleeps only if we ccme.
			try{Thread.sleep(150);}catch(Exception e){}
		}
		
		return com.toString();
	}

}
