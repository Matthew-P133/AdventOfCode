import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Stream;

public class Station {
	
	public int stationNumber;
	public HashMap<Integer, int[]> beacons;
	public ArrayList<int[]> distances;
	public boolean used = false;
	public HashMap<Integer, int[]> stations;
	
	

	
	public Station(int stationNumber, Scanner s) {
		this.stationNumber = stationNumber;
		beacons = new HashMap<Integer, int[]>();
		stations = new HashMap<Integer, int[]>();
		int i = 0;
		
		while (true) {
			String line = s.nextLine().strip();
			if (line.equals("")) {
				break;
			}
			int[] temp = Stream.of(line.split(",")).mapToInt(n -> Integer.parseInt(n)).toArray();
			beacons.put(i, temp);
			i++;
		}
		int [] temp = {0, 0, 0};
		stations.put(0, temp);
		this.joinTheDots();
	}
	
	// create new station, rotating a previous one
	public Station(Station stationTwo, int instruction) {
		
		beacons = new HashMap<Integer, int[]>();
		stations = new HashMap<Integer, int[]>();
		
		for (Integer b : stationTwo.beacons.keySet()) {
			int[] temp = stationTwo.beacons.get(b);
			int x = temp[0];
			int y = temp[1];
			int z = temp[2];
			
			int[] n = new int[3];
			
			if (instruction == 0) {
				n[0] = x;
				n[1] = y;
				n[2] = z;
			}
			if (instruction == 1) {
				n[0] = x;
				n[1] = -y;
				n[2] = -z;
			}
			if (instruction == 2) {
				n[0] = x;
				n[1] = z;
				n[2] = -y;
			}
			if (instruction == 3) {
				n[0] = x;
				n[1] = -z;
				n[2] = y;
			}
			if (instruction == 4) {
				n[0] = -x;
				n[1] = y;
				n[2] = -z;
			}
			if (instruction == 5) {
				n[0] = -x;
				n[1] = -y;
				n[2] = z;
			}
			if (instruction == 6) {
				n[0] = -x;
				n[1] = z;
				n[2] = y;
			}
			if (instruction == 7) {
				n[0] = -x;
				n[1] = -z;
				n[2] = -y;
			}
			if (instruction == 8) {
				n[0] = y;
				n[1] = x;
				n[2] = -z;
			}
			if (instruction == 9) {
				n[0] = y;
				n[1] = -x;
				n[2] = z;
			}
			if (instruction == 10) {
				n[0] = y;
				n[1] = z;
				n[2] = x;
			}
			if (instruction == 11) {
				n[0] = y;
				n[1] = -z;
				n[2] = -x;
			}
			if (instruction == 12) {
				n[0] = -y;
				n[1] = x;
				n[2] = z;
			}
			if (instruction == 13) {
				n[0] = -y;
				n[1] = -x;
				n[2] = -z;
			}
			if (instruction == 14) {
				n[0] = -y;
				n[1] = z;
				n[2] = -x;
			}
			if (instruction == 15) {
				n[0] = -y;
				n[1] = -z;
				n[2] = x;
			}
			if (instruction == 16) {
				n[0] = z;
				n[1] = x;
				n[2] = -y;
			}
			if (instruction == 17) {
				n[0] = z;
				n[1] = -x;
				n[2] = y;
			} 
			if (instruction == 18) {
				n[0] = z;
				n[1] = y;
				n[2] = x;
			}
			if (instruction == 19) {
				n[0] = z;
				n[1] = -y;
				n[2] = -x;
			}
			if (instruction == 20) {
				n[0] = -z;
				n[1] = x;
				n[2] = -y;
			}
			if (instruction == 21) {
				n[0] = -z;
				n[1] = -x;
				n[2] = y;
			}
			if (instruction == 22) {
				n[0] = -z;
				n[1] = y;
				n[2] = x;
			}
			if (instruction == 23) {
				n[0] = -z;
				n[1] = -y;
				n[2] = -x;
			}
			beacons.put(b, n);	
		}
			for (Integer s : stationTwo.stations.keySet()) {
				int[] temp = stationTwo.stations.get(s);
				int x = temp[0];
				int y = temp[1];
				int z = temp[2];
				
				int[] n = new int[3];
				
				if (instruction == 0) {
					n[0] = x;
					n[1] = y;
					n[2] = z;
				}
				if (instruction == 1) {
					n[0] = x;
					n[1] = -y;
					n[2] = -z;
				}
				if (instruction == 2) {
					n[0] = x;
					n[1] = z;
					n[2] = -y;
				}
				if (instruction == 3) {
					n[0] = x;
					n[1] = -z;
					n[2] = y;
				}
				if (instruction == 4) {
					n[0] = -x;
					n[1] = y;
					n[2] = -z;
				}
				if (instruction == 5) {
					n[0] = -x;
					n[1] = -y;
					n[2] = z;
				}
				if (instruction == 6) {
					n[0] = -x;
					n[1] = z;
					n[2] = y;
				}
				if (instruction == 7) {
					n[0] = -x;
					n[1] = -z;
					n[2] = -y;
				}
				if (instruction == 8) {
					n[0] = y;
					n[1] = x;
					n[2] = -z;
				}
				if (instruction == 9) {
					n[0] = y;
					n[1] = -x;
					n[2] = z;
				}
				if (instruction == 10) {
					n[0] = y;
					n[1] = z;
					n[2] = x;
				}
				if (instruction == 11) {
					n[0] = y;
					n[1] = -z;
					n[2] = -x;
				}
				if (instruction == 12) {
					n[0] = -y;
					n[1] = x;
					n[2] = z;
				}
				if (instruction == 13) {
					n[0] = -y;
					n[1] = -x;
					n[2] = -z;
				}
				if (instruction == 14) {
					n[0] = -y;
					n[1] = z;
					n[2] = -x;
				}
				if (instruction == 15) {
					n[0] = -y;
					n[1] = -z;
					n[2] = x;
				}
				if (instruction == 16) {
					n[0] = z;
					n[1] = x;
					n[2] = -y;
				}
				if (instruction == 17) {
					n[0] = z;
					n[1] = -x;
					n[2] = y;
				} 
				if (instruction == 18) {
					n[0] = z;
					n[1] = y;
					n[2] = x;
				}
				if (instruction == 19) {
					n[0] = z;
					n[1] = -y;
					n[2] = -x;
				}
				if (instruction == 20) {
					n[0] = -z;
					n[1] = x;
					n[2] = -y;
				}
				if (instruction == 21) {
					n[0] = -z;
					n[1] = -x;
					n[2] = y;
				}
				if (instruction == 22) {
					n[0] = -z;
					n[1] = y;
					n[2] = x;
				}
				if (instruction == 23) {
					n[0] = -z;
					n[1] = -y;
					n[2] = -x;
				}
			stations.put(s, n);
		}
		this.joinTheDots();
	}

