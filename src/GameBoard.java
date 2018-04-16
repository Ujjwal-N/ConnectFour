

public class GameBoard {
	Piece[][] array;
	public GameBoard() {
		array = new Piece[7][6];
		for(int columnCount = 0; columnCount < array.length; columnCount++) {
			for(int rowCount = 0; rowCount < array[columnCount].length; rowCount++) {
				array[columnCount][rowCount] = new Piece(Piece.Type.EMPTY, columnCount, rowCount);
			}
		}
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
	public Piece getPieceDiff(Piece piece, int xDiff, int yDiff) {
		return array[piece.getColumn() + yDiff][piece.getRow() + xDiff];
	}
}
