package bina.project.alphaBeta;

import java.util.List;

public class BranchingPlayer extends Player {

	private int mBranchingFactor;

	public BranchingPlayer(Turn turn, int mBranchingFactor) {
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
		
//		int value = AlphBetaAlgorithms.evalBfs(move, mBranchingFactor, alpha, beta, mTurn, stats);
		int value = AlphBetaAlgorithms.evalBranch(move, mBranchingFactor, alpha, beta, mTurn, stats);
		//value = AlphBetaAlgorithms.evalDepth(move, branchingFactor,alpha, beta, turn, stats);
		//value = negaMaxEval(move, DEPTH, alpha, beta, turn);
		
		stats.endMonitoring();
		mStatistics.add(stats);
		mGameStatistics.visitNode(stats.getMaxDepth(), stats.getAvgBrnchingFactor());
//		
//		System.out.println("eval value:"+value);
//		System.out.println("branch Max:"+stats.getMaxBrnchingFactor()+", Avg:"+stats.getAvgBrnchingFactor());
//		System.out.println("depth Max:"+stats.getMaxDepth()+", Avg:"+stats.getAvgDepth());
//
		boolean found = false;
		GameNode childMove;
		List<GameNode> list = AlphBetaAlgorithms.getNextMoves(move, mBranchingFactor);
		for (int i = 0; i<list .size(); i++) {
			childMove = list.get(i);
			if(childMove.getValue() == value){

				found = true;
				move = childMove; 
				break;
			}
		}

		if(!found){
			System.out.println("NOT FOUND!");
			throw new RuntimeException();
		}
		return move;
	}

}
