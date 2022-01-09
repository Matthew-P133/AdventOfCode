
public class Basin implements Comparable {
	private int size = 0;

	public Basin() {
		size = 1;
	}

	public void incrementSize() {
		this.size++;
	}

	public int getSize() {
		return size;
	}

	public int compareTo(Object o) {

		if (this.getSize() < ((Basin) o).getSize()) {
			return -1;
		}
		if (this.getSize() > ((Basin) o).getSize()) {
			return 1;
		}

		return 0;

	}

}
