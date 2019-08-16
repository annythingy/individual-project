package social.oracles;

import automata.turing.Tape;
import social.OmegaConfiguration;

public class NoOpOracle extends Oracle {
	protected NoOpOracle(OracleMachine parent) {
		super(parent);
	}

	@Override
	public void setAttributes(String attributes) { }

	@Override
	public String getAttributes() {
		return "";
	}

	@Override
	public void execute(OmegaConfiguration config) { }

	@Override
	public void gossip(OmegaConfiguration config, Tape neighbourTape) { }
}
