package chess;

import boardGame.Board;
import boardGame.Position;
import chess.pieces.*;

public class ChessMatch {
    private Board board;
    public ChessMatch () {
        board = new Board(8, 8);
        initialSetup();
    }

    public ChessPiece[][] getPieces () {
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                mat[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return mat;
    }

    private void placeNewPiece (char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
    }

    private void initialSetup () {
        // white pieces
        placeNewPiece('a',1, new Rook(board, Color.WHITE));
        placeNewPiece('b',1, new Knight(board, Color.WHITE));
        placeNewPiece('c',1, new Bishop(board, Color.WHITE));
        placeNewPiece('d',1, new Queen(board, Color.WHITE));
        placeNewPiece('e',1, new King(board, Color.WHITE));
        placeNewPiece('f',1, new Bishop(board, Color.WHITE));
        placeNewPiece('g',1, new Knight(board, Color.WHITE));
        placeNewPiece('h',1, new Rook(board, Color.WHITE));

        // black pieces

        placeNewPiece('h',8, new Rook(board, Color.BLACK));
        placeNewPiece('g',8, new Knight(board, Color.BLACK));
        placeNewPiece('f',8, new Bishop(board, Color.BLACK));
        placeNewPiece('e',8, new Queen(board, Color.BLACK));
        placeNewPiece('d',8, new King(board, Color.BLACK));
        placeNewPiece('c',8, new Bishop(board, Color.BLACK));
        placeNewPiece('b',8, new Knight(board, Color.BLACK));
        placeNewPiece('a',8, new Rook(board, Color.BLACK));

    }
}

