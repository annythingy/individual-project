package social;

import gui.editor.Tool;
import gui.viewer.AutomatonDrawer;
import gui.viewer.AutomatonPane;

import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

public class OracleTool extends Tool {
	
	OracleMachine oMachine = null;

	public OracleTool(AutomatonPane view, OmegaDrawer drawer) {
		super(view, drawer);
	}

	public String getToolTip() {
		return "Oracle Creator";
	}

	protected Icon getIcon() {
		java.net.URL url = getClass().getResource("/ICON/default.gif");
		return new ImageIcon(url);
	}

	public void mousePressed(MouseEvent event) {
		if (getDrawer().getAutomaton().getEnvironmentFrame() !=null)
    		((OmegaEnvironment)getDrawer().getAutomaton().getEnvironmentFrame().getEnvironment()).saveStatus();
		
		oMachine = ((OmegaMachine) getAutomaton()).createOracleMachine(event.getPoint());
		
		getView().repaint();
	}

	public void mouseDragged(MouseEvent event) {
		oMachine.setPoint(event.getPoint());
		getView().repaint();
	}

	public KeyStroke getKey() {
		return KeyStroke.getKeyStroke('o');
	}

}
