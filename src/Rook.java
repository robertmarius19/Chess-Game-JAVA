public class Rook extends Piece{
    Rook(int x,int y, String color){
        super(x,y,color);
    }
    @Override
    boolean isValidMove(Board board, int x, int y) throws Exception {
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            throw new  ArrayIndexOutOfBoundsException("Coordinates are out of bounds.");
        }
        int[][] b= {{0,1},{0,-1},{1,0},{-1,0}};
        for(int i=0; i<4; ++i)
        {
            int d=this.getCoordY()+b[i][0];
            int u=this.getCoordX()+b[i][1];
            while((u>=0 && u<8) && (d>=0 && d<8) && board.getBox(u,d)==null) {
                if (d == y && u == x)
                    return true;
                d = d + b[i][0];
                u = u + b[i][1];
            }
            if((u>=0&&u<8) &&(d>=0 &&d<8) && !((board.getBox(u,d)!=null && board.getBox(u,d).getColor().equals("BLACK")) ^ (this.getColor().equals("WHITE"))) )
                if(d==y && u==x)
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
        else throw new Exception("Invalid move for Rook"+this.getColor());
    }
}
