import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

public class NineA1 {
	public static void main(String[] args) {

		String fp = System.getProperty("user.dir") + "\\input";
		FileReader fr = null;
		try {
			fr = new FileReader(fp);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		Zone[][] heightMap = new Zone[100][100];
		
		if (fr != null) {
			Scanner s = new Scanner(fr);
			String line = "";
			int rowNum = 0;
	
			// parse file to array
			while (s.hasNext()) {
				line = s.nextLine().strip();
				for (int column = 0; column < line.length(); column ++) {
					heightMap[rowNum][column] = new Zone(Integer.parseInt(line.substring(column, column + 1)));
					
				}
				rowNum++;
			}
				
		// part 1	
		int riskSum = 0;
			
			for (int row = 0; row < 100; row++) {
				for (int column = 0; column < 100; column++) {
					if (lowPoint(row, column, heightMap)) {
						riskSum += heightMap[row][column].height + 1;
					}
				}
			}

			System.out.println("Part 1: " + riskSum);
			
			// part 2
			
			ArrayList<Basin> basins = new ArrayList<Basin>();

			for (int row = 0; row < 100; row++) {
				for (int column = 0; column < 100; column++) {
					Zone zone = heightMap[row][column];
					if (zone.inBasin || zone.height == 9) {
						continue;
					} else {
						zone.inBasin = true;
						Basin basin = new Basin();
						basin = explore(row, column, heightMap, basin);
						basins.add(basin);
					}
				}
			}
			
			Object[] sortedBasins = basins.toArray();
			Arrays.sort(sortedBasins);

			int basinProduct = 1;
			for (int i = sortedBasins.length - 1; i > sortedBasins.length - 4; i--) {
				basinProduct *= ((Basin) sortedBasins[i]).getSize();
			}
			System.out.println("Part 2: " + basinProduct);
		}

	}
	

	public static Basin explore(int row, int column, Zone[][] heightMap, Basin basin) {
		for (int i = row - 1; i <= row + 1; i += 2) {
			if (i >= 0 && i < 100) {
				Zone zone = heightMap[i][column];
				if (zone.height != 9 && !zone.inBasin) {
					zone.inBasin = true;
					basin.incrementSize();
					basin = explore(i, column, heightMap, basin);
				}
			}
		}
		for (int j = column - 1; j <= column + 1; j += 2) {
			if (j >= 0 && j < 100) {
				Zone zone = heightMap[row][j];
				if (zone.height != 9 && !zone.inBasin) {
					zone.inBasin = true;
					basin.incrementSize();
					basin = explore(row, j, heightMap, basin);
				}
			}
		}
		return basin;
	}
	

	public static boolean lowPoint(int row, int column, Zone[][] heightMap) {
		int height = heightMap[row][column].height;

		for (int i = row - 1; i <= row + 1; i += 2) {
			if (i >= 0 && i < 100) {
				if (heightMap[i][column].height <= height) {
					return false;
				}
			}
		}
		for (int j = column - 1; j <= column + 1; j += 2) {
			if (j >= 0 && j < 100) {
				if (heightMap[row][j].height <= height) {
					return false;
				}
			}
		}
		return true;
	}
}