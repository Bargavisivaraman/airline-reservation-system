import java.util.Scanner;

public class Lab2 {
   
   /* TODO: Write recursive digitCount() method here. */
   public static int digitCount(int num) {
	   
	   if(num < 10 && num > -10) {
		   return 1;
	   }
	   return 1 + (digitCount(num / 10));
   }
	
   public static void main(String[] args) {
      Scanner scnr = new Scanner(System.in);
      int num, digits;
      
      System.out.print("Enter an integer: ");
      num = scnr.nextInt();
      scnr.close();
      digits = digitCount(num);
      
      if(digits == 1) {
    	System.out.println("There is " + digits + " digit in the number " + num);
      }
      else {
      	System.out.println("There are " + digits + " digits in the number " + num);
      }
   }
}