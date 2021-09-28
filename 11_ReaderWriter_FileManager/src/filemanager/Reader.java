package filemanager;

public class Reader extends Thread{
	FileManager f;
	public Reader(FileManager f, String name) {
		super(name);
		this.f = f;
	}
	public void run() {
			f.read();
	}
}
