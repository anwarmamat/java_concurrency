import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
 
public class ScheduledExample {
    final static DateFormat fmt = DateFormat.getTimeInstance(DateFormat.LONG);
    public static void main(String[] args) {
        // Create a scheduled thread pool with 5 core threads
        ScheduledThreadPoolExecutor sch = (ScheduledThreadPoolExecutor) 
                Executors.newScheduledThreadPool(5);
         
        // Create a task for one-shot execution using schedule()
        Runnable oneShotTask = new Runnable(){
            @Override
            public void run() {
                System.out.println("\t oneShotTask Execution Time: "
                            + fmt.format(new Date()));
            }
        };
         
        // Create another task
        Runnable delayTask = new Runnable(){
            @Override
            public void run() {
                try{
                    System.out.println("\t delayTask Execution Time: "
                            + fmt.format(new Date()));
                    Thread.sleep(10 * 1000);
                    System.out.println("\t delayTask End Time: "
                            + fmt.format(new Date()));
                }catch(Exception e){
                     
                }
            }
        };
         
        // And yet another
        Runnable periodicTask = new Runnable(){
            @Override
            public void run() {
                try{
                    System.out.println("\t periodicTask Execution Time: "
                            + fmt.format(new Date()));
                    Thread.sleep(2 * 1000);
                    System.out.println("\t periodicTask End Time: "
                            + fmt.format(new Date()));
                }catch(Exception e){
                     
                }
            }
        };
         
        System.out.println("Submission Time: " + fmt.format(new Date()));
        
        
        
      //ScheduledFuture<?> oneShotFuture = sch.schedule(oneShotTask, 5, TimeUnit.SECONDS);
        
        
        /**
        Creates and executes a periodic action that becomes enabled first after the given 
        initial delay, and subsequently with the given delay between the termination of 
        one execution and the commencement of the next. If any execution of the task 
        encounters an exception, subsequent executions are suppressed. Otherwise, the 
        task will only terminate via cancellation or termination of the executor.
        */
        
       /**
		   delay start 5 secs. after the task finishes wait 5 secs and start again
       */
       
	//       ScheduledFuture<?> delayFuture = sch.scheduleWithFixedDelay(delayTask, 5, 5, TimeUnit.SECONDS);
       
        /**
        Creates and executes a periodic action that becomes enabled first after the given
         initial delay, and subsequently with the given period; that is executions will 
         commence after initialDelay then initialDelay+period, then initialDelay + 2 * period,
          and so on. If any execution of the task encounters an exception, subsequent 
          executions are suppressed. Otherwise, the task will only terminate via 
          cancellation or termination of the executor. If any execution of this task 
          takes longer than its period, then subsequent executions may start late, but
           will not concurrently execute.
       	*/ 
        
	    /**
		   delay start 5 secs. start the task in every 5 secs. 
		   if tasks run longer than 5 secs, start immediately after 
		   the tasks finishes
       */
	ScheduledFuture<?> periodicFuture = sch.scheduleAtFixedRate(periodicTask, 5, 5, TimeUnit.SECONDS);
    }
}
