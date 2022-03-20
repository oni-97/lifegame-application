package lifegame;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class BoardView extends JPanel implements BoardListener, MouseListener, MouseMotionListener {

	private int cols;
	private int rows;
	private BoardModel model;
	private int preX;
	private int preY;

	public BoardView(BoardModel model) {
		this.cols = model.getCols();
		this.rows = model.getRows();
		this.model = model;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	private int changeColumnToXcordinate(int pRetu) {
		int masuWidth = (this.getWidth()) / (cols + 2);
		int masuHeight = (this.getHeight()) / (rows + 2);
		if (masuWidth < masuHeight) {
			return pRetu * masuWidth;
		} else {
			return (this.getWidth() - masuHeight * (cols + 2)) / 2 + masuHeight * pRetu;
		}
	}

	private int changeRowToYcordinate(int qGyou) {
		int masuWidth = (this.getWidth()) / (cols + 2);
		int masuHeight = (this.getHeight()) / (rows + 2);
		if (masuWidth >= masuHeight) {
			return qGyou * masuHeight;
		} else {
			return (this.getHeight() - masuWidth * (rows + 2)) / 2 + masuWidth * qGyou;
		}
	}

	@Override
	public void paint(Graphics g) {

		super.paint(g);
		for (int i = 0; i < cols + 1; i++) {
			g.drawLine(changeColumnToXcordinate(i + 1), changeRowToYcordinate(1), changeColumnToXcordinate(i + 1),
					changeRowToYcordinate(rows + 1));
		}
		for (int i = 0; i < rows + 1; i++) {
			g.drawLine(changeColumnToXcordinate(1), changeRowToYcordinate(i + 1), changeColumnToXcordinate(cols + 1),
					changeRowToYcordinate(i + 1));
		}

		for (int j = 0; j < rows; j++) {
			for (int i = 0; i < cols; i++) {
				if (model.isAlive(i, j))
					g.fillRect(changeColumnToXcordinate(i + 1), changeRowToYcordinate(j + 1),
							changeColumnToXcordinate(i + 2) - changeColumnToXcordinate(i + 1),
							changeRowToYcordinate(j + 2) - changeRowToYcordinate(j + 1));
			}
		}

	}

	private int changeXcordinateToColumn(int x) {
		int cellX = -1;
		for (int i = 1; i < cols + 1; i++) {
			if ((changeColumnToXcordinate(i) < x) && (x < changeColumnToXcordinate(i + 1)))
				cellX = i - 1;
		}
		return cellX;
	}

	private int changeYcordinateToRow(int y) {
		int cellY = -1;
		for (int i = 1; i < rows + 1; i++) {
			if ((changeRowToYcordinate(i) < y) && (y < changeRowToYcordinate(i + 1)))
				cellY = i - 1;
		}
		return cellY;
	}

	private boolean checkCellRange(int x, int y) {
		if (x < 0 || y < 0)
			return false;
		else
			return true;
	}

	@Override
	public void mousePressed(MouseEvent e) {

		int cellX = changeXcordinateToColumn(e.getX());
		int cellY = changeYcordinateToRow(e.getY());
		if (checkCellRange(cellX, cellY)) {
			boolean tmp[][] = new boolean[rows][cols];
			model.copyArray(tmp, model.getCells());
			model.getBoardList().add(tmp);
			if (model.getBoardList().size() > model.getListMaxSize())
				model.getBoardList().remove(0);
			model.changeCellState(cellX, cellY);
			model.fireUpdate();
		}

		preX = cellX;
		preY = cellY;

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point point = e.getPoint();
		int nowX = changeXcordinateToColumn(point.x);
		int nowY = changeYcordinateToRow(point.y);
		if (nowX != preX || nowY != preY)
			if (checkCellRange(nowX, nowY)) {
				boolean tmp[][] = new boolean[rows][cols];
				model.copyArray(tmp, model.getCells());
				model.getBoardList().add(tmp);
				if (model.getBoardList().size() > model.getListMaxSize())
					model.getBoardList().remove(0);
				model.changeCellState(nowX, nowY);
				model.fireUpdate();
			}
		preX = nowX;
		preY = nowY;

	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void updated(BoardModel m) {
		this.repaint();
	}

}
