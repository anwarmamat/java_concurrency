package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import filemanager.FileManager;
import filemanager.Reader;
import filemanager.Writer;

public class PublicTests {

	@Test
	public void test() {
		FileManager f = new FileManager();
		Thread r1 = new Reader(f, "1");
		Thread r2 = new Reader(f, "2");
		Thread r3 = new Reader(f, "3");
		Thread w1 = new Writer(f,"1");
		Thread w2 = new Writer(f,"2");
		r1.start();
		r2.start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		w1.start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		r3.start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		w2.start();
		try {
			r1.join();
			r2.join();
			w1.join();
			r3.join();
			w2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
