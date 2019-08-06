package social;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JButton;

import automata.Automaton;
import automata.State;
import automata.Transition;
import automata.turing.TuringMachine;
import automata.turing.TuringMachineBuildingBlocks;
import gui.action.OpenAction;

public class OmegaMachine extends Automaton implements Serializable {
	
	private static final long serialVersionUID = 1L; //TODO what is that?
	
	PersistentTuringMachine coreTM;
	Set<OracleMachine> oracleMs;
	Set<Connection> connections;
	
	public OmegaMachine(){
		coreTM = null;
		oracleMs = new LinkedHashSet<>(); 
		connections = new LinkedHashSet<Connection>();
	}
	
	public OracleMachine createOracleMachine(Point point) {
		int i = 0;
		while (getOracleMachineWithID(i) != null)
			i++;
		OracleMachine om = new OracleMachine(i, point, this);
		addOracleMachine(om);
		return om;
	}
	
	public OracleMachine createOracleMachineWithID(Point point, Integer id) {
		OracleMachine om = new OracleMachine(id, point, this);
		addOracleMachine(om);
		return om;
	}
	
	public void addOracleMachine(OracleMachine om) {
		om.setTM(coreTM);
		oracleMs.add(om);
	}
	
	public void removeOracleMachine(OracleMachine om){
		oracleMs.remove(om);
	}
	
	public OracleMachine getOracleMachineWithID(int id) {
		Iterator<OracleMachine> it = oracleMs.iterator();
		while (it.hasNext()) {
			OracleMachine om = (OracleMachine) it.next();
			if (om.getID() == id)
				return om;
		}
		return null;
	}
	
	public void selectOraclesWithinBounds(Rectangle bounds){
		Set<OracleMachine> omSet = getOracleMachines();
		for(OracleMachine om : omSet){
			om.setSelected(false);
			if(bounds.contains(om.getPoint())){	
				om.setSelected(true);
			}
		}
	}
	
	public void addConnection(OracleMachine omA, OracleMachine omB) {
		connections.add(new Connection(omA, omB));
	}
	
	public void removeConnection(Connection c) {
		c.removeSelf();
		connections.remove(c);
	}
	
	public Set<OracleMachine> getOracleMachines(){
		return oracleMs;
	}
	
	public Set<Connection> getConnections(){
		return connections;
	}
	
	public PersistentTuringMachine createPTM(Point point){
		OpenAction read = new OpenAction();
		OpenAction.setOpenOrRead(true);
		JButton button = new JButton(read);
		button.doClick();
		OpenAction.setOpenOrRead(false);
		return getAutomatonFromFile(point);
	}
	
	public void setCore(PersistentTuringMachine ptm){
		this.coreTM = ptm;
		ptm.setOmegaParent(this);
		for(OracleMachine om : oracleMs) {
			om.setTM(ptm);
		}
	}
	
	public PersistentTuringMachine getCore(){
		return coreTM;
	}
	
	@Override
	public State getInitialState() {
		return coreTM.getInitialState();
	}
	
	@Override
	public Transition[] getTransitionsFromState(State state) {
		return coreTM.getTransitionsFromState(state);
	}
	
	@Override
	public boolean isFinalState(State state) {
		return coreTM.isFinalState(state);
	}
	
	private PersistentTuringMachine getAutomatonFromFile(Point point) {
		if (OpenAction.dontOpen) {
			return null;
		}
		
		Serializable serial = OpenAction.getLastObjectOpened();
		File lastFile = OpenAction.getLastFileOpened();
		if (lastFile == null || OpenAction.isOpened() == false) {
			return null;
		}

        TuringMachine tm = (TuringMachine) serial;

        tm.setEnvironmentFrame(this.getEnvironmentFrame());

        PersistentTuringMachine block = new PersistentTuringMachine(tm);
        block.setPoint(point);
		block.setName(lastFile.getName().substring(0, lastFile.getName().length() - 4));
		block.setFilePath(lastFile.getPath());
		
		System.out.println(tm);
		
		return block;
	}

	public int tapes() {
		return coreTM.tapes();
	}
}