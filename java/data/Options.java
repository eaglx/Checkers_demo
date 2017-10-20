package data;

public enum Options {
	EXIT(0, "Exit."),
	NEWGAME(1, "New game.");
	private int value;
	private String description;

	public int getValue(){
		return value;
	}

	public String getDescription(){
		return description;
	}

	Options(int value, String desc){
		this.value=value;
		this.description=desc;
	}

	@Override
	public String toString(){
		return value+"."+description;
	}

	public static Options createFromInt(int option){
		Options result=null;
		result=Options.values()[option];
		return result;
	}
}
