package passenger;

public class PassengerModel {
    private String title, firstName, lastName, email, phone, city, state, dob;

    public PassengerModel(String title, String firstName, String lastName, String email, String phone, String city, String state, String dob) {
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.state = state;
        this.dob = dob;
    }

    // Getters
    public String getFullName() {
        return title + " " + firstName + " " + lastName;
    }

    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getDob() { return dob; }
}
