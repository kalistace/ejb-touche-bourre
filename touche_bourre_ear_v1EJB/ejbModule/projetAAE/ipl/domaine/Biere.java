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
	@Column(name = "ID")
	private int id;
	
	@Column
	private int x;
	
	@Column
	private int y;
	
	@Enumerated(EnumType.STRING)
	private ETable tableTouchee;
	
	
	public Biere() {
		
	}
	
	public Biere(int x, int y) throws ArgumentInvalideException {
		
		Util.checkPositiveOrZero(x);
		Util.checkPositiveOrZero(y);
		this.x = x;
		this.y = y;
	}
	
	public void setTableTouchee(ETable t) {
		this.tableTouchee = t;
	}

}
