import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameBehavior extends JPanel {
    private Board board;
    private Piece selectedPiece;
    private int startX, startY;
    private String turn="WHITE";
    public GameBehavior(Board board) {
        this.board = board;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int col = (e.getX() - 100) / 50;
                int row = (e.getY() - 100) / 50;
                if (col >= 0 && col < 8 && row >= 0 && row < 8) {
                    handleMouseClick(row, col);
                }
            }
        });
    }
    public void resetGame(){
        this.turn="WHITE";
        board=new Board();
        board.initBoard();
        selectedPiece=null;
        repaint();
    }
    public Piece  getKing(Board board,String color) throws Exception {
        for(int i=0;i<8;++i)
            for(int j=0;j<8;++j)
            {
                if(board.getBox(i,j) instanceof King && board.getBox(i,j).getColor()==color)
                    return board.getBox(i,j);
            }
        return null;
    }
    public boolean Check(Board board, String color) throws Exception {
        Piece king = getKing(board, color);
        int xKing = king.getCoordX();
        int yKing = king.getCoordY();
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                Piece piece = board.getBox(i, j);
                if (piece != null && !piece.getColor().equals(color)) {
                    if (piece.isValidMove(board, xKing, yKing)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean Checkmate(Board board, String color) throws Exception {
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                Piece piece = board.getBox(i, j);
                if (piece != null && piece.getColor().equals(color)) {
                    for (int x = 0; x < 8; ++x) {
                        for (int y = 0; y < 8; ++y) {
                            Piece target = board.getBox(x, y);
                            if ((target == null || !target.getColor().equals(color)) && piece.isValidMove(board, x, y)) {
                                String fenBeforeMove = boardToFEN(board, color);
                                Board testBoard = fenToBoard(fenBeforeMove);
                                Piece testPiece = testBoard.getBox(i, j);
                                testPiece.movePiece(testBoard, x, y);
                                if (!Check(testBoard, color)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public void switchTurn(){
        if(this.turn=="WHITE")
            this.turn="BLACK";
        else
            this.turn="WHITE";
    }
    private void handleMouseClick(int row, int col) {
        try {
            if (selectedPiece == null) {
                selectPiece(row, col);
            } else {
                if (selectedPiece.getColor() == this.turn)
                    movePiece(row, col);
                else {
                    selectedPiece = null;
                    repaint();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public String boardToFEN(Board board, String turn) throws Exception {
        StringBuilder fen = new StringBuilder();

        for (int row = 0; row < 8; row++) {
            int emptyCount = 0;

            for (int col = 0; col < 8; col++) {
                Piece piece = board.getBox(row, col);

                if (piece == null) {
                    emptyCount++;
                } else {
                    if (emptyCount > 0) {
                        fen.append(emptyCount);
                        emptyCount = 0;
                    }
                    char symbol = getPieceSymbol(piece);
                    fen.append(symbol);
                }
            }

            if (emptyCount > 0) fen.append(emptyCount);
            if (row < 7) fen.append('/');
        }

        fen.append(" ").append(turn.equals("WHITE") ? "w" : "b");
        return fen.toString();
    }

    private char getPieceSymbol(Piece piece) {
        char symbol;
        if (piece instanceof King) symbol = 'k';
        else if (piece instanceof Queen) symbol = 'q';
        else if (piece instanceof Rook) symbol = 'r';
        else if (piece instanceof Bishop) symbol = 'b';
        else if (piece instanceof Knight) symbol = 'n';
        else symbol = 'p'; // Pawn

        return piece.getColor().equals("WHITE") ? Character.toUpperCase(symbol) : symbol;
    }
    public Board fenToBoard(String fen) {
        Board board = new Board();
        String[] parts = fen.split(" ");
        String[] rows = parts[0].split("/");

        for (int row = 0; row < 8; row++) {
            int col = 0;
            for (char c : rows[row].toCharArray()) {
                if (Character.isDigit(c)) {
                    int empty = c - '0';
                    col += empty;
                } else {
                    Piece piece = createPieceFromSymbol(c, row, col);
                    board.setBox(row, col, piece);
                    col++;
                }
            }
        }
        return board;
    }

    private Piece createPieceFromSymbol(char c, int row, int col) {
        String color = Character.isUpperCase(c) ? "WHITE" : "BLACK";
        char lower = Character.toLowerCase(c);

        if (lower == 'k') return new King(row, col, color);
        else if (lower == 'q') return new Queen(row, col, color);
        else if (lower == 'r') return new Rook(row, col, color);
        else if (lower == 'b') return new Bishop(row, col, color);
        else if (lower == 'n') return new Knight(row, col, color);
        else return new Pawn(row, col, color);
    }
    private void selectPiece(int row, int col) throws Exception {
        Piece piece = board.getBox(row, col);
        if (piece != null) {
            selectedPiece = piece;
            startX = row;
            startY = col;
            repaint();
        }
    }

    private void movePiece(int row, int col) throws Exception {
        if (selectedPiece != null) {
            int startX = selectedPiece.getCoordX();
            int startY = selectedPiece.getCoordY();
            if (selectedPiece.isValidMove(board, row, col)) {
                String fenBeforeMove = boardToFEN(board, turn);
                Board testBoard = fenToBoard(fenBeforeMove);
                Piece testPiece = testBoard.getBox(startX, startY);
                testPiece.movePiece(testBoard, row, col);
                if (Check(testBoard, turn)) {
                    selectedPiece = null;
                    repaint();
                    return;
                }
                selectedPiece.movePiece(board, row, col);
                String opponentColor = turn.equals("WHITE") ? "BLACK" : "WHITE";
                if (Check(board, opponentColor)) {
                    if (Checkmate(board, opponentColor)) {
                        repaint();
                        JOptionPane.showMessageDialog(this,"Checkmate! the "+turn+" has won");
                    } else {
                        System.out.println(opponentColor + " is in check!");
                    }
                }
                switchTurn();
            }
            selectedPiece = null;
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int tileSize = 50;
        int boardStartX = 100, boardStartY = 100;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 != 0) {
                    g.setColor(Color.LIGHT_GRAY);
                } else {
                    g.setColor(Color.DARK_GRAY);
                }
                int x = boardStartX + col * tileSize;
                int y = boardStartY + row * tileSize;
                g.fillRect(x, y, tileSize, tileSize);
            }
        }
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = null;
                try {
                    piece = board.getBox(row, col);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                if (piece != null) {
                    String nume=piece.getClass().getSimpleName()+piece.getColor()+".png";
                    Image pieceImage = new ImageIcon(getClass().getResource("/ChessPieces/" + nume)).getImage();
                    int x=100+row*tileSize;
                    int y=100+col*tileSize;
                    g.drawImage(pieceImage,y,x,tileSize,tileSize,this);
                }
            }
        }
        if (selectedPiece != null) {
            Color x;
            if(selectedPiece.getColor()!=turn)
            x = new Color(255, 0, 0, 120);
            else
             x=new Color(0,255,0,120);
            g.setColor(x);
            g.fillRect(boardStartX + startY * tileSize, boardStartY + startX * tileSize, tileSize, tileSize);
        }
    }
}
