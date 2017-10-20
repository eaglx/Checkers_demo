package data;

public enum Sides {
	WHITE(0), BLACK(1), NONE(2);
	private int value;

	Sides(int value){
		this.value=value;
	}

	public int getSideValue(){
		return value;
	}
}
