package social;

import java.awt.Polygon;

public class Connection {

	OracleMachine omA, omB;

	public Connection(OracleMachine omA, OracleMachine omB) {
		this.omA = omA;
		this.omB = omB;
	}

	public void removeSelf() {
		omA.removeNeighbour(omB);
		omB.removeNeighbour(omA);
	}

	public Polygon getSelectableArea() {
		int padding = 10;
		int x1 = omA.getPoint().x;
		int y1 = omA.getPoint().y;
		int x2 = omB.getPoint().x;
		int y2 = omB.getPoint().y;

		int[] xpoints = { x1 - padding, x1 + padding, x2 + padding, x2 - padding };
		int[] ypoints = { y1, y1, y2, y2 };

		Polygon area = new Polygon(xpoints, ypoints, 4);

		return area;
	}

}
