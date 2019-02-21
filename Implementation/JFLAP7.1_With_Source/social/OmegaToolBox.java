package social;

import java.util.List;

import gui.editor.ArrowTool;
import gui.editor.DeleteTool;
import gui.editor.RedoTool;
import gui.editor.Tool;
import gui.editor.ToolBox;
import gui.editor.TransitionTool;
import gui.editor.UndoTool;
import gui.viewer.AutomatonDrawer;
import gui.viewer.AutomatonPane;

public class OmegaToolBox implements ToolBox{
	
	public List<Tool> tools(AutomatonPane view, AutomatonDrawer drawer) {
		List<Tool> list = new java.util.ArrayList<>();
		list.add(new ArrowTool(view, drawer));
		list.add(new TMTool(view, (OmegaDrawer) drawer));
		list.add(new OMTool(view, (OmegaDrawer) drawer));
		list.add(new ConnectionTool(view, (OmegaDrawer) drawer));
		list.add(new DeleteTool(view, drawer));
		//list.add(new UndoTool(view, drawer));
		//list.add(new RedoTool(view, drawer));
		return list;
	}
}
