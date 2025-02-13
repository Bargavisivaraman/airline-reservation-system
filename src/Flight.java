import javax.swing.JOptionPane;

class Flight {

	private String location; // FINAL destination
	private String dateTime;
	private Seat seat; //instance
	private boolean isCancelled;
	
	public Flight(String location, String dateTime, boolean isCancelled, Seat seat) { //constructor
		this.location = location;
		this.dateTime = dateTime;
		this.seat = seat;	
		this.isCancelled = isCancelled;
	}
	
	// getters
	public String getLocation() { return location; }
	public String getDateTime() { return dateTime; }
	public Seat getSeat() { return seat; }
	public boolean isCancelled() { return isCancelled; }
	
	// setters
	public void setDateTime(String dateTime) { this.dateTime = dateTime; }
	public void setSeat(Seat seat) { this.seat = seat; }
	public void setCancelled(boolean isCancelled) { this.isCancelled = isCancelled; }
	
	
	
	public void p1Info() {
		String flighQuestion = JOptionPane.showInputDialog("What flight inforamtion would you like to see? p1/p2 and what output option? Joption or Scanner");
		
		if("P1/Joption".equalsIgnoreCase(flighQuestion)) {
			JOptionPane.showMessageDialog(null," Flight is headed to: " + location + "\n Flight Arrival: " + dateTime + "\n Flight Passangers names: " + seat.getP1());
		}
		else if("p2/Joption".equalsIgnoreCase(flighQuestion)) {
			JOptionPane.showMessageDialog(null," Flight is headed to: " + location + "\n Flight Arrival: " + dateTime + "\n Flight Passangers names: " + seat.getP2());			
		}		
		else if("P1/Scanner".equalsIgnoreCase(flighQuestion)) {
			System.out.println(" Flight is headed to: " + location + "\n Flight Arrival: " + dateTime + "\n Flight Passangers names: " + seat.getP1().getName());
		}
		else {
			System.out.println(" Flight is headed to: " + location + "\n Flight Arrival: " + dateTime + "\n Flight Passangers names: " + seat.getP2());
		
		}
	
	}
	
	
	
		
}

