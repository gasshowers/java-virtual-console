package gas.showers.apiframeworkmk1;

import java.util.UUID;

public class GetTask implements APIInterface {
	private API api;
	private UUID uuid;
	
	private UUID targetUuid;
	
	public GetTask(API _api) {
		this(_api, null);
	}
	
	public GetTask(API _api, UUID _targetUuid) {
		api = _api;
		uuid = UUID.randomUUID();
		targetUuid = _targetUuid;
	}

	@Override
	public String command() {
		return "gt";
	}

	@Override
	public String description() {
		return "Calls to this task must be passed with the uuid of a running task, that returns something, otherwise throws an exception.";
	}

	@Override
	public long life() {
		return 0;
	}

	@Override
	public APIInterface generate(String[] args) throws APIArgumentException {
		if (args.length < 1)
			throw new APIArgumentException("No uuid passed to gt.");
		try {
			targetUuid = UUID.fromString(args[0]);
		} catch (Exception e) {}
		if (targetUuid == null)
			throw new APIArgumentException("Bad uuid passed to gt.");
		if (api.getInstance(targetUuid) == null)
			throw new APIArgumentException("Uuid passed to gt could not be resolved.");
		if (api.getInstance(targetUuid).get() == null)
			throw new APIArgumentException("Uuid passed resolved, but returned null.");
		APIInterface gen = new GetTask(api, targetUuid);
		return gen;
	}
	@Override
	public UUID getUUID() {
		return uuid;
	}

	@Override
	public String get() {
		return api.getInstance(targetUuid).get();
	}

}
