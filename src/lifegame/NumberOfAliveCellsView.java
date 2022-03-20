package lifegame;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class NumberOfAliveCellsView extends JPanel implements BoardListener {

	private int cols;
	private int rows;
	private BoardModel model;
	private ArrayList<Integer> numberOfAliveCellList;

	public NumberOfAliveCellsView(BoardModel model) {
		cols = model.getCols();
		rows = model.getRows();
		this.model = model;
		this.numberOfAliveCellList = model.getNUmberOfAliveCellList();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		int listNum = numberOfAliveCellList.size();
		int max = 0;
		for (int k = 0; k < listNum; k++) {
			if (numberOfAliveCellList.get(k) > max)
				max = numberOfAliveCellList.get(k);
		}

		int min = cols * rows;
		for (int k = 0; k < listNum; k++) {
			if (numberOfAliveCellList.get(k) < min)
				min = numberOfAliveCellList.get(k);
		}

		if (max == min && listNum > 1) {
			g.drawLine(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);
		} else if (listNum > 1) {
			for (int k = 0; k < listNum - 1; k++) {
				g.drawLine(k * this.getWidth() / (listNum - 1), zahyou(numberOfAliveCellList.get(k), min, max),
						(k + 1) * this.getWidth() / (listNum - 1), zahyou(numberOfAliveCellList.get(k + 1), min, max));
			}
		}
	}

	private int zahyou(Integer alive, Integer min, Integer max) {
		if (alive == max)
			return 3;
		else if (alive == min)
			return this.getHeight() - 1;
		else
			return this.getHeight() - (alive - min) * this.getHeight() / (max - min);
	}

	@Override
	public void updated(BoardModel m) {
		numberOfAliveCellList.add(model.aliveCellNumber());
		this.repaint();
	}

}
