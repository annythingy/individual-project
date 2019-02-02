package social;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import automata.Automaton;
import automata.State;
import automata.turing.TuringMachine;

public class OmegaMachine extends Automaton {
	
	private static final long serialVersionUID = 1L; //TODO what is that?
	
	TuringMachine coreTM = new TuringMachine(2);
	Set<OracleMachine> oracleMs = new HashSet<>()
;	
	public OmegaMachine(){
		OracleMachine om = new OracleMachine(new TuringMachine(2));
	}
	
}