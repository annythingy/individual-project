package social;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import gui.editor.BuildingBlockTool;
import gui.viewer.AutomatonDrawer;
import gui.viewer.AutomatonPane;

public class TMTool extends BuildingBlockTool {

	public TMTool(AutomatonPane view, OmegaDrawer drawer) {
		super(view, drawer);
		// TODO Auto-generated constructor stub
	}
	
	protected Icon getIcon() {
		java.net.URL url = getClass().getResource("/ICON/tm.gif");
		return new ImageIcon(url);
	}

}
