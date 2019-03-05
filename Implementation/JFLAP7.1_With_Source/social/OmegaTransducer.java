package social;

import java.io.Serializable;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import file.xml.AbstractTransducer;

public class OmegaTransducer extends AbstractTransducer {

	@Override
	public String getType() {
		return "omega";
	}

	@Override
	public Serializable fromDOM(Document document) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Document toDOM(Serializable structure) {
		OmegaMachine machine = (OmegaMachine) structure;
		Document doc = newEmptyDocument();
		Element se = doc.getDocumentElement();
		se.appendChild(createMachineElement(doc, machine, "machine"));
		return doc;
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
		se.appendChild(createPTMElement(doc, ptm, machine));
		se.appendChild(createOMSetElement(doc, omSet, machine));
		return se;
	}

	private Element createPTMElement(Document doc, PersistentTuringMachine ptm, OmegaMachine container) {
		Element be = createElement(doc, "ptmCore", null, null);
		be.setAttribute("name", "" + ptm.getName());
		return be;
	}

	private Element createOMSetElement(Document doc, Set<OracleMachine> omSet, OmegaMachine container) {
		Element be = createElement(doc, "omSet", null, null);
		for (OracleMachine om : omSet) {
			be.appendChild(createOracleMachineElement(doc, om, container));
		}
		return be;
	}

	protected Element createOracleMachineElement(Document doc, OracleMachine om, OmegaMachine container) {
		Element se = createElement(doc, "oracleMachine", null, null);
		se.setAttribute("id", "" + om.getID());
		se.appendChild(createElement(doc, "name", null, om.getName()));
		se.appendChild(createElement(doc, "x", null, "" + om.getPoint().getX()));
		se.appendChild(createElement(doc, "y", null, "" + om.getPoint().getY()));
		return se;
	}

}
