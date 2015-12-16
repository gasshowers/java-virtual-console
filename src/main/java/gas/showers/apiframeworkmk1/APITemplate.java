package gas.showers.apiframeworkmk1;

import java.time.LocalDateTime;
import java.util.UUID;

import gas.showers.datetime.DateHelper;

public abstract class APITemplate implements APIInterface {
	/**
	 * Run while this and api.getKill() are both false.
	 */
	protected boolean localKill;
	protected API api;
	protected long lifeSeconds;
	protected LocalDateTime birth;
	protected UUID uuid;

	/**
	 * Passing the active API to the instance allows it to exit when needed.
	 * 
	 * @param _api
	 * @param _lifeSeconds
	 *            This parameter is how many seconds this has to live. -1 =
	 *            never kill.
	 */
	public APITemplate(API _api, long _lifeSeconds) {
		api = _api;
		localKill = false;
		lifeSeconds = _lifeSeconds;
		birth = LocalDateTime.now();
		uuid = UUID.randomUUID();
	}

	public void stop() {
		localKill = true;
		lifeSeconds = 0;
	}

	@Override
	public long life() {
		if (api.getKill())
			this.stop();
		if (lifeSeconds < 0)
			return -1;
		// find life left
		long lifeLeft = lifeSeconds;
		lifeLeft -= DateHelper.calculateSecondsBetweenLocalDateTime(birth, LocalDateTime.now());
		if (lifeLeft <= 0) {
			this.stop();
			lifeLeft = 0;
		}
		return lifeLeft;
	}

	@Override
	public UUID getUUID() {
		return uuid;
	}

}
