
public class Piece {
	public enum Type{
		EMPTY, OPPONENT, MINE, IMAGINARY
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
		//this a test comment
		String retString = "||";
		if(this.type == Type.EMPTY) {
			retString += "Empty";
		}else if(this.type == Type.OPPONENT) {
			retString += "Opponent";
		}else if(this.type == Type.MINE){
			retString += "Mine";
		}else{
			retString += "Imaginary";
		}
		retString += "|Column: " + column + "|Row: " + row + "||"; 
		return retString;
	}
	public Boolean isReal() {
		return (type == Piece.Type.MINE || type == Piece.Type.OPPONENT);
	}
}
