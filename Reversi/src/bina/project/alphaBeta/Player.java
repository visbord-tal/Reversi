package bina.project.alphaBeta;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {

	protected ArrayList<Statistics> mStatistics;
	
	protected Statistics mGameStatistics;
	
	protected Turn mTurn;
	
	protected AlphBetaAlgorithms mAlgorithm;
	
	public Player(Turn turn) {
		this.mTurn = turn;
		mStatistics = new ArrayList<Statistics>();
		mGameStatistics = new Statistics();
		mAlgorithm = new AlphBetaAlgorithms();
	}

	public abstract List<GameNode> getNextMoves(GameNode move);

	public abstract int runAlphaBetaAlgorithm(GameNode move, IStatistics stats);
	
	public GameNode playTurn(GameNode move){
		if(move.isLeaf()){//No moves
			return null;
		}
		
		Statistics stats = new Statistics();
		stats.startMonitoring();
		
		int value = runAlphaBetaAlgorithm(move, stats);
		
		stats.endMonitoring();
		mStatistics.add(stats);
		mGameStatistics.visitNode(stats.getMaxDepth(), stats.getAvgBrnchingFactor());
		
		return chooseBestMove(getNextMoves(move), value);
	}

	protected GameNode chooseBestMove(List<GameNode> nextMoves, int value) {
		GameNode childMove;
		List<GameNode> bestMoves = new ArrayList<GameNode>(); 
		
		for (int i = 0; i<nextMoves.size(); i++) {
			childMove = nextMoves.get(i);
			if(childMove.getValue()!=null && value == childMove.getValue()){
				bestMoves.add(childMove);	
			}
		}
		
		if(bestMoves.size()==0){
			System.out.println("NOT FOUND!");
		}
		
		int randomIndex = (int)(Math.random()*(bestMoves.size()));
		
		return bestMoves.get(randomIndex);
	}
	
	public void printStatistics(){
		Statistics stat;
		System.out.println("########################################################################");
		System.out.println("############## Game Statistics - Player "+mTurn+" ######################");
		System.out.println("########################################################################");
		System.out.println("Average depth ="+ mGameStatistics.getAvgDepth());
		System.out.println("Max depth ="+ mGameStatistics.getMaxDepth());
		//System.out.println("Node visited="+ mGameStatistics.getNumOfNodesVisited());
		System.out.println("########################################################################\n");
		
//		for (int i =0; i<mStatistics.size(); i++) {
//			stat = mStatistics.get(i);
//			System.out.println(i+") Max  Depth: "+ stat.getMaxDepth());
//			System.out.println("   Average Branch Factor: "+ stat.getAvgBrnchingFactor());
//			System.out.println("   Total Time: "+stat.getTotalTime());
//		}
	}
	
	
	
	
}
