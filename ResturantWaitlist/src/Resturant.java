
public class Resturant {
	
	private static String name;
	private static String address;
	private static int Partysize;
	private static int PhoneNumber;
	
	public Resturant(String name, String address, int Partysize,int PhoneNumber ) {
		this.name = name;
		this.address = address;
		this.Partysize = Partysize;
		this.PhoneNumber = PhoneNumber;
	}
	
	public static String getName(String name) {
		return name;
	}
	
	
	public static String getAdress(String address) {
		return address;
	}
	
	public static int getPartysize(int Partysize) {
		return Partysize;
	}
	
	public static int getPhoneNumber(int PhoneNumbe) {
		return PhoneNumbe;
	}
	
	public void Info() {
		System.out.println("name entered:" + name + "\naddress entered: " + address + "\nPartySize entered: " + Partysize + "\nPhoneNumber: " + PhoneNumber);
		
		
	}
	
	

}
