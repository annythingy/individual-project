package social;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import automata.State;
import automata.turing.TMState;
import automata.turing.TuringMachine;
import automata.turing.TuringMachineBuildingBlocks;

public class OmegaMachine extends TuringMachineBuildingBlocks {
	
	private static final long serialVersionUID = 1L; //TODO what is that?
	
	TuringMachine coreTM;
	LinkedHashSet<OracleMachine> oracleMs;
	//HashMap<OracleMachine, LinkedHashSet<OracleMachine>> connections;
	HashSet<Connection> connections;
	
	public OmegaMachine(){
		coreTM = null;
		oracleMs = new LinkedHashSet<>(); 
		connections = new HashSet<>();
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
	
	public void addConnection(Connection c){
		connections.add(c);
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
	
	public HashSet<Connection> getConnections(){
		return connections;
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