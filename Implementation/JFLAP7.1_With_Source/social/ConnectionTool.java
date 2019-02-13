package social;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import gui.editor.Tool;
import gui.viewer.AutomatonPane;

public class ConnectionTool extends Tool {
	
	protected OracleMachine first;
	protected Point hover;
	
	protected ConnectionCreator creator;

	private static Stroke STROKE = new java.awt.BasicStroke(2.4f);
	public static java.awt.Color COLOR = new java.awt.Color(.5f, .5f, .5f, .5f);

	public ConnectionTool(AutomatonPane view, OmegaDrawer drawer, ConnectionCreator creator) {
		super(view, drawer);
		this.creator = creator;
	}

	protected Icon getIcon() {
		java.net.URL url = getClass().getResource("/ICON/connection.gif");
		return new ImageIcon(url);
	}
	
	public String getToolTip() {
		return "Connection Creator";
	}
	
	public void mousePressed(MouseEvent event) {
		if (getDrawer().getAutomaton().getEnvironmentFrame() !=null)
    		((OmegaEnvironment)getDrawer().getAutomaton().getEnvironmentFrame().getEnvironment()).saveStatus();
		first = ((OmegaDrawer) getDrawer()).oMachineAtPoint(event.getPoint());
		if (first != null) hover = first.getPoint();
		if (first != null) System.out.println(first.toString());
		else System.out.println("No oMachine");
	}
	
	public void mouseDragged(MouseEvent event) {
		if (first == null)
			return;
		hover = event.getPoint();
		getView().repaint();
	}
	
	public void draw(Graphics g) {
		if (first == null)
			return;
		java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
		Stroke s = g2.getStroke();
		g2.setStroke(STROKE);
		g2.setColor(COLOR);
		g2.drawLine(first.getPoint().x, first.getPoint().y, hover.x, hover.y);
		g2.setStroke(s);
	}
	
	public void mouseReleased(MouseEvent event) {
		if (first == null)
			return;
		OracleMachine om = ((OmegaDrawer) getDrawer()).oMachineAtPoint(event.getPoint());
		if (om != null) {
		    ((OmegaDrawer) getDrawer()).drawConnection(((OmegaDrawer) getDrawer()).getGraphics(), first, om);
		}
		first = null;
		getView().repaint();
	}


}
