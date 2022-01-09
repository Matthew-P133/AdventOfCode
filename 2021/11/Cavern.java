
public class Cavern {
	public Octopus[][] octopusses = new Octopus[10][10];
	public int flashes = 0;
	
	public Cavern() {}
	
	public void addOctopus(int row, int column, int energy) {
		octopusses[row][column] = new Octopus(energy, this, row, column);
	}
	
	public void step(int stepNumber) {
		for (int row = 0; row < 10; row++) {
			for (int column = 0; column < 10; column++) {
				octopusses[row][column].energy++;
			}
		}
		int recentFlashes = 1000;
		
		while (recentFlashes > 0) {
			recentFlashes = flashes;

			for (int row = 0; row < 10; row++) {
				for (int column = 0; column < 10; column++) {
					if (octopusses[row][column].energy > 9 && !(octopusses[row][column].flashed)) {
						octopusses[row][column].flash();
//						System.out.println(this.toString());
//						System.exit(0);
					}
				}
			}
			recentFlashes -= flashes;
			recentFlashes *= -1;
		}
		
		int numberFlashed = this.reset();
		if (numberFlashed == 100) {
			System.out.println(stepNumber);
			System.exit(0);
		}
		
		System.out.println(this.toString());
		
	}
	
	public int reset() {
		int numberFlashed = 0;
		for (int row = 0; row < 10; row++) {
			for (int column = 0; column < 10; column++) {
				if (octopusses[row][column].flashed) {
					numberFlashed++;
					octopusses[row][column].energy = 0;
				}
				octopusses[row][column].flashed = false;

			}
		}
		System.out.println(numberFlashed);
		return numberFlashed;
	}
	
	public String toString() {
		String print = "";
		for (int row = 0; row < 10; row++) {
			for (int column = 0; column < 10; column++) {
				print += octopusses[row][column].energy + " ";
			}
			print += "\n";
		}
		return print;
	}
}
