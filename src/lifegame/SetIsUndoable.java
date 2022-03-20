package lifegame;

import javax.swing.JButton;

public class SetIsUndoable implements BoardListener {

	private JButton undoButton;

	public SetIsUndoable(JButton undoButton) {
		this.undoButton = undoButton;
	}

	public void updated(BoardModel model) {
		undoButton.setEnabled(model.isUndoable());
	}
}
