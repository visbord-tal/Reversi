package bina.project.alphaBeta;

enum Direction{
	UP, LEFT, DOWN, RIGHT, DIAGONAL1, DIAGONAL2, DIAGONAL3, DIAGONAL4;

	static Direction[] mDirections = new Direction[] {UP, LEFT, DOWN, RIGHT, DIAGONAL1, DIAGONAL2, DIAGONAL3, DIAGONAL4};
	
	Pair[][] mPairs =  {{new Pair(0,0), new Pair(0,1), new Pair(0,2), new Pair(0,3), new Pair(0,4), new Pair(0,5), new Pair(0,6), new Pair(0,7)},
						{new Pair(1,0), new Pair(1,1), new Pair(1,2), new Pair(1,3), new Pair(1,4), new Pair(1,5), new Pair(1,6), new Pair(1,7)},
						{new Pair(2,0), new Pair(2,1), new Pair(2,2), new Pair(2,3), new Pair(2,4), new Pair(2,5), new Pair(2,6), new Pair(2,7)},
						{new Pair(3,0), new Pair(3,1), new Pair(3,2), new Pair(3,3), new Pair(3,4), new Pair(3,5), new Pair(3,6), new Pair(3,7)},
						{new Pair(4,0), new Pair(4,1), new Pair(4,2), new Pair(4,3), new Pair(4,4), new Pair(4,5), new Pair(4,6), new Pair(4,7)},
						{new Pair(5,0), new Pair(5,1), new Pair(5,2), new Pair(5,3), new Pair(5,4), new Pair(5,5), new Pair(5,6), new Pair(5,7)},
						{new Pair(6,0), new Pair(6,1), new Pair(6,2), new Pair(6,3), new Pair(6,4), new Pair(6,5), new Pair(6,6), new Pair(6,7)},
						{new Pair(7,0), new Pair(7,1), new Pair(7,2), new Pair(7,3), new Pair(7,4), new Pair(7,5), new Pair(7,6), new Pair(7,7)}};

	
	Pair next(Pair p){
		switch (this) {
		case UP:
//			return new Pair(p.i-1, p.j);
			return mPairs[p.i-1][p.j];
		case LEFT:
//			return new Pair(p.i, p.j-1);
			return mPairs[p.i][p.j-1];
		case DOWN:
//			return new Pair(p.i+1, p.j);
			return mPairs[p.i+1][p.j];
		case RIGHT:
//			return new Pair(p.i, p.j+1);
			return mPairs[p.i][p.j+1];
		case DIAGONAL1:
//			return new Pair(p.i+1, p.j+1);
			return mPairs[p.i+1][p.j+1];
		case DIAGONAL2:
//			return new Pair(p.i+1, p.j-1);
			return mPairs[p.i+1][p.j-1];
		case DIAGONAL3:
//			return new Pair(p.i-1, p.j+1);
			return mPairs[p.i-1][p.j+1];
		case DIAGONAL4:
//			return new Pair(p.i-1, p.j-1);
			return mPairs[p.i-1][p.j-1];
		};
		return null;
	}

	boolean hasNext(Pair p, int matrixSize){
		switch (this) {
		case UP:
			return p.i > 0;
		case LEFT:
			return p.j > 0;
		case DOWN:
			return p.i < matrixSize - 1;
		case RIGHT:
			return p.j < matrixSize - 1;
		case DIAGONAL1:
			return p.i < matrixSize - 1 && p.j < matrixSize - 1;
		case DIAGONAL2:
			return p.i < matrixSize- 1 && p.j > 0;
		case DIAGONAL3:
			return p.i > 0 && p.j < matrixSize - 1;
		case DIAGONAL4:
			return p.i > 0 && p.j > 0;
		};
		return false;
	}

	static Direction[] getAllDirections(){
		return mDirections; 
	}
}
