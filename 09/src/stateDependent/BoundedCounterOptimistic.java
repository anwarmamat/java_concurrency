package stateDependent;

public class BoundedCounterOptimistic {
	private int value = 0;
	private final int upperBound;
	
	//INVARIANT:  in all instances 0 <= value <= upperBound
	
	//Precondition:  argument must be >= 0
	//Postcondition:  object created
	//Exception:  If argument < 0, IllegalArgumentException thrown
	BoundedCounterOptimistic (int upperBound) throws IllegalArgumentException {
		if (upperBound >= 0) this.upperBound = upperBound;
		else throw new IllegalArgumentException (
				"Bad argument to BoundedCounter: " + upperBound + "; must be >= 0");
	}
	
	//Precondition:  none
	//Postcondition:  current value returned
	//Exception:  none	
	public synchronized int current () {
		return value;
	}
	
	// Precondition:  none
	// Postcondition:  update value to newState if value is equal to new state; return
	//	boolean indicating update succeeded
	// Exception:  none
	public synchronized boolean commit (int oldState, int newState) {
		if (value == oldState) {
			value = newState;
			return true;
		}
		else return false;
	}
	
	//Precondition:  none
	//Postcondition:  value reset to 0
	//Exception:  none
	public void reset () {
		for (;;) {
			int currentState = current();
			if (commit(currentState, 0)) break;
			else Thread.yield(); // give someone a chance to modify
		}
	}
	
	//Precondition:  none
	//Postcondition:  returns boolean indicating whether or not value is maxed out
	//Exception:  none
	public boolean isMaxed () {
		return (current() == upperBound);
	}
		
	//Precondition:  none
	//Postcondition:  increment value if not maxed; otherwise, do nothing.
	//Exception:  none
	public void inc () {
		for (;;) { // Retry-based
			int currentState = current();
			if ((currentState < upperBound) && (commit(currentState, currentState+1)))
				break;
			else Thread.yield();
		}
	}
	
	//Precondition:  none
	//Postcondition:  decrement value if not maxed; otherwise, do nothing.
	//Exception:  none
	public void dec () {
		for (;;) { // Retry-based
			int currentState = current();
			if ((currentState > 0) && (commit(currentState, currentState-1))) break;
			else Thread.yield();
		}
	}


}
