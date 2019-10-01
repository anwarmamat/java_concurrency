package stateDependent;
public class BoundedBufferExceptioneDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BoundedBufferException buffer = new BoundedBufferException(1);
		
		try {
			buffer.put(new Integer(1));
			System.out.println("put 1");
			//buffer.put(new Integer(2));
			//System.out.println("put 2");
			buffer.take();
			System.out.println("take 1");
			buffer.take();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
