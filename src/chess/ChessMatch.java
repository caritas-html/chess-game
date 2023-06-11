package chess;

import boardGame.Board;
import boardGame.Piece;
import boardGame.Position;
import chess.pieces.*;

public class ChessMatch {

    private int turn;
    private Color currentPlayer;
    private Board board;
    public ChessMatch () {
        board = new Board(9, 9);
        turn = 1;
        currentPlayer = Color.WHITE;
        initialSetup();
    }

    public int getTurn () {
        return turn;
    }

    public Color getCurrentPlayer () {
        return currentPlayer;
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

    public boolean[][] possibleMoves (ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece CapturedPiece = makeMove(source, target);
        nextTurn();
        return (ChessPiece) CapturedPiece;
    }

    private Piece makeMove(Position source, Position target) {
        Piece p = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);
        return capturedPiece;
    }

    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) {
            throw new ChessException("Nao ha peca na posicao de origem");
        }
        if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {
            throw new ChessException("A peca escolhida nao eh sua");
        }
        if (!board.piece(position).isThereAnyPossibleMove()) {
            throw new ChessException("Nao existe movimentos possiveis para a peca");
        }
    }

    private void validateTargetPosition (Position source, Position target) {
        if (!board.piece(source).possibleMove(target)) {
            throw new ChessException("A pessa escolhida nao pode se mover para a casa de destino");
        }
    }

    private void nextTurn() {
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
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

        placeNewPiece('h',3, new Rook(board, Color.BLACK));
        placeNewPiece('g',3, new Knight(board, Color.BLACK));
        placeNewPiece('f',3, new Bishop(board, Color.BLACK));
        placeNewPiece('e',3, new Queen(board, Color.BLACK));
        placeNewPiece('d',3, new King(board, Color.BLACK));
        placeNewPiece('c',3, new Bishop(board, Color.BLACK));
        placeNewPiece('b',3, new Knight(board, Color.BLACK));
        placeNewPiece('a',3, new Rook(board, Color.BLACK));

    }
}

