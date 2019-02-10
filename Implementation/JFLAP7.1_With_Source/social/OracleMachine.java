package social;

import java.awt.Point;

import automata.Automaton;
import automata.event.AutomataStateEvent;
import automata.turing.TuringMachine;

public class OracleMachine extends Automaton {
	
	private static final long serialVersionUID = 1L; //TODO what is that?
	
	Integer id;
	Point point;
	TuringMachine tm;
	
	public OracleMachine(Integer id, Point point, TuringMachine tm){
		this.id = id;
		this.tm = tm;
		System.out.println("My id is " + id + ". My TMummy has " + tm.tapes + " tapes. I live at " + point.x + " and " + point.y);
	}
	
	public Integer getID(){
		return id;
	}
	
	public void setPoint(Point point) {
		this.point = point;
	}
	
}