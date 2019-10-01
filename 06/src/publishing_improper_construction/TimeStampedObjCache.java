package publishing_improper_construction;

/**
 * Caches most recently created time-stamped object
 * 
 */
public class TimeStampedObjCache {
    static public TimeStampedObj lastObjCreated =
    		new TimeStampedObj (new Object ());
}
