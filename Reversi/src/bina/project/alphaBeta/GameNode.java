package bina.project.alphaBeta;
import java.util.List;

 
public interface GameNode{
		
	public List<GameNode> getAllLegalMoves();

	public  boolean isLeaf();
	
	public int getHeuristicEval();

	public void printState();
	
	public Integer getValue();
	
	public void setValue(int value);
	
	public GameNode playTurn(int i, int j);
	
	public GameNode passTurn();

	public int getScore();
	
 }