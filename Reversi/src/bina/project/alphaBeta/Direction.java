package bina.project.alphaBeta;

enum Direction{
	UP, LEFT, DOWN, RIGHT, DIAGONAL1, DIAGONAL2, DIAGONAL3, DIAGONAL4;

	Pair next(Pair p){
		switch (this) {
		case UP:
			return new Pair(p.i-1, p.j);
		case LEFT:
			return new Pair(p.i, p.j-1);
		case DOWN:
			return new Pair(p.i+1, p.j);
		case RIGHT:
			return new Pair(p.i, p.j+1);
		case DIAGONAL1:
			return new Pair(p.i+1, p.j+1);
		case DIAGONAL2:
			return new Pair(p.i+1, p.j-1);
		case DIAGONAL3:
			return new Pair(p.i-1, p.j+1);
		case DIAGONAL4:
			return new Pair(p.i-1, p.j-1);
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
		return new Direction[] {UP, LEFT, DOWN, RIGHT, DIAGONAL1, DIAGONAL2, DIAGONAL3, DIAGONAL4,};
	}
}
