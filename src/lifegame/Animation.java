package lifegame;

public class Animation extends Thread {

	private BoardModel model;

	public Animation(BoardModel model) {
		this.model = model;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(500);
				model.next();
			} catch (InterruptedException e) {
				break;
			}
		}
	}
}
