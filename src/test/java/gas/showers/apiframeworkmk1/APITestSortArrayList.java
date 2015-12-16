package gas.showers.apiframeworkmk1;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Will sort the arraylist when it has 1 minute left to live.
 * @author Joey
 *
 */
public class APITestSortArrayList extends APITemplateThreaded{
	
	private ArrayList<Double> values;

	public APITestSortArrayList(API _api, long _lifeSeconds, ArrayList<Double> _values) {
		super(_api, _lifeSeconds);
		values = _values;
	}

	@Override
	public String command() {
		return "sortarraylist";
	}

	@Override
	public String description() {
		return null;
	}

	@Override
	public APIInterface generate(String[] args) throws APIArgumentException {
		APITestSortArrayList gen = new APITestSortArrayList(api, lifeSeconds, values);
		gen.start();
		return gen;
	}

	@Override
	public String get() {
		return null;
	}

	@Override
	public void abRun() throws Exception {
		while (!localKill && !api.getKill()) {
			if (this.life() <= 60) {
				Collections.sort(values);
				break;
			}
			this.sleep();
		}
		
		//set life to 0 so we confirm death
		this.lifeSeconds = 0;
	}

}
