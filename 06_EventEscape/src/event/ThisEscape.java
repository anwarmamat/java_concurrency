package event;
import java.util.*;

public class ThisEscape {
  private final int num;

  public ThisEscape(Taker taker) {
	  taker.insert(
        new Event() {
          public void go() {
            doSomething();
          }
        });
	  //---->
    num = 42; 
  }

  private void doSomething() {
    if (num != 42) {
      System.out.println("Race condition detected at " +
          new Date());
    }
  }
}    
  
