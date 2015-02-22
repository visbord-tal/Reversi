package bina.project.alphaBeta;

public class DepthPlayer extends Player {

	private int mDepth;
	
	public DepthPlayer(Turn turn, int depth) {
		super(turn);
		mDepth = depth;
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
		
		int value = AlphBetaAlgorithms.evalDepth(move, mDepth ,alpha, beta, Turn.MAX, stats);
		
		stats.endMonitoring();
		mStatistics.add(stats);
		mGameStatistics.visitNode(stats.getMaxDepth(), stats.getAvgBrnchingFactor());

		return chooseBestMove(move.getAllLegalMoves(), value);
	}

}
