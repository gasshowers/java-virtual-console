package gas.showers.example;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.logging.Level;

import gas.showers.apiframeworkmk1.API;
import gas.showers.simplethreads.SimpleProcess;

public class SocketManager extends SimpleProcess {

	private ServerSocket server;
	private ArrayList<SocketConnectorThreaded> socks;
	private int max;
	private boolean kill;
	private API api;

	public SocketManager(ServerSocket _server, ArrayList<SocketConnectorThreaded> _socks, int _max, API _api) {
		super("SocketManager", API.logger);
		server = _server;
		socks = _socks;
		max = _max;
		kill = false;
		api = _api;
	}

	@Override
	public void abRun() throws Exception {
		while (!kill) {
			if (socks.size() < max) {
				socks.add(new SocketConnectorThreaded(server, api));
				logger.log(Level.FINE, socks.get(socks.size()-1).getIP()+" connected to our socket.");
			} else {
				try {
					Thread.sleep(50);
				} catch (Exception e) {
				}
			}
		}
	}

	public void kill() {
		kill = true;
	}

}
