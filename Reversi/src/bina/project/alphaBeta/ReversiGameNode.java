package bina.project.alphaBeta;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Comparator;

import bina.project.alphaBeta.ReversiGameNode.Tile;

public class ReversiGameNode implements GameNode{

	public static int BOARD_SIZE = 8;

	private Board mBoard;

	private Tile mCurrentTurn;

	private Integer mValue;

	private List<GameNode> mChildMoves;
	
	private Comparator<GameNode> mComparator = new Comparator<GameNode>() {
		@Override
		public int compare(GameNode o1, GameNode o2) {
			return o2.getHeuristicEval() - o1.getHeuristicEval();
		}
	}; 
	
	private int mHuristicEval;//TEMP

	private ReversiGameNode(){
		mBoard = new Board();
		mCurrentTurn = Tile.BLACK;
		
//		mHuristicEval = getHeuristicEval();
	}

	private ReversiGameNode(ReversiGameNode move){
		mBoard = new Board(move.mBoard);
		mCurrentTurn = move.mCurrentTurn;
	}

	public static GameNode getInitalMove(){
		ReversiGameNode node = new ReversiGameNode();
		node.calcHeuristicEval();
		return node;
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
		mChildMoves = moves;//mBoard  mCurrentTurn this
		
		Collections.sort(mChildMoves, mComparator);
	}
	
	@Override
	public ReversiGameNode playTurn(int i, int j) {
		if(!mBoard.isValidMove(i, j)){
			throw new RuntimeException("coordinats not valid");
		}

		ReversiGameNode newMove = new ReversiGameNode(this);

		newMove.mBoard.placeTile(i,j);

		newMove.mCurrentTurn = mCurrentTurn.opposite();
		
//		newMove.mHuristicEval = newMove.getHeuristicEval();
		newMove.calcHeuristicEval();
		
		return newMove;
	}

	@Override
	public boolean isLeaf() {
		return getAllLegalMoves().size() == 0;
	}

	@Override
	public int getHeuristicEval() {
		return mHuristicEval;
	}
	
	public void calcHeuristicEval() {
		 mHuristicEval = HeuristicEval.dynamic_heuristic_evaluation_function(this);
	}
	
	@Override
	public int getScore() {
		int sum = 0;
		for (int i = 0; i < mBoard.matrix.length; i++) {
			for (int j = 0; j < mBoard.matrix[0].length; j++) {
				sum+= mBoard.matrix[i][j].value;
			}
		}
		return sum;
	}

	@Override
	public void printState() {
		System.out.println(mBoard.toString());
	}


	@Override
	public Integer getValue() {
		return mValue;
	}

	@Override
	public void setValue(int value) {
		mValue = value;
	}

	@Override
	public ReversiGameNode passTurn() {
		ReversiGameNode node = new ReversiGameNode(this);
		node.mCurrentTurn = mCurrentTurn.opposite();
//		node.mHuristicEval = node.getHeuristicEval();
		node.calcHeuristicEval();
		return node;
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
				if(d.hasNext(p, BOARD_SIZE)){
					flipDirection(d.next(p), d);
				}
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
				if(!direction.hasNext(p, BOARD_SIZE)){
					return false;
				}
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
			if(!direction.hasNext(p, BOARD_SIZE)){
				return false;
			}
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

	public Tile[][] getBoardMatrix() {
		return mBoard.matrix;
	}


}