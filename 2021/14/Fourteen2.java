import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

public class Fourteen2 {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();

		String fp = "input";
		FileReader fr = null;
		try {
			fr = new FileReader(fp);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (fr != null) {
			Scanner s = new Scanner(fr);
			String polymerTemplate = "";
			String[][] instructions = new String[100][2];
			HashMap<String, Pair> pairs = new HashMap<String, Pair>();

			// parse template and instructions
			polymerTemplate = s.nextLine().strip();
			s.nextLine();
			for (int i = 0; i < 100; i++) {
				String[] instruction = s.nextLine().strip().split(" -> ");
				instructions[i][0] = instruction[0];
				instructions[i][1] = instruction[1];
			}

			// generate data structure
			pairs = findPairs("KFVHSNCPOB");

			// populate with starting state
			pairs = count(pairs, polymerTemplate);

			for (String pair : pairs.keySet()) {
				if (pair.length() == 1) {
					System.out.println(pair + " " + pairs.get(pair).count);
				}
			}

			for (int i = 0; i < 40; i++) {

				pairs = step(pairs, instructions);

				System.out.printf("After Step %d: \n", i + 1);

				for (String pair : pairs.keySet()) {
					if (pair.length() == 1) {
						System.out.println(pair + " " + pairs.get(pair).count);
					}
				}

			}

		}
		long end = System.currentTimeMillis();
		long actual = end - start;
		System.out.println("That took: " + actual + " ms");

	}

	public static HashMap<String, Pair> step(HashMap<String, Pair> pairs, String[][] instructions) {
		for (Pair pair : pairs.values()) {
			pair.newCount = pair.count;
		}

		for (String[] instruction : instructions) {
			String pair = instruction[0];
			String letter = instruction[1];

			pairs = grow(pairs, pair, letter);

		}
		for (Pair pair : pairs.values()) {
			pair.count = pair.newCount;
			pair.newCount = 0;

		}
		return pairs;
	}

	private static HashMap<String, Pair> grow(HashMap<String, Pair> pairs, String pair, String letter) {
		// reset pair to zero
		long temp = pairs.get(pair).count;
		pairs.get(pair).newCount -= temp;

		String first = pair.substring(0, 1) + letter;
		String second = letter + pair.substring(1, 2);

		// add number of new pairs formed
		pairs.get(first).newCount += temp;
		pairs.get(second).newCount += temp;

		// add number of placements of letter
		pairs.get(letter).newCount += temp;

		return pairs;
	}

	public static HashMap<String, Pair> findPairs(String letters) {
		// generate HashMap with key for each possible pair of letters and value of zero
		HashMap<String, Pair> pairs = new HashMap<String, Pair>();
		for (int a = 0; a < letters.length(); a++) {
			for (int b = 0; b < letters.length(); b++) {
				String pair = letters.substring(a, a + 1) + letters.substring(b, b + 1);
				pairs.putIfAbsent(pair, new Pair(pair));
			}
		}

		for (int i = 0; i < letters.length(); i++) {
			pairs.put(letters.substring(i, i + 1), new Pair(letters.substring(i, i + 1)));
		}

		return pairs;
	}

	public static HashMap<String, Pair> count(HashMap<String, Pair> pairs, String polymerTemplate) {
		// populate with starting state
		for (int a = 0; a < polymerTemplate.length() - 1; a++) {
			String pair = polymerTemplate.substring(a, a + 2);
			pairs.get(pair).count++;
		}

		for (int i = 0; i < polymerTemplate.length(); i++) {
			pairs.get(polymerTemplate.substring(i, i + 1)).count++;
		}

		return pairs;
	}
}
