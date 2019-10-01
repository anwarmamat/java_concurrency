package stateDependent;
public class BoundedBufferReturnCodeDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BoundedBufferReturnCode buffer = new BoundedBufferReturnCode(1);
		BoundedBufferReturnCode.ReturnVal v;
		v = buffer.put(new Integer(1));
		System.out.println (v.code);
		v = buffer.put(new Integer(2));
		System.out.println (v.code);
		v = buffer.take();
		System.out.println (v.code);
		System.out.println (v.obj);
		v = buffer.take();
		System.out.println (v.code);
	}

}
