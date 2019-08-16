package social.oracles;

import automata.turing.Tape;
import social.OmegaConfiguration;
import social.OmegaSimulator;

public class InformationOracle extends Oracle {
	private String info = "";

	protected InformationOracle(OracleMachine parent) {
		super(parent);
	}

	@Override
	public void setAttributes(String attributes) {
		info = attributes;
	}

	@Override
	public String getAttributes() {
		return info;
	}

	@Override
	public void execute(OmegaConfiguration config) {
		// If probed, respond with character at that position
		Tape tape = config.getTapes()[parent.id+2]; // +2 to account for PTM state tapes
		char request = tape.readChar();
		if(request == Tape.BLANK)
			return;

		int access = Integer.parseInt(""+request);
		if(access < info.length())
			tape.writeChar(info.charAt(access));
		else
			tape.writeChar(Tape.BLANK);
	}

	@Override
	public void gossip(OmegaConfiguration config, Tape neighbourTape) {
		// An intellectual does not gossip
	}

}
