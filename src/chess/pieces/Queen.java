package chess.pieces;

import boardGame.Board;
import chess.ChessPiece;
import chess.Color;

public class Queen extends ChessPiece {
    public Queen (Board board, Color color) {
        super(board, color);
    }
    @Override
    public String toString() {
        char[] unicode = Character.toChars(9813);
        String unicodeString = new String(unicode);
        return " " + unicodeString + "";

    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        return mat;
    }
}
