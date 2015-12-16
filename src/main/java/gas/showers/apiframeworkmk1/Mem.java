package gas.showers.apiframeworkmk1;

import org.json.JSONObject;

public class Mem extends APITemplate {

	public Mem(API _api) {
		super(_api, 0);
	}

	@Override
	public String command() {
		return "mem";
	}

	@Override
	public String description() {
		return "Returns a json containing information about the state of the JVM.";
	}

	@Override
	public APIInterface generate(String[] args) throws APIArgumentException {
		return new Mem(api);
	}

	@Override
	public String get() {
		int mb = 1024 * 1024;
		Runtime runtime = Runtime.getRuntime();
		JSONObject result = new JSONObject();

		// Print used memory
		result.put("used", (runtime.totalMemory() - runtime.freeMemory()) / mb);

		// Print free memory
		result.put("free", runtime.freeMemory() / mb);

		// Print total available memory
		result.put("total", runtime.totalMemory() / mb);

		// Print Maximum available memory
		result.put("max", runtime.maxMemory() / mb);

		return result.toString();
	}

}
