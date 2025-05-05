package registration.template;

public class UserSession {
    private static String firstName;
    private static String lastName;
    private static String email;
    private static String reservationId;

    public static void setFirstName(String name) { firstName = name; }
    public static void setLastName(String name) { lastName = name; }
    public static void setEmail(String mail) { email = mail; }
    public static void setReservationId(String id) { reservationId = id; }

    public static String getFirstName() { return firstName; }
    public static String getLastName() { return lastName; }
    public static String getEmail() { return email; }
    public static String getReservationId() { return reservationId; }

    public static boolean isLoggedIn() {
        return firstName != null && email != null;
    }
    
    public static void clearSession() {
        firstName = null;
        lastName = null;
        email = null;
        reservationId = null;
    }
}