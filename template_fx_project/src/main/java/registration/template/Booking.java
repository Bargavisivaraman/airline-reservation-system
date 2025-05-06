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

    // Return flight info (nullable for one-way bookings)
    private final String returnDepartureCode;
    private final String returnArrivalCode;
    private final String returnDepartDate;
    private final String returnDepartTime;
    private final String returnArrivalTime;
    private final String returnDuration;

    // One-way constructor
    public Booking(String bookingId, String departureCode, String arrivalCode, String departDate, String departTime,
                   String arrivalTime, String duration, double price, List<Passenger> passengers) {
        this.bookingId = bookingId;
        this.departureCode = departureCode;
        this.arrivalCode = arrivalCode;
        this.departDate = departDate;
        this.departTime = departTime;
        this.arrivalTime = arrivalTime;
        this.duration = duration;
        this.price = price;
        this.passengers = passengers;

        this.returnDepartureCode = null;
        this.returnArrivalCode = null;
        this.returnDepartDate = null;
        this.returnDepartTime = null;
        this.returnArrivalTime = null;
        this.returnDuration = null;
    }

    // Round-trip constructor
    public Booking(String bookingId, String departureCode, String arrivalCode, String departDate, String departTime,
                   String arrivalTime, String duration,
                   String returnDepartureCode, String returnArrivalCode, String returnDepartDate,
                   String returnDepartTime, String returnArrivalTime, String returnDuration,
                   double price, List<Passenger> passengers) {
        this.bookingId = bookingId;
        this.departureCode = departureCode;
        this.arrivalCode = arrivalCode;
        this.departDate = departDate;
        this.departTime = departTime;
        this.arrivalTime = arrivalTime;
        this.duration = duration;

        this.returnDepartureCode = returnDepartureCode;
        this.returnArrivalCode = returnArrivalCode;
        this.returnDepartDate = returnDepartDate;
        this.returnDepartTime = returnDepartTime;
        this.returnArrivalTime = returnArrivalTime;
        this.returnDuration = returnDuration;

        this.price = price;
        this.passengers = passengers;
    }

    public String getBookingId() { return bookingId; }
    public List<Passenger> getPassengers() { return passengers; }
    public double getPrice() { return price; }

    public String getDepartureCode() { return departureCode; }
    public String getArrivalCode() { return arrivalCode; }
    public String getDepartDate() { return departDate; }
    public String getDepartTime() { return departTime; }
    public String getArrivalTime() { return arrivalTime; }
    public String getDuration() { return duration; }

    public String getReturnDepartureCode() { return returnDepartureCode; }
    public String getReturnArrivalCode() { return returnArrivalCode; }
    public String getReturnDepartDate() { return returnDepartDate; }
    public String getReturnDepartTime() { return returnDepartTime; }
    public String getReturnArrivalTime() { return returnArrivalTime; }
    public String getReturnDuration() { return returnDuration; }

    public String getSummary() {
        String date = (departDate == null || departDate.isBlank()) ? "unknown date" : departDate;
        String time = (departTime != null) ? departTime : "unknown time";
        return departureCode + " → " + arrivalCode + " on " + date + " at " + time;
    }
}
