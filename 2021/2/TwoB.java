import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class TwoB {
	public static void main(String[] args) {

		String fp = System.getProperty("user.dir") + "\\input";
		FileReader fr = null;
		try {
			fr = new FileReader(fp);
			System.out.println("file opened");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (fr != null) {
			int horizontal = 0;
			int depth = 0;
			int aim = 0;
			
			Scanner s = new Scanner(fr);
			
			while (s.hasNext()) {
				String instruction = s.next();
				int displacement = s.nextInt();
				s.nextLine();
				
				switch (instruction) {
				
				case "forward":
					horizontal += displacement;
					depth += displacement * aim;
					break;
				
				case "down":
					aim += displacement;
					break;
					
				case "up":
					aim -= displacement;
					break;
					
				}
				
			}
			System.out.printf("depth: %d\n", depth);
			System.out.printf("horizontal: %d\n", horizontal);
			System.out.println(depth * horizontal);
		}
		
		
		
	}
}
