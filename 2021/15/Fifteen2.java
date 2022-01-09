import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

public class Fifteen2 {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();

		String fp = "input";
		FileReader fr = null;
		try {
			fr = new FileReader(fp);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Location[][] map = new Location[500][500];

		if (fr != null) {
			Scanner s = new Scanner(fr);
			for (int i = 0; i < 100; i ++) {
				String line = s.nextLine().strip();
				for (int j = 0; j < 100; j++) {
					map[i][j] = new Location(i, j, Integer.parseInt(line.substring(j, j + 1)));
				}
			}
			
			
			for (int i = 1; i <= 4; i++) {
				for (int row = 0; row < 100; row++) {
					for (int column = 0; column < 100; column++) {
						map[row][column + 100*i] = new Location(row, column + 100*i, map[row][column].risk + i <= 9 ? map[row][column].risk + i : map[row][column].risk + i - 9);
					}
				}
			}
			
			for (int i = 1; i <= 4; i++) {
				for (int row = 0; row < 100; row++) {
					for (int column = 0; column < 500; column++) {
						map[row + 100*i][column] = new Location(row + 100*i, column, map[row][column].risk + i <= 9 ? map[row][column].risk + i : map[row][column].risk + i - 9);
					}
				}
			}
			
			map = findShortestRoute(map);
			System.out.println(map[499][499].distanceTo);

		}
		long end = System.currentTimeMillis();
		long actual = end - start;
		System.out.println("That took: " + actual + " ms");

	}

	private static Location[][] findShortestRoute(Location[][] map) {

		
		while (!map[499][499].visited) {
			
		
		Location current = lowestUnvisited(map);
		System.out.println(current.row + " " + current.column);
		
		for (int[] temp : current.neighbours) {

			Location neighbour = map[temp[0]][temp[1]];
			if (!neighbour.visited) {

				int distance = current.distanceTo + neighbour.risk;

				if (neighbour.distanceTo == -1 || distance < neighbour.distanceTo) {

					neighbour.distanceTo = distance;

				}
			}
		}
		current.visited = true;
		
		}
		
		return map;
	}

	private static Location lowestUnvisited(Location[][] map) {
		Location lowest = map[0][0];
		
		if (!lowest.visited) {
			return lowest;
		}
		else {
			int lowestDistanceTo = 1000000;
		
		for (int i = 0; i < 500; i ++) {
			for (int j = 0; j < 500; j++) {
				if (map[i][j].distanceTo < lowestDistanceTo && !map[i][j].visited && (map[i][j].distanceTo) != -1) {
					lowest = map[i][j];
					lowestDistanceTo = lowest.distanceTo;
				}
			}
		}
		return lowest;
		}
	}
}
