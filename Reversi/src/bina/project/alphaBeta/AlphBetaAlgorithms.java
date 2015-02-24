package bina.project.alphaBeta;

import java.util.ArrayList;
import java.util.List;

public class AlphBetaAlgorithms {
	
	private int mMaxNodeCount = 25000;
	
	private int mNodeCount;
	private boolean mShouldStop;
	
	public void setmMaxNodeCount(int mMaxNodeCount) {
		this.mMaxNodeCount = mMaxNodeCount;
	}

	public int evalDepth(GameNode move, int maxDepth , IStatistics stats){
		int beta = Integer.MAX_VALUE;
		int alpha = beta*-1;
		
		return evalDepth2(move, maxDepth ,alpha, beta, Turn.MAX, stats);
	}
	
	public int evalDepth2(GameNode rootMove, int depth , int alpha, int beta, Turn turn, IStatistics stats){
		if(rootMove.isLeaf()|| depth==0){
			int val = rootMove.getHeuristicEval();
			stats.visitNode(depth, 0);
			return val;
		}
		
		int current;
		List<GameNode> nextMoves = rootMove.getAllLegalMoves();
		int childValue;
		
		if(turn == Turn.MIN){//Min turn
			
			current = beta;
			stats.visitNode(depth, nextMoves.size());
			for (GameNode child : nextMoves) {
				childValue = evalDepth2(child, depth -1, alpha, current, turn.next(), stats);
				child.setValue(childValue);
				
				current = Math.min(current, childValue);
				if(current <= alpha){
					return alpha;
				}
			}
			
			return current;
		}
		
		else{//Max turn
			
			current = alpha;
			
			for (GameNode child : nextMoves) {
				childValue = evalDepth2(child, depth -1, current, beta, turn.next(), stats);
				child.setValue(childValue);
				
				current = Math.max(current, childValue);
				if(current >= beta){
					return beta;
				}
			}
			return current;
		}
	}
		
	public int evalBranch(GameNode move, int branchingFactor, IStatistics stats){
		mNodeCount = 0;
		int beta = Integer.MAX_VALUE;
		int alpha = beta*-1;

		return evalBranch2(move, branchingFactor, 0, alpha, beta, Turn.MAX, stats);
	}
	
	private int evalBranch2(GameNode rootMove, int branchingFactor, int depth ,int alpha, int beta, Turn turn, IStatistics stats){
		mNodeCount++;
		if(rootMove.isLeaf() || mNodeCount>=mMaxNodeCount){
			int val = rootMove.getHeuristicEval();
			stats.visitNode(depth, 0);
			return val;
		}
		
		int current;
		List<GameNode> nextMoves = getNextBestMoves(rootMove, branchingFactor);
		int childValue;
		
		if(turn == Turn.MIN){//Min turn
			
			current = beta;
			stats.visitNode(depth, nextMoves.size());
			for (GameNode child : nextMoves) {
				childValue = evalBranch2(child, branchingFactor ,  depth+1 , alpha, current, turn.next(), stats);
				child.setValue(childValue);
				
				current = Math.min(current, childValue);
				if(current <= alpha){
					return alpha;
				}
			}
			
			return current;
		}
		
		else{//Max turn
			
			current = alpha;
			
			for (GameNode child : nextMoves) {
				childValue = evalBranch2(child, branchingFactor, depth+1, current, beta, turn.next(), stats);
				child.setValue(childValue);
				
				current = Math.max(current, childValue);
				if(current >= beta){
					return beta;
				}
			}
			return current;
		}
	}
	
	public int iterativeDeepeningEvalBranch(GameNode rootMove, int branchingFactor, IStatistics stats){
		int beta = Integer.MAX_VALUE;
		int alpha = beta*-1;
		mShouldStop = false;
		mNodeCount = 0;

		int best = 0;
		int maxDepth = 2;
		while(mNodeCount < mMaxNodeCount && !mShouldStop){
			mShouldStop = true;
			mNodeCount = 0;
			best = iterativeDeepeningEvalBranch2(rootMove, branchingFactor, 0, alpha, beta, Turn.MAX, maxDepth ,stats);
			maxDepth ++;
		}
		return best;
	}
	
	private int iterativeDeepeningEvalBranch2(GameNode rootMove, int branchingFactor, int currentDepth ,int alpha, int beta, Turn turn,int maxDepth ,IStatistics stats){
		mNodeCount++;
		if(rootMove.isLeaf() || currentDepth==maxDepth){
			int val = rootMove.getHeuristicEval();
			stats.visitNode(currentDepth, 0);
			if(!rootMove.isLeaf() ){
				mShouldStop = false ;
			}
			return val;
		}
		
		int current;
		List<GameNode> nextMoves = getNextBestMoves(rootMove, branchingFactor);
		int childValue;
		
		if(turn == Turn.MIN){//Min turn
			
			current = beta;
			stats.visitNode(currentDepth, nextMoves.size());
			for (GameNode child : nextMoves) {
				childValue = iterativeDeepeningEvalBranch2(child, branchingFactor ,  currentDepth+1 , alpha, current, turn.next(), maxDepth,stats);
				child.setValue(childValue);
				
				current = Math.min(current, childValue);
				if(current <= alpha){
					return alpha;
				}
			}
			
			return current;
		}
		
		else{//Max turn
			
			current = alpha;
			
			for (GameNode child : nextMoves) {
				childValue = iterativeDeepeningEvalBranch2(child, branchingFactor, currentDepth+1, current, beta, turn.next(),maxDepth, stats);
				child.setValue(childValue);
				
				current = Math.max(current, childValue);
				if(current >= beta){
					return beta;
				}
			}
			return current;
		}
	}
	
	public List<GameNode> getNextBestMoves(GameNode move,int branchingFactor) {
		List<GameNode> allLegalMoves = move.getAllLegalMoves();
		if(allLegalMoves.size() <= branchingFactor){
			return allLegalMoves;
		}
		ArrayList<GameNode> list = new  ArrayList<GameNode>();
		for (int i = 0; i < branchingFactor; i++) {
			list.add(allLegalMoves.get(i));
		}
		return list;
	} 
	
}
