package bina.project.alphaBeta;

import java.util.ArrayList;
import java.util.List;

public class BranchFactorPlayer extends Player {

	private int mBranchingFactor;

	public BranchFactorPlayer(Turn turn, int mBranchingFactor) {
		super(turn);
		this.mBranchingFactor = mBranchingFactor;
	}

	public int getBranchingFactor(){
		return mBranchingFactor;
	}

	@Override
	public GameNode playTurn(GameNode move) {

		if(move.isLeaf()){//No moves
			return null;
		}
		
		int beta = move.getMaxScore();
		int alpha = beta*-1;
		
		Statistics stats = new Statistics();
		stats.startMonitoring();
		
		int value = AlphBetaAlgorithms.evalBranch(move, mBranchingFactor, alpha, beta, stats);
		
		stats.endMonitoring();
		mStatistics.add(stats);
		mGameStatistics.visitNode(stats.getMaxDepth(), stats.getAvgBrnchingFactor());

		boolean found = false;
		GameNode childMove;
		List<GameNode> nextMoves = AlphBetaAlgorithms.getNextMoves(move, mBranchingFactor);
		List<GameNode> bestMoves = new ArrayList<GameNode>(); 
		
		for (int i = 0; i<nextMoves .size(); i++) {
			childMove = nextMoves.get(i);
			if(childMove.getValue() == value){
				bestMoves.add(childMove);	
			}
		}

		if(bestMoves.size()==0){
			System.out.println("NOT FOUND!");
			throw new RuntimeException();
		}
		
		int randomIndex = (int)(Math.random()*(bestMoves.size()));
		
		return bestMoves.get(randomIndex);
	}

}
