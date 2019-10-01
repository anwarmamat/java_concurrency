package publishing_improper_construction;

/**
 * Caches most recently created time-stamped object

 */
public class FixedTimeStampedObjCache {
    static public volatile FixedTimeStampedObj lastObjCreated =
    		FixedTimeStampedObj.newInstance(new Object ());
}
