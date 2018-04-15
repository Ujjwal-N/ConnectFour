
public class Piece {
	public enum Type{
		EMPTY, OPPONENT, MINE
	}
	private final int row;
	private final int column;
	private Type type;
	public Piece(Type type, int column, int row) {
		this.type = type;
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
	public Type getType() {
		return type;
	}
	public Piece clone() {
		return new Piece(getType(), getColumn(), getRow());
	}
	public String toString() {
		if(this.type == Type.EMPTY) {
			return "EMPTY";
		}
		if(this.type == Type.OPPONENT) {
			return "OPPONENT";
		}
		return "MINE";
	}
}
