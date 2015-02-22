package bina.project.alphaBeta;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AlphBetaAlgorithms {
	
	final static int NODE_COUNT = 100000;
	
	public static int evalDepth(GameNode move, int depth , int alpha, int beta, Turn turn, IStatistics stats){
		if(move.isLeaf()|| depth==0){
			int val = move.getHeuristicEval();
			move.setValue(val);
			return val;
		}
		
		int current;
		List<GameNode> nextMoves;
		int childValue;
		
		if(turn == Turn.MIN){//Min turn
			
			current = beta;
			nextMoves = move.getAllLegalMoves();
			stats.visitNode(depth, nextMoves.size());
			for (GameNode child : nextMoves) {
				childValue = evalDepth(child, depth -1, alpha, current, turn.next(), stats);
			//	child.setValue(childValue);
				
				current = Math.min(current, childValue);
				if(current <= alpha){
					move.setValue(alpha);
					return alpha;
				}
			}
			
			move.setValue(current);
			return current;
		}
		
		else{//Max turn
			
			current = alpha;
			nextMoves = move.getAllLegalMoves();
			
			for (GameNode child : nextMoves) {
				childValue = evalDepth(child, depth -1, current, beta, turn.next(), stats);
				//child.setValue(childValue);
				
				current = Math.max(current, childValue);
				if(current >= beta){
					move.setValue(beta);
					return beta;
				}
			}
			move.setValue(current);
			return current;
		}
	}
		
	public static int negaMaxEval(GameNode move, int depth, int alpha, int beta, Turn turn){
		if(move.isLeaf() || depth==0){
			int val = move.getHeuristicEval();
			move.setValue(val);
			return val;
		}
		for (GameNode childMove : move.getAllLegalMoves()) {
			int childVal = negaMaxEval(childMove, depth-1, -beta, -alpha, turn.next());
			Math.max(alpha, -childVal);	
			if(alpha >= beta){
				move.setValue(alpha);
				return alpha;
			}
		}
		move.setValue(alpha);
		return alpha;
	}
	
	static int count;
	public static int evalBranch(GameNode move, int branchingFactor, int alpha, int beta,/* Turn turn,*/ IStatistics stats){
		count = 0;
		return evalBranch2(move, branchingFactor, 0, alpha, beta, Turn.MAX, stats);
	}
	
	public static int evalBranch2(GameNode move, int branchingFactor, int depth ,int alpha, int beta, Turn turn, IStatistics stats){
		count++;
		if(move.isLeaf()|| count>=NODE_COUNT){
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
