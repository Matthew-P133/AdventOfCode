import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class SixA2 {

	public static void main(String[] args) {

		long[] numOf = new long[9];

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
			s.useDelimiter(",");
			s.tokens().forEach((n) -> numOf[Integer.parseInt(n.strip())]++);
		}

		int days = 256;

		for (int i = 0; i < days; i++) {
			long spawn = numOf[0];
			numOf[0] = numOf[1];
			numOf[1] = numOf[2];
			numOf[2] = numOf[3];
			numOf[3] = numOf[4];
			numOf[4] = numOf[5];
			numOf[5] = numOf[6];
			numOf[6] = numOf[7] + spawn;
			numOf[7] = numOf[8];
			numOf[8] = spawn;
		}

		long lanternFish = 0;
		for (long i : numOf) {
			lanternFish += i;
		}
		System.out.println("Part 2: " + lanternFish);
	}
}
