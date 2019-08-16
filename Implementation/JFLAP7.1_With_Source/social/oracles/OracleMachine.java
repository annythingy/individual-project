package social.oracles;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import automata.Automaton;
import automata.turing.Tape;
import automata.turing.TuringMachine;
import social.OmegaConfiguration;
import social.OmegaMachine;
import social.PersistentTuringMachine;

public class OracleMachine extends Automaton {

	private static final long serialVersionUID = 1L; // TODO what is that?

	public Integer id;
	public Point point;
	PersistentTuringMachine tmCore;
	
	OmegaMachine omegaParent;
	String name;
	
	String defaultName = "Someone";

	public Set<OracleMachine> neighbours;

	Oracle oracle;
	Tape internalState;

	boolean selected;

	public OracleMachine(Integer id, Point point, OmegaMachine omegaParent) {
		this.id = id;
		this.point = point;
		this.tmCore = null;
		this.name = defaultName;
		oracle = new NoOpOracle(this);
		this.neighbours = new HashSet<OracleMachine>();
		this.omegaParent = omegaParent;
		this.internalState = new Tape();
	}

	public Integer getID() {
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public void setOracle(String type, String attributes) {
		oracle = Oracle.constructOracle(type, this);
		oracle.setAttributes(attributes);
	}
	
	public void setAttributes(String attributes) {
		oracle.setAttributes(attributes);
	}
	
	public String getType() {
		return oracle.getClass().getSimpleName();
	}
	
	public String getAttributes() {
		return oracle.getAttributes();
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

	public void addNeighbour(OracleMachine on) {
		neighbours.add(on);
	}

	public void removeNeighbour(OracleMachine on) {
		neighbours.remove(on);
	}

	public Set<OracleMachine> getNeighbours() {
		return neighbours;
	}

	public OmegaMachine getOmegaParent() {
		return omegaParent;
	}
	
	/*
	 * Begin an Oracle's execution based on the context of the surrounding OmegaMachine.
	 */
	public void execute(OmegaConfiguration config) {
		// Further Work: Allow runtime definition of Oracle Machines (not possible in Java)
		oracle.execute(config);
	}
	
	/*
	 * Respond to a gossip request via an intermediary tape
	 * using the existing context.
	 */
	public void gossip(Tape tape, OmegaConfiguration config) {
		oracle.gossip(config, tape);
	}
}