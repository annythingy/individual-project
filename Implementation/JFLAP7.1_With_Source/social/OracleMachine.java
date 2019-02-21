package social;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import automata.Automaton;
import automata.turing.TuringMachine;

public class OracleMachine extends Automaton {
	
	private static final long serialVersionUID = 1L; //TODO what is that?
	
	Integer id;
	Point point;
	TuringMachine tmCore;
	
	Set<OracleMachine> neighbours;
	
	Set<Oracle> oracles;
	
	boolean selected;
	
	public OracleMachine(Integer id, Point point){
		this.id = id;
		this.point = point;
		this.tmCore = null;
		this.oracles = new HashSet<Oracle>(1);
		this.neighbours = new HashSet<OracleMachine>();
	}
	
	public Integer getID(){
		return id;
	}
	
	public void setPoint(Point point) {
		this.point = point;
	}

	public Point getPoint() {
		return point;
	}
	
	@Override
	public String toString(){
		return "oMachine " + id + " with parent " + tmCore + " at " + point.x + " and " +point.y;
	}
	
	public void setTM(TuringMachine tm) {
		this.tmCore = tm;
	}
	
	public void setSelected(boolean selected){
		this.selected = selected;
	}
	
	public boolean isSelected(){
		return selected;
	}
	
	public TuringMachine getTM() {
		return tmCore;
	}
	
	public void addOracle(Oracle o) {
		oracles.add(o);
	}
	
	public void addNeighbour(OracleMachine on){
		neighbours.add(on);
	}
	
	public Set<OracleMachine> getNeighbours(){
		return neighbours;
	}
	
	public boolean isAtomic() {
		return oracles.size() == 1;
	}

}