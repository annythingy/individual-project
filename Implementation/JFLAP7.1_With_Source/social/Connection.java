package social;

public class Connection{
	
	OracleMachine omA, omB;
	
	public Connection(OracleMachine omA, OracleMachine omB){
		this.omA = omA;
		this.omB = omB;
	}
	
	public OracleMachine getStart(){
		return omA;
	}
	
	public OracleMachine getEnd(){
		return omB;
	}
}
