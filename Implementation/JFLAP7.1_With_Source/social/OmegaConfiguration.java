package social;

import automata.State;
import automata.turing.AcceptanceFilter;
import automata.turing.TMConfiguration;
import automata.turing.Tape;

public class OmegaConfiguration extends TMConfiguration {

	public OmegaConfiguration(State state, TMConfiguration parent, Tape[] tapes, AcceptanceFilter[] filters) {
		super(state, parent, tapes, filters);
		// TODO: Do we want to add individual Oracle state info here?
	}
	
	@Override
	public boolean isAccept() {
		int currTape = 0;
		for(Tape t : getTapes()) {
			String tapeContents = t.getContents();
			if(((currTape != 1) && ((tapeContents.charAt(0) != Tape.BLANK) || (tapeContents.length() > 1)))
				|| ((currTape == 1) && (tapeContents.length() <= 1) && (tapeContents.charAt(0) == Tape.BLANK)))
				return false;
			
			currTape++;
		}
		
		return true;
	}
}
