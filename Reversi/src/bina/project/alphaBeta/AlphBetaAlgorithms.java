package bina.project.alphaBeta;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AlphBetaAlgorithms {
	
	final static int MAX_NODE_COUNT = 1000;
	
	public static int evalDepth(GameNode move, int depth , int alpha, int beta, Turn turn, IStatistics stats){
		if(move.isLeaf()|| depth==0){
			int val = move.getHeuristicEval();
//			move.setValue(val);
			stats.visitNode(depth, 0);
			return val;
		}
		
		int current;
		List<GameNode> nextMoves = move.getAllLegalMoves();
		int childValue;
		
		if(turn == Turn.MIN){//Min turn
			
			current = beta;
			stats.visitNode(depth, nextMoves.size());
			for (GameNode child : nextMoves) {
				childValue = evalDepth(child, depth -1, alpha, current, turn.next(), stats);
				child.setValue(childValue);
				
				current = Math.min(current, childValue);
				if(current <= alpha){
//					move.setValue(alpha);
					return alpha;
				}
			}
			
//			move.setValue(current);
			return current;
		}
		
		else{//Max turn
			
			current = alpha;
			
			for (GameNode child : nextMoves) {
				childValue = evalDepth(child, depth -1, current, beta, turn.next(), stats);
				child.setValue(childValue);
				
				current = Math.max(current, childValue);
				if(current >= beta){
//					move.setValue(beta);
					return beta;
				}
			}
//			move.setValue(current);
			return current;
		}
	}
		
//	public static int negaMaxEval(GameNode move, int depth, int alpha, int beta, Turn turn){
//		if(move.isLeaf() || depth==0){
//			int val = move.getHeuristicEval();
//			move.setValue(val);
//			return val;
//		}
//		for (GameNode childMove : move.getAllLegalMoves()) {
//			int childVal = negaMaxEval(childMove, depth-1, -beta, -alpha, turn.next());
//			Math.max(alpha, -childVal);	
//			if(alpha >= beta){
//				move.setValue(alpha);
//				return alpha;
//			}
//		}
//		move.setValue(alpha);
//		return alpha;
//	}
	
	static int count;
	public static int evalBranch(GameNode move, int branchingFactor, int alpha, int beta,/* Turn turn,*/ IStatistics stats){
		count = 0;
		return evalBranch2(move, branchingFactor, 0, alpha, beta, Turn.MAX, stats);
	}
	
	private static int evalBranch2(GameNode move, int branchingFactor, int depth ,int alpha, int beta, Turn turn, IStatistics stats){
		count++;
		if(move.isLeaf() || count>=MAX_NODE_COUNT){
			int val = move.getHeuristicEval();
//			move.setValue(val);
			stats.visitNode(depth, 0);
			return val;
		}
		
		int current;
		List<GameNode> nextMoves = getNextMoves(move, branchingFactor);
		int childValue;
		
		if(turn == Turn.MIN){//Min turn
			
			current = beta;
//			nextMoves = getNextMoves(move, branchingFactor);
			stats.visitNode(depth, nextMoves.size());
			for (GameNode child : nextMoves) {
				childValue = evalBranch2(child, branchingFactor ,  depth+1 , alpha, current, turn.next(), stats);
				child.setValue(childValue);
				
				current = Math.min(current, childValue);
				if(current <= alpha){
//					move.setValue(alpha);
					return alpha;
				}
			}
			
//			move.setValue(current);
			return current;
		}
		
		else{//Max turn
			
			current = alpha;
//			nextMoves = getNextMoves(move, branchingFactor);
			
			for (GameNode child : nextMoves) {
				childValue = evalBranch2(child, branchingFactor, depth+1, current, beta, turn.next(), stats);
				child.setValue(childValue);
				
				current = Math.max(current, childValue);
				if(current >= beta){
//					move.setValue(beta);
					return beta;
				}
			}
//			move.setValue(current);
			return current;
		}
	}
	
	/**/
	static int nodeCount;
	public static int iterativeDeepeningEvalBranch(GameNode move, int branchingFactor, int alpha, int beta, IStatistics stats){
		nodeCount = 0;
		int maxDepth = 2;
		int best = 0;
		Boolean shouldStop = true;
		while(nodeCount < MAX_NODE_COUNT && !shouldStop){
			nodeCount = 0;
			best = iterativeDeepeningEvalBranch2(move, branchingFactor, 0, alpha, beta, Turn.MAX, maxDepth ,stats, shouldStop );
			maxDepth ++;
			System.out.println("maxDepth:"+maxDepth);
		}
		return best;
	}
	
	private static int iterativeDeepeningEvalBranch2(GameNode move, int branchingFactor, int depth ,int alpha, int beta, Turn turn,int maxDepth ,IStatistics stats, Boolean shouldStop){
		nodeCount++;
		if(move.isLeaf() || depth==maxDepth){
			int val = move.getHeuristicEval();
			stats.visitNode(depth, 0);
			if(depth==maxDepth){
				 shouldStop = false;
			}
			return val;
		}
		
		int current;
		List<GameNode> nextMoves = getNextMoves(move, branchingFactor);
		int childValue;
		
		if(turn == Turn.MIN){//Min turn
			
			current = beta;
			stats.visitNode(depth, nextMoves.size());
			for (GameNode child : nextMoves) {
				childValue = iterativeDeepeningEvalBranch2(child, branchingFactor ,  depth+1 , alpha, current, turn.next(), maxDepth,stats, shouldStop);
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
				childValue = iterativeDeepeningEvalBranch2(child, branchingFactor, depth+1, current, beta, turn.next(),maxDepth, stats, shouldStop);
				child.setValue(childValue);
				
				current = Math.max(current, childValue);
				if(current >= beta){
					return beta;
				}
			}
			return current;
		}
	}
	
//	
//	static int count2;
//	public static int evalBfs(GameNode move, int branchingFactor, int alpha, int beta, Turn turn, IStatistics stats){
//		count2 = 0;
//		Queue<GameNode> q = new  LinkedList<GameNode>();
//		q.add(move);
//		return evalBfs2(/*move, */branchingFactor, 0, alpha, beta, turn, stats, q);
//	}
//	
//	public static int evalBfs2(/*GameNode move,*/ int branchingFactor, int depth ,int alpha, int beta, Turn turn, IStatistics stats, Queue<GameNode> q){
//		GameNode move = q.poll();
//		count2++;
//		if(move.isLeaf()|| count2>=NODE_COUNT){
//			int val = move.getHeuristicEval();
////			move.setValue(val);
//			stats.visitNode(depth, 0);
//			return val;
//		}
//		
//		int current;
//		List<GameNode> nextMoves;
//		int childValue;
//		
//		if(turn == Turn.MIN){//Min turn
//			
//			current = beta;
//			nextMoves = getNextMoves(move, branchingFactor);
//			stats.visitNode(depth, nextMoves.size());
//			
//			for (GameNode child : nextMoves) {
//				childValue = evalBfs2(/*child, */branchingFactor ,  depth+1 , alpha, current, turn.next(), stats, q);
//				q.add(child);
//				
//				child.setValue(childValue);
//				
//				current = Math.min(current, childValue);
//				if(current <= alpha){
////					move.setValue(alpha);
//					return alpha;
//				}
//			}
//			
////			move.setValue(current);
//			return current;
//		}
//		
//		else{//Max turn
//			
//			current = alpha;
//			nextMoves = getNextMoves(move, branchingFactor);
//			
//			for (GameNode child : nextMoves) {
//				childValue = evalBfs2(/*child,*/ branchingFactor, depth+1, current, beta, turn.next(), stats, q);
//				q.add(child);
//				child.setValue(childValue);
//				
//				current = Math.max(current, childValue);
//				if(current >= beta){
////					move.setValue(beta);
//					return beta;
//				}
//			}
////			move.setValue(current);
//			return current;
//		}
//	}

	public static List<GameNode> getNextMoves(GameNode move,int branchingFactor) {
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
