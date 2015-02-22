package bina.project.alphaBeta;

import java.util.List;
import java.util.Scanner;

public class Game {
	
	public void manVsMachine(HumanPlayer humanPlayer, Player player){
		Turn turn = Turn.MAX;
		GameNode move = ReversiGameNode.getInitalMove();

		move.printState();
		
//		HumanPlayer humanPlayer = new HumanPlayer(turn);
//		BranchFactorPlayer player= new BranchFactorPlayer(turn.next(), branchingFactor);
		
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
				temp = player.playTurn(move);
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
		
	}
	
	public void machineVsMachine(Player player1, Player player2){
		Turn turn = Turn.MAX;
		GameNode gameState = ReversiGameNode.getInitalMove();
		
//		Player player1 = new BranchFactorPlayer(turn, b1);
//		Player player2 = new BranchFactorPlayer(turn.next(), b2);
		
		GameNode playerMove;
		boolean gameOver = false;
		boolean played = true;
		while (!gameOver){
			playerMove = turn.isMax() ? player1.playTurn(gameState) : player2.playTurn(gameState);
			
			if(playerMove != null){
				gameState = playerMove;
				played = true;
			}
			else{
				gameState = gameState.passTurn();
				if (!played){
					gameOver = true;
				}
				played = false;
			}
//			gameState .printState();/**/
			turn = turn.next();
		}
		
		declareWinner(gameState, player1, player2);
	}

	private void declareWinner(GameNode move, Player player1, Player player2) {
		System.out.println("**************GAME_OVER**************");
		int score = move.getScore();
		System.out.println("SCORE:"+score);
		if(score == 0){
			System.out.println("it's a TIE!");
		}
		else{
			System.out.println(score>0 ? "MAXI WINS!": "MIN WINS!");
		}
		System.out.println("**************************************\n");
	}
	
	
	
	

}
