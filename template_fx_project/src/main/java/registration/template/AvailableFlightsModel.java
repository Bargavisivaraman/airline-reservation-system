package registration.template;

public class AvailableFlightsModel {
    private String flightID;
    private String departure;
    private String arrival;
    private String price;
    private Integer availableSeats;

    public String getFlightID() {
        return flightID;
    }

    public String getDeparture() {
        return departure;
    }

    public String getArrival() {
        return arrival;
    }

    public String getPrice() {
        return price;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public AvailableFlightsModel(String flightID, String departure, String arrival, String price, int availableSeats) {
        this.flightID = flightID;
        this.departure = departure;
        this.arrival = arrival;
        this.price = price;
        this.availableSeats = availableSeats;
    }
    
}
