package lifegame;

import java.util.ArrayList;

public class BoardModel {
	private int cols;
	private int rows;
	private boolean[][] cells;
	private ArrayList<BoardListener> listeners;
	private ArrayList<boolean[][]> boardList;
	private int listMaxSize;
	private ArrayList<Integer> numberOfAliveCellList;

	public BoardModel(int c, int r) {
		this.cols = c;
		this.rows = r;
		this.cells = new boolean[rows][cols];
		this.boardList = new ArrayList<boolean[][]>();
		this.listeners = new ArrayList<BoardListener>();
		this.listMaxSize = 32;
		numberOfAliveCellList = new ArrayList<Integer>();
		numberOfAliveCellList.add(aliveCellNumber());
	}

	public int getCols() {
		return cols;
	}

	public int getRows() {
		return rows;
	}

	public int getListMaxSize() {
		return listMaxSize;
	}

	public boolean[][] getCells() {
		return cells;
	}

	public ArrayList<boolean[][]> getBoardList() {
		return boardList;
	}

	public ArrayList<Integer> getNUmberOfAliveCellList() {
		return numberOfAliveCellList;
	}

	public boolean isAlive(int x, int y) {
		return cells[y][x];
	}

	synchronized public void changeCellState(int x, int y) {
		cells[y][x] = !cells[y][x];
	}

	public void addListener(BoardListener listener) {
		listeners.add(listener);
	}

	public void fireUpdate() {
		for (BoardListener listener : listeners) {
			listener.updated(this);
		}
	}

	synchronized public void next() {
		boolean tmp[][] = new boolean[rows][cols];
		copyArray(tmp, cells);
		boardList.add(tmp);
		if (boardList.size() > listMaxSize)
			boardList.remove(0);
		for (int j = 0; j < rows; j++) {
			for (int i = 0; i < cols; i++) {
				int state = surroundingAliveNumber(i, j, tmp);
				if (cells[j][i] && (state <= 1 || 4 <= state)) // tmp?
					changeCellState(i, j);
				else if ((!cells[j][i]) && state == 3)
					changeCellState(i, j);
			}
		}
		this.fireUpdate();
	}

	public void copyArray(boolean[][] destination, boolean[][] sorce) {
		if (destination.length == rows && sorce.length == rows && destination[0].length == cols
				&& sorce[0].length == cols) {
			for (int j = 0; j < rows; j++) {
				for (int i = 0; i < cols; i++) {
					destination[j][i] = sorce[j][i];
				}
			}
		}
	}

	private int surroundingAliveNumber(int x, int y, boolean[][] tmp) {
		int alive = 0;
		for (int j = y - 1; j <= y + 1; j++) {
			for (int i = x - 1; i <= x + 1; i++) {
				if (!((i < 0 || cols <= i) || (j < 0 || rows <= j) || (i == x && j == y))) {
					if (tmp[j][i])
						alive++;
				}
			}
		}
		return alive;
	}

	synchronized public void undo() {
		cells = boardList.get(boardList.size() - 1);
		boardList.remove(boardList.size() - 1);
		this.fireUpdate();
	}

	public boolean isUndoable() {
		if (boardList.size() > 0)
			return true;
		else
			return false;
	}

	public int aliveCellNumber() {
		int cellNum = 0;
		for (int j = 0; j < rows; j++) {
			for (int i = 0; i < cols; i++) {
				if (this.isAlive(i, j))
					cellNum++;
			}
		}
		return cellNum;
	}

}
