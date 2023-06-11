package boardGame;

public class Board {
    private int rows;
    private int columns;
    private Piece[][] pieces;

    public Board (int rows, int columns) {
        if (rows < 1 || columns < 1) {
            throw new BoardException("o tabuleiro deve haver ao menos 1 linha e coluna");
        }
        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns]; // instanciaciao das pecas com os numeros das colunas e linhas
    }

    public int getRows () {
        return rows;
    }

    public int getColumns () {
        return columns;
    }

    public Piece piece (int row, int column) {
        if (!positionExists(row, column)) {
            throw new BoardException("Position náo está no tabuleiro");
        }
        return pieces[row][column];
    }

    public Piece piece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Position nao esta no tabuleiro");
        }
        return pieces[position.getRow()][position.getColumn()];
    }

    public void placePiece (Piece piece, Position position) {
        if (thereIsAPiece(position)) {
            throw new BoardException("tem uma peca nesta possicao" + position);
        }
        pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }

    public Piece removePiece (Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Posicao nao esta no board");
        }
        if (piece(position) == null) {
            return null;
        }
        Piece aux = piece(position);
        aux.position = null;
        pieces[position.getRow()][position.getColumn()] = null;
        return aux;
    }

    private boolean positionExists(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    public boolean positionExists(Position position) {
        return positionExists(position.getRow(), position.getColumn());
    }

    public boolean thereIsAPiece (Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Nao ha esta posicao no tabuleiro");
        }
        return piece(position) != null;
    }
}

