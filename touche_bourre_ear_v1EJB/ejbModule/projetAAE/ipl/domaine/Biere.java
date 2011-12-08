package projetAAE.ipl.domaine;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import projetAAE.ipl.exceptions.ArgumentInvalideException;
import projetAAE.ipl.util.Util;

@Entity
@Table(name="BIERES", schema="TOUCHEBOURRE")
public class Biere implements Serializable {
	
	@Id @GeneratedValue
	@Column(name = "ID_BIERE")
	private int id;
	
	@Column
	private int x;
	
	@Column
	private int y;
	
	@Column
	private boolean aCoule;
	
	@Enumerated(EnumType.STRING)
	private ETable tableTouchee;
	
	
	public Biere() {
		
	}
	
	public Biere(int x, int y, ETable tableTouchee) throws ArgumentInvalideException {
		
		Util.checkPositiveOrZero(x);
		Util.checkPositiveOrZero(y);
		this.x = x;
		this.y = y;
		this.tableTouchee = tableTouchee;
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

	public ETable getTableTouchee() {
		return tableTouchee;
	}

	public void setTableTouchee(ETable tableTouchee) {
		this.tableTouchee = tableTouchee;
	}

	public int getId() {
		return id;
	}

	public boolean aCoule() {
		return aCoule;
	}

	public void setaCoule(boolean aCoule) {
		this.aCoule = aCoule;
	}
	
	
	
	

}
