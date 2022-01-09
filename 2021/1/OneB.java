import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class OneB {
	public static void main(String[] args) {
		String fp = System.getProperty("user.dir") + "\\One_A_1";
		FileReader fr = null;
		try {
			fr = new FileReader(fp);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int goingDeeper = 0;
		
		
		if (fr != null) {
			Scanner s = new Scanner(fr);
			
			int twoPrev = 0;
			int prev = 0;
			int curr = 0;
			
			int prevSum = 0;
			int currSum = 0;
			
			try {
				twoPrev = s.nextInt();
				prev = s.nextInt();
				curr = s.nextInt();
				currSum = twoPrev + prev + curr;
			} catch (Exception e) {
				
			}
			
			while (s.hasNextInt()) {
				
				twoPrev = prev;
				prev = curr;

				curr = s.nextInt();
				prevSum = currSum;
				currSum = twoPrev + prev + curr;
				
				if (currSum > prevSum && prevSum != 0) {
					goingDeeper++;
				}

			}
			System.out.println(goingDeeper);
			
			
			
	
		}
		
	}
}
