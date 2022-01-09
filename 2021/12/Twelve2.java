import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

public class Twelve2 {
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

		String[][] routes = new String[25][2];
		HashMap<String, Integer> used = new HashMap<String, Integer>();

		if (fr != null) {
			Scanner s = new Scanner(fr);
			int lineNum = 0;
			while (s.hasNext()) {
				String line = s.nextLine().strip();
				routes[lineNum] = line.split("-");
				lineNum++;
			}

			for (int i = 0; i < 25; i++) {
				for (int j = 0; j < 2; j++) {
					System.out.print(routes[i][j] + " ");
					used.putIfAbsent(routes[i][j], 0);
				}
				System.out.println();
			}

			for (int i = 0; i < 25; i++) {
				for (int j = 0; j < 2; j++) {
					if (routes[i][j].equals("start")) {

						used.replace(routes[i][j], used.get(routes[i][j]) + 1);

						explore(i, (j == 1 ? 0 : 1), routes, ("start"), used);

						used.replace(routes[i][j], used.get(routes[i][j]) - 1);

					}
				}
			}
		}

		long end = System.currentTimeMillis();
		long duration = end - start;
		System.out.println("Finished in " + duration + " milliseconds");
	}

	public static void explore(int i, int j, String[][] routes, String path, HashMap<String, Integer> used) {

		if (routes[i][j].equals("end") || routes[i][j == 1 ? 0 : 1].equals("end")) {
			System.out.println(path + ",end");
		} else {
			for (int row = 0; row < 25; row++) {

				for (int column = 0; column < 2; column++) {
					if (routes[row][column].equals(routes[i][j])) {
						if (((!routes[row][column].equals(routes[row][column].toLowerCase())) || ((routes[row][column].equals(routes[row][column].toLowerCase()) && used.get(routes[row][column]) <= 1)))) {// && ((!routes[row][column == 1 ? 0 : 1].equals(routes[row][column == 1 ? 0 : 1].toLowerCase())) || (routes[row][column == 1 ? 0 : 1].equals(routes[row][column == 1 ? 0 : 1].toLowerCase()) && used.get(routes[row][column == 1 ? 0 : 1]) <= 1))) {
							int numSmallsTwice = 0;
							used.replace(routes[i][j], used.get(routes[i][j]) + 1);
							for (String key : used.keySet()) {
								if (used.get(key) == 2 && key.equals(key.toLowerCase())) {
									numSmallsTwice++;
								}
							}
							if (numSmallsTwice > 1) {
								used.replace(routes[i][j], used.get(routes[i][j]) -1);
								continue;
							}
							if (routes[row][column].equals("start")) {
								used.replace(routes[i][j], used.get(routes[i][j]) -1);
								continue;
							}
							path += "," + routes[row][column];
							
							explore(row, column == 1 ? 0 : 1, routes, path, used);
							used.replace(routes[i][j], used.get(routes[i][j]) -1);
							path = path.substring(0, path.length() - routes[row][column].length() - 1);
						}
					}
					
				}

			}

		}
		return;

	}
}
