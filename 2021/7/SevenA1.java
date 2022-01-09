import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Arrays;

public class SevenA1 {
	public static void main(String[] args) {

		String fp = System.getProperty("user.dir") + "\\input";
		FileReader fr = null;
		try {
			fr = new FileReader(fp);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (fr != null) {
			Scanner s = new Scanner(fr);
			s.useDelimiter(",");

			int[] crabs = s.tokens().mapToInt((n) -> Integer.parseInt(n.strip())).toArray();

			Arrays.sort(crabs);

			int median = crabs.length % 2 == 1 ? crabs[crabs.length / 2]
					: (int) Math.round(1.0 * (crabs[crabs.length / 2] + crabs[crabs.length / 2 + 1]) / 2);

			int sum = 0;
			for (int crab : crabs) {
				sum += crab;
			}
			int meanHigh = (int) Math.floor(1.0 * sum / crabs.length);
			int meanLow = (int) Math.ceil(1.0 * sum / crabs.length);

			// part 1
			int target = median;
			int part1fuelSum = 0;
			for (int crab : crabs) {
				part1fuelSum += Math.abs(crab - target);
			}

			// part 2
			target = meanHigh;
			int fuelSumA = 0;
			for (int crab : crabs) {
				fuelSumA += triangle(Math.abs(crab - target));
			}

			target = meanHigh;
			int fuelSumB = 0;
			for (int crab : crabs) {
				fuelSumB += triangle(Math.abs(crab - target));
			}

			int part2FuelSum = fuelSumA < fuelSumB ? fuelSumA : fuelSumB;

			System.out.println("Part 1: " + part1fuelSum);
			System.out.println("Part 2: " + part2FuelSum);

		}
	}

	public static int triangle(int n) {
		return (n * (n + 1)) / 2;
	}
}
