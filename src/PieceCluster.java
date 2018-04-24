import java.util.ArrayList;

public class PieceCluster {
	GameBoard gameBoard = new GameBoard();
	ArrayList<Piece> array = new ArrayList<Piece>();
	public PieceCluster(GameBoard gameBoard) {
		
	}
	public ArrayList<Piece> getArray() {
		return array;
	}
	public void addPiece(Piece piece) {
		array.add(piece);
	}
	public int findEmpty() {
		System.out.println(array);
		Piece lastPiece = new Piece(Piece.Type.IMAGINARY, -1 ,-1);
		for(Piece piece : array) {
			if(lastPiece.getType() == Piece.Type.IMAGINARY) {
				lastPiece = piece;
			}else {
				if(gameBoard.getPieceDiff(lastPiece, 0, 1).getColumn() != piece.getColumn()) {
					System.out.println(gameBoard.getPieceDiff(lastPiece, 0, 1));
					return gameBoard.getPieceDiff(lastPiece, 0, 1).getColumn();
				}
			}
			lastPiece = piece;
		}
		return -1;
	}
	public String toString() {
		return array.toString();
	}
}
