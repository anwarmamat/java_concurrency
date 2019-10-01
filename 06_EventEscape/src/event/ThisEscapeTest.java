package event;
public class ThisEscapeTest {
  public static void main(String[] args) throws InterruptedException {
    Taker es = new Taker();
    es.start();
    while(true) {
      new ThisEscape(es);
      //Thread.sleep(100);
      System.out.println(".");
    }
  }
}
  
