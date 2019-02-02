package social;

import automata.Automaton;
import automata.turing.TuringMachine;

public class OracleMachine extends Automaton {
	
	private static final long serialVersionUID = 1L; //TODO what is that?
	
	public OracleMachine(TuringMachine tm){
		System.out.println("My TMummy has " + tm.tapes + " tapes.");
	}
	
}