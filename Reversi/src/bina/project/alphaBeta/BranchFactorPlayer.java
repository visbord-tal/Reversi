package bina.project.alphaBeta;

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
	public int runAlphaBetaAlgorithm(GameNode move, IStatistics stats) {
//		int value = evalBranch(move, mBranchingFactor, alpha, beta, stats);
		int value = mAlgorithm.iterativeDeepeningEvalBranch(move, mBranchingFactor, stats);
		return value;
	}

	@Override
	public List<GameNode> getNextMoves(GameNode move) {
		return mAlgorithm.getNextBestMoves(move, mBranchingFactor);
	}

}
