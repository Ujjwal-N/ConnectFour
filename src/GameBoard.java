import java.util.ArrayList;

public class GameBoard {
	Piece[][] array;
	ArrayList<Piece> opponentPieces = new ArrayList<Piece>();
	public GameBoard() {
		array = new Piece[7][6];
		for(int columnCount = 0; columnCount < array.length; columnCount++) {
			for(int rowCount = 0; rowCount < array[columnCount].length; rowCount++) {
				array[columnCount][rowCount] = new Piece(Piece.Type.EMPTY, columnCount, rowCount);
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
	public int threatDetect(){
		//System.out.println("THREAT");
		for(Piece opponentPiece : opponentPieces) {
			//TOP CHECK
			if(getPieceDiff(opponentPiece, -1, 0).getType() == Piece.Type.OPPONENT) {
				if(getPieceDiff(opponentPiece, -2, 0).getType() == Piece.Type.OPPONENT) {
					if(getPieceDiff(opponentPiece, -3, 0).getType() == Piece.Type.EMPTY) {
						return opponentPiece.getColumn();
					}
				}
			}
			//RIGHT CHECK
			if(getPieceDiff(opponentPiece, 0, 1).getType() == Piece.Type.OPPONENT) {
				if(getPieceDiff(opponentPiece, 0, 2).getType() == Piece.Type.OPPONENT) {
					if(getPieceDiff(opponentPiece, 0, 3).getType() == Piece.Type.EMPTY) {
						System.out.println(getPieceDiff(opponentPiece, 1, 3).isReal());
						if(getPieceDiff(opponentPiece, 1, 3).isReal() || opponentPiece.getRow() == 5) {
							return getPieceDiff(opponentPiece, 0, 3).getColumn();
						}
					}
					
				}
			}
			
			//LEFT CHECK
//			if(getPieceDiff(opponentPiece, 0, -1).getType() == Piece.Type.OPPONENT) {
//				if(getPieceDiff(opponentPiece, 0, -2).getType() == Piece.Type.OPPONENT) {
//					//System.out.println(opponentPiece);
//					if(getPieceDiff(opponentPiece, 0, -3).getType() != Piece.Type.IMAGINARY) {
//						if(getPieceDiff(opponentPiece, -1, -3).isReal() || opponentPiece.getRow() == 5) {
//							return getPieceDiff(opponentPiece, 0, -3).getColumn();
//						}
//					}
//					
//				}
//			}
//			if(getPieceDiff(opponentPiece, 1, 1).getType() == Piece.Type.OPPONENT) {
//				if(getPieceDiff(opponentPiece, 2, 2).getType() == Piece.Type.OPPONENT) {
//					
//				}
//			}
			
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
