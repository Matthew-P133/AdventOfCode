import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class OneA {
	public static void main(String[] args) {
		String fp = System.getProperty("user.dir") + "\\part1";
		FileReader fr = null;
		try {
			fr = new FileReader(fp);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int goingDeeper = 0;
		int prevDepth = 0;
		int currDepth = 0;
		
		if (fr != null) {
			Scanner s = new Scanner(fr);
			while (s.hasNextInt()) {
				prevDepth = currDepth;
				currDepth = s.nextInt();
				if (currDepth > prevDepth && prevDepth != 0) {
					goingDeeper++;
				}
			}
			System.out.println(goingDeeper);
		}
		
	}
}
