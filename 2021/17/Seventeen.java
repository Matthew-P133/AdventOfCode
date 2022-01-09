
public class Seventeen {
	public static void main(String[] args) {
		// target area: x=253..280, y=-73..-46
		
		long start = System.currentTimeMillis();

		int xmin = 253;
		int xmax = 280;
		int ymin = -73;
		int ymax = -46;
		int triangleXMin = triangle(xmin);

		// part 1
		int n = 0;
		for (int i = -ymin - 1; i >= 1; i--) {
			n += i;
		}
		System.out.println("Part 1: " + n);
		
		
		int counter = 0;
		for (int i = ymin; i < -1 * ymin; i++) {
			for (int j = triangleXMin; j <= xmax; j++) {
				if (hitsTarget(i, j, xmin, xmax, ymin, ymax)) {
					counter++;
				}
			}
		}
		System.out.println("Part 2: " + counter);
		
		
		long end = System.currentTimeMillis();
		System.out.println(end - start + " ms");
	}

	private static int triangle(int xmin) {
		int i = 1;
		int triangularNum = 0;
		int n = 0;

		while (true) {
			triangularNum += i;
			i++;
			n++;
			if (triangularNum >= xmin) {
				break;
			}
		}
		return n;
	}

	private static boolean hitsTarget(int i, int j, int xmin, int xmax, int ymin, int ymax) {
		boolean hitTarget = false;
		int x = 0;
		int y = 0;
		while (true) {
			x += j;
			y += i;
			i--;
			j = j == 0 ? 0 : j - 1;
			if ((x >= xmin && x <= xmax) && (y <= ymax && y >= ymin)) {
				hitTarget = true;
				break;
			}
			if (j == 0 && x < xmin) {
				break;
			}
			if (y < ymin) {
				break;
			}
		}
		return hitTarget;
	}
}
