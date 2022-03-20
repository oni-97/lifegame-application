package lifegame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class AutoButtonListener implements ActionListener {

	private BoardModel model;
	private Main m;
	private JButton autoButton;

	public AutoButtonListener(BoardModel model, Main m, JButton autoButton) {
		this.model = model;
		this.m = m;
		this.autoButton = autoButton;
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("start")) {
			autoButton.setActionCommand("stop");
			autoButton.setText("Auto : stop");
			Animation th = new Animation(model);
			th.start();
			m.setThread(th);
		} else if (cmd.equals("stop")) {
			autoButton.setActionCommand("start");
			autoButton.setText("Auto : start");
			if (m.getThread() != null)
				m.getThread().interrupt();
		}
	}
}
