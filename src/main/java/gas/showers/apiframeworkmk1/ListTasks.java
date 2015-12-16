package gas.showers.apiframeworkmk1;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Lives for 0 seconds, returns the currently running tasks as jsonarray, immediately.
 * @author Joey
 *
 */
public class ListTasks extends APITemplate {
	public ListTasks(API _api) {
		super(_api, 0);
	}

	@Override
	public String command() {
		return "lt";
	}

	@Override
	public String description() {
		return "Lives for 0 seconds, returns the currently running tasks as jsonarray, immediately.";
	}

	@Override
	public APIInterface generate(String[] args) throws APIArgumentException {
		return new ListTasks(api);
	}

	@Override
	public String get() {		
		JSONArray temp = new JSONArray(api.getAllExecuting());
		JSONArray result = new JSONArray();
		
		for (int x = 0; x < temp.length(); x++) {
			JSONObject a = temp.getJSONObject(x);
			if (!a.getJSONArray("uuid").getString(0).equals(uuid.toString())) {
				JSONObject b = new JSONObject();
				b.put("uuid", a.getJSONArray("uuid").getString(0));
				b.put("command", a.getJSONArray("command").getString(0));
				b.put("life", a.getJSONArray("life").getInt(0));
				result.put(b);
			}
		}
		
		return result.toString();
	}

}
