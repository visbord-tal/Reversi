package bina.project.alphaBeta;

import java.util.List;

public class DepthPlayer extends Player {

	private int mDepth;
	
	public DepthPlayer(Turn turn, int depth) {
		super(turn);
		mDepth = depth;
	}

	@Override
	public int runAlphaBetaAlgorithm(GameNode move, IStatistics stats) {
		int value = mAlgorithm.evalDepth(move, mDepth, stats);
		return value;
	}

	@Override
	public List<GameNode> getNextMoves(GameNode move) {
		return move.getAllLegalMoves();
	}

}