	public Station(Station s1, Station temp, int x, int y, int z) {
		
		beacons = new HashMap<Integer, int[]>();
		stations = new HashMap<Integer, int[]>();
		//System.out.println(x + " " + y + " " + z);
		
		// copy beacons from s1 to new station
		for (Integer b : s1.beacons.keySet()) {
			
			int[] value = new int[3];
			
			value[0] = s1.beacons.get(b)[0];
			value[1] = s1.beacons.get(b)[1];
			value[2] = s1.beacons.get(b)[2];
			
			beacons.put(b, value);
		}
		for (Integer s : s1.stations.keySet()) {
			
			int[] value = new int[3];
			
			value[0] = s1.stations.get(s)[0];
			value[1] = s1.stations.get(s)[1];
			value[2] = s1.stations.get(s)[2];
			
			stations.put(s, value);
		}
		
		// copy beacons from temp adjusting for x,y, z offset to new station
		int n = beacons.size();
		
		for (Integer b : temp.beacons.keySet()) {
			
			int[] value = new int[3];
			
			value[0] = temp.beacons.get(b)[0] + x;
			value[1] = temp.beacons.get(b)[1] + y;
			value[2] = temp.beacons.get(b)[2] + z;
			
			// make sure not to put in duplicate beacons
			if (!duplicate(value[0], value[1], value[2])) {
				beacons.put(n, value);
				n++;
			}
		}
		n = stations.size();
		
		for (Integer s : temp.stations.keySet()) {
			
			int[] value = new int[3];
			
			value[0] = temp.stations.get(s)[0] + x;
			value[1] = temp.stations.get(s)[1] + y;
			value[2] = temp.stations.get(s)[2] + z;
			
			// make sure not to put in duplicate beacons
			
			stations.put(n, value);
			n++;
			
		}
		
		// call join the dots
		this.joinTheDots();
	}



