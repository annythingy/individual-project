package social;

import java.awt.Point;

import automata.turing.TuringMachine;

public class PersistentTuringMachine extends TuringMachine {

	private static final long serialVersionUID = 1L;
	
	String name;
	OmegaMachine omegaParent;
	Point point;
	
	public PersistentTuringMachine(Point point, OmegaMachine omegaParent){
		super(2);
		this.omegaParent = omegaParent;
		this.point = point;
	}
	
	public PersistentTuringMachine(TuringMachine tm){
		this.tapes = tm.tapes();
		this.states = tm.states;
		this.transitions = tm.transitions;
		this.finalStates = tm.finalStates;
		this.initialState = tm.getInitialState(); 
		this.transitionFromStateMap = tm.transitionFromStateMap;
		this.transitionToStateMap = tm.transitionToStateMap;
		this.transitionArrayFromStateMap = tm.transitionArrayFromStateMap;
		this.transitionArrayToStateMap = tm.transitionArrayToStateMap;
	}
	
	public OmegaMachine getOmegaParent(){
		return omegaParent;
	}

	public void setOmegaParent(OmegaMachine omPar) {
		this.omegaParent = omPar;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}

}
