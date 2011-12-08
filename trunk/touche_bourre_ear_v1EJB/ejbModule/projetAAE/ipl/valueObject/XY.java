package projetAAE.ipl.valueObject;

import projetAAE.ipl.exceptions.ArgumentInvalideException;
import projetAAE.ipl.util.Util;

public class XY {
	
	private int x;
	
	private int y;
	
	public XY(int x, int y) throws ArgumentInvalideException {
		
		Util.checkPositiveOrZero(x);
		Util.checkPositiveOrZero(y);
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	

}
