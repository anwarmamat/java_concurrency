package executor;

import java.util.concurrent.Executor;

public class ExcutorExample{
	public static void main(String[] args) {
		int numTasks = 5;
		DirectExecutor direct = new DirectExecutor();
		ThreadPerTaskExecutor perTask = new ThreadPerTaskExecutor();
		SerialExecutor serial = new SerialExecutor(perTask);
		Runnable[] tasks = new Runnable[numTasks];
		Runnable r1 = new Task();
		for(int i = 0; i < numTasks; i++) {
			tasks[i] =r1;
		}
		/*for(Runnable t: tasks) {
			direct.execute(t);
		}*/
		/*
		for(Runnable t: tasks) {
			perTask.execute(t);
		}
		*/
		for(Runnable t: tasks) {
			serial.execute(t);
		}
		
		
	}

}
