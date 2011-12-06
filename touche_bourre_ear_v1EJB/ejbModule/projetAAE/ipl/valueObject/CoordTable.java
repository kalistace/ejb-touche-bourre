package projetAAE.ipl.valueObject;

public class CoordTable {
	private int numBat;
	private int ptDepartX;
	private int ptDepartY;
	private int ptArriveX;
	private int ptArriveY;
	
	public CoordTable(int numBat, int ptDepartX, int ptDepartY, int ptArriveX,
			int ptArriveY) {
		super();
		this.numBat = numBat;
		this.ptDepartX = ptDepartX;
		this.ptDepartY = ptDepartY;
		this.ptArriveX = ptArriveX;
		this.ptArriveY = ptArriveY;
	}

	public int getNumBat() {
		return numBat;
	}

	public int getPtDepartX() {
		return ptDepartX;
	}

	public int getPtDepartY() {
		return ptDepartY;
	}

	public int getPtArriveX() {
		return ptArriveX;
	}

	public int getPtArriveY() {
		return ptArriveY;
	}
}
