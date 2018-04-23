import java.util.ArrayList;
import java.util.Arrays;

public class GameBoard {
	Piece[][] array;
	Piece[][] rowArray;
	ArrayList<Piece> opponentPieces = new ArrayList<Piece>();
	public GameBoard() {
		array = new Piece[7][6];
		rowArray = new Piece[6][7];
		for(int columnCount = 0; columnCount < array.length; columnCount++) {
			for(int rowCount = 0; rowCount < array[columnCount].length; rowCount++) {
				array[columnCount][rowCount] = new Piece(Piece.Type.EMPTY, columnCount, rowCount);
			}
		}
		for(int rowXCount = 0; rowXCount < rowArray.length; rowXCount++) {
			for(int columnXCount = 0; columnXCount < rowArray[rowXCount].length; columnXCount++) {
				rowArray[rowXCount][columnXCount] = new Piece(Piece.Type.EMPTY, columnXCount, rowXCount);
			}
		}
	}
	public void updateOpponentPieceArray(Piece opponentPiece){
		opponentPieces.add(opponentPiece.clone());
		//System.out.println(opponentPieces);
		//((Appendable) opponentPieces).append(opponentPiece.clone());
	}
	public void playMove(Piece piece) {
		array[piece.getColumn()][piece.getRow()] = piece.clone();
		rowArray[piece.getRow()][piece.getColumn()] =  piece.clone();
	}
	public Piece[][] getArray(){
		return array;
	}
	public int getLowestRow(int column) {
		for(int i = 0; i < array[column].length; i++) {
			Piece thisPiece = array[column][i];
			//System.out.println(i);
			Piece nextPiece = getPieceDiff(array[column][i],1,0);
			if(nextPiece.isReal()){
				return thisPiece.getRow();
			}
		}
		return 5;
	}
	private int getSmallestPiece(ArrayList<Piece> allPiece){
		int lowestPiece = 0;
		for(int piece = 0; piece < allPiece.size(); piece++) {
			if(allPiece.get(piece).getRow() < allPiece.get(lowestPiece).getRow()) {
				lowestPiece =  piece;
			}
			if(allPiece.get(piece).getRow() == allPiece.get(lowestPiece).getRow()) {
				if(allPiece.get(piece).getColumn() < allPiece.get(lowestPiece).getColumn()) {
					lowestPiece = piece;
				}
			}
		}
		return lowestPiece;
	}
	private Piece[][] rowColumn(){
		ArrayList<Piece> allPiece = new ArrayList<Piece>();
		Piece[][] retArray = new  Piece[6][7];
		for(Piece[] column : array) {
			for(Piece row : column) {
				allPiece.add(row);
			}
		}
		ArrayList<Piece> sortedPiece = new ArrayList<Piece>();
		while(sortedPiece.size() < 42) {
			int lowestPiece = getSmallestPiece(allPiece);
			sortedPiece.add(allPiece.get(lowestPiece));
			allPiece.remove(lowestPiece);
		}
		for(int i = 0; i < 6; i++){
			for(int ix = 0; ix < 7; ix++){
				int row = (i * 7) + ix;
				retArray[i][ix] = sortedPiece.get(row);
			}
		}
		return retArray;
	}
	private int rowAnalyzer(int row) {
//		for(Piece[] rowx : rowArray) {
//			System.out.println(Arrays.toString(rowx));
//		}
		Piece lastPiece = new Piece(Piece.Type.IMAGINARY, -1, -1);
		int distance = 0;
		PieceCluster pc = new PieceCluster(this);
		ArrayList<PieceCluster> clusters = new ArrayList<PieceCluster>();
		for(Piece piece : rowArray[row]) {
//			System.out.println();
//			System.out.println(lastPiece);
//			System.out.println(piece);
			if(lastPiece.getType() == Piece.Type.IMAGINARY) {
				if(piece.getType() == Piece.Type.OPPONENT) {
					//System.out.println("HERE");
					lastPiece = piece;
					pc.addPiece(piece);
				}
			}else {
				if(piece.getType() == Piece.Type.MINE) {
					if(pc.getArray().size() > 0) {
						clusters.add(pc);
					}
					pc = new PieceCluster(this);
					distance = -1000;
				}
				if(piece.getType() == Piece.Type.EMPTY) {
					if(lastPiece.getType() == Piece.Type.OPPONENT) {
						distance = 1;
					}else {
						if(pc.getArray().size() > 0) {
							clusters.add(pc);
						}
						pc = new PieceCluster(this);
					}
				}
				if(piece.getType() == Piece.Type.OPPONENT) {
					pc.addPiece(piece);
					if(piece.getColumn() == 6) {
						//System.out.println("HEREE");
						if(pc.getArray().size() > 0) {
							clusters.add(pc);
						}
					}
					distance = 0;
				}
				lastPiece = piece;
			}
		}
		for(PieceCluster cluster : clusters) {
			if(pc.getArray().size() > 2) {
				int empty = pc.findEmpty();
				if(empty != -1) {
					return empty;
				}
			}
			System.out.println(cluster);
		}
//		Boolean pursueThreat = true;
		


		//System.out.println(pursueThreat);
		return -1;
	}
	public int threatDetect(){
		for(Piece opponentPiece : opponentPieces) {
			//TOP CHECK
			if(getPieceDiff(opponentPiece, -1, 0).getType() == Piece.Type.OPPONENT) {
				if(getPieceDiff(opponentPiece, -2, 0).getType() == Piece.Type.OPPONENT) {
					if(getPieceDiff(opponentPiece, -3, 0).getType() == Piece.Type.EMPTY) {
						return opponentPiece.getColumn();
					}
				}
			}
			//RIGHT CONSECUTIVE CHECK
			if(getPieceDiff(opponentPiece, 0, 1).getType() == Piece.Type.OPPONENT) {
				if(getPieceDiff(opponentPiece, 0, 2).getType() == Piece.Type.OPPONENT) {
					if(getPieceDiff(opponentPiece, 0, 3).getType() == Piece.Type.EMPTY) {
						//System.out.println(getPieceDiff(opponentPiece, 1, 3).isReal());
						if(getPieceDiff(opponentPiece, 1, 3).isReal() || opponentPiece.getRow() == 5) {
							return getPieceDiff(opponentPiece, 0, 3).getColumn();
						}
					}
					
				}
			}
			//LEFT CONSECUTIVE CHECK
			if(getPieceDiff(opponentPiece, 0, -1).getType() == Piece.Type.OPPONENT) {
				if(getPieceDiff(opponentPiece, 0, -2).getType() == Piece.Type.OPPONENT) {
					if(getPieceDiff(opponentPiece, 0, -3).getType() == Piece.Type.EMPTY) {
						//System.out.println(getPieceDiff(opponentPiece, 1, 3).isReal());
						if(getPieceDiff(opponentPiece, 1, -3).isReal() || opponentPiece.getRow() == 5) {
							return getPieceDiff(opponentPiece, 0, -3).getColumn();
						}
					}
					
				}
			}
		}
		//SIDE CENTER CHECK
		for(Piece[] row : rowArray) {
			int opponentPieces = 0;
			for(Piece piece : row) {
				if(piece.getType() == Piece.Type.OPPONENT) {
					opponentPieces++;
				}
			}
			if((opponentPieces >= 3)) {
				int potentialThreat = rowAnalyzer(row[0].getRow());
				if(potentialThreat != -1) {
					return potentialThreat;
				}
			}
		}
		return -1;
	}
	
	public Piece getPieceDiff(Piece piece, int xDiff, int yDiff) {
		try {
		   return array[piece.getColumn() + yDiff][piece.getRow() + xDiff];
		}
		catch(ArrayIndexOutOfBoundsException exception) {
			//System.out.println("HERE");
		    return new Piece(Piece.Type.IMAGINARY, -1, -1);
		}
		
	}
}
