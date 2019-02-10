package social;

import java.awt.Point;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import automata.Automaton;
import automata.State;
import automata.turing.TuringMachine;
import automata.turing.TuringMachineBuildingBlocks;

public class OmegaMachine extends TuringMachineBuildingBlocks {
	
	private static final long serialVersionUID = 1L; //TODO what is that?
	
	TuringMachine coreTM;
	Set<OracleMachine> oracleMs;
;	
	public OmegaMachine(){
		coreTM = new TuringMachine(2);
		oracleMs = new HashSet<>(); 
	}
	
	public OracleMachine createOracleMachine(Point point) {
		int i = 0;
		while (getOracleMachineWithID(i) != null)
			i++;
		OracleMachine om = new OracleMachine(i, point, coreTM);
		addOracleMachine(om);
		return om;
	}
	
	public void addOracleMachine(OracleMachine om) {
		oracleMs.add(om);
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
	
	
	
}