package boardGame;

public class Piece {
    protected Position position;
    private Board board;

    public Piece (Board board) {
        this.board = board;
        position = null; // optional
    }

    protected Board getBoard() {
        return board;
    }
}
