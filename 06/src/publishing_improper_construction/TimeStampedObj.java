package publishing_improper_construction;
//import java.util.Date;

import java.util.Date;

/**
 * Class of time-stamped objects.  Adapted from an example by Evan Golub.
 *
 */
public class TimeStampedObj {
	private final Object payload;
	private final Date timeStamp;

	public TimeStampedObj(Object o) {	
		TimeStampedObjCache.lastObjCreated = this;
		payload = o;
		timeStamp = new Date();
		
	}

	public Date getTimeStamp() {
		return timeStamp;
	}
	
	public Object getPayload() {
		return payload;
	}
}
