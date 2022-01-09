import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Twenty {

public static void main(String[] args) {
		long start = 0;
		FileReader fr = null;
		try {
			fr = new FileReader("input");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (fr != null) {
			start = System.currentTimeMillis();
			
			Scanner s = new Scanner(fr);
			
			// parse image enhancement algorithm
			String algorithm = s.nextLine();
			
			s.nextLine();
			
			// parse input image
			char [][] inputImage = new char[100][100];
			for (int i = 0; i < 100; i ++) {
				String line = s.nextLine().strip();
				for (int j = 0; j < 100; j++) {
					inputImage[i][j] = line.charAt(j);
				}
			}
			
			// enhance
			start = System.currentTimeMillis();
			char[][] intermediateOutput = inputImage;
			
			
			for (int i = 0; i < 50; i++) {
				if (i % 2 == 0) {
					intermediateOutput = enhance(intermediateOutput, algorithm, '0');
				} else {
					intermediateOutput = enhance(intermediateOutput, algorithm, '1');
				}
			}
			
			char[][] finalOutput = intermediateOutput;

			// find number of lit pixels
			int litPixels = 0;
			for (int i = 0; i < finalOutput.length; i ++) {
				for (int j = 0; j < finalOutput.length; j++) {
					litPixels += finalOutput[i][j] == '#' ? 1 : 0;
				}
			}
			System.out.println("Part 2: " + litPixels);

						
			long end = System.currentTimeMillis();
			System.out.println(end - start + " ms");
			
			
		}
}

private static char[][] enhance(char[][] inputImage, String algorithm, char defaultChar) {


	// create blank output image (4x4 bigger than input)
	int size = inputImage.length;
	char[][] output = new char[size+4][size+4];
	
	// for each pixel in output image 
	for (int row = 0; row < size+4; row++) {
		for (int column = 0; column < size+4; column++) {
			
			String binary = "";
			
			for (int i = (row) - 1; i <= (row) + 1; i++) {
				for (int j = (column) - 1; j <= (column) + 1; j++) {
					
					char ch;
					
					try {
						ch = inputImage[i-2][j-2] == '.' ? '0' : '1' ;
					}
					catch (Exception e) {
						ch = defaultChar;
					}
					binary += ch;
				}
			}
			output[row][column] = algorithm.charAt(binToDec(binary));
		}
	}
	
	return output;
}

private static int binToDec(String binary) {
	int decimal = 0;
	
	for (int i = binary.length() - 1; i >= 0; i--) {
		if (binary.substring(i, i+1).equals("1")) {
			decimal += Math.pow(2, binary.length() - i - 1);
		}
	}
	
	return decimal;
}
}