package cpu;

import java.util.ArrayList;

import data.Figures;
import data.Player;
import data.Board;

public class AiData {
	private Board board=new Board();
	private int value;
	private Player player=new Player();
	private ArrayList<AiData>leaves=new ArrayList<>();
	
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		int i;
		for(i=0; i<100; i++){
			this.board.setBoard(i, board.getBoard(i));
		}
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public ArrayList<AiData> getLeaves() {
		return leaves;
	}
	public AiData getLeaves(int number) {
		return leaves.get(number);
	}
	public Figures[] getLeavesBoard(int number) {
		return leaves.get(number).board.getBoard();
	}
	public void setLeaves(ArrayList<AiData> leaves) {
		this.leaves = leaves;
	}
}
