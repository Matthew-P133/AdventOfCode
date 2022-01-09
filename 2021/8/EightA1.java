import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Arrays;

public class EightA1 {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();

		String fp = System.getProperty("user.dir") + "\\input";
		FileReader fr = null;
		try {
			fr = new FileReader(fp);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int oneFourSevenEights = 0;
		int outputSum = 0;
		
		if (fr != null) {
			Scanner s = new Scanner(fr);
			while (s.hasNext()) {
				String line = s.nextLine();
				String [] numbers = line.substring(line.indexOf("|")+1, line.length()).strip().split(" ");
				String[] digits = line.substring(0, line.indexOf("|")).split(" ");
				
				
				// part 1

				for (String number : numbers) {
					if (number.length() == 2 || number.length() == 4 || number.length() == 3 || number.length() == 7) {
						oneFourSevenEights++;
					}
				}
				
				/* part 2 Approach
				 *
				 * 					0000
				 * 		1111					2222
				 * 		1111					2222
				 *					3333	
				 *		4444					5555
				 *		4444					5555
				 *					6666
				 *
				 *
				 *		
				 *		segment 1111 is			only segment that appears 6 times
				 *		segment 4444 is 		only segment that appears 4 times
				 *		segment 5555 is 		only segment that appears 9 times
				 *		
				 *		segment 2222 is 		the one in digit of length 2 which isn't segment 5
				 *      segment 0000 is 		the one which appears 8 times and isn't segment 2
				 *		segment 3333 is 		the one in digit of length 4 which isn't segment 1,2 or 5

				 *		segment 6666 is 		the last one left
				 *				
				*/
				
				
				
				String[] segment = new String[7];

				
				// number of appearances of each segment
				HashMap<String, Integer> segmentAppearances = new HashMap<String, Integer>();
				for (String st : digits) {
					for (int i = 0; i < st.length(); i ++) {
						String position = st.substring(i, i + 1);
						if (!segmentAppearances.containsKey(position)) {
							segmentAppearances.put(position, 1);
						} else {
							segmentAppearances.replace(position, segmentAppearances.get(position)+1);
						}
					}
				}
				
				// identify letters corresponding to segments 1, 4 and 5
				for (String seg : segmentAppearances.keySet()) {

					if (segmentAppearances.get(seg).equals(4)) {
						segment[4] = seg;
					}
					if (segmentAppearances.get(seg).equals(6)) {
						segment[1] = seg;
					}
					if (segmentAppearances.get(seg).equals(9)) {
						segment[5] = seg;
					}
				}
				
				// identify letter corresponding to segment 2
				for (String digit : digits) {
					if (digit.length() == 2) {
						for (int i = 0; i < digit.length(); i++) {
							if (!digit.substring(i, i + 1).equals(segment[5])) {
								segment[2] = digit.substring(i, i + 1);
							}
						}
					}

				}
				
				for (String seg : segmentAppearances.keySet()) {

					if (segmentAppearances.get(seg).equals(8) && !seg.equals(segment[2])) {
						segment[0] = seg;
					}
				}

				// identify letter corresponding to segment 3
				for (String digit : digits) {

					if (digit.length() == 4) {
						for (int i = 0; i < digit.length(); i++) {
							String letter = digit.substring(i, i + 1);
							if (!letter.equals(segment[5]) && !letter.equals(segment[2])
									&& !letter.equals(segment[1])) {
								segment[3] = letter;
							}
						}
					}

				}

				// identify letter corresponding to segment 6
				String letters = "abcdefg";
				for (String seg : segment) {
					if (seg != null) {
						int index = letters.indexOf(seg);
						
						if (index != -1) {
						letters = letters.substring(0, index) + letters.substring(index + 1, letters.length());
						}
					}
				}
				segment[6] = letters;

				
				String temp = "";
				for (String number : numbers) {
					temp += translate(number, segment);
				}
				//System.out.println(temp);
				outputSum += Integer.parseInt(temp);

			}
			// part 1
			System.out.println("Number of 1/4/7/8s: " + oneFourSevenEights);
			// part 2
			System.out.println("Sum: " + outputSum);

			
			long end = System.currentTimeMillis();
			long duration = end - start;
			System.out.println("Finished in " + duration + " milliseconds");
			
		}

	}

	public static int translate(String number, String[] segment) {

		String letter = "";
		int length = number.length();
		boolean thisNumber = true;

		if (length == 2) {
			// one
			return 1;
		}
		if (length == 4) {
			return 4;
		}
		if (length == 7) {
			return 8;
		}
		if (length == 3) {
			return 7;
		}

		if (length == 5) {
			// two
			thisNumber = true;
			for (int i = 0; i < number.length(); i++) {
				if (length != 5) {
					thisNumber = false;
				}
				letter = number.substring(i, i + 1);
				if (!(letter.equals(segment[0]) || letter.equals(segment[2]) || letter.equals(segment[3])
						|| letter.equals(segment[4]) || letter.equals(segment[6]))) {
					thisNumber = false;
				}
			}
			if (thisNumber) {
				return 2;
			}

			// three
			thisNumber = true;
			for (int i = 0; i < number.length(); i++) {
				if (length != 5) {
					thisNumber = false;
				}
				letter = number.substring(i, i + 1);
				if (!(letter.equals(segment[0]) || letter.equals(segment[2]) || letter.equals(segment[3])
						|| letter.equals(segment[5]) || letter.equals(segment[6]))) {
					thisNumber = false;
				}
			}
			if (thisNumber) {
				return 3;
			}
			
			//five
			thisNumber = true;
			for (int i = 0; i < number.length(); i++) {
				if (length != 5) {
					thisNumber = false;
				}
				letter = number.substring(i, i+1);
				if (!(letter.equals(segment[0]) || letter.equals(segment[1])|| letter.equals(segment[3]) || letter.equals(segment[5]) || letter.equals(segment[6]))) {
					thisNumber = false;
				}
			}
			if (thisNumber) {
				return 5;
			}
		}
		
		if (length == 6) {
			//six
			thisNumber = true;
			for (int i = 0; i < number.length(); i++) {
				if (length != 6) {
					thisNumber = false;
				}
				letter = number.substring(i, i+1);
				if (!(letter.equals(segment[0]) || letter.equals(segment[1])|| letter.equals(segment[3]) || letter.equals(segment[5]) || letter.equals(segment[6])|| letter.equals(segment[4]))) {
					thisNumber = false;
				}
			}
			if (thisNumber) {
				return 6;
			}
			

			
			//zero
			
			thisNumber = true;
			for (int i = 0; i < number.length(); i++) {
				if (length != 6) {
					thisNumber = false;
				}
				letter = number.substring(i, i+1);
				if (!(letter.equals(segment[0]) || letter.equals(segment[1]) || letter.equals(segment[2]) || letter.equals(segment[4]) || letter.equals(segment[5]) || letter.equals(segment[6]))) {
					thisNumber = false;
				}
			}
			if (thisNumber) {
				return 0;
			}
			
		
			
		}
		return 9;


		
		
		

		
		
		
		
		
		
	}
}
