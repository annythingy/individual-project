package social;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import automata.Automaton;
import automata.State;
import automata.mealy.MooreMachine;
import automata.turing.TuringMachine;
import file.DataException;
import file.xml.AbstractTransducer;
import file.xml.AutomatonTransducer;
import file.xml.MooreTransducer;
import file.xml.TMTransducer;
import social.oracles.OracleMachine;

public class OmegaTransducer extends AbstractTransducer {

	private Map<String, Automaton> machineMap = new java.util.HashMap<>();
	PTMTransducer autoTrans = new PTMTransducer();
	
	@Override
	public String getType() {
		return "omega";
	}

	@Override
	public Serializable fromDOM(Document document) {
		machineMap.clear();
		OmegaMachine om = createEmptyMachine(document);
        Node parent = document.getDocumentElement().getElementsByTagName("machine").item(0);
        if(parent == null) parent = document.getDocumentElement();
        return readMachine(parent, document);
	}

	@Override
	public Document toDOM(Serializable structure) {
		OmegaMachine machine = (OmegaMachine) structure;
		Document doc = newEmptyDocument();
		Element se = doc.getDocumentElement();
		se.appendChild(createMachineElement(doc, machine, "machine"));
		Element tapesElement = createElement(doc, "tapes", null, "" + machine.tapes());
		se.appendChild(tapesElement);
		return doc;
	}

	private OmegaMachine createEmptyMachine(Document doc){
		return new OmegaMachine();
	}
	
	public java.io.Serializable readMachine(Node parent, Document doc) {
		Set<Object> locatedOMs = new java.util.HashSet<>();
		OmegaMachine root = createEmptyMachine(doc);
        if(parent == null) return root;

        readPTM(parent, root, doc);
        readOMs(parent, root, locatedOMs, doc);
        machineMap.put(parent.getNodeName(), root);
		return root;
	}
	
	private void readPTM(Node node, OmegaMachine omParent, Document doc){
		
		NodeList allNodes = node.getChildNodes();
				
		for (int k = 0; k < allNodes.getLength(); k++){
			if(allNodes.item(k).getNodeName().equals("ptmCore")){
				createPTM(allNodes.item(k), omParent, doc);
			}
		}
		
	}
	
	private void createPTM(Node node, OmegaMachine omParent, Document doc){
		Map<String, String> e2t = elementsToText(node);
		
		java.awt.Point p = new java.awt.Point();
		boolean hasLocation = true;
		double x = 0, y = 0;
		try {
			x = Double.parseDouble((e2t.get("x").toString()));
		} catch (NullPointerException e) {
			hasLocation = false;
		} catch (NumberFormatException e) {
			throw new DataException("The x coordinate "
					+ e2t.get("x") + " could not be read for the core PTM.");
		}
		try {
			y = Double.parseDouble((e2t.get("y").toString()));
		} catch (NullPointerException e) {
			hasLocation = false;
		} catch (NumberFormatException e) {
			throw new DataException("The y coordinate "
					+ e2t.get("y") + " could not be read for the core PTM");
		}
		p.setLocation(x, y);

		PersistentTuringMachine ptm = new PersistentTuringMachine((TuringMachine) autoTrans.readAutomaton(node, doc));
		String name = ((Element) node).getAttribute("name");
		ptm.setName(name);
		ptm.setPoint(p);
		omParent.setCore(ptm);
		
		
	}
	
