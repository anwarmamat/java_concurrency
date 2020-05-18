

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.math.BigDecimal;
//import Compute;

public class Client {
    public static void main(String args[]) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "Compute";
            Registry registry = LocateRegistry.getRegistry(args[0]);
            Compute comp = (Compute) registry.lookup(name);
            Adder task = new Adder(10,20);
            Integer sum = comp.executeTask(task);
            System.out.println(sum);
        } catch (Exception e) {
            System.err.println("Adder exception:");
            e.printStackTrace();
        }
    }
}
