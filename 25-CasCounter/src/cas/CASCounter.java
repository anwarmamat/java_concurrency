package cas;

/*
 * 	Implements AtomicInteger using Compare and Swap 
 */
import java.lang.reflect.Field;

import sun.misc.Unsafe;
/*
 * no sync
 * no lock/unlock
 */
public class CASCounter {
    private Unsafe unsafe;//low level library
    private volatile long counter = 0;
    private long offset;
 
    private Unsafe getUnsafe() throws IllegalAccessException, NoSuchFieldException {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        return (Unsafe) f.get(null);
    }
 
    public CASCounter() throws Exception {
        unsafe = getUnsafe();
        offset = unsafe.objectFieldOffset(CASCounter.class.getDeclaredField("counter"));
    }
 
    public void increment() {
        long before = counter;
        //loop 
        while (!unsafe.compareAndSwapLong(this, offset, before, before + 1)) {
            before = counter;
        }
    }
 
    public long getCounter() {
        return counter;
    }
}