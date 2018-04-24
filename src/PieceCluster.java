import java.util.ArrayList;

public class PieceCluster {
	GameBoard gameBoard;
	ArrayList<Piece> array = new ArrayList<Piece>();
	public PieceCluster(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}
	public ArrayList<Piece> getArray() {
		return array;
	}
	public void addPiece(Piece piece) {
		array.add(piece);
	}
	public int findEmpty() {
		System.out.println("HEREE2");
		Piece lastPiece = new Piece(Piece.Type.IMAGINARY, -1 ,-1);
		for(Piece piece : array) {
			if(lastPiece.getType() == Piece.Type.IMAGINARY) {
				lastPiece = piece;
			}else {
				if(gameBoard.getPieceDiff(lastPiece, 0, 1).getColumn() != piece.getColumn()) {
					gameBoard.printBoard();
					System.out.println(gameBoard.getPieceDiff(lastPiece, 1, 1));
					if((gameBoard.getPieceDiff(lastPiece, 1, 1).isReal()) || piece.getRow() == 5) {
						System.out.println("HEREE4");
						return gameBoard.getPieceDiff(lastPiece, 0, 1).getColumn();
					}
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
