package publishing_escape;
import java.lang.reflect.Field;

// 

/**
 * This tests accessing an outer object from an inner one.
 * Credit to:
 * http://stackoverflow.com/questions/763543/in-java-how-do-i-access-the-outer-class-when-im-not-in-the-inner-class
 *
 */
public class OuterInnerTest {

	public static void main(String[] args) {
		
		Outer.Inner v = new Outer().new Inner();
		
		//static nested class does not have a reference to the enclosing class
		//Outer.Inner v =new Outer.Inner();
		
		v.foo ();
	    try {
	    	Field outerThis = v.getClass().getDeclaredField("this$0");
			Outer u = (Outer)outerThis.get(v);
			u.foo();
	    } catch (NoSuchFieldException e) {
	    	throw new RuntimeException(e); 
	    }  catch (IllegalAccessException e) {
	    	throw new RuntimeException(e);
	    }
	}

}
