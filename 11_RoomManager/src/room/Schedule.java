package room;

class Schedule extends Thread{
	private PatientType type;
	private RoomManager room;
	public Schedule(RoomManager m, PatientType t) {
		room = m;
		type = t;
	}
	public void run() {
		if(type == PatientType.NonCovid)
			room.noncovid();
		else
			room.covid();
	}
}