package social;

import gui.viewer.SelectionDrawer;

public class OmegaSelectionDrawer extends SelectionDrawer {
	private OmegaMachine om;

	public OmegaSelectionDrawer(OmegaMachine om) {
		super(om.coreTM);
		this.om = om;
	}
}
