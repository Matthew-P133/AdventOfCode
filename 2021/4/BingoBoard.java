
public class BingoBoard {
	public Square[][] board;
	public int size;
	public int winningNumber;
	public boolean complete;
	
	public BingoBoard(int size) {
		board = new Square[size][size];
		this.size = size;
		complete = false;
	}
	
	public void mark(int number) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (board[i][j].number == number) {
					board[i][j].called = true;
				}
			}
		}
	}
	
	public boolean check() {
		
		// look for valid rows
		for (int i = 0; i < size; i++) {
			boolean validRow = true;
			for (int j = 0; j < size; j++) {
				if (board[i][j].called == false) {
					validRow = false;
				}
			}
			if (validRow) {
				return validRow;
			}
		}
		
		// look for valid columns
		for (int j = 0; j < size; j++) {
			boolean validColumn = true;
			for (int i = 0; i < size; i++) {
				if (board[i][j].called == false) {
					validColumn = false;
				}
			}
			if (validColumn) {
				return validColumn;
			}
		}
		return false;
	
	}

	public int score() {
		int sum = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (!board[i][j].called) {
					sum += board[i][j].number;
				}
			}
		}
		return (sum*winningNumber);
	}
	
	public String toString() {
		String bingoCard = "";
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				bingoCard += board[i][j].number + " " + board[i][j].called;
		}
			bingoCard += "\n";
		}
		return bingoCard;
	}
}
