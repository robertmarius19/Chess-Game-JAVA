abstract class Piece {
    private int coordX;
    private int coordY;
    private String color;
    Piece(int x,int y, String color){
        this.coordX=x;
        this.coordY=y;
        this.color=color;
    }
    abstract boolean isValidMove(Board board,int x,int y) throws Exception;
    abstract void movePiece(Board board,int x,int y) throws Exception;

    public int getCoordY() {
        return this.coordY;
    }

    public int getCoordX() {
        return this.coordX;
    }

    public void setCoord(int coordX,int coordY) {
        this.coordX = coordX;
        this.coordY=coordY;
    }

    public String getColor() {
        return this.color;
    }
    public void setColor(String x)
    {
        this.color=x;
    }
}
