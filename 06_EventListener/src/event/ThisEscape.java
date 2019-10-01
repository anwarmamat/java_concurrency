/**
 * ThisEscape
 * <p/>
 * Implicitly allowing the this reference to escape
 *
 * @author Brian Goetz and Tim Peierls
 */

package event;
import java.util.*;

public class ThisEscape {
  private final int num;

  public ThisEscape(EventSource source) {
    source.registerListener(
        new EventListener() {
          public void onEvent(Event e) {
            doSomething(e);
          }
        });
    num = 42;
  }

  private void doSomething(Event e) {
    if (num != 42) {
      System.out.println("Race condition detected at " +
          new Date());
    }
  }
}    
  
