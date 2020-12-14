package copyonwritearraylist;

import java.util.*;
import java.util.concurrent.*;
 
/**
 *
 * This program demonstrates how CopyOnWriteArrayList works.
 *  
 * Writer1: adds an item to the list 
 * Writer2: removes an item from the list
 * Reader: Iterates the list the prints the list.
 * 
 * If non-thread-safe collection is used instead of 
 * CopyOnWriteArrayList, it will fail
 * 
 */
public class CopyOnWriteArrayListExample {
 
    public static void main(String[] args) {
 
        List<Integer> list = new CopyOnWriteArrayList<>();
    	//List<Integer> list = new ArrayList<>();   //will fail
 
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        new WriteThread1("Writer1", list).start();
        new WriteThread2("Writer2", list).start();
        new ReadThread("Reader", list).start();
    }
}
 
 
class WriteThread1 extends Thread {
    private List<Integer> list;
    public WriteThread1(String name, List<Integer> list) {
        this.list = list;
        super.setName(name);
    }
    public void run() {
        int count = 6;
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            list.add(count++);
            System.out.println(super.getName() + " done");
        }
    }
}
 
class WriteThread2 extends Thread {
    private List<Integer> list;
    public WriteThread2(String name, List<Integer> list) {
        this.list = list;
        super.setName(name);
    }
    public void run() {
       int count = 0;
       while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            list.remove(count);
            System.out.println(super.getName() + " done");
       }
    }
}
 
 
class ReadThread extends Thread {
    private List<Integer> list;
 
    public ReadThread(String name, List<Integer> list) {
        this.list = list;
        super.setName(name);
    }
 
    public void run() {
        while (true) {
            String output = "\n" + super.getName() + ":";
            Iterator<Integer> iterator = list.iterator();
            while (iterator.hasNext()) {
                Integer next = iterator.next();
                output += " " + next;
                // fake processing time
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println(output);
        }
    }
}