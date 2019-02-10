package social;

import java.util.List;

import automata.turing.TuringMachine;
import automata.turing.TuringMachineBuildingBlocks;
import gui.editor.ArrowTool;
import gui.editor.BlockTransitionTool;
import gui.editor.BuildingBlockTool;
import gui.editor.DefaultToolBox;
import gui.editor.DeleteTool;
import gui.editor.RedoTool;
import gui.editor.StateTool;
import gui.editor.Tool;
import gui.editor.TransitionTool;
import gui.editor.UndoTool;
import gui.viewer.AutomatonDrawer;
import gui.viewer.AutomatonPane;

public class OmegaToolBox extends DefaultToolBox{
	
	public List<Tool> tools(AutomatonPane view, AutomatonDrawer drawer) {
		List<Tool> list = new java.util.ArrayList<>();
		list.add(new ArrowTool(view, drawer));
		list.add(new BuildingBlockTool(view, drawer));
		list.add(new OracleTool(view, drawer));
		list.add(new TransitionTool(view, drawer));
		list.add(new DeleteTool(view, drawer));
		list.add(new UndoTool(view, drawer));
		list.add(new RedoTool(view, drawer));
		return list;
	}
}
