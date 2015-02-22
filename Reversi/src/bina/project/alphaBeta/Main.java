package bina.project.alphaBeta;

public class Main {
	
	public static void main(String[] args){
		Player player1 = new BranchFactorPlayer(Turn.MAX, 2);
		Player player2 = new BranchFactorPlayer(Turn.MIN, 5);
		new Game().machineVsMachine(player1, player2);
		player1 = new BranchFactorPlayer(Turn.MAX, 5);
		player2 = new BranchFactorPlayer(Turn.MIN, 2);
		new Game().machineVsMachine(player1, player2);
		player1 = new BranchFactorPlayer(Turn.MAX, 3);
		player2 = new BranchFactorPlayer(Turn.MIN, 6);
		new Game().machineVsMachine(player1, player2);
		player1 = new BranchFactorPlayer(Turn.MAX, 6);
		player2 = new BranchFactorPlayer(Turn.MIN, 3);
		new Game().machineVsMachine(player1, player2);
		
		
		
		
	}

}
