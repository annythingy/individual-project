package social.oracles;

import automata.turing.Tape;
import social.OmegaConfiguration;

public abstract class Oracle {
	protected OracleMachine parent;
	protected Tape ioTape, internalTape;
	
	protected Oracle(OracleMachine parent) {
		this.parent = parent;
		this.internalTape = new Tape();
	}
	
	public static String[] OracleList() {
		return new String[] {
			"NoOpOracle",
			"RumourSpreadingOracle",
			"LyingResponderOracle",
			"InformationOracle"
		};
	}
	
	public static Oracle constructOracle(String type, OracleMachine parent) {
		switch(type) {
			case "NoOpOracle":
				return new NoOpOracle(parent);
			case "RumourSpreadingOracle":
				return new RumourSpreadingOracle(parent);
			case "LyingResponderOracle":
				return new LyingResponderOracle(parent);
			case "InformationOracle":
				return new InformationOracle(parent);
			default:
				throw new IllegalArgumentException("Unknown Oracle type: " + type);
		}
	}
	
	public abstract void setAttributes(String attributes);
	public abstract String getAttributes();

	public abstract void execute(OmegaConfiguration config);
	public abstract void gossip(OmegaConfiguration config, Tape neighbourTape);
}