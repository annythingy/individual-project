package social;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import automata.Automaton;
import automata.turing.TuringMachine;

public class OracleMachine extends Automaton {

	private static final long serialVersionUID = 1L; // TODO what is that?

	Integer id;
	Point point;
	PersistentTuringMachine tmCore;
	
	OmegaMachine omegaParent;
	String name;
	
	String defaultName = "Someone";

	Set<OracleMachine> neighbours;

	Set<Oracle> oracles;
	Oracle oracle;

	boolean selected;

	public OracleMachine(Integer id, Point point, OmegaMachine omegaParent) {
		this.id = id;
		this.point = point;
		this.tmCore = null;
		this.name = defaultName;
		oracle = new Oracle(this);
		this.oracles = new HashSet<Oracle>(1);
		addOracle(oracle);
		this.neighbours = new HashSet<OracleMachine>();
		this.omegaParent = omegaParent;
	}

	public Integer getID() {
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public Point getPoint() {
		return point;
	}

	@Override
	public String toString() {
		return "oMachine " + id + " with parent " + tmCore + " at " + point.x + " and " + point.y;
	}

	public void setTM(PersistentTuringMachine tm) {
		this.tmCore = tm;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}

	public TuringMachine getTM() {
		return tmCore;
	}

	public void addOracle(Oracle o) {
		oracles.add(o);
	}

	public void addNeighbour(OracleMachine on) {
		neighbours.add(on);
	}

	public void removeNeighbour(OracleMachine on) {
		neighbours.remove(on);
	}

	public Set<OracleMachine> getNeighbours() {
		return neighbours;
	}

	public boolean isAtomic() {
		return oracles.size() == 1;
	}

	public OmegaMachine getOmegaParent() {
		return omegaParent;
	}

}