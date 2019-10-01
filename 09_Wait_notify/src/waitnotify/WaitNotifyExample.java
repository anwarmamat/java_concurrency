/**
 * Wait and Notify example
 * 
 * Ping: pings and waits
 * Pong: sleeps 2 seconds, pongs, and notifies
 * ping: wakes up and pings again
 * 
 */
package waitnotify;

public class WaitNotifyExample {
	public static void main(String[] args) {
		Object lock = new Object();
		int numberOfPings = 5;
		Thread[] pings = new Thread[numberOfPings]; 
		Thread[] pongs = new Thread[numberOfPings];
		for(int i = 0; i < numberOfPings; i++) {
			pings[i] = new Ping1(lock,"ping" + Integer.toString(i));
			pings[i].start();
		}
		
		for(int i = 0; i < numberOfPings; i++) {
			pongs[i] = new Pong(lock,"pong" + Integer.toString(i));
			pongs[i].start();
		}
	}
}
