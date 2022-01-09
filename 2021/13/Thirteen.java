import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

public class Thirteen {
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
		
		
		ArrayList<int[]> coords = new ArrayList<int[]>();
		int maxX = 0;
		int maxY = 0;

		if (fr != null) {
			Scanner s = new Scanner(fr);
			
			while (s.hasNext()) {
				String line = s.nextLine().strip();
				if (line.equals("")) {
					break;
				}
				String [] xandY = line.strip().split(",");
				int[] coord = {Integer.parseInt(xandY[0]), Integer.parseInt(xandY[1])};
				coords.add(coord);
				
				if (coord[0] > maxX) {
					maxX = coord[0];
				}
				if (coord[1] > maxY) {
					maxY = coord[1];
				}

			}
//			System.out.println(maxX);
//			System.out.println(maxY);
			
			// create sheet and add dots as 1s
			int[][] sheet = new int[maxX + 1][maxY + 1];
			for (int[] coord : coords) {
				sheet[coord[0]][coord[1]] = 1;
			}
			
			String[] firstInstruction = s.nextLine().strip().substring(11).split("=");
			int foldX = Integer.parseInt(firstInstruction[1]);
			int counter = 0;

			for (int x = foldX; x <= maxX; x++) {
				for (int y = 0; y <= maxY; y++) {
					if (!(foldX - (x - foldX) < 0)) {
						if (sheet[x][y] == 1) {
							sheet[foldX - (x - foldX)][y] = 1;
						}
					}
				}
			}
			
			counter = 0;
			
			for (int x = 0; x < foldX; x ++) {
				for (int y = 0; y <= maxY; y++) {
					if (sheet[x][y] == 1) {
						counter++;
					}
				}
			}
			System.out.println(counter);
			

		}

	}
}
