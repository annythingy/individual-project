package social;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class OracleMachinePane extends JPanel {

	private static final long serialVersionUID = 1L;
	
	OracleMachine om;
	
	public OracleMachinePane(OracleMachine om){
		this.om = om;
		initGUI();
	}
	
	private void initGUI(){
		add(new JLabel("This is where oracle config happens"));
	}

}
