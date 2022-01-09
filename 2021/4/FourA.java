import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class FourA {

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

		int size = 5;
		
		if (fr != null) {

			Scanner s = new Scanner(fr);
			//numbers
			String[] numbers = s.nextLine().split(",");
			int[] draw = new int[numbers.length];
			for (int i = 0; i < numbers.length; i++) {
				draw[i] = Integer.parseInt(numbers[i]);
			}
			
			// boards
			ArrayList<BingoBoard> boards = new ArrayList<BingoBoard>();
			while (s.hasNextLine()) {
				BingoBoard b = new BingoBoard(size);
				for (int i = 0; i < size; i++) {
					for (int j = 0; j < size; j++) {
						b.board[i][j] = new Square(s.nextInt());
					}
					s.nextLine();
				}			
				boards.add(b);
			}

			// part 1
			BingoBoard winningBoard = playBingo(boards, draw);
			//System.out.println(winningBoard);
			System.out.println("part 1: " + winningBoard.score());
			
			// part 2
			BingoBoard losingBoard = loseBingo(boards, draw);
			//System.out.println(losingBoard);
			System.out.println("part 2: " + losingBoard.score());
		}
	}
	
	public static BingoBoard playBingo(ArrayList<BingoBoard> boards, int[] draw) {
		for (int number : draw) {
			for (BingoBoard b : boards) {
				b.mark(number);
				if (b.check()) {
					b.winningNumber = number;
					return b;
				}
			}
		}
		return null;
	}

	public static BingoBoard loseBingo(ArrayList<BingoBoard> boards, int[] draw) {
		BingoBoard b = null;
		for (int number : draw) {
			for (BingoBoard board : boards) {
				if (!board.complete) {
					board.mark(number);
					if (board.check()) {
						board.complete = true;
						board.winningNumber = number;
						b = board;
					}
				}
			}
		}
		return b;
	}
	
}
