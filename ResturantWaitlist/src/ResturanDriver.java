import java.util.Scanner;

import javax.swing.JOptionPane;
public class ResturanDriver {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("what is the password?: ");
		String response = scanner.nextLine();
		if("yareliStinks".equalsIgnoreCase(response)) {
			
		while(true) {
			
			String name = JOptionPane.showInputDialog("What is your name?:");
			String Name = Resturant.getName(name);
			
			String address = JOptionPane.showInputDialog("what is your address? :");
			String Address = Resturant.getAdress(address);
			
			String partysize = JOptionPane.showInputDialog("How many people?: ");
			int PartySize = Integer.parseInt(partysize);
			int Partysize = Resturant.getPartysize(PartySize);
			
			String phonenumber = JOptionPane.showInputDialog("What is your phone number?: ");
			int Phonenumber = Integer.parseInt(phonenumber);
			int PhoneNumber = Resturant.getPhoneNumber(Phonenumber);
			
			Resturant resturant = new Resturant(Name,Address,Partysize, PhoneNumber);
			
			resturant.Info();
			
			String Response = JOptionPane.showInputDialog("Do you want to add another party?:");
			if (!"Yes".equalsIgnoreCase(Response)) {
			break;
			}
			
			
			
		}
			
		}else {
			System.out.println ("OKAY BYE!!!");
		}
		 
		scanner.close();
		
		
		

	}

}
