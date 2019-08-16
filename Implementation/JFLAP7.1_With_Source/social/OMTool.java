package social;

import gui.editor.Tool;
import gui.viewer.AutomatonPane;
import social.oracles.Oracle;
import social.oracles.OracleMachine;

import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

public class OMTool extends Tool {
	
	OracleMachine oMachine = null;

	public OMTool(AutomatonPane view, OmegaDrawer drawer) {
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

		String[] Oracles = Oracle.OracleList();
		String type = (String) JOptionPane.showInputDialog(null,
				"Set Oracle type:", "New Oracle",
				JOptionPane.QUESTION_MESSAGE, null, Oracles, Oracles[0]);

		String attributes = (String) JOptionPane.showInputDialog(null,
				"Set Oracle attributes (if necessary):", "New Oracle",
				JOptionPane.QUESTION_MESSAGE, null, null, "");
		
		oMachine = ((OmegaMachine) getAutomaton()).createOracleMachine(event.getPoint());
		oMachine.setOracle(type, attributes);
		
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
