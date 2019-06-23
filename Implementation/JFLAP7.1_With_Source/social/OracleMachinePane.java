package social;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import gui.editor.EditorPane;

public class OracleMachinePane extends JPanel {

	private static final long serialVersionUID = 1L;
	
	OracleMachine om;
	
	public OracleMachinePane(OracleMachine om){
		this.om = om;
		
		initGUI();
	}
	
	private void initGUI(){
		GridLayout layout = new GridLayout(2,2);
		setLayout(layout);
		
		add(initTuringPanel());
		//add(initOraclePanel());
		//add(initFunctionPanel());
		//add(initModePanel());
	}
	
	private JPanel initTuringPanel(){
		JPanel tp = new JPanel();
		tp.add(new JLabel(om.toString()/*getOmegaParent().getFileName()*/));
		//tp.add(new EditorPane(om));
		
		return tp;
	}
	
	private JPanel initOraclePanel(){
		JPanel op = new JPanel();
		op.add(new JLabel("oracle panel idk"));
		
		return op;
	}
	
	private JPanel initFunctionPanel(){
		JPanel fp = new JPanel();
		fp.add(new JLabel("function panel idk"));
		
		return fp;
	}
	
	private JPanel initModePanel(){
		JPanel mp = new JPanel();
		mp.setLayout(new BoxLayout(mp, BoxLayout.Y_AXIS));
		
		mp.add(new JLabel("PROBLEM MODE"), BorderLayout.PAGE_START);
		
		ButtonGroup buttGroup = new ButtonGroup();
		JRadioButton binRadio = new JRadioButton("Decision problem");
		JRadioButton funRadio = new JRadioButton("Function problem");
		buttGroup.add(binRadio);
		buttGroup.add(funRadio);
		
		
		
		mp.add(binRadio, BorderLayout.CENTER);
		mp.add(funRadio, BorderLayout.CENTER);
		
		return mp;
	}
}
