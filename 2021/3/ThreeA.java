import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.ArrayList;

public class ThreeA {
	public static void main(String[] args) {

		String fp = System.getProperty("user.dir") + "\\input";
		FileReader fr = null;
		try {
			fr = new FileReader(fp);
			System.out.println("file opened");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (fr != null) {
	
			Scanner s = new Scanner(fr);
			
			int counter = 0;
			String data = s.next();
			int length = data.length();
			s.nextLine();
			
			int[] bitSum = new int[length];
			//ArrayList<String> input = new ArrayList<String>();
			//input.add(data);
			
			// sum bits
			while (s.hasNext()) {
				if (counter > 0) {
					data = s.next();
					//input.add(data);
					s.nextLine();
				}
				
				for (int i = 0; i < length; i++) {
					bitSum[i] += Integer.parseInt(data.substring(i, i+1));
				}
				counter++;
			}
			
			//System.out.println(counter);
			
			String gamma = "";
			String epsilon = "";
			
			for(int i = 0; i < length; i++) {
				if (bitSum[i] > 500) {
					gamma += "1";
					epsilon += "0";
				}
				else {
					gamma += "0";
					epsilon += "1";
				}
			}
			
			System.out.printf("gamma %s : %d\n", gamma, binToDec(gamma));
			System.out.printf("epsilon %s : %d\n", epsilon, binToDec(epsilon));
			
			System.out.printf("answer 1 : %d\n", binToDec(epsilon)*binToDec(gamma));
			
//			System.out.printf("oxygen %s : %d\n", findRating(gamma, input, length), binToDec(findRating(gamma, input, length)));
//			System.out.printf("CO2 %s : %d\n", findRating(epsilon, input, length), binToDec(findRating(epsilon, input, length)));
//			
//			System.out.printf("answer 2 : %d\n", binToDec(findRating(gamma, input, length))*binToDec(findRating(epsilon, input, length)));
			
			
//			System.out.println(gamma);
//			System.out.println(findRating(gamma, input, length));
//			System.out.println(epsilon);
//			System.out.println(findRating(epsilon, input, length));
		}
		
		
		
	}
	
//	static String findRating(String s, ArrayList<String> input, int length) {
//		String result = "";
//		for (int i = length; i >= 0; i--) {
//			int num = 0;
//			for (String temp : input) {
//				if (temp.startsWith(s.substring(0, i))) {
//					return temp;
//					
//				}
//			}
//
//		}
//		return result;
//	}
	
	static long binToDec(String s) {
		long result = 0;
		int exponent = 0;
		
		for (int i = s.length() - 1; i >= 0; i--) {
			result += (s.charAt(i) == '1' ? Math.pow(2, exponent) : 0);
			exponent++;
		}
		return result;
	}
}
