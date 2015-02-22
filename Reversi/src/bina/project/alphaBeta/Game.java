package bina.project.alphaBeta;

import java.util.List;
import java.util.Scanner;

public class Game {
	
	public void manVsMachine(int branchingFactor){
		Turn turn = Turn.MAX;
		GameNode move = ReversiGameNode.getInitalMove();

		move.printState();
		
		HumanPlayer humanPlayer = new HumanPlayer(turn);
		BranchingPlayer branchingPlayer= new BranchingPlayer(turn.next(), branchingFactor);
		
		boolean played = true;
		boolean gameOver = false;
		GameNode temp;
		while (!gameOver){
			System.out.println("Turn: "+turn);
			
			if(turn.isMax()){
				if(move.isLeaf()){
					if(played){
						System.out.println("Sory, you have no moves. computer plays again..");
					}
					else{
						gameOver = true;
					}
					played = false;
				}
				else{//not leaf
					move = humanPlayer.playTurn(move);
					played = true;
				}
			}//MIN turn
			else{
				temp = branchingPlayer.playTurn(move);
				if(temp == null){
					if(played){
						System.out.println("computer got no moves, you go again.");
					}
					else{
						gameOver = true;
					}
					played = false;
				}
				else{
					move = temp;
				}
			}

			turn = turn.next();
			
			move.printState();
			int val = move.getHeuristicEval();
			System.out.println("SCORE:"+val);
			
		}
		move.printState();
		//declareWinner(move, humanPlayer, branchingPlayer);
	}
	
	public void machineVsMachine(int b1, int b2){
		Turn turn = Turn.MAX;
		GameNode gameState = ReversiGameNode.getInitalMove();
		
//		gameState.printState();
		
		BranchingPlayer player1 = new BranchingPlayer(turn, b1);
		BranchingPlayer player2 = new BranchingPlayer(turn.next(), b2);
		
		GameNode playerMove;
		int val ;
		boolean gameOver = false;
		boolean played = true;
		while (!gameOver){
//			System.out.println("Turn: "+turn);
			
			playerMove = turn.isMax() ? player1.playTurn(gameState) : player2.playTurn(gameState);
			
			if(playerMove != null){
				gameState = playerMove;
				played = true;
			}
			else{
//				System.out.println(turn+" PASS");
				gameState = gameState.passTurn();
				if (!played){
					gameOver = true;
				}
				played = false;
			}
			
			turn = turn.next();
			
//			gameState.printState();
//			val = gameState.heuristicEval();
//			System.out.println("SCORE:"+val);
		}
		
		declareWinner(gameState, player1, player2);
	}

	private void declareWinner(GameNode move, BranchingPlayer player1, BranchingPlayer player2) {
		System.out.println("**************GAME_OVER**************");
		int val = move.getHeuristicEval();
		System.out.println("SCORE:"+val);
		if(val == 0){
			System.out.println("it's a TIE!");
		}
		else{
			System.out.println(val>0 ? "MAXI WINS!": "MIN WINS!");
		}
		System.out.println("PLAYER 1 BRANCH:"+player1.getBranchingFactor());
		player1.printStatistics();
		System.out.println("PLAYER 2 BRANCH:"+player2.getBranchingFactor());
		player2.printStatistics();
		
	}
	
	
	
	

}
