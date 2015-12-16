package gas.showers.apiframeworkmk1;

import java.util.UUID;

public class NewTask implements APIInterface{
	
	API api;
	
	public NewTask(API _api) {
		api = _api;
	}

	@Override
	public String command() {
		return "nt";
	}

	@Override
	public String description() {
		return "A default task, instances a new task, passes parameters onwards (Stripping the command, nt).";
	}

	/**
	 * Dies immediately.
	 */
	@Override
	public long life() {
		return 0;
	}

	
	@Override
	public APIInterface generate(String[] args) throws APIArgumentException {
		//parse the arguments, stripping the nt command.
		if (args.length < 1)
			throw new APIArgumentException("No command passed to nt.");
		String pass = args[0];
		for (int x = 1; x < args.length; x++)
			pass = pass + " " + args[x];
		//find our command, might throw an exception.
		APIInterface gen = api.getInstance(api.console(pass));
		return gen;
	}

	@Override
	public UUID getUUID() {
		return null;
	}

	@Override
	public String get() {
		return null;
	}

}
