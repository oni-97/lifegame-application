package lifegame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SetButtonListener implements ActionListener {


	private ArrayList<Integer> numberOfCell;
	BoardModel model;

	public SetButtonListener(BoardModel model,NumberOfAliveCellsView m) {
		this.model=model;
		numberOfCell=model.getNUmberOfAliveCellList();
	}

	public void actionPerformed(ActionEvent e) {
		int number = numberOfCell.size();
		for(int i=0; i< number; i++) {
			numberOfCell.remove(0);
		}
		model.fireUpdate();
	}

}
