package bina.project.alphaBeta;

public class Main {
	
	public static void main(String[] args){
		Player b1 = new BranchFactorPlayer(Turn.MAX, 4);
//		Player b2 = new BranchFactorPlayer(Turn.MIN, 5);
//		Player d1 = new DepthPlayer(Turn.MAX, 7);
		Player d2 = new DepthPlayer(Turn.MIN, 7);
		Statistics stats ;
		String winnerString;
		Player winner;
		for (int i = 0; i < 5; i++) {
			winner = new Game().machineVsMachine(b1, d2);
			if (winner instanceof DepthPlayer) {
				winnerString = "Depth";
			}
			else{
				winnerString = "BranchFactor";
			}
			System.out.println(winnerString + "wins");
			
			winner = new Game().machineVsMachine(d2, b1);
			if (winner instanceof DepthPlayer) {
				winnerString = "Depth";
			}
			else{
				winnerString = "BranchFactor";
			}
			System.out.println(winnerString + "wins");

		}
	}

}
