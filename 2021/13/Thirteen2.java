import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

public class Thirteen2 {
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
			
			while (s.hasNext()) {
				String[] firstInstruction = s.nextLine().strip().substring(11).split("=");
				int foldLine = Integer.parseInt(firstInstruction[1]);
				String axis = firstInstruction[0];
				if (axis.equals("x")) {
					sheet = verticalFold(sheet, foldLine);
				}
			
				

				else {
					sheet = horizontalFold(sheet, foldLine);
				}
				for (int x = 0; x < sheet.length; x++) {
				for ( int y = sheet[0].length - 1; y >= 0; y--) {
					if (sheet[x][y] == 1) {
						System.out.print("#");
					}
					else {
						System.out.print(" ");
					}
					
				}
				System.out.println();
			}
			//System.out.println(sheet.length);
			//System.out.println(sheet[0].length);
			System.out.println();
			System.out.println();
			}
		}

	}
	
	public static int[][] horizontalFold(int[][] sheet, int foldLine) {
		// decide if top fold or bottom fold
		if (foldLine >= (sheet[0].length / 2)) {
			// bottom fold
			for (int y = foldLine; y < sheet[0].length; y++) {
				for (int x = 0; x < sheet.length; x++) {
					if (sheet[x][y] == 1) {
						sheet[x][foldLine - (y - foldLine)] = 1;
					}
				}
			}
			int[][] temp = new int[sheet.length][foldLine];
			for (int y = 0; y < foldLine; y++) {
				for (int x = 0; x < sheet.length; x++) {
					temp[x][y] = sheet[x][y];
				}
			}
			sheet = temp;
				
			
		} else {
			// top fold
			for (int y = foldLine; y >= 0; y--) {
				for (int x = 0; x < sheet.length; x++) {
					if (sheet[x][y] == 1) {
						sheet[x][foldLine + (foldLine - y)] = 1;
					}
				}
			}
			int[][] temp = new int[sheet.length][sheet[0].length - foldLine - 1];
			for (int x = 0; x < foldLine; x++) {
				for (int y = 0; y < sheet[0].length; y++) {
					temp[x][y] = sheet[x][y + foldLine + 1];
				}
			}
			sheet = temp;
		}
		return sheet;
		

		
	}
	
	
	public static int[][] verticalFold(int[][] sheet, int foldLine) {
		// decide if left fold or right fold
		if (foldLine >= (sheet.length / 2)) {
			// right fold
			for (int x = foldLine; x < sheet.length; x++) {
				for (int y = 0; y < sheet[0].length; y++) {
					if (sheet[x][y] == 1) {
						sheet[foldLine - (x - foldLine)][y] = 1;
					}
				}
			}
			int[][] temp = new int[foldLine][sheet[0].length];
			for (int x = 0; x < foldLine; x++) {
				for (int y = 0; y < sheet[0].length; y++) {
					temp[x][y] = sheet[x][y];
				}
			}
			sheet = temp;
				
			
		} else {
			// left fold
			for (int x = foldLine; x >= 0; x--) {
				for (int y = 0; y < sheet[0].length; y++) {
					if (sheet[x][y] == 1) {
						sheet[foldLine + (foldLine - x)][y] = 1;
					}
				}
			}
			int[][] temp = new int[sheet.length - 1 - foldLine][sheet[0].length];
			for (int x = 0; x < foldLine; x++) {
				for (int y = 0; y < sheet[0].length; y++) {
					temp[x][y] = sheet[x + foldLine + 1][y];
				}
			}
			sheet = temp;
		}
		return sheet;
		

		
	}
}

