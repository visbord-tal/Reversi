package bina.project.alphaBeta;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Comparator;

public class ReversiGameNode implements GameNode{

	public static int BOARD_SIZE = 8;

	private Board mBoard;

	private Tile mCurrentTurn;

	private int mValue;

	private List<GameNode> mChildMoves;

//	private int mHeuristicEval;

	private ReversiGameNode(){
		mBoard = new Board();
		mCurrentTurn = Tile.BLACK;
		calcHeuristicEval();
	}

	private ReversiGameNode(ReversiGameNode move){
		mBoard = new Board(move.mBoard);
		mCurrentTurn = move.mCurrentTurn;
//		calcHeuristicEval();
	}

	public static GameNode getInitalMove(){
		return new ReversiGameNode();
	}

	@Override
	public List<GameNode> getAllLegalMoves(){
		if(mChildMoves == null){
			initChildMoves();
		}
		return mChildMoves;
	}

	private void initChildMoves(){
		List<GameNode> moves = new ArrayList<GameNode>();
		for (int i = 0; i < mBoard.matrix.length; i++) {
			for (int j = 0; j < mBoard.matrix[0].length; j++) {
				if(mBoard.isValidMove(i,j)){
					//ReversiGameNode newMove = new ReversiGameNode(this);
					ReversiGameNode newMove = playTurn(i,j);

					moves.add(newMove);
				}
			}
		}	
		mChildMoves = moves;
		
		sortChildMoves();
	}
	
	private void sortChildMoves(){
		Comparator<GameNode> c = new Comparator<GameNode>() {
			@Override
			public int compare(GameNode o1, GameNode o2) {
				return o1.getHeuristicEval() - o2.getHeuristicEval();
			}
		}; 
		
		Collections.sort(mChildMoves, c);
	}

	@Override
	public ReversiGameNode playTurn(int i, int j) {
		if(!mBoard.isValidMove(i, j)){
			throw new RuntimeException("coordinats not valid");
		}

		ReversiGameNode newMove = new ReversiGameNode(this);

		newMove.mBoard.placeTile(i,j);

		newMove.mCurrentTurn = mCurrentTurn.opposite();

//		newMove.calcHeuristicEval();
		
		return newMove;
	}

	@Override
	public boolean isLeaf() {
		return getAllLegalMoves().size() == 0;
	}

	@Override
	public int getHeuristicEval() {
//		calcHeuristicEval();//TODO!
		return calcHeuristicEval();
	}
	
	@Override
	public void printState() {
		System.out.println(mBoard.toString());
	}


	@Override
	public int getValue() {
		return mValue;
	}

	@Override
	public void setValue(int value) {
		mValue = value;
	}

	@Override
	public int getMaxScore() {
		return BOARD_SIZE*BOARD_SIZE;
	}
	
	private int calcHeuristicEval(){
		//todo improve heuristic function
		int sum = 0;
		for (int i = 0; i < mBoard.matrix.length; i++) {
			for (int j = 0; j < mBoard.matrix[0].length; j++) {
				sum+= mBoard.matrix[i][j].value;
			}
		}
//		mHeuristicEval = sum;
		return sum;
	}

	public Tile getTurn() {
		return mCurrentTurn;
	}

	private class Board{

		Tile[][] matrix;

		public Board(){
			matrix = new Tile[BOARD_SIZE][BOARD_SIZE];

			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[0].length; j++) {
					matrix[i][j] = Tile.EMPTY;
				}
			}


			int center = BOARD_SIZE/2 -1 ;
			matrix[center][center] = Tile.BLACK;
			matrix[center][center+1] = Tile.WHITE;
			matrix[center+1][center+1] = Tile.BLACK;
			matrix[center+1][center] = Tile.WHITE; 
		}

		public Board(Board board){
			matrix = new Tile[BOARD_SIZE][BOARD_SIZE];
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[0].length; j++) {
					matrix[i][j] = board.matrix[i][j];
				}
			}
		}

		public void placeTile(int i, int j) {

			matrix[i][j] = mCurrentTurn;
			//			flipAll(i,j);
			flipAll(new Pair(i,j));
		}

		private void flipAll(Pair p) {
			for (Direction d : Direction.getAllDirections()) {
				flipDirection(d.next(p), d);
			}
		}

		private boolean flipDirection(Pair p, Direction direction){
			if(p.i<0 || p.i>matrix.length-1 || p.j<0 || p.j>matrix.length-1){
				return false;
			}
			if(matrix[p.i][p.j] == Tile.EMPTY){
				return false;
			}
			if(matrix[p.i][p.j] == mCurrentTurn){
				return true;
			}
			else{
				boolean bool = flipDirection(direction.next(p), direction);
				if(bool){
					matrix[p.i][p.j] = mCurrentTurn;
				}
				return bool;
			}
		}

		public boolean isValidMove(int i, int j ) {
			if(i<0 || j<0 || i >= matrix.length || j >= matrix[0].length){
				return false;
			}
			if(matrix[i][j] != Tile.EMPTY){
				return false;
			}
			return checkValidAllDirections(new Pair(i, j));
		}


		private boolean checkValidAllDirections(Pair p) {
			for (Direction d : Direction.getAllDirections()) {
				if(checkDirection(p, d)){
					return true;
				}
			}
			return false;
		}

		private boolean checkDirection(Pair p, Direction direction){
			Pair next = direction.next(p);
			if(direction.hasNext(p, BOARD_SIZE) && matrix[next.i][next.j] == mCurrentTurn.opposite() ){
				while(direction.hasNext(next, BOARD_SIZE)){
					next = direction.next(next);
					if(matrix[next.i][next.j] == Tile.EMPTY){
						return false;
					}
					else if(matrix[next.i][next.j] == mCurrentTurn){
						return true;
					}
				}
			}
			return false;
		}

		public String toString(){
			StringBuilder builder = new StringBuilder();
			int rows = matrix.length;
			int columns = matrix[0].length;

			for(int i=0;i<rows;i++){
				builder.append("\t"+i);
			}

			builder.append("\t");

			for(int i=0;i<rows;i++){
				builder.append("|\n"+i+"|\t");

				for(int j=0;j<columns;j++){
					builder.append(matrix[i][j]);
					builder.append("\t");
				}
			}

			return builder.toString();
		}
	}

	public enum Tile{

		BLACK(1), WHITE(-1), EMPTY(0);

		int value;

		Tile(int x){
			value = x;
		}

		Tile opposite(){
			switch(this){
			case BLACK :
				return WHITE;
			case WHITE :
				return BLACK;
			default:
				return EMPTY;
			}
		}

		public String toString(){
			switch(this){
			case BLACK :
				return "+";
			case WHITE :
				return "-";
			default:
				return "0";
			}
		}
	}

	@Override
	public ReversiGameNode passTurn() {
		ReversiGameNode node = new ReversiGameNode(this);
		node.mCurrentTurn = mCurrentTurn.opposite();
		return node;
	}

}