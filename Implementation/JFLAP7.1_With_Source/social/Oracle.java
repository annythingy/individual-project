package social;

import automata.turing.Tape;

public abstract class Oracle {
	protected OracleMachine parent;
	protected Tape ioTape, internalTape;

	public abstract void execute(OmegaConfiguration config);
	public abstract void gossip(OmegaConfiguration config, Tape neighbourTape);
}