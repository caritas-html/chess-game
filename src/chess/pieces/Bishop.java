package chess.pieces;

import boardGame.Board;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece {
    public Bishop (Board board, Color color) {
        super(board, color);
    }
    @Override
    public String toString() {
        char[] unicode = Character.toChars(9815);
        String unicodeString = new String(unicode);
        return "" + unicodeString + " ";

    }
}
