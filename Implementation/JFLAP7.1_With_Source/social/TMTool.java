package social;

import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import automata.turing.TuringMachineBuildingBlocks;
import gui.editor.BuildingBlockTool;
import gui.viewer.AutomatonPane;

import social.OmegaDrawer;

public class TMTool extends BuildingBlockTool {
	
	public TMTool(AutomatonPane view, OmegaDrawer drawer) {
		super(view, drawer);
	}
	
	protected Icon getIcon() {
		java.net.URL url = getClass().getResource("/ICON/tm.gif");
		return new ImageIcon(url);
	}
	
	@Override
	public void mousePressed(MouseEvent event) {
		if(((OmegaMachine) drawer.getAutomaton()).getCore() == null) {
			automata.turing.TMState block = ((TuringMachineBuildingBlocks) getAutomaton()).createBlock(event.getPoint());
			((OmegaMachine) drawer.getAutomaton()).setCore(new PersistentTuringMachine(block.getInnerTM()));
			((OmegaDrawer) drawer).setTMX(event.getPoint().x);
			((OmegaDrawer) drawer).setTMY(event.getPoint().y);
			getView().repaint();
		} else System.out.println("Only one TM at a time for now.");
	}
	
}
