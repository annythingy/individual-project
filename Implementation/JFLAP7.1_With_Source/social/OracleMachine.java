package social;

import java.awt.Point;

import automata.Automaton;
import automata.event.AutomataStateEvent;
import automata.turing.TuringMachine;

public class OracleMachine extends Automaton {
	
	private static final long serialVersionUID = 1L; //TODO what is that?
	
	Integer id;
	Point point;
	TuringMachine tmCore;
	
	boolean selected;
	
	public OracleMachine(Integer id, Point point){
		this.id = id;
		this.point = point;
		this.tmCore = null;
	}
	
	public Integer getID(){
		return id;
	}
	
	public void setPoint(Point point) {
		this.point = point;
	}

	public Point getPoint() {
		return point;
	}
	
	@Override
	public String toString(){
		return "oMachine " + id + " with parent " + tmCore + " at " + point.x + " and " +point.y;
	}
	
	public void setTM(TuringMachine tm) {
		this.tmCore = tm;
	}
	
	public void setSelected(boolean selected){
		this.selected = selected;
	}
	
	public boolean isSelected(){
		return selected;
	}

}