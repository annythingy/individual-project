package social;

import gui.editor.EditorPane;
import gui.viewer.SelectionDrawer;

public class EditOraclePane extends EditorPane {

	private static final long serialVersionUID = 1L;
	
	protected OracleMachine myOM = null;

	public EditOraclePane(OracleMachine automaton) {
		super(new SelectionDrawer(automaton));
	}

	public void setOM(OracleMachine om) {
		myOM = om;
	}

	public OracleMachine getOM() {
		return myOM;
	}


}
