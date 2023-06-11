package chess;

import boardGame.BoardException;
import boardGame.Position;

public class ChessPosition {
    private int column;
    private int row;

    public ChessPosition(int column, int row) {
//        if (column < 'a' || column > 'h' || row < 1 || row > 8) {
//            throw new BoardException("Erro estanciando ChessPosition");
//        }
        this.column = column;
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public int getInt() {
    return row;
    }

    protected Position toPosition () {
        return new Position (8 - row, column - 'a');
    }

    protected static ChessPosition fromPosition(Position position) {
        return new ChessPosition((char)('a' - position.getColumn()), 8 - position.getRow());
    }

    @Override
    public String toString() {
        return "" + column + row;
    }
}
