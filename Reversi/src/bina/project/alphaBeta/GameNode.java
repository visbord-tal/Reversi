package bina.project.alphaBeta;
import java.util.List;

 
public interface GameNode{
		
	public List<GameNode> getAllLegalMoves();

	public  boolean isLeaf();
	
	public int getHeuristicEval();

	public void printState();
	
	public int getValue();
	
	public void setValue(int value);
	
	public GameNode playTurn(int i, int j);
	
	public int getMaxScore();

	public GameNode passTurn();
	
 }