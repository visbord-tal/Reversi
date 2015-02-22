package bina.project.alphaBeta;

import static bina.project.alphaBeta.AlphBetaAlgorithms.evalBranch;

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
		
//		int value = evalBranch(move, mBranchingFactor, alpha, beta, stats);
		int value = AlphBetaAlgorithms.iterativeDeepeningEvalBranch(move, mBranchingFactor, alpha, beta, stats);
		
		stats.endMonitoring();
		mStatistics.add(stats);
		mGameStatistics.visitNode(stats.getMaxDepth(), stats.getAvgBrnchingFactor());

		return chooseBestMove(AlphBetaAlgorithms.getNextMoves(move, mBranchingFactor), value);
	}

}
