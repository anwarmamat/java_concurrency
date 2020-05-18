import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MyClient{
  public static void main(String args[]){
    try{
        Registry registry = LocateRegistry.getRegistry(null);
         // Looking up the registry for the remote object
        Adder stub = (Adder) registry.lookup("Adder");
        System.out.println(stub.add(100, 200));
    }catch(Exception e){

    }
  }
}
