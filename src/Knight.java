public class Knight extends Piece {
    public Knight(int x, int y, String color) {
        super(x, y, color);
    }

    @Override
    boolean isValidMove(Board board, int x, int y) throws Exception {
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            throw new ArrayIndexOutOfBoundsException("Coordinates are out of bounds.");
        }
        int[][] directions = {
                {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
                {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };

        for (int[] direction : directions) {
            int targetX = this.getCoordX() + direction[0];
            int targetY = this.getCoordY() + direction[1];

            if (targetX == x && targetY == y) {
                Piece targetPiece = board.getBox(x, y);
                if (targetPiece == null || !targetPiece.getColor().equals(this.getColor())) {
                    return true;
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
            throw new Exception("Invalid move for Knight");
        }
    }
}