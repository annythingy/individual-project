package social;

import automata.turing.TuringMachine;

public class PersistentTuringMachine extends TuringMachine {

	private static final long serialVersionUID = 1L;
	
	public PersistentTuringMachine(){
		super(2);
	}
	
	public PersistentTuringMachine(TuringMachine tm){
		this.tapes = tm.tapes();
	}

}
