import java.rmi.*;
import java.rmi.server.*;

public class AdderRemote implements Adder{ //extends UnicastRemoteObject

  //AdderRemote()throws RemoteException{
//    super();
//  }
  public int add(int x,int y){
    return x  * y;
  }
}
