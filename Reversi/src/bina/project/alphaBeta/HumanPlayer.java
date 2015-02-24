package bina.project.alphaBeta;

import java.util.Scanner;

public class HumanPlayer implements IPlayer {

	@Override
	public GameNode playTurn(GameNode move) {
		System.out.println("your turn");
		GameNode nextMove;
		while(true)
		{
			try{
				Scanner in = new Scanner(System.in);
				int i = in.nextInt();
				int j = in.nextInt();
				in.close();
				if(move == null){
					System.out.println();
				}
				nextMove = move.playTurn(i, j);
				break;
			}
			catch(Exception e){
				System.out.println("Failed - try again.");
				e.printStackTrace();
			}
		}
		return nextMove;
	}
	

}
