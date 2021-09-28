package room;

import java.util.concurrent.Semaphore;

public class RoomManager {
	private static final int N = 5;
	private Semaphore seat = new Semaphore(N,true);
		
	public void noncovid() {
		System.out.println("NonCovid patient Arrived.");
		try {
			seat.acquire();
			System.out.println("NonCovid patient entered. \t" + seat.availablePermits() + " seats are available.");
			Thread.sleep(1000);
			
		} catch (InterruptedException e) {
		}
		seat.release();
		System.out.println("NonCovid patient exited. \t" + seat.availablePermits() + " seats are available.");
	}
	
	public void covid() {
		System.out.println("Covid patient Arrived.");
		try {
			seat.acquire(N);
			System.out.println("Covid patient entered. \t" + seat.availablePermits() + " seats are available.");
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
		seat.release(N);
		System.out.println("Covid patient exited. \t" + seat.availablePermits() + " seats are available.");
	}
	
	private static void delay(int n) {
		try {
			Thread.sleep(n);
		} catch (InterruptedException e) {
		}
	}
	
	public static void main(String[] args) {
		RoomManager r = new RoomManager();

		Thread n1 = new Schedule(r, PatientType.NonCovid);
		Thread n2 = new Schedule(r, PatientType.NonCovid);
		Thread n3 = new Schedule(r, PatientType.Covid);
		Thread n4 = new Schedule(r, PatientType.NonCovid);
		Thread n5 = new Schedule(r, PatientType.NonCovid);
		n1.start();
		delay(50);
		n2.start();
		delay(50);
		n3.start();
		delay(100);
		n4.start();
		n5.start();
	}
}
