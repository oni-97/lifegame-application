package lifegame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Main extends WindowAdapter implements Runnable {
	private Animation thread;

	public void setThread(Animation thread) {
		this.thread = thread;
	}

	public Animation getThread() {
		return this.thread;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Main());
	}

	public void run() {
		BoardModel model = new BoardModel(16, 15);

		JFrame frame = new JFrame("Lifegame");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel base = new JPanel();
		frame.setContentPane(base);
		base.setPreferredSize(new Dimension(400, 310));
		frame.setMinimumSize(new Dimension(380, 210));
		frame.addWindowListener(new myListener());

		base.setLayout(new BorderLayout());
		BoardView view = new BoardView(model);
		model.addListener(view);
		base.add(view, BorderLayout.CENTER);

		ButtonListener buttonListener = new ButtonListener(model);
		JPanel buttonPanel = new JPanel();
		base.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new FlowLayout());

		JButton nextButton = new JButton("Next");
		buttonPanel.add(nextButton);
		nextButton.setActionCommand("next");
		nextButton.addActionListener(buttonListener);

		JButton undoButton = new JButton("Undo");
		buttonPanel.add(undoButton);
		undoButton.setActionCommand("undo");
		undoButton.setEnabled(model.isUndoable());
		model.addListener(new SetIsUndoable(undoButton));
		undoButton.addActionListener(buttonListener);

		JButton newButton = new JButton("New Game");
		buttonPanel.add(newButton);
		newButton.setActionCommand("new");
		newButton.addActionListener(new ButtonListener(model));

		JButton autoButton = new JButton("Auto : start");
		buttonPanel.add(autoButton);
		autoButton.setActionCommand("start");
		AutoButtonListener autoButtonListener = new AutoButtonListener(model, this, autoButton);
		autoButton.addActionListener(autoButtonListener);

		frame.pack();
		frame.setVisible(true);

		JFrame cellFrame = new JFrame("Number of alive cells");
		cellFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel base2 = new JPanel();
		cellFrame.setContentPane(base2);
		base2.setPreferredSize(new Dimension(400, 310));
		cellFrame.setMinimumSize(new Dimension(380, 210));
		base2.setLayout(new BorderLayout());
		NumberOfAliveCellsView moniteringCell = new NumberOfAliveCellsView(model);
		model.addListener(moniteringCell);
		base2.add(moniteringCell, BorderLayout.CENTER);
		SetButtonListener setButtonListener = new SetButtonListener(model,moniteringCell);
		JPanel buttonPanel2 = new JPanel();
		base2.add(buttonPanel2, BorderLayout.SOUTH);
		buttonPanel2.setLayout(new FlowLayout());

		JButton setButton = new JButton("set");
		buttonPanel2.add(setButton);
		setButton.addActionListener(setButtonListener);
		cellFrame.pack();
		cellFrame.setVisible(true);

	}

	public class myListener extends WindowAdapter {
		public void windowClosed(WindowEvent e) {
			if (thread != null)
				thread.interrupt();
		}
	}

}
