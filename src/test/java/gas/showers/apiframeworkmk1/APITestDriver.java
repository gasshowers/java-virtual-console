package gas.showers.apiframeworkmk1;

import java.util.ArrayList;

public class APITestDriver extends API {
	public APITestDriver(ArrayList<Double> listToSort) {
		super();
		//set up commands
		APIInterface temp = null;
		temp = new APITestSortArrayList(this, 65, listToSort);
		commands.put(temp.command(), temp);
	}
}
