package social;

import gui.editor.EditorPane;
import gui.viewer.SelectionDrawer;

public class EditPTMPane extends EditorPane {

	private static final long serialVersionUID = 1L;

	public EditPTMPane(PersistentTuringMachine automaton) {
		super(new SelectionDrawer(automaton));
	}

	public void setPTM(PersistentTuringMachine ptm) {
		myPTM = ptm;
	}

	public PersistentTuringMachine getPTM() {
		return myPTM;
	}

	protected PersistentTuringMachine myPTM = null;

}
