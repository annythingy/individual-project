package social;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import automata.Automaton;
import automata.State;
import gui.viewer.CurvedArrow;
import gui.viewer.SelectionDrawer;
import gui.viewer.StateDrawer;

public class OmegaDrawer extends SelectionDrawer{
	
	Automaton automaton;
	Graphics2D g;
	
	private static Stroke STROKE = new java.awt.BasicStroke(2.4f);
	public static java.awt.Color COLOR = new java.awt.Color(.5f, .5f, .5f, .5f);

	public OmegaDrawer(Automaton automaton) {
		super(automaton);
		this.automaton = automaton;
		// TODO Auto-generated constructor stub
	}
	
	public void drawAutomaton(Graphics g2) {
		super.drawAutomaton(g2);
		
		this.g = (Graphics2D) g2.create();
		
        Set<OracleMachine> oMachines = ((OmegaMachine) automaton).getOracleMachines();
        
        for (OracleMachine o : oMachines){
            drawOracleMachine(g, o);
        }
        
	}
	
	public void drawOracleMachine(Graphics g, OracleMachine om) {
		Point point = om.getPoint();
		int radius = 25;
		g.setColor(Color.black);

		int dx = ((int) g.getFontMetrics().getStringBounds("No one", g).getWidth()) >> 1;
		int dy = ((int) g.getFontMetrics().getAscent()) >> 1;
		
		g.drawString("No one", point.x - dx, point.y + dy);
		g.drawOval(point.x - radius, point.y - radius, 2 * radius, 2 * radius);
	}
	
	public void drawConnection(Graphics g, OracleMachine omA, OracleMachine omB){
		if (omA == null)
			return;
		java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
		Stroke s = g2.getStroke();
		g2.setStroke(STROKE);
		g2.setColor(COLOR);
		g2.drawLine(omA.getPoint().x, omA.getPoint().y, omB.getPoint().x, omB.getPoint().y);
		g2.setStroke(s);
		System.out.println("e haide de");
	}
	
	public OracleMachine oMachineAtPoint(Point point) {
		LinkedHashSet<OracleMachine> oms = (LinkedHashSet<OracleMachine>) ((OmegaMachine) getAutomaton()).getOracleMachines();
		LinkedList<OracleMachine> omsList = new LinkedList<>(oms);
		Iterator<OracleMachine> itr = omsList.descendingIterator();
		while(itr.hasNext()) {
		    OracleMachine o = itr.next();
		    if (point.distance(o.getPoint()) <= StateDrawer.STATE_RADIUS)
				return o;
		}
		return null;
	}
	
	public Graphics2D getGraphics(){
		return g;
	}

}
