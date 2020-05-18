

//import Task;
import java.io.Serializable;
import java.math.BigDecimal;

public class Adder implements Task<Integer>, Serializable {

    private static final long serialVersionUID = 227L;
    private int a,b;

    /**
     * Construct a task to calculate pi to the specified
     * precision.
     */
    public Adder(int a, int b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Calculate pi.
     */
    public Integer execute() {
        return add(a,b);
    }


    public static Integer add(int a,int b) {
        return a + b;
    }

}
