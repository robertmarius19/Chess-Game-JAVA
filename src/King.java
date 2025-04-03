public class King extends Piece {

    public King(int x, int y, String color) {
        super(x, y, color);
    }

    @Override
    boolean isValidMove(Board board, int x, int y) throws Exception {
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            throw new ArrayIndexOutOfBoundsException("Coordinates are out of bounds.");
        }
        int[][] directions = {{1, 0}, {1, 1}, {1, -1}, {0, 1}, {0, -1}, {-1, 0}, {-1, 1}, {-1, -1}};

        for (int[] direction : directions) {
            int targetX = this.getCoordX() + direction[0];
            int targetY = this.getCoordY() + direction[1];
            if (targetX == x && targetY == y) {
                Piece targetPiece = board.getBox(x, y);
                if (targetPiece == null || !targetPiece.getColor().equals(this.getColor())) {
                    return true; // Valid move or capture
                }
            }
        }

        return false;
    }

    @Override
    void movePiece(Board board, int x, int y) throws Exception {

        if (isValidMove(board, x, y)) {
            board.setBox(this.getCoordX(), this.getCoordY(), null);
            this.setCoord(x, y);
            board.setBox(x, y, this);
        } else {
            throw new Exception("Invalid move for King");
        }
    }
}