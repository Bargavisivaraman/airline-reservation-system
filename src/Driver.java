
public class Driver {

	public static void main(String[] args) {
		
		//seat:p1(passanger) p2(passanger)
		//passenger: name(string),dlnum(String),boardinggroupid(int)
		//flight needs (String),location.datetime, and a seat
		
		Passenger p1 = new Passenger("Tommy Lee", "B123456", 1);
		Passenger p2 = new Passenger("George Lopez", "B987654", 2);
		
		Seat seat = new Seat(p1,p2);
		Flight flight = new Flight("NYC", "10-05-2023-7:00", false,seat);
		
		flight.p1Info();
		
		
		

	}
	
	
}
