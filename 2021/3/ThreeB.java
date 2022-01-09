import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.ArrayList;

public class ThreeB {
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
			String data = "";

			ArrayList<String> input = new ArrayList<String>();
			ArrayList<String> inputCopy = new ArrayList<String>();

			while (s.hasNext()) {

				data = s.next();
				input.add(data);
				inputCopy.add(data);
				s.nextLine();

				counter++;
			}


			boolean finished = false;
			long oxygenRating = 0;
			long CO2Rating = 0;

			while (!finished) {
				for (int i = 0; i < data.length(); i++) {
					ArrayList<String> filteredInputOx = new ArrayList<String>();
					int bitsum = 0;

					for (String temp : input) {
						bitsum += Integer.parseInt(temp.substring(i, i + 1));

					}

					for (String temp : input) {

						if (bitsum >= input.size() / 2 + input.size() % 2 && temp.substring(i, i + 1).equals("1")) {

							filteredInputOx.add(temp);
						}
						if (bitsum < input.size() / 2 + input.size() % 2 && temp.substring(i, i + 1).equals("0")) {

							filteredInputOx.add(temp);
						}
					}
					input = filteredInputOx;
					if (input.size() == 1) {
						//System.out.println(input.get(0));
						System.out.println("Oxygen rating: " + binToDec(input.get(0)));
						oxygenRating = binToDec(input.get(0));
						finished = true;
						break;
					}

				}
			}

			finished = false;

			while (!finished) {
				for (int i = 0; i < data.length(); i++) {
					ArrayList<String> filteredInputCO2 = new ArrayList<String>();

					int bitsum = 0;

					for (String temp : inputCopy) {
						bitsum += Integer.parseInt(temp.substring(i, i + 1));

					}


					for (String temp : inputCopy) {
						
						if (bitsum >= inputCopy.size() / 2 + inputCopy.size() % 2
								&& temp.substring(i, i + 1).equals("0")) {

							filteredInputCO2.add(temp);
						}
						if (bitsum < inputCopy.size() / 2 + inputCopy.size() % 2
								&& temp.substring(i, i + 1).equals("1")) {

							filteredInputCO2.add(temp);
						}
					}
					inputCopy = filteredInputCO2;



					if (inputCopy.size() == 1) {
						//System.out.println(inputCopy.get(0));
						System.out.println("CO2 rating: " + binToDec(inputCopy.get(0)));
						CO2Rating = binToDec(inputCopy.get(0));
						finished = true;
						break;
					}

				}
			}
			System.out.println("Part 2 Answer: " + oxygenRating*CO2Rating);
		}
		
	}

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
