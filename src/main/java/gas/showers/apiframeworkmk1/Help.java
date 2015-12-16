package gas.showers.apiframeworkmk1;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;

public class Help extends APITemplate {
	//load the help JSON string on construct
	private String help;
	
	public Help(API _api) {
		super(_api, 0);
		//build a json array from new lines
		JSONArray result = new JSONArray();
		String temp = api.listCommands();
		String[] split = temp.split("\n");
		ArrayList<String> temp2 = new ArrayList<String>();
		for (int x = 0; x<split.length; x++)
			temp2.add(split[x]);
		Collections.sort(temp2);
		for (String s : temp2) {
			result.put(s);
		}
		help = result.toString();
	}
	
	public Help(API _api, String _help) {
		super(_api, 0);
		help = _help;
	}

	@Override
	public String command() {
		// TODO Auto-generated method stub
		return "help";
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Returns all available commands.";
	}

	@Override
	public APIInterface generate(String[] args) throws APIArgumentException {
		APIInterface temp = new Help(api, help);
		return temp;
	}

	@Override
	public String get() {
		return help;
	}

}
