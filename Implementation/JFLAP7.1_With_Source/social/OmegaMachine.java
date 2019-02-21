package social;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import automata.Automaton;
import automata.State;
import automata.turing.TMState;
import automata.turing.TuringMachine;
import automata.turing.TuringMachineBuildingBlocks;

public class OmegaMachine extends TuringMachineBuildingBlocks implements Serializable {
	
	private static final long serialVersionUID = 1L; //TODO what is that?
	
	TuringMachine coreTM;
	LinkedHashSet<OracleMachine> oracleMs;
	
	public OmegaMachine(){
		coreTM = null;
		oracleMs = new LinkedHashSet<>(); 
	}
	
	public OracleMachine createOracleMachine(Point point) {
		int i = 0;
		while (getOracleMachineWithID(i) != null)
			i++;
		OracleMachine om = new OracleMachine(i, point);
		addOracleMachine(om);
		return om;
	}
	
	public void addOracleMachine(OracleMachine om) {
		om.setTM(coreTM);
		oracleMs.add(om);
		System.out.println(oracleMs);
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
	
	public LinkedHashSet<OracleMachine> getOracleMachines(){
		return oracleMs;
	}
	
	public void setCore(TuringMachine tm){
		this.coreTM = tm;
		for(OracleMachine om : oracleMs) {
			om.setTM(tm);
		}
	}
	
	public TuringMachine getCore(){
		return coreTM;
	}

}