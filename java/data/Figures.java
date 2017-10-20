package data;

public enum Figures {
	EMPTY(0), WP(1), BP(2), WK(3), BK(4), BLOCKFIELD(5), BORDER(6);
	private int value;

	Figures(int value){
		this.value=value;
	}

	public int getFiguresValue(){
		return value;
	}
}
