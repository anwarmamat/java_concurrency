import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;

public class MyServer{
  public static void main(String args[]){
    try{
      Adder obj = new AdderRemote();
      Adder stub = (Adder) UnicastRemoteObject.exportObject(obj, 0);
      Registry registry = LocateRegistry.getRegistry();
      registry.rebind("Adder", stub);

    }catch(Exception e){
      System.out.println(e);
    }
  }
}
