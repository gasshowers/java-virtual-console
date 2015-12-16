package gas.showers.example;

import java.util.ConcurrentModificationException;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import gas.showers.apiframeworkmk1.API;
import gas.showers.apiframeworkmk1.APIArgumentException;
import gas.showers.apiframeworkmk1.APIInterface;
import gas.showers.apiframeworkmk1.APITemplateThreaded;

/**
 * This command could run forever, until it is killed or it completes it's task, which is waiting 1000ms for 10 times. 
 * 
 * Usage: send "example" command, will return any existing ones if there are any.
 * - Counts to 10, waiting 1000ms
 * - lt to view running tasks, kill with kill. (requires uuid returned in get, and get returned as jsonobject not array).
 * - If you call this from code, use APIv1.run or equivalent code.
 * 
 * Try changing the secondslife to 5, from -1 to see what happens.
 * @author Joey
 *
 */
public class SingleInstanceCommandExample extends APITemplateThreaded {
	private int timesWaited;

	public SingleInstanceCommandExample(API _api) {
		super(_api, -1); //change this 
		timesWaited = 0;
	}

	@Override
	public String command() {
		return "example";
	}

	@Override
	public String description() {
		return "Read the code at gas.showers.example.SingleInstaceCommandExample";
	}

	@Override
	public APIInterface generate(String[] args) throws APIArgumentException {
		APIInterface gen = null;
		JSONArray temp = new JSONArray(api.getAllExecuting());
		try {
			for (int x = 0; x < temp.length(); x++) {
				if (temp.getJSONObject(x).getJSONArray("command").getString(0).equals(this.command())) {
					gen = api.getInstance(UUID.fromString(temp.getJSONObject(x).getJSONArray("uuid").getString(0)));
				}
			}
		} catch (ConcurrentModificationException z) {
			throw APIArgumentException.newCCME();
		}

		if (gen == null) {
			SingleInstanceCommandExample t = new SingleInstanceCommandExample(api);
			t.start();
			gen = t;
		}

		return gen;
	}

	@Override
	public String get() {
		JSONObject result = new JSONObject();
		result.put("message", "We have waited "+timesWaited+" times.");
		result.put("uuid", this.uuid.toString());//necessary to kill
		return result.toString();
	}

	@Override
	public void abRun() throws Exception {
		while(timesWaited < 10) {
			if (this.localKill) {
				this.stop();
				return;
			}
			this.sleep(1000);			
			timesWaited++;
		}
		this.stop();
	}

}
