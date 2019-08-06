package social;

import automata.turing.Tape;

public class RumourSpreadingOracle extends Oracle {

	public RumourSpreadingOracle(OracleMachine parent) {
		this.parent = parent;
		this.internalTape = new Tape();
	}
	
	@Override
	public void execute(OmegaConfiguration config) {
		// If connected directly, respond
		Tape tape = config.getTapes()[parent.id+2]; // +2 to account for PTM state tapes
		if(tape.readChar() == '1')
			return;
		
		// Otherwise, check if neighbours have been connected to
		for(OracleMachine o : parent.neighbours) {
			Tape itape = new Tape();
			o.gossip(itape, config);
			if(itape.readChar() == '1')
				tape.writeChar('1');
		}
	}

	@Override
	public void gossip(OmegaConfiguration config, Tape neighbourTape) {
		Tape otape = config.getTapes()[parent.id+2]; // +2 to account for PTM state tapes
		if(otape.readChar() == '1')
			neighbourTape.writeChar('1');
		else
			neighbourTape.writeChar('0');
	}
}
