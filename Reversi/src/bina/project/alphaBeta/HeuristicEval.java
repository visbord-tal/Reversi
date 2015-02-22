package bina.project.alphaBeta;


public class HeuristicEval {

	private static int V[][] = {{20, -3, 11, 8, 8, 11, -3, 20},
            {-3, -7, -4, 1, 1, -4, -7, -3},
            {11, -4, 2, 2, 2, 2, -4, 11},
            {8, 1, 2, -3, -3, 2, 1, 8},
            {8, 1, 2, -3, -3, 2, 1, 8},
            {11, -4, 2, 2, 2, 2, -4, 11},
            {-3, -7, -4, 1, 1, -4, -7, -3},
            {20, -3, 11, 8, 8, 11, -3, 20}};
	
    public static int dynamic_heuristic_evaluation_function(ReversiGameNode reversiGameNode) {
        ReversiGameNode.Tile grid[][] = reversiGameNode.getBoardMatrix();
        ReversiGameNode.Tile my_color = reversiGameNode.getTurn();
        ReversiGameNode.Tile opp_color = reversiGameNode.getTurn().opposite();
        int my_score = 0;
        int opp_score = 0;

        //up and down
        for (int i = 0; i < 2; i++) {
            for (int j = 2; j < 6; j++) {
                if (grid[i][j].equals(my_color)) {
                    my_score += V[i][j];
                } else if (grid[i][j].equals(opp_color)) {
                    opp_score += V[i][j];
                }
            }
            if (i == 1) {
                i = i + 4;
            }
        }
        //center
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                if (grid[i][j].equals(my_color)) {
                    my_score += V[i][j];
                } else if (grid[i][j].equals(opp_color)) {
                    opp_score += V[i][j];
                }

            }
        }

// Corner occupancy
            if (grid[0][0] == my_color) my_score += V[0][0];
            else if (grid[0][0] == opp_color) opp_score += V[0][0];
            if (grid[0][7] == my_color) my_score += V[0][7];
            else if (grid[0][7] == opp_color) opp_score += V[0][7];
            if (grid[7][0] == my_color) my_score += V[7][0];
            else if (grid[7][0] == opp_color) opp_score += V[7][0];
            if (grid[7][7] == my_color) my_score += V[7][7];
            else if (grid[7][7] == opp_color) opp_score += V[7][7];

// Corner closeness
            if (grid[0][0] == ReversiGameNode.Tile.EMPTY) {
                if (grid[0][1] == my_color) my_score += V[0][1];
                else if (grid[0][1] == opp_color) opp_score += V[0][1];
                if (grid[1][1] == my_color) my_score += V[1][1];
                else if (grid[1][1] == opp_color) opp_score += V[1][1];
                if (grid[1][0] == my_color) my_score += V[1][0];
                else if (grid[1][0] == opp_color) opp_score += V[1][0];
            }
            if (grid[0][7] == ReversiGameNode.Tile.EMPTY) {
                if (grid[0][6] == my_color) my_score += V[0][6];
                else if (grid[0][6] == opp_color) opp_score += V[0][6];
                if (grid[1][6] == my_color) my_score += V[1][6];
                else if (grid[1][6] == opp_color) opp_score += V[1][6];
                if (grid[1][7] == my_color) my_score += V[1][7];
                else if (grid[1][7] == opp_color) opp_score += V[1][7];
            }
            if (grid[7][0] == ReversiGameNode.Tile.EMPTY) {
                if (grid[7][1] == my_color) my_score += V[7][1];
                else if (grid[7][1] == opp_color) opp_score += V[7][1];
                if (grid[6][1] == my_color) my_score += V[6][1];
                else if (grid[6][1] == opp_color) opp_score += V[6][1];
                if (grid[6][0] == my_color) my_score += V[6][0];
                else if (grid[6][0] == opp_color) opp_score += V[6][0];
            }
            if (grid[7][7] == ReversiGameNode.Tile.EMPTY) {
                if (grid[6][7] == my_color) my_score += V[6][7];
                else if (grid[6][7] == opp_color) opp_score += V[6][7];
                if (grid[6][6] == my_color) my_score += V[6][6];
                else if (grid[6][6] == opp_color) opp_score += V[6][6];
                if (grid[7][6] == my_color) my_score += V[7][6];
                else if (grid[7][6] == opp_color) opp_score += V[7][6];
            }

//        int sum = 0;
//		for (int i = 0; i < grid.length; i++) {
//			for (int j = 0; j < grid[0].length; j++) {
//				sum+= grid[i][j].value;
//			}
//		}
//		System.out.println("Sum is: " + sum);
//            // final weighted score
//        reversiGameNode.printState();
            int score = my_score - opp_score;
//        System.out.println("Score is: "+score);
            return score;
        }


}
