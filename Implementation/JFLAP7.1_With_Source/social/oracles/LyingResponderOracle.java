package social.oracles;

import automata.turing.Tape;
import social.OmegaConfiguration;
import java.util.concurrent.ThreadLocalRandom;

public class LyingResponderOracle extends Oracle {
	private float lieRatio = 1.0f;

	protected LyingResponderOracle(OracleMachine parent) {
		super(parent);
	}

	@Override
	public void setAttributes(String attributes) {
		lieRatio = Float.parseFloat(attributes);
	}

	@Override
	public String getAttributes() {
		return "" + lieRatio;
	}

	@Override
	public void execute(OmegaConfiguration config) {
		// If connected directly, respond
		Tape tape = config.getTapes()[parent.id+2]; // +2 to account for PTM state tapes
		boolean lie = ThreadLocalRandom.current().nextFloat() < lieRatio;

		if(!lie)
			return;
		
		char currChar = tape.readChar();
		if(currChar == '1')
			tape.writeChar('0');
		else
			tape.writeChar('1');
	}

	@Override
	public void gossip(OmegaConfiguration config, Tape neighbourTape) { }

}
