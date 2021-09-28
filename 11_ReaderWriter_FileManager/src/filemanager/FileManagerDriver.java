package filemanager;

public class FileManagerDriver {

	public static void main(String[] args) {
		
		int numReader = 4;
		int numWriter = 2;
		
		FileManager f = new FileManager();
		
		Thread[] readers = new Thread[numReader];
		Thread[] writers = new Thread[numWriter];
		
		
		for(int i  = 0; i < numReader; i++) {
			readers[i] = new Reader(f, Integer.toString(i));
		}
		for(int i  = 0; i < numWriter; i++) {
			writers[i] = new Writer(f,Integer.toString(i));
		}
		
		for(int i  = 0; i < numReader; i++) {
			readers[i].start();
		}
		for(int i  = 0; i < numWriter; i++) {
			writers[i].start();
		}
		try {
			for(int i  = 0; i < numReader; i++) {
				readers[i].join();
			}
			for(int i  = 0; i < numWriter; i++) {
				writers[i].join();
			}
		}catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
