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
		System.out.println(opponentPieces);
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
			System.out.println(i);
			if(i != 5) {
				Piece nextPiece = getPieceDiff(array[column][i],1,0);
				if(!(nextPiece.getType() == Piece.Type.EMPTY)){
					return thisPiece.getRow();
				}
			}
		}
		return 5;
	}
	public int threatDetect(){
		return -1;
	}
	public Piece getPieceDiff(Piece piece, int xDiff, int yDiff) {
		return array[piece.getColumn() + yDiff][piece.getRow() + xDiff];
	}
}