	private boolean duplicate(int i, int j, int k) {
		for (int a = 0; a < beacons.size(); a++) {
			int[] t = beacons.get(a);
			
			if (i == t[0] && j == t[1] && k == t[2]) {
				return true;
			}
		}
		return false;
	}

	public String toString() {
		String string = "";
		for (int i : beacons.keySet()) {
			int[] temp = beacons.get(i);
			string += temp[0] + "," + temp[1] + "," + temp[2] + "\n";
		}
		return string;
	}

	public void joinTheDots() {
		distances = new ArrayList<int[]>();
		
		int numOfBeacons = beacons.size();
		
		for (int i = 0; i < numOfBeacons; i++) {
			for (int j = 0; j < numOfBeacons; j++) {
				if (j > i) {
				int[] temp = new int[5];
				for (int k = 0; k < 3; k++) {
					temp[k] = beacons.get(j)[k] - beacons.get(i)[k];
				}
				temp[3] = i;
				temp[4] = j;
				distances.add(temp);
			
				}
			}
		}
	}
	
	
	public ArrayList<int[]> overlap(Station s2) {
		
		
		ArrayList<int[]> ovs = new ArrayList<int[]>();

	
		
		for (int i = 0; i < this.distances.size(); i ++) {
			for (int j = 0; j < s2.distances.size(); j ++) {
				int[] t1 = this.distances.get(i);
				int[] t2 = s2.distances.get(j);
			
				
				
					
					
					if (t1[0] == t2[0] && t1[1] == t2[1] && t1[2] == t2[2]) {
						int[] ov = {t1[3], t1[4], t2[3], t2[4]};
						ovs.add(ov);

					}
					
				
			}
		}

		return ovs;
		
	}

	public Station join(Station s2) {
		

			
		
		for (int i = 0; i < 24; i++) {
			
				
				Station temp = new Station(s2, i);
				ArrayList<int[]> ov = overlap(temp);

				
				
				// for each row in the overlap between this and station 2
				for (int j = 0; j < ov.size(); j++) {
					
					int[] details = ov.get(j);
					
					
					// positions one and two
					int[] p2 = temp.beacons.get(details[2]);
					int[] p1 = this.beacons.get(details[0]);
					
					int x1  = p1[0];
					int y1 = p1[1];
					int z1 = p1[2];
					
					int x2  = p2[0];
					int y2 = p2[1];
					int z2 = p2[2];
					
					// offsets
					int x = x1 - x2;
					int y = y1 - y2;
					int z = z1 - z2;
					
					int matches = 0;
					
					for (int a = 0; a < this.beacons.size(); a ++) {
						for (int b = 0; b < temp.beacons.size(); b ++) {
							
							int[] b1 = this.beacons.get(a);
							int[] b2 = temp.beacons.get(b);	

							if (b1[0] == b2[0] + x && b1[1] == b2[1] + y && b1[2] == b2[2] + z) {
								matches++;
							}
						}
					}
					
					if (matches >= 12) {
					return new Station(this, temp, x, y, z);
					}
				}
			}
		

	return null;
}




}
