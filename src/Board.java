public class Board implements Cloneable{
    private Piece[][] board = new Piece[8][8];

    public Piece getBox(int x, int y) throws Exception {
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            throw new Exception("Index out of bound");
        }
        return this.board[x][y];
    }
    public Board(){

    }
    public Board(Board other) {
        board = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (other.board[i][j] != null) {
                    board[i][j] = other.board[i][j];
                }
            }
        }
    }
    public void setBox(int x,int y,Piece p)
    {
        this.board[x][y]=p;
    }

    public void initBoard() {
        this.board[0][0] = new Rook(0, 0, "WHITE");
        this.board[0][1] = new Knight(0, 1, "WHITE");
        this.board[0][2] = new Bishop(0, 2, "WHITE");
        this.board[0][3] = new Queen(0, 3, "WHITE");
        this.board[0][4] = new King(0, 4, "WHITE");
        this.board[0][5] = new Bishop(0, 5, "WHITE");
        this.board[0][6] = new Knight(0, 6, "WHITE");
        this.board[0][7] = new Rook(0, 7, "WHITE");
        for (int j = 0; j < 8; j++) {
            this.board[1][j] = new Pawn(1, j, "WHITE"); // White pawns
        }
        this.board[7][0] = new Rook(7, 0, "BLACK");
        this.board[7][1] = new Knight(7, 1, "BLACK");
        this.board[7][2] = new Bishop(7, 2, "BLACK");
        this.board[7][3] = new Queen(7, 3, "BLACK");
        this.board[7][4] = new King(7, 4, "BLACK");
        this.board[7][5] = new Bishop(7, 5, "BLACK");
        this.board[7][6] = new Knight(7, 6, "BLACK");
        this.board[7][7] = new Rook(7, 7, "BLACK");
        for (int j = 0; j < 8; j++) {
            this.board[6][j] = new Pawn(6, j, "BLACK");
        }

        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                this.board[i][j] = null; // Empty squares
            }
        }
    }
}


