import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Schedule {
	public static void main(String[] args) {
		final int INTERRUPTER_NUM = 20;
		ScheduledExecutorService timer1 = Executors.newSingleThreadScheduledExecutor();
		ScheduledExecutorService timer2 = Executors.newSingleThreadScheduledExecutor();
		ScheduledExecutorService[] interrupter = new ScheduledExecutorService[INTERRUPTER_NUM];
		
		Date now = new Date();
		System.out.println("First TimeStamp : " + new SimpleDateFormat("MM-dd-yyyy / hh:mm:ss").format(now) + "\n");
		
		Runnable timeStamp1 = new Runnable() {
			@Override
			public void run() {
				Date now = new Date();
				System.out.println("TimeStamp1 : " + new SimpleDateFormat("MM-dd-yyyy / hh:mm:ss").format(now));
				try{
				Thread.sleep(6000);
				}catch(Exception e){}
			}	
		};
		
		Runnable timeStamp2 = new Runnable() {
			@Override
			public void run() {
				Date now = new Date();
				System.out.println("TimeStamp2 : " + new SimpleDateFormat("MM-dd-yyyy / hh:mm:ss").format(now)+ "\n");
				try{
				Thread.sleep(6000);
				}catch(Exception e){}
			}
		};
		
		Runnable interrupt = new Runnable() {
			@Override
			public void run() {
				while(true){
					
				}
			}
		};
		
		/*for(int i=0; i<INTERRUPTER_NUM; i++){
			interrupter[i] = Executors.newSingleThreadScheduledExecutor();
			interrupter[i].execute(interrupt);
		}*/
		//timer1.scheduleAtFixedRate(timeStamp1, 2, 4, TimeUnit.SECONDS);
		timer2.scheduleWithFixedDelay(timeStamp2, 5, 4, TimeUnit.SECONDS);
		
	}
}
