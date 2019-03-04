package social;

import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import gui.editor.Tool;
import gui.viewer.AutomatonPane;

import social.OmegaDrawer;

public class PTMTool extends Tool {
	
	public PTMTool(AutomatonPane view, OmegaDrawer drawer) {
		super(view, drawer);
	}
	
	protected Icon getIcon() {
		java.net.URL url = getClass().getResource("/ICON/tm.gif");
		return new ImageIcon(url);
	}
	
	public void mousePressed(MouseEvent event) {
		if(((OmegaMachine) drawer.getAutomaton()).getCore() == null) {
			PersistentTuringMachine ptm = ((OmegaMachine) getAutomaton()).createPTM(event.getPoint());
			((OmegaMachine) drawer.getAutomaton()).setCore(ptm);
			((OmegaDrawer) drawer).setTMX(event.getPoint().x);
			((OmegaDrawer) drawer).setTMY(event.getPoint().y);
			getView().repaint();
		} else System.out.println("Only one TM at a time for now.");
	}
	
}