	private Map<Integer, OracleMachine> readOMs(Node node, OmegaMachine machine, Set<Object> locatedOMs, Document doc) {
		Map<Integer, OracleMachine> i2o = new java.util.HashMap<Integer, OracleMachine>();
        if(node == null) return i2o;
		NodeList allNodes = node.getChildNodes();
		ArrayList<Node> omNodes = new ArrayList<Node>();
		ArrayList<Node> cNodes = new ArrayList<Node>();
		for (int k = 0; k < allNodes.getLength(); k++) {
			Node thisNode = allNodes.item(k);
			String thisName = thisNode.getNodeName();
			
			if (thisName.equals("omSet")) {
				NodeList thisOmSet = thisNode.getChildNodes();
				for (int m = 0; m < thisOmSet.getLength(); m++) {
					if (thisOmSet.item(m).getNodeName().equals("oracleMachine")) {
						omNodes.add(thisOmSet.item(m));
					}
				}
			} else if (thisName.equals("cSet")) {
				NodeList thisCSet = thisNode.getChildNodes();
				for (int m = 0; m < thisCSet.getLength(); m++) {
					if (thisCSet.item(m).getNodeName().equals("connection")) {
						cNodes.add(thisCSet.item(m));
					}
				}
			}
		}

		Map<Integer, Node> i2sn = new java.util.TreeMap<Integer, Node>(new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				if (o1 instanceof Integer && !(o2 instanceof Integer))
					return -1;
				if (o1 instanceof Integer)
					return ((Integer) o1).intValue()
							- ((Integer) o2).intValue();
				if (o2 instanceof Integer)
					return 1;
				return ((Comparable) o1).compareTo(o2);
			}
		});
		createOracleMachines(omNodes, i2sn, machine, locatedOMs, i2o, doc);
		createConnections(cNodes, machine);
		return i2o;
	}
	
	protected void createOracleMachines(ArrayList<Node> omNodes, Map<Integer, Node> i2sn,
		OmegaMachine machine, Set<Object> locatedOMs, Map<Integer, OracleMachine> i2s, Document doc) {
		
		for (int i = 0; i < omNodes.size(); i++) {
			Node omNode = (Node) omNodes.get(i);
			if (omNode.getNodeType() != Node.ELEMENT_NODE) continue;

			String idString = ((Element) omNode).getAttribute("id");

			if (idString == null)
				throw new DataException(
						"Oracle machine without id attribute encountered!");
			Integer id = Integer.valueOf(idString);

			if (i2sn.put(id, omNode) != null)
				throw new DataException("The oracle machine ID " + id
						+ " appears twice!");
		}

		Iterator<Integer> it = i2sn.keySet().iterator();		
		while (it.hasNext()) {
			Integer id = (Integer) it.next();
			Element omNode = (Element) i2sn.get(id);
			Map<String, String> e2t = elementsToText(omNode);

			java.awt.Point p = new java.awt.Point();
			boolean hasLocation = true;

			double x = 0, y = 0;
			try {
				x = Double.parseDouble(e2t.get("x").toString());
			} catch (NullPointerException e) {
				hasLocation = false;
			} catch (NumberFormatException e) {
				throw new DataException("The x coordinate "
						+ e2t.get("x")
						+ " could not be read for state " + id + ".");
			}
			try {
				y = Double.parseDouble(e2t.get("y").toString());
			} catch (NullPointerException e) {
				hasLocation = false;
			} catch (NumberFormatException e) {
				throw new DataException("The y coordinate "
						+ e2t.get("y") + " could not be read for state " + id + ".");
			}
			p.setLocation(x, y);
			
			OracleMachine om = machine.createOracleMachineWithID(p, id.intValue());
            
			if (hasLocation && locatedOMs != null)
				locatedOMs.add(om);
			i2s.put(id, om);

			String name = ((Element) omNode).getAttribute("name");
			String type = ((Element) omNode).getAttribute("type");
			String attributes = ((Element) omNode).getAttribute("attributes");
			if(name.equals(""))
				om.setName("o"+id.intValue());
            else
            	om.setName(name);
			om.setOracle(type, attributes);
		}
	}
	
	protected void createConnections(ArrayList<Node> cNodes, OmegaMachine machine) {
		for (int i = 0; i < cNodes.size(); i++) {
			Node omNode = (Node) cNodes.get(i);
			if (omNode.getNodeType() != Node.ELEMENT_NODE) continue;

			String idAs = ((Element) omNode).getAttribute("omA");
			String idBs = ((Element) omNode).getAttribute("omB");

			if (idAs == null || idBs == null)
				throw new DataException(
						"Connection without Oracle ID attributes encountered!");
			Integer omAid = Integer.valueOf(idAs);
			Integer omBid = Integer.valueOf(idBs);
			OracleMachine omA = machine.getOracleMachineWithID(omAid);
			OracleMachine omB = machine.getOracleMachineWithID(omBid);

			machine.addConnection(omA, omB);
			omA.addNeighbour(omB);
			omB.addNeighbour(omA);
		}
	}

	private Node createMachineElement(Document doc, OmegaMachine machine, String name) {
		Element se = doc.getDocumentElement();
		Element be = createElement(doc, name, null, null);
		se.appendChild(be);
		writeMachineContents(doc, machine, be);
		return be;
	}

	private Element writeMachineContents(Document doc, OmegaMachine machine, Element se) {
		PersistentTuringMachine ptm = machine.getCore();
		Set<OracleMachine> omSet = machine.getOracleMachines();
		Set<Connection> connectionSet = machine.getConnections();
		se.appendChild(createPTMElement(doc, ptm, machine));
		se.appendChild(createOMSetElement(doc, omSet, machine));
		se.appendChild(createCSetElement(doc, connectionSet, machine));
		return se;
	}

	private Element createPTMElement(Document doc, PersistentTuringMachine ptm, OmegaMachine container) {
		Element ae = autoTrans.createAutomatonElement(doc, ptm, "ptmCore");
		ae.appendChild(createComment(doc, "Location parameters"));
		ae.appendChild(createElement(doc, "x", null, "" + ptm.getPoint().getX()));
		ae.appendChild(createElement(doc, "y", null, "" + ptm.getPoint().getY()));
		ae.setAttribute("name", "" + ptm.getName());
		return ae;
	}

	private Element createOMSetElement(Document doc, Set<OracleMachine> omSet, OmegaMachine container) {
		Element be = createElement(doc, "omSet", null, null);
		for (OracleMachine om : omSet) {
			be.appendChild(createOracleMachineElement(doc, om, container));
		}
		return be;
	}

	private Element createCSetElement(Document doc, Set<Connection> cSet, OmegaMachine container) {
		Element be = createElement(doc, "cSet", null, null);
		for (Connection c : cSet) {
			be.appendChild(createConnectionElement(doc, c, container));
		}
		return be;
	}

	protected Element createOracleMachineElement(Document doc, OracleMachine om, OmegaMachine container) {
		Element se = createElement(doc, "oracleMachine", null, null);
		se.setAttribute("id", "" + om.getID());
		se.setAttribute("name", om.getName());
		se.setAttribute("type", om.getType());
		se.setAttribute("attributes", om.getAttributes());
		se.appendChild(createElement(doc, "x", null, "" + om.getPoint().getX()));
		se.appendChild(createElement(doc, "y", null, "" + om.getPoint().getY()));
		return se;
	}

	protected Element createConnectionElement(Document doc, Connection c, OmegaMachine container) {
		Element se = createElement(doc, "connection", null, null);
		se.setAttribute("omA", "" + c.omA.id);
		se.setAttribute("omB", "" + c.omB.id);
		return se;
	}
}
