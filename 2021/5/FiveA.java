import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;

public class FiveA {

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
			s.useDelimiter("\n");
			String[] line = s.tokens().toArray(String[]::new);

			Integer[][] temp = new Integer[line.length][4];
			for (int i = 0; i < line.length; i++) {
				int j = line[i].lastIndexOf(" -> ");
				line[i] = line[i].substring(0, j) + "," + line[i].substring(j + 4).strip();
				temp[i] = Arrays.stream(line[i].split(",")).map((n) -> Integer.parseInt(n))
						.toArray(Integer[]::new);
			}
			
			int[][] coordinates = new int[line.length][4];
			
			for (int i = 0; i < line.length; i++) {
				for (int j = 0; j < 4; j++)	{
					coordinates[i][j] = temp[i][j];
					}
				}
			
			int size = 1000;
			int[][] seaFloor = new int[size][size];
					
			
			for (int[] vents : coordinates) {
				seaFloor = mark(vents, seaFloor);
			}

			
			int danger = count(seaFloor);
			System.out.println("Part 2: " + danger);

		}

	}
	


	private static int count(int[][] seaFloor) {
		int count = 0;
		for (int i = 0; i < seaFloor.length; i++) {
			for (int j = 0; j < seaFloor.length; j++) {
				if (seaFloor[i][j] > 1) {
					count++;
				}
			}
		}
		return count;
	}

	private static String toString(int[][] seaFloor) {
		String s = "";
		for (int i = 0; i < seaFloor.length; i++) {
			for (int j = 0; j < seaFloor.length; j++) {
				s += seaFloor[i][j] + " ";
			}
			s += "\n";
		}
		return s;
	}

	public static int[][] mark(int[] vents, int[][] seaFloor) {
		
		if (vents[0] == vents[2]) { // vertical

			int column = vents[0];
			int start = vents[1] < vents[3] ? vents[1] : vents[3];
			int end = start == vents[1] ? vents[3] : vents[1];
			
			for (int i = start; i <= end; i++) {
				seaFloor[i][column]++;
			}
		}
		else if (vents[1] == vents[3]) { // horizontal
			
			int row = vents[1];
			int start = vents[0] < vents[2] ? vents[0] : vents[2];
			int end = start == vents[0] ? vents[2] : vents[0];
			
			for (int i = start; i <= end; i++) {
				seaFloor[row][i]++;
			}
		}
		else { // diagonal
			int startX = vents[0];
			int endX = vents[2];
			int startY = vents[1];
			int endY = vents[3];
			
			if (startX > endX && startY > endY) {
				for (int x = startX, y = startY; x >= endX; x--, y--) {
					seaFloor[y][x]++;
				}
			}
			
			if (startX < endX && startY > endY) {
				for (int x = startX, y = startY; x <= endX; x++, y--) {
					seaFloor[y][x]++;
				}
			}
			if (startX > endX && startY < endY) {
				for (int x = startX, y = startY; x >= endX; x--, y++) {
					seaFloor[y][x]++;
				}
			}
			if (startX < endX && startY < endY) {
				for (int x = startX, y = startY; x <= endX; x++, y++) {
					seaFloor[y][x]++;
				}
			}
			
		}
		
		return seaFloor;
	}
}
