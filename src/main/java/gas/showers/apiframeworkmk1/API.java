package gas.showers.apiframeworkmk1;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.json.JSONArray;
import org.json.JSONObject;

import gas.showers.simplethreads.SimpleProcess;

public class API {
	//Logging using java.util.logging
	public final static Logger logger = Logger.getLogger(API.class.getName());
	private static FileHandler fh = null;
	public static void init() {
		try {
			int limit = 1000000*50; // 50 Mb
			fh = new FileHandler("apiframeworkmk1.log", limit, 1, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Logger l = Logger.getLogger("");
		fh.setFormatter(new SimpleFormatter());
		l.addHandler(fh);
		l.setLevel(Level.ALL);
	}

	protected HashMap<String, APIInterface> commands;
	private HashMap<UUID, APIInterface> executing;
	private GarbageCollector gc;
	
	private boolean kill;
	/**
	 * Used by other threads to see if they should exit.
	 * @return
	 */
	public boolean getKill() {
		return kill;
	}
	
	/**
	 * This class should be instanced once per application, uses static instances (logging etc).
	 */
	public API() {
		init(); //start the logger
		logger.log(Level.INFO, "Setting up the API.");
		commands = new HashMap<String, APIInterface>();
		executing = new HashMap<UUID, APIInterface>();
		kill = false;
		//set up garbage collection
		gc = new GarbageCollector(executing, logger);
		gc.start();
		//instance setup commands
		APIInterface temp = null;
		temp = new NewTask(this);
		this.commands.put(temp.command(), temp);
		temp = new GetTask(this);
		this.commands.put(temp.command(), temp);
		
		temp = new ListTasks(this);
		this.commands.put(temp.command(), temp);
		
		temp = new HelloWorld(this);
		this.commands.put(temp.command(), temp);
		
		temp = new Mem(this);
		this.commands.put(temp.command(), temp);
		
		temp = new Kill(this);
		this.commands.put(temp.command(), temp);
		
		//put help. Redo this, for extensions of this class.
		temp = new Help(this);
		this.commands.put(temp.command(), temp);	
		
	}
	
//-----------------------------------------------------------------------------------
//accessors for the API
//-----------------------------------------------------------------------------------
	
	/**
	 * Returns the result of the command, if there is none returns the UUID.
	 * @param commandString
	 * @return
	 */
	public String execute(String commandString) {
		if (gc == null || gc.getRunning() == false)
			return APIJSONHandler.handle(new Exception("Garbage collector isn't started! Would be a bad idea to execute..."));
		try {
			UUID uuid = null;
			try {
				uuid = this.console(commandString);
			} catch (APIArgumentException e) {
				return APIJSONHandler.handle(e);
			}

			if (uuid != null) {
				String output = this.getInstance(uuid).get();
				if (output == null) {
					//if there is no output return JSON info about the running command for later
					JSONObject outJSON = new JSONObject();
					outJSON.put("uuid", this.getInstance(uuid).getUUID());
					outJSON.put("command", this.getInstance(uuid).command());
					outJSON.put("life", this.getInstance(uuid).life());
					return APIJSONHandler.handle(outJSON);
				} else
					return APIJSONHandler.handle(output);
			} else
				return APIJSONHandler.handle(new Exception("A unspecified error occured, check initial command string."));
		} catch (Exception e) {
			return APIJSONHandler.handle(e);
		}
	}

	/**
	 * Returns a JSONArray of all executing actions.
	 * @return
	 */
	public String getAllExecuting() {
		JSONArray result = new JSONArray();
		for (Map.Entry<UUID, APIInterface> command : executing.entrySet()) {
			JSONObject temp = new JSONObject();
			temp.append("uuid", command.getValue().getUUID());
			temp.append("command", command.getValue().command());
			temp.append("life", command.getValue().life());
			result.put(temp);
		}
		return result.toString();
	}
	/**
	 * Get the instance of an executing action / api call.
	 * @param uuid
	 * @return
	 */
	public APIInterface getInstance(UUID uuid) {
		return executing.get(uuid);
	}
	
	/**
	 * Attempts to gracefully exit all running threads. Could hang.
	 */
	public void stop() {
		logger.log(Level.INFO, "Attempting to gracefully stop all running processes.");
		
		kill = true;
		//now we wait. gc will remove them.
		int waitMS = 1000;
		int waitTimes = 0;
		while (!executing.isEmpty()) {
			try {Thread.sleep(waitMS);} catch (InterruptedException e) {}
			waitTimes++;
			if (waitTimes > 5)
				logger.log(Level.WARNING, "After approx "+(waitMS*waitTimes/1000)+" seconds, still trying to kill...");
			else if (waitTimes > 10) {
				logger.log(Level.SEVERE, (waitMS*waitTimes/1000)+" seconds so far. JSON OF RUNNING :: "+this.getAllExecuting());
			} else if (waitTimes > 30) {
				logger.log(Level.SEVERE, (waitMS*waitTimes/1000)+" seconds so far. FORCING SHUTDOWN. JSON OF RUNNING :: "+this.getAllExecuting());
				System.exit(0);
			}
		}
		
		//kill the garbage collector. Not checked, but shouldn't bug.
		gc.stop();
		gc = null;
		
		logger.log(Level.INFO, "Succeeded, should exit shortly..");
		System.exit(0);
	}
	
//-----------------------------------------------------------------------------------
//virtual console
//-----------------------------------------------------------------------------------
	/**
	 * Parse commands to the console, space delineated for parameters.
	 * @param commandString
	 * @return
	 * @throws APIArgumentException 
	 */
	public UUID console(String commandString) throws APIArgumentException {
		String[] args = commandString.split(" ");
		if (args.length <= 0)
			return null;
		String[] pass = new String[args.length-1];
		for (int x = 1; x < args.length; x++)
			pass[x-1] = args[x];
		
		String command = args[0].toLowerCase();
		if (commands.containsKey(command)) {
			//generates and runs a new instance of this command
			APIInterface theInstance = commands.get(command).generate(pass);
			executing.put(theInstance.getUUID(), theInstance);
			return theInstance.getUUID();
		}
		return null;
	}
	public String listCommands() {
		String result = "";
		for (Map.Entry<String, APIInterface> command : commands.entrySet()) {
			result+=command.getValue().command()+" - "+command.getValue().description()+"\n";
		}
		return result;
	}
	
//-----------------------------------------------------------------------------------
/**
 * Helper class for removing references to finished threads.
 */
	public class GarbageCollector extends SimpleProcess {
		private boolean kill;
		private HashMap<UUID, APIInterface> executing;
		public GarbageCollector(HashMap<UUID, APIInterface> _executing, Logger _logger) {
			super("API.GarbageCollector", _logger);
			executing = _executing;
			kill = false;
		}
		@Override
		public void abRun() throws Exception {
			logger.log(Level.INFO, "Garbage collector started.");
			ArrayList<UUID> toRemove = new ArrayList<UUID>();
			while (!kill) {
				try {
					for (Map.Entry<UUID, APIInterface> command : executing.entrySet()) {
						if (command.getValue().life() == 0)
							toRemove.add(command.getKey());
					}
					//logger.log(Level.FINE, "Total of "+toRemove.size()+" to remove..");
					if (!toRemove.isEmpty()) {
						for (UUID command : toRemove)
							executing.remove(command);
						toRemove.clear();
					}
					this.sleep();
				} catch (ConcurrentModificationException z) {
					logger.log(Level.WARNING, "CC ex, continuing", z);
				}
			}
		}
		public void stop() {
			kill = true;
		}
	}
	
}