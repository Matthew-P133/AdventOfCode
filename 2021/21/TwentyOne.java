public class TwentyOne {

	public static void main(String[] args) {

		long start = System.currentTimeMillis();

		// PART 1

		// parse file
		Player one = new Player(8);
		Player two = new Player(3);

		int counter = 0;

		// if no player has reached 1000 points
		while (true) {
			boolean finished;
			if (counter % 2 == 0) {
				// System.out.println("one turn");
				finished = one.turn();
			} else {
				// System.out.println("two turn");
				finished = two.turn();
			}

			if (finished) {
				break;
			}
			counter++;
		}

		System.out.println("Part 1: " + (one.score < two.score ? one.score : two.score) * Dice.rolls);

		long end = System.currentTimeMillis();
		System.out.println(end - start + " ms");

		// Part 2

		long[][][][] games = new long[10][60][10][60];

		// initial state
		games[7][0][2][0] = 1;

		long[] winners = new long[2];

		counter = 0;

		while (counter < 30) {
			//System.out.println("turn " + counter);
	
			if (counter % 2 == 0) {
				long[][][][] tempGames = new long[10][60][10][60];

				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 22; j++) {
						for (int k = 0; k < 10; k++) {
							for (int l = 0; l < 22; l++) {

								// update games
								tempGames[rolloverPosition(i, 3)][j + rolloverPosition(i, 3) + 1][k][l] += games[i][j][k][l] * 1;
								tempGames[rolloverPosition(i, 4)][j + rolloverPosition(i, 4) + 1][k][l] += games[i][j][k][l] * 3;
								tempGames[rolloverPosition(i, 5)][j + rolloverPosition(i, 5) + 1][k][l] += games[i][j][k][l] * 6;
								tempGames[rolloverPosition(i, 6)][j + rolloverPosition(i, 6) + 1][k][l] += games[i][j][k][l] * 7;
								tempGames[rolloverPosition(i, 7)][j + rolloverPosition(i, 7) + 1][k][l] += games[i][j][k][l] * 6;
								tempGames[rolloverPosition(i, 8)][j + rolloverPosition(i, 8) + 1][k][l] += games[i][j][k][l] * 3;
								tempGames[rolloverPosition(i, 9)][j + rolloverPosition(i, 9) + 1][k][l] += games[i][j][k][l] * 1;

								
								if (j + rolloverPosition(i, 3) + 1 >= 21) {winners[0] += games[i][j][k][l] * 1; tempGames[rolloverPosition(i, 3)][j + rolloverPosition(i, 3) + 1][k][l] -= games[i][j][k][l] * 1;
								}
								if (j + rolloverPosition(i, 4) + 1 >= 21) {winners[0] += games[i][j][k][l] * 3; tempGames[rolloverPosition(i, 4)][j + rolloverPosition(i, 4) + 1][k][l] -= games[i][j][k][l] * 3;
								}
								if (j + rolloverPosition(i, 5) + 1 >= 21) {winners[0] += games[i][j][k][l] * 6; tempGames[rolloverPosition(i, 5)][j + rolloverPosition(i, 5) + 1][k][l] -= games[i][j][k][l] * 6;
								}
								if (j + rolloverPosition(i, 6) + 1 >= 21) {winners[0] += games[i][j][k][l] * 7; tempGames[rolloverPosition(i, 6)][j + rolloverPosition(i, 6) + 1][k][l] -= games[i][j][k][l] * 7;
								}
								if (j + rolloverPosition(i, 7) + 1 >= 21) {winners[0] += games[i][j][k][l] * 6; tempGames[rolloverPosition(i, 7)][j + rolloverPosition(i, 7) + 1][k][l] -= games[i][j][k][l] * 6;
								}
								if (j + rolloverPosition(i, 8) + 1 >= 21) {winners[0] += games[i][j][k][l] * 3; tempGames[rolloverPosition(i, 8)][j + rolloverPosition(i, 8) + 1][k][l] -= games[i][j][k][l] * 3;
								}
								if (j + rolloverPosition(i, 9) + 1 >= 21) {winners[0] += games[i][j][k][l] * 1; tempGames[rolloverPosition(i, 9)][j + rolloverPosition(i, 9) + 1][k][l] -= games[i][j][k][l] * 1;
								}

							}
						}
					}
				}

				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 22; j++) {
						for (int k = 0; k < 10; k++) {
							for (int l = 0; l < 22; l++) {
								games[i][j][k][l] = tempGames[i][j][k][l];
							}
						}
					}
				}
			}
			else {
				long[][][][] tempGames = new long[10][60][10][60];

				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 22; j++) {
						for (int k = 0; k < 10; k++) {
							for (int l = 0; l < 22; l++) {

								// update games
								tempGames[k][l][rolloverPosition(i, 3)][j + rolloverPosition(i, 3) + 1] += games[k][l][i][j] * 1;
								tempGames[k][l][rolloverPosition(i, 4)][j + rolloverPosition(i, 4) + 1] += games[k][l][i][j] * 3;
								tempGames[k][l][rolloverPosition(i, 5)][j + rolloverPosition(i, 5) + 1] += games[k][l][i][j] * 6;
								tempGames[k][l][rolloverPosition(i, 6)][j + rolloverPosition(i, 6) + 1] += games[k][l][i][j] * 7;
								tempGames[k][l][rolloverPosition(i, 7)][j + rolloverPosition(i, 7) + 1] += games[k][l][i][j] * 6;
								tempGames[k][l][rolloverPosition(i, 8)][j + rolloverPosition(i, 8) + 1] += games[k][l][i][j] * 3;
								tempGames[k][l][rolloverPosition(i, 9)][j + rolloverPosition(i, 9) + 1] += games[k][l][i][j] * 1;

				
								if (j + rolloverPosition(i, 3) + 1 >= 21) {winners[1] += games[k][l][i][j] * 1;	tempGames[k][l][rolloverPosition(i, 3)][j + rolloverPosition(i, 3) + 1] -= games[k][l][i][j] * 1;
								}
								if (j + rolloverPosition(i, 4) + 1 >= 21) {winners[1] += games[k][l][i][j] * 3; tempGames[k][l][rolloverPosition(i, 4)][j + rolloverPosition(i, 4) + 1] -= games[k][l][i][j] * 3;
								}
								if (j + rolloverPosition(i, 5) + 1 >= 21) {winners[1] += games[k][l][i][j] * 6; tempGames[k][l][rolloverPosition(i, 5)][j + rolloverPosition(i, 5) + 1] -= games[k][l][i][j] * 6;
								}
								if (j + rolloverPosition(i, 6) + 1 >= 21) {winners[1] += games[k][l][i][j] * 7; tempGames[k][l][rolloverPosition(i, 6)][j + rolloverPosition(i, 6) + 1] -= games[k][l][i][j] * 7;
								}
								if (j + rolloverPosition(i, 7) + 1 >= 21) {winners[1] += games[k][l][i][j] * 6; tempGames[k][l][rolloverPosition(i, 7)][j + rolloverPosition(i, 7) + 1] -= games[k][l][i][j] * 6;
								}
								if (j + rolloverPosition(i, 8) + 1 >= 21) {winners[1] += games[k][l][i][j] * 3; tempGames[k][l][rolloverPosition(i, 8)][j + rolloverPosition(i, 8) + 1] -= games[k][l][i][j] * 3;
								}
								if (j + rolloverPosition(i, 9) + 1 >= 21) {winners[1] += games[k][l][i][j] * 1; tempGames[k][l][rolloverPosition(i, 9)][j + rolloverPosition(i, 9) + 1] -= games[k][l][i][j] * 1;
								}

							}
						}
					}
				}

				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 22; j++) {
						for (int k = 0; k < 10; k++) {
							for (int l = 0; l < 22; l++) {
								games[k][l][i][j] = tempGames[k][l][i][j];
							}
						}
					}
				}
			}

		

		counter++;
		
	}
		
		System.out.println("Part 2: " + (winners[0] > winners[1] ? winners[0] : winners[1]));
		long time = System.currentTimeMillis();
		System.out.println(time - end + " ms");


	
}

	private static int rolloverPosition(int i, int j) {
		int position = i + j;
		if (position > 9) {
			position -= 10;
		}

		return position;
	}
}