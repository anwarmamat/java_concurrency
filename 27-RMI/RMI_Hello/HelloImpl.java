// Implementing the remote interface
public class HelloImpl implements Hello {

   // Implementing the interface method
   public String printMsg() {
      System.out.println("This is an example RMI program");
      return "a Message from the server";
   }
}
