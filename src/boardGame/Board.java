package boardGame;

public class Board {
    private int rows;
    private int columns;
    private piece[][] Pieces;

    public Board (int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns]; // instanciaciao das pecas com os numeros das colunas e linhas
    }

    public int getRows () {
        return rows;
    }
}
