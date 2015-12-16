package gas.showers.apiframeworkmk1;

import java.util.UUID;

import org.json.JSONObject;

public class Kill extends APITemplate {
	private String toKill;

	public Kill(API _api) {
		super(_api, 0);
	}

	public Kill(API _api, String _toKill) {
		super(_api, 0);
		toKill = _toKill;
	}

	@Override
	public String command() {
		return "kill";
	}

	@Override
	public String description() {
		return "Kills a task by uuid or command, if you pass a command the command must return a uuid keyed string value in the main json object.";
	}

	@Override
	public APIInterface generate(String[] args) throws APIArgumentException {
		if (args.length != 1)
			throw new APIArgumentException("Wrong number of args for kill.");
		Kill gen = new Kill(api, args[0]);
		return gen;
	}

	@Override
	public String get() {
		String result = "Failed to recognise the parameter '" + toKill + "' as valid input.";

		if (api.commands.containsKey(toKill)) {
			JSONObject com = new JSONObject(api.execute(toKill));
			if (com.has("uuid")) {
				result = "Recognized command, returned "+com.toString()+". KILL response: "+api.execute("kill "+com.getString("uuid"));
			} else {
				result = "Recognised command but unable to get uuid. "+com.toString();
			}
		} else {
			try {
				UUID _uuid = UUID.fromString(toKill);
				APIInterface com = api.getInstance(_uuid);
				if (com instanceof APITemplateThreaded) {
					((APITemplateThreaded) com).stop();
					result = "Sent command to stop " + toKill + ".";
				} else {
					result = "UUID recognized, but is not killable.";
				}
			} catch (Exception e) {
			}
		}

		return result;
	}

}
