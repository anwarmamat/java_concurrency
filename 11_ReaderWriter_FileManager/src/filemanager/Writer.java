package filemanager;

public class Writer extends Thread{
	FileManager f;
	public Writer(FileManager f, String name) {
		super(name);
		this.f = f;
	}
	public void run() {	
			f.write();
	}	
}
