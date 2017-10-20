package data;

public class Pieces {
	private int position;
	private boolean canMove;
	private boolean onBeating;

	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public boolean isCanMove() {
		return canMove;
	}
	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}
	public boolean isOnBeating() {
		return onBeating;
	}
	public void setOnBeating(boolean onBeating) {
		this.onBeating = onBeating;
	}

	public Pieces(){
		this.setOnBeating(false);
	}
}
