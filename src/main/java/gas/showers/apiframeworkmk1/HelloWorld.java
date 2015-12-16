package gas.showers.apiframeworkmk1;

/**
 * Returns 'hello world' and lives for 10 seconds.
 * @author Joey
 *
 */
public class HelloWorld extends APITemplate {

	public HelloWorld(API _api) {
		super(_api, 10);
	}

	@Override
	public String command() {
		return "helloworld";
	}

	@Override
	public String description() {
		return "Returns 'hello world' and lives for 10 seconds.";
	}

	@Override
	public APIInterface generate(String[] args) throws APIArgumentException {
		return new HelloWorld(api);
	}

	@Override
	public String get() {
		return "hello world";
	}

}
