public class Deadlock {
	private static int count = 0;
	static class Friend {
		String name;

		public Friend(String name) {
			this.name = name;
		}

		public synchronized void bow(Friend bower) {
			System.out
					.println(count + ":\t" + "I, " + this.name + " bow to you, " + bower.name);
			count++;
			bower.bowBack(this);
		}

		public synchronized void bowBack(Friend bower) {
			System.out.println(count +":\t" + "I, " + this.name + " bow back to you,"
					+ bower.name);
			
		}		
	}

	public static void main(String[] args) {
		final Friend f1 = new Friend("Alphonse"), f2 = new Friend("Gaston");

		(new Thread(new Runnable() {
			public void run() {
				while (true) {
					f1.bow(f2);
				}
			}
		})).start();
		
		(new Thread(new Runnable() {
			public void run() {
				while (true) {
					f2.bow(f1);
				}
			}
		})).start();
	}
}