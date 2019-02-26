package social;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.RoundRectangle2D;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import automata.turing.TuringMachine;
import gui.viewer.SelectionDrawer;
import gui.viewer.StateDrawer;

public class OmegaDrawer extends SelectionDrawer {

	OmegaMachine automaton;
	Graphics2D g;

	private static Stroke STROKE = new java.awt.BasicStroke(0.4f);
	Stroke dotted = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 1, 2 }, 0);;
	public static java.awt.Color COLOR = new java.awt.Color(.5f, .5f, .5f, .5f);
	public static Color HIGHLIGHT_COLOR = new Color(100, 200, 200);

	private boolean selectedTM;
	private int tmX, tmY;

	public OmegaDrawer(OmegaMachine automaton) {
		super(automaton);
		this.automaton = automaton;
	}

	public void drawAutomaton(Graphics g2) {
		this.g = (Graphics2D) g2.create();

		Set<OracleMachine> oMachines = ((OmegaMachine) automaton).getOracleMachines();

		for (OracleMachine o : oMachines) {
			drawConnections(g, o);
		}
		
		for (OracleMachine o : oMachines) {
			drawOracleMachine(g, o);
		}

		Graphics2D g = (Graphics2D) g2.create();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(g.getFont().deriveFont(12.0f));
		g.setColor(Color.black);

		if (((OmegaMachine) automaton).getCore() != null)
			drawTuringMachine(g2);
		else {
			setTMX(0);
			setTMY(0);
		}

		this.drawSelectionBox(g);
		g.dispose();

	}

	public void drawTuringMachine(Graphics g2) {
		if (tmX == 0 && tmY == 0)
			return;
		Graphics2D g = (Graphics2D) g2.create();

		int width = 80;
		int height = 80;

		g.setColor(Color.pink);
		g.fill(new RoundRectangle2D.Double(tmX - width / 2, tmY - height / 2, width, height, 10, 10));
		g.setColor(Color.black);
		g.draw(new RoundRectangle2D.Double(tmX - width / 2, tmY - height / 2, width, height, 10, 10));
		g.drawString("TM CORE", tmX - width / 2 + 10, tmY + 5); 
	}

	public void drawOracleMachine(Graphics g1, OracleMachine om) {
		Graphics2D g = (Graphics2D) g1.create();
		Stroke s = g.getStroke();
		Point point = om.getPoint();
		int radius = 30;

		g.setColor(Color.lightGray);
		g.setStroke(dotted);
		if (tmX != 0 && tmY != 0)
			g.drawLine(point.x, point.y, tmX, tmY);
		g.setStroke(s);

		if (om.isSelected())
			g.setColor(HIGHLIGHT_COLOR);
		g.fillOval(point.x - radius, point.y - radius, 2 * radius, 2 * radius);

		g.setColor(Color.black);

		int dx = ((int) g.getFontMetrics().getStringBounds(om.getName(), g).getWidth()) >> 1;
		int dy = ((int) g.getFontMetrics().getAscent()) >> 1;

		g.drawString(om.getName(), point.x - dx, point.y + dy);
		g.drawOval(point.x - radius, point.y - radius, 2 * radius, 2 * radius);

	}

	public void drawConnections(Graphics g, OracleMachine om) {
		java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
		Stroke s = g2.getStroke();
		g2.setStroke(STROKE);
		g2.setColor(Color.lightGray);
		for (OracleMachine on : om.getNeighbours()) {
			if (on != null) {
				g2.drawLine(om.getPoint().x, om.getPoint().y, on.getPoint().x, on.getPoint().y);
			}
		}
		g2.setStroke(s);

	}

	public OracleMachine oMachineAtPoint(Point point) {
		LinkedHashSet<OracleMachine> oms = (LinkedHashSet<OracleMachine>) ((OmegaMachine) getAutomaton())
				.getOracleMachines();
		LinkedList<OracleMachine> omsList = new LinkedList<>(oms);
		Iterator<OracleMachine> itr = omsList.descendingIterator();
		while (itr.hasNext()) {
			OracleMachine o = itr.next();
			if (point.distance(o.getPoint()) <= StateDrawer.STATE_RADIUS)
				return o;
		}
		return null;
	}

	public TuringMachine corePTMachineAtPoint(Point point) {
		Point turingPoint = new Point(tmX, tmY);
		if (point.distance(turingPoint) <= StateDrawer.STATE_RADIUS)
			return ((OmegaMachine) automaton).getCore();
		return null;
	}

	public Connection connectionAtPoint(Point point) {
		for (Connection c : ((OmegaMachine) automaton).getConnections()) {
			if (c.getSelectableArea().contains(point))
				return c;
		}
		return null;
	}

	public Graphics2D getGraphics() {
		return g;
	}

	public void setTMX(int x) {
		this.tmX = x;
	}

	public void setTMY(int y) {
		this.tmY = y;
	}

	public int getTMX() {
		return tmX;
	}

	public int getTMY() {
		return tmY;
	}

	public boolean isTMSelected() {
		return selectedTM;
	}

	public void setTMSelected(boolean selected) {
		this.selectedTM = selected;
	}

}
