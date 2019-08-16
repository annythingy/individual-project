package social;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import automata.Automaton;
import automata.AutomatonSimulator;
import automata.Configuration;
import automata.State;
import automata.Transition;
import automata.turing.AcceptanceFilter;
import automata.turing.TMTransition;
import automata.turing.Tape;
import social.oracles.OracleMachine;

// TODO: Oh boy it would be nice if this was Simulator<Omega> but that means rewriting the whole system.
public class OmegaSimulator extends AutomatonSimulator {
	public static final char ANY_CHAR = '~';

	public OmegaSimulator(Automaton automaton) {
		super(automaton);
	}

	/*
	 * Create an initial state with the same string on every tape.
	 */
	@Override
	public Configuration[] getInitialConfigurations(String input) {
		int numTapes = ((OmegaMachine) myAutomaton).getCore().tapes();
		String[] inputs = new String[numTapes];
		// TODO: Use a special character to split rather than duplicate
		for(int i = 0; i < numTapes; i++)
			inputs[i] = input;
		return getInitialConfigurations(inputs);
	}
	
	/*
	 * Create an initial state with a string per tape.
	 */
	public Configuration[] getInitialConfigurations(String[] inputs) {
		Tape[] tapes = new Tape[inputs.length];
		for(int i = 0; i < tapes.length; i++)
			tapes[i] = new Tape(inputs[i]);
		
		OmegaConfiguration[] ocs = new OmegaConfiguration[1];
		ocs[0] = new OmegaConfiguration(((OmegaMachine) myAutomaton).getInitialState(),
				null, tapes, new AcceptanceFilter[0]);
		return ocs;
	}

	/*
	 * Loop:
	 *   - Run PTM from init to final state (writing to input and output tapes)
	 *   - Execute logic for each oracle (writing to respective PTM tape)
	 */
	@Override
	public ArrayList<Configuration> stepConfiguration(Configuration config) {
		ArrayList<Configuration> branches = new ArrayList<>();
		OmegaConfiguration oc = (OmegaConfiguration) config;
		State currState = oc.getCurrentState();
		Tape[] oldTapes = oc.getTapes();
		Transition[] possibleTransitions = myAutomaton.getTransitionsFromState(currState);
		
		// Debugging
		System.out.print(currState.getName() + " | ");
		for(Tape t : oldTapes)
			System.out.print(t.getContents() + " : ");
		System.out.print('\n');
		
		// If Final State execute Oracles and go to Initial
	    if(myAutomaton.isFinalState(currState)) {
	    	System.out.println("Executing Omegas...");
			for(OracleMachine oracle : ((OmegaMachine) myAutomaton).getOracleMachines()) {
				// TODO: Execute based on context and tape ID?
				oracle.execute(oc); // Operates on existing tapes
			}
			OmegaConfiguration rerunOm = new OmegaConfiguration(myAutomaton.getInitialState(),
					oc, oc.getTapes(), new AcceptanceFilter[0]); // AcceptanceFilters unused
			branches.add(rerunOm);
		}
		
		// If not final execute normal TM
		else {
			// Attempting following every possible transition
			for(Transition trn : possibleTransitions) {
				// If the read condition isn't satisfied for a tape, skip
				boolean readsSatisfied = true;
				for(int t = 0; t < oldTapes.length; t++) {
					String expected = ((TMTransition) trn).getRead(t);
					if(expected.charAt(0) == ANY_CHAR)
						continue;
					if(!oldTapes[t].read().equals(expected))
						readsSatisfied = false;
				}
				if(!readsSatisfied)
					continue;
				
				// If all reads satisfied, write updates to cloned tapes and add to branches
				Tape[] newTapes = new Tape[oldTapes.length];
				for(int t = 0; t < oldTapes.length; t++) {
					String newChar = ((TMTransition) trn).getWrite(t);
					newTapes[t] = new Tape(oldTapes[t]);
					if(newChar.charAt(0) != ANY_CHAR)
						newTapes[t].write(newChar);
					newTapes[t].moveHead(((TMTransition) trn).getDirection(t));
				}
				OmegaConfiguration stateStep = new OmegaConfiguration(trn.getToState(),
						oc, newTapes, new AcceptanceFilter[0]);
				branches.add(stateStep);
			}
		}
		
		myConfigurations = new HashSet<Configuration>(branches);
		return branches;
	}

	/*
	 * If the core reaches a finishing state with nothing left on the input
	 * and Oracle tapes then it can be assumed all processing has finished.
	 */
	@Override
	public boolean isAccepted() {
		Iterator<Configuration> it = myConfigurations.iterator();
		
		// Don't accept on a zero state
		if(!it.hasNext())
			return false;

		// Should only be single configuration as is deterministic, but just in case check all
		while (it.hasNext()) {
			boolean branchAccepted = true;
			OmegaConfiguration configuration = (OmegaConfiguration) it.next();
			State currentState = configuration.getCurrentState();

			if(!myAutomaton.isFinalState(currentState)) {
				continue;
			}
			
			// Check all tapes except the output tape are empty (i.e. no more work)
			int tapeNum = 0;
			for(Tape t : configuration.getTapes()) {
				String tapeContents = t.getContents();
				if(((tapeNum != 1) && ((tapeContents.charAt(0) != Tape.BLANK) || (tapeContents.length() > 1)))
					|| ((tapeNum == 1) && (tapeContents.length() <= 1) && (tapeContents.charAt(0) == Tape.BLANK)))
					branchAccepted = false;

				tapeNum++;
			}
			
			if(branchAccepted)
				return true;
		} 

		return false;
	}

	/*
	 * Step through configurations until reaching an accepting state (true)
	 * or generating nothing else to execute (false).
	 */
	@Override
	public boolean simulateInput(String input) {
		myConfigurations = new HashSet<>(Arrays.asList(getInitialConfigurations(input)));
		while (!myConfigurations.isEmpty()) {
			if(isAccepted()) return true;
			// Copy out, clear, and execute current configurations to generate new
			Set<Configuration> currConfigs = myConfigurations;
			myConfigurations = new HashSet<Configuration>();
			for(Configuration config : currConfigs) {
				myConfigurations.addAll(stepConfiguration(config));
			}
		}
		return false;
	}

}
