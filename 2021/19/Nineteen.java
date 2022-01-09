import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Nineteen {
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
			
			// parse file to Station objects
			Scanner s = new Scanner(fr);
			start = System.currentTimeMillis();
			ArrayList<Station> stations = new ArrayList<Station>();
			while (s.hasNext()) {
				
				String line = s.nextLine().strip();
				
				// create stations
				if (line.startsWith("---")) {
					
					Scanner temp = new Scanner(line);
					temp.next();
					temp.next();
					int stationNumber = temp.nextInt();
					temp.close();
		
					Station station = new Station(stationNumber, s);
					stations.add(station);
				}
				else break;
			}
			
			while (stations.size() > 1) {
			stations = merge(stations);
			System.out.printf("; %d stations remaining\n", stations.size());
			}
			
			// part 1
			System.out.printf("\nThere are a total of %d beacons\n", stations.get(0).beacons.size());
			

			long end = System.currentTimeMillis();			
			System.out.println(end - start + " ms");
			
			Station result = stations.get(0);
			int manhattan = 0;
			
			for (int i = 0; i < result.stations.size(); i++ ) {
				for (int j = 0; j < result.stations.size(); j++ ) {
					if (i != j) {
						int x = Math.abs(result.stations.get(i)[0] - result.stations.get(j)[0]);
						int y = Math.abs(result.stations.get(i)[1] - result.stations.get(j)[1]);
						int z = Math.abs(result.stations.get(i)[2] - result.stations.get(j)[2]);
						
						int currManhattan = x + y + z;
						
						if (currManhattan > manhattan) {
							manhattan = currManhattan;
						}
					}
				}
			}
			
			//part 2
			System.out.printf("The largest manhattan distance between two stations is %d\n", manhattan);
			
			long part2 = System.currentTimeMillis();
			System.out.println(part2 - end + " ms");
			
			
		}
	}

	private static ArrayList<Station> merge(ArrayList<Station> stations) {
		
		for (int i = 0; i < stations.size(); i++) {
			for (int j = 0; j < stations.size(); j++) {
				if (i != j) {
					Station s1 = stations.get(i);
					Station s2 = stations.get(j);
					
						Station joinedStation = s1.join(s2);
						
						if (joinedStation != null) {
							System.out.printf("Joined stations %d and %d", i, j);

							if (i > j) {
								stations.remove(i);
								stations.remove(j);
							}
							else {
								stations.remove(j);
								stations.remove(i);
							}

							
							stations.add(joinedStation);
							return stations;
						}	
				}
			}
		}

		return stations;
	}


}
