public class Pawn extends Piece{
    Pawn(int x,int y, String color){
        super(x,y,color);
    }

    @Override
    boolean isValidMove(Board board, int x, int y) throws Exception {
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            throw new ArrayIndexOutOfBoundsException("Coordinates are out of bounds.");
        }
        int direction = this.getColor().equals("WHITE") ? 1 : -1;

        // Move one step forward
        if (board.getBox(x, y) == null && x == this.getCoordX() + direction && y == this.getCoordY()) {
            return true;
        }
        else if (this.getCoordX() == (this.getColor().equals("WHITE") ? 1 : 6)
                && board.getBox(x, y) == null
                && x == this.getCoordX() + 2 * direction
                && y ==this.getCoordY()
                && board.getBox(x - direction, y) == null) {
            return true;
        }
        else if (board.getBox(x, y) != null && board.getBox(x, y).getColor() != this.getColor()
                && Math.abs(x - this.getCoordX()) == 1 && Math.abs(y - this.getCoordY()) == 1
                && ((this.getColor().equals("WHITE") && x > this.getCoordX()) || (this.getColor().equals("BLACK") && x < this.getCoordX()))) {
            return true;
        }
        return false;
    }
    @Override
    void movePiece(Board board, int x, int y) throws Exception {
        if(isValidMove(board,x,y)==true){
            board.setBox(this.getCoordX(),this.getCoordY(),null);
            this.setCoord(x,y);
            board.setBox(this.getCoordX(),this.getCoordY(),this);
        }
        else throw new Exception("Invalid move for pawn");
    }
}
