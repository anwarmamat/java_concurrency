package ping;

import java.util.concurrent.Semaphore;

class Ping extends Thread{
	private Semaphore pong;
	private Semaphore ping;
	public Ping(Semaphore ping,Semaphore pong) {
		this.pong = pong;
		this.ping = ping;
	}
	public void run() {
		for(int i = 0; i < 5; i++) {
			int duration = (int)( Math.random() * 1000);
			try {
				ping.acquire();
				System.out.println("ping");
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				sleep(duration);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			pong.release();
		}
	}
}

class Pong extends Thread{
	private Semaphore pong;
	private Semaphore ping;
	public Pong(Semaphore ping,Semaphore pong) {
		this.pong = pong;
		this.ping = ping;
	}
	public void run() {
		for(int i = 0; i < 5; i++) {
			int duration = (int)( Math.random() * 1000);
			try {
				pong.acquire();
				System.out.println("pong");
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				sleep(duration);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ping.release();
		}
	}
}
public class PingTest {

	public static void main(String[] args) {

		final Semaphore ping = new Semaphore(1, true); 		//Semaphore(int permits, boolean fair)
		final Semaphore pong = new Semaphore(0, true);
		Thread ping1 = new Ping(ping,pong);
		Thread pong1 = new Pong(ping,pong);
		ping1.start();
		pong1.start();
	}

}
