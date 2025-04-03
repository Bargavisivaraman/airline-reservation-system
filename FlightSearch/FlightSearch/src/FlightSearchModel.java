public class FlightSearchModel {

    Integer countryID;
    String iata_code, airport_name;
    String city_name, country_name;

    public FlightSearchModel(Integer countryID, String iata_code, String airport_name, String city_name,
            String country_name) {
        this.countryID = countryID;
        this.iata_code = iata_code;
        this.airport_name = airport_name;
        this.city_name = city_name;
        this.country_name = country_name;
    }

    public Integer getCountryID() {
        return countryID;
    }

    public String getIata_code() {
        return iata_code;
    }

    public String getAirport_name() {
        return airport_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountryID(Integer countryID) {
        this.countryID = countryID;
    }

    public void setIata_code(String iata_code) {
        this.iata_code = iata_code;
    }

    public void setAirport_name(String airport_name) {
        this.airport_name = airport_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

}
