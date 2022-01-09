import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

public class ElevenA1 {
	public static void main(String[] args) {

		String fp =  "input";
		FileReader fr = null;
		try {
			fr = new FileReader(fp);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		;

		if (fr != null) {
			Scanner s = new Scanner(fr);
			String line = "";
			int rowNum = 0;
			Cavern cavern = new Cavern();
	
			// parse file to array
			while (s.hasNext()) {
				line = s.nextLine().strip();
				for (int column = 0; column < line.length(); column ++) {
					cavern.addOctopus(rowNum, column, Integer.parseInt(line.substring(column, column + 1)));
				}
				rowNum++;
			}
			//System.out.println(cavern.toString());
			
			for (int i = 0; i < 1000; i++) {
			
				cavern.step(i+1);
				
			}
			
			
			//System.out.println();
			//System.out.println();
			//System.out.println(cavern.flashes);
			

		}
	}
}