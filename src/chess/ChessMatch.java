package chess;

import boardGame.Board;
import boardGame.Piece;
import boardGame.Position;
import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {

    private int turn;
    private Color currentPlayer;
    private Board board;
    private boolean check;
    private boolean checkMate = false;


    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();
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

    public boolean getCheck () {
        return check;
    }

    public boolean getCheckMate() {
        return checkMate;
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
        Piece capturedPiece = makeMove(source, target);

        if (testCheck(currentPlayer)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("Você não pode se colocar em check");
        }

        check = testCheck(opponent(currentPlayer)) ? true : false; // esta ou nao em check? (same ternary)

        if (testCheckMate(opponent(currentPlayer))) {
            checkMate = true;
        } else {
            nextTurn();
        }
        return (ChessPiece) capturedPiece;
    }

    private Piece makeMove (Position source, Position target) {
         ChessPiece p = (ChessPiece)board.removePiece(source);
         p.increaseMoveCount();
         Piece capturedPiece = board.removePiece(target);
         board.placePiece(p, target);

        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }
        return capturedPiece;
    }

    private void undoMove (Position source, Position target, Piece capturedPiece) {
        ChessPiece p = (ChessPiece)board.removePiece(target);
        p.increaseMoveCount();
        board.placePiece(p, source);

        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }
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

    // retorna a cor do oponente da cor enviada como param
    private Color opponent (Color color) {
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private ChessPiece king (Color color) {
        List<Piece> list = piecesOnTheBoard.stream().filter(piece -> ((ChessPiece)piece).getColor() == color)
                .collect(Collectors.toList());
        for (Piece p : list) {
            if (p instanceof King) {
                return (ChessPiece)p;
            }
        }
        throw new IllegalStateException("Nao ha rei da cor" + "color + no tabuleiro");
    }

//    pegar o rei da cor x em x posicao e transformar para Position "xn";
    private boolean testCheck (Color color) {
        Position kingPosition = king(color).getChessPosition().toPosition(); //
        List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(piece -> ((ChessPiece)piece).getColor()
                == opponent(color)).collect(Collectors.toList());
        for (Piece p : opponentPieces) {
            boolean[][] mat = p.possibleMoves();
            if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
                return true;
            }
        }
        return false;
    }

    private boolean testCheckMate (Color color) {
        if (!testCheck(color)) {
            return false;
        }
        List<Piece> list = piecesOnTheBoard.stream().filter(piece -> ((ChessPiece)piece).getColor()
                == color).collect(Collectors.toList());
        for (Piece p : list) {
            boolean[][] mat = p.possibleMoves();
            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getRows(); j++) {
                    if (mat[i][j]) {
                        Position source = ((ChessPiece)p).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source, target);
                        boolean testCheck = testCheck(color);
                        undoMove(source, target, capturedPiece);

                        if (!testCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void placeNewPiece (char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
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

        placeNewPiece('a',2, new Pawn(board, Color.WHITE));
        placeNewPiece('b',2, new Pawn(board, Color.WHITE));
        placeNewPiece('c',2, new Pawn(board, Color.WHITE));
        placeNewPiece('d',2, new Pawn(board, Color.WHITE));
        placeNewPiece('e',2, new Pawn(board, Color.WHITE));
        placeNewPiece('f',2, new Pawn(board, Color.WHITE));
        placeNewPiece('g',2, new Pawn(board, Color.WHITE));
        placeNewPiece('h',2, new Pawn(board, Color.WHITE));



        // black pieces

        placeNewPiece('h',8, new Rook(board, Color.BLACK));
        placeNewPiece('g',8, new Knight(board, Color.BLACK));
        placeNewPiece('f',8, new Bishop(board, Color.BLACK));
        placeNewPiece('e',8, new King(board, Color.BLACK));
        placeNewPiece('d',8, new Queen(board, Color.BLACK));
        placeNewPiece('c',8, new Bishop(board, Color.BLACK));
        placeNewPiece('b',8, new Knight(board, Color.BLACK));
        placeNewPiece('a',8, new Rook(board, Color.BLACK));

        placeNewPiece('h',7, new Pawn(board, Color.BLACK));
        placeNewPiece('g',7, new Pawn(board, Color.BLACK));
        placeNewPiece('f',7, new Pawn(board, Color.BLACK));
        placeNewPiece('e',7, new Pawn(board, Color.BLACK));
        placeNewPiece('d',7, new Pawn(board, Color.BLACK));
        placeNewPiece('c',7, new Pawn(board, Color.BLACK));
        placeNewPiece('b',7, new Pawn(board, Color.BLACK));
        placeNewPiece('a',7, new Pawn(board, Color.BLACK));

    }
}

