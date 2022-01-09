import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

public class TenA1 {
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
			s.useDelimiter("\n");
			String[] lines = s.tokens().toArray(String[]::new);
			ArrayList<String> incompleteLines = new ArrayList<String>();
			
			// part 1
			int score = 0;
			
			for (String line : lines) {
				int thisScore = analyse(line);
				if (thisScore == 0) {
					incompleteLines.add(line);
				}
				score += thisScore;
			}
			System.out.println("Part 1: " + score);
			
			// part 2
			long[] scores = new long[incompleteLines.size()];
			int i = 0;
			for (String line : incompleteLines) {
				long thisScore = score(complete(line));
				scores[i] = thisScore;
				i++;
			}
			
			Arrays.sort(scores);
			
			System.out.println("Part 2: " + scores[(scores.length - 1)/2]);
			
	
		}
		}
	
	public static int analyse(String line) {
		String letter = "";
		
		String[] stack = new String[line.length()];
		int top = 0;
		
		int score = 0;
		
		for (int i = 0; i < line.length(); i++) {
			letter = line.substring(i, i + 1);
			if (letter.equals("{") || letter.equals("(") || letter.equals("[") || letter.equals("<")) {
				stack[top] = letter;
				top++;
			}
			if (letter.equals("}") || letter.equals(")") || letter.equals("]") || letter.equals(">")) {
				if (!(stack[top-1].equals(flip(letter)))) {
					if (letter.equals(")")) {
						score += 3;
					}
					if (letter.equals("]")) {
						score += 57;
					}
					if (letter.equals("}")) {
						score += 1197;
					}
					if (letter.equals(">")) {
						score += 25137;
					}
				}
				stack[top-1] = "";
				top--;
			}
		}
		return score;
		
	}
	
	public static String complete(String line) {
		String letter = "";
		String completing = "";
		
		String[] stack = new String[line.length()];
		int top = 0;
		
		
		for (int i = 0; i < line.length(); i++) {
			letter = line.substring(i, i + 1);
			if (letter.equals("{") || letter.equals("(") || letter.equals("[") || letter.equals("<")) {
				stack[top] = letter;
				top++;
			}
			if (letter.equals("}") || letter.equals(")") || letter.equals("]") || letter.equals(">")) {
				stack[top-1] = "";
				top--;
			}
		}
		
		for (int i = top - 1; i >= 0; i--) {
			completing += flip(stack[i]);
		}
		return completing;
	}
	
	public static long score(String completing) {
		String letter = "";
	    long score = 0;
		for (int i = 0; i < completing.length(); i++) {
			letter = completing.substring(i, i+1);
			int letterScore = 0;
			if (letter.equals(")")) {
				letterScore = 1;
			}
			if (letter.equals("]")) {
				letterScore = 2;
			}
			if (letter.equals("}")) {
				letterScore = 3;
			}
			if (letter.equals(">")) {
				letterScore = 4;
			}
			
			
			score *= 5;
			score += letterScore;
			
		}
		return score;
	}
	
	public static String flip(String letter) {
		if (letter.equals("}")) {
			return "{";
		}
		if (letter.equals(")")) {
			return "(";
		}
		if (letter.equals("]")) {
			return "[";
		}
		if (letter.equals(">")) {
			return "<";
		}
		if (letter.equals("{")) {
			return "}";
		}
		if (letter.equals("(")) {
			return ")";
		}
		if (letter.equals("[")) {
			return "]";
		}
		if (letter.equals("<")) {
			return ">";
		}
		return "";
	}
}