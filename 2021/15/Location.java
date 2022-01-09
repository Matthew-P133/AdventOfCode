import java.util.ArrayList;

public class Location {
	public boolean visited;
	public int risk;
	public int row;
	public int column;
	public int distanceTo;
	public Iterable<int[]> neighbours;
	
	public Location(int row, int column, int risk) {
		this.row = row;
		this.column = column;
		this.risk = risk;
		this.visited = false;
		distanceTo = -1;
		
		if (row == 0 && column == 0) {
			distanceTo = 0;
		}
		
		this.neighbours = findNeighbours();
	}
	
	public Iterable<int[]> findNeighbours() {
		ArrayList<int[]> neighbours = new ArrayList<int[]>();
		
		for (int i = row - 1; i <= row + 1; i++) {
			for (int j = column - 1; j <= column + 1; j++) {
				if (!(i == row && j == column) && (i >= 0 && j >= 0 && i < 500 && j < 500)) {
					if (!((i == row - 1 && j == column - 1) || (i == row + 1 && j == column + 1) || (i == row - 1 && j == column + 1) || (i == row + 1 && j == column - 1))) {
						int[] temp = {i, j};
						neighbours.add(temp);
					}
				}
			}
		}
		
		return neighbours;
		
	}
}
