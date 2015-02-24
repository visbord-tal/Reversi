package bina.project.alphaBeta;

public class Main {
	
	public static void main(String[] args){
		Player b1 = new BranchFactorPlayer(Turn.MAX, 8);
		Player b2 = new BranchFactorPlayer(Turn.MIN, 5);
        System.out.println("niv");
//		Player d1 = new DepthPlayer(Turn.MAX, 6);
//		Player d2 = new DepthPlayer(Turn.MIN, 6);

		Statistics stats = new Statistics();
		new Game().machineVsMachine(b1, b2);
		stats.endMonitoring();
		System.out.println("TOTAL TIME:"+stats.getTotalTime());
//		
//		int dWins = 0, bWins = 0;
//		Player winner;
//		for(int i =0; i<10; i++){
//			winner = new Game().machineVsMachine(b1, d2);
//			if(winner==b1){
//				bWins++;
//			}
//			else if(winner==d2){
//				dWins++;
//			}
//			b1.mTurn = b1.mTurn.next();
//			d2.mTurn = d2.mTurn.next();
//			winner = new Game().machineVsMachine(d2, b1);
//			if(winner==b1){
//				bWins++;
//			}
//			else if(winner==d2){
//				dWins++;
//			}
//			b1.mTurn = b1.mTurn.next();
//			d2.mTurn = d2.mTurn.next();
//		}
//		
//		System.out.println("bWins:"+bWins + "   dWins:"+dWins);

		
		

//		player1 = new BranchFactorPlayer(Turn.MAX, 5);
//		player2 = new BranchFactorPlayer(Turn.MIN, 2);
//		new Game().machineVsMachine(player1, player2);
//		player1 = new BranchFactorPlayer(Turn.MAX, 3);
//		player2 = new BranchFactorPlayer(Turn.MIN, 6);
//		new Game().machineVsMachine(player1, player2);
//		player1 = new BranchFactorPlayer(Turn.MAX, 6);
//		player2 = new BranchFactorPlayer(Turn.MIN, 3);
//		new Game().machineVsMachine(player1, player2);
	}

}
