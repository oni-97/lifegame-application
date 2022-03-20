package lifegame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

public class ButtonListener implements ActionListener {

	private BoardModel model;

	public ButtonListener(BoardModel model) {
		this.model = model;
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("next")) {
			model.next();
		} else if (cmd.equals("undo")) {
			model.undo();
		} else if (cmd.equals("new")) {
			SwingUtilities.invokeLater(new Main());
		}
	}

}
