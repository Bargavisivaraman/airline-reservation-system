package registration.template;

import java.util.List;

public class Booking {
    private final String bookingId;
    private final String departureCode;
    private final String arrivalCode;
    private final String departDate;
    private final String departTime;
    private final String arrivalTime;
    private final String duration;
    private final double price;
    private final List<Passenger> passengers;

    public Booking(String bookingId, String departureCode, String arrivalCode,
                   String departDate, String departTime, String arrivalTime,
                   String duration, double price, List<Passenger> passengers) {
                    
        this.bookingId = bookingId;
        this.departureCode = departureCode;
        this.arrivalCode = arrivalCode;
        this.departDate = departDate;
        this.departTime = departTime;
        this.arrivalTime = arrivalTime;
        this.duration = duration;
        this.price = price;
        this.passengers = passengers;
    }

    public String getBookingId() { 
        return bookingId; 
    }

    public String getSummary() {
    String date = (departDate == null || departDate.isBlank()) ? "unknown date" : departDate;
    String time = (departTime != null) ? departTime : "unknown time";
    return departureCode + " → " + arrivalCode + " on " + date + " at " + time;
    }

    public List<Passenger> getPassengers() { return passengers; }
    public double getPrice() { return price; }
}
