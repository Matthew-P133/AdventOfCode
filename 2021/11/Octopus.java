
public class Octopus {
	public int energy;
	public Cavern cavern;
	public int row;
	public int column;
	public boolean flashed = false;
	
	public Octopus(int energy, Cavern cavern, int row, int column) {
		this.energy = energy;
		this.cavern = cavern;
		this.row = row;
		this.column = column;
	}
	
	public void flash() {
		cavern.flashes++;
		flashed = true;
		
//		System.out.println("flashing!");
//		System.out.println(row);
//		System.out.println(column);
		
		for (int i = row - 1; i <= row + 1; i++) {
			for (int j = column - 1; j <= column + 1; j++) {
				if (i >= 0 && i < 10 && j >= 0 && j < 10 && !(i == row && j == column)) {
					cavern.octopusses[i][j].energy++;
					//System.out.println("row: " + i + " column: " + j);
				}
			}
		}
//		for (int i = row - 1; i <= row + 1; i++) {
//			for (int j = column - 1; j <= column + 1; j++) {
//				if (i >= 0 && i < 10 && j >= 0 && j <= 10 && i != j) {
//					if (cavern.octopusses[i][j].energy > 9 && !cavern.octopusses[i][j].flashed) {
//						cavern.octopusses[i][j].flash();
//					}
//					
//				}
//			}
//		}
		
	}
}
