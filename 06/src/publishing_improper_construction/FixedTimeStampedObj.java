package publishing_improper_construction;
import java.util.Date;

/**
 * Fixed class of timed-stamped objects.

 */
public class FixedTimeStampedObj {
	private final Object payload;
	private final Date timeStamp;

	/**
	 * To avoid publishing this in constructor, make it private and
	 * do not assign to cache
	 * 
	 * @param o	payload object
	 */
	private FixedTimeStampedObj(Object o) {
		payload = o;
		timeStamp = new Date();
	}
	
	/**
	 * Static factory method used to create, publish objects
	 * 
	 * @param o	payload
	 * @return	time-stamped object containing payload
	 */
	public static FixedTimeStampedObj newInstance  (Object o) {
		FixedTimeStampedObj tso = new FixedTimeStampedObj(o);
		FixedTimeStampedObjCache.lastObjCreated = tso; 
		return tso;
	}

	public Date getTimeStamp() {
			return timeStamp;
	}
	
	public Object getPayload() {
		return payload;
	}
}
