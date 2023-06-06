package chess.pieces;

import boardGame.Board;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {
    public Rook (Board board, Color color) {
        super(board, color);
    }
    @Override
    public String toString() {
        char[] unicode = Character.toChars(9814);
        String unicodeString = new String(unicode);
        return "" + unicodeString + " ";

    }
}


