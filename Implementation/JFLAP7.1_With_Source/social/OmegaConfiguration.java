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
		Tape stateTape = getTapes()[0];
		return stateTape.getContents().equals("X");
	}
}
