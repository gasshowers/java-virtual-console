package gas.showers.apiframeworkmk1;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Makes sure all results returned are in JSON.
 * 
 * Defaults:
 * - A JSON object is returned
 * - Key "response" is the get value of the API
 * - Key "javaexception" is the exception string of the api
 * 
 * @author Joey
 *
 */
public class APIJSONHandler {
	/**
	 * If it's JSON then return the string as is, if not then convert to JSON.
	 * @param aString
	 * @return
	 */
	public static String handle(String aString) {
		boolean isJSON = false;
		try {
			new JSONObject(aString);
			isJSON = true;
		} catch (Exception e){}
		try {
			new JSONArray(aString);
			isJSON = true;
		} catch (Exception e){}
		
		if (isJSON)
			return aString;
		else {
			JSONObject result = new JSONObject();
			result.put("response", aString);
			return result.toString();
		}
	}
	
	public static String handle(Exception aException) {
		JSONObject result = new JSONObject();
		result.put("javaexception", aException.toString());
		return result.toString();
	}
	
	public static String handle(JSONArray aJSONArray) {
		return aJSONArray.toString();
	}
	
	public static String handle(JSONObject aJSONObject) {
		return aJSONObject.toString();
	}
}
