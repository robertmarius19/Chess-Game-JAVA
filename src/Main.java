import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Chess Game");
        frame.setSize(300, 300);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.lightGray);
        Board board = new Board();
        board.initBoard();
        GameBehavior gameBehaviorPanel = new GameBehavior(board);
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new GridLayout(2, 2, 10, 10));
        playerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,10));
        JLabel player1Label = new JLabel("Player 1 (White):");
        JTextField player1Field = new JTextField();
        JLabel player2Label = new JLabel("Player 2 (Black):");
        JTextField player2Field = new JTextField();
        playerPanel.add(player1Label);
        playerPanel.add(player1Field);
        playerPanel.add(player2Label);
        playerPanel.add(player2Field);
        JPanel imagePanel=new JPanel();
        ImageIcon image= new ImageIcon("background.jpg");
        JLabel imageLabel=new JLabel(image);
        imagePanel.add(imageLabel);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton start = new JButton("Start Game");
        JButton reset = new JButton("Reset Game");
        reset.setVisible(false);

        buttonPanel.add(start);
        buttonPanel.add(reset);
        start.addActionListener(e -> {
            String player1 = player1Field.getText();
            String player2 = player2Field.getText();

            if (player1.isEmpty() || player2.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter both player names!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            frame.setSize(700,700);
            frame.setResizable(false);
            imageLabel.setVisible(false);
            frame.add(gameBehaviorPanel, BorderLayout.CENTER);
            playerPanel.setVisible(true);
            player1Field.setEditable(false);
            player2Field.setEditable(false);
            buttonPanel.remove(start);
            reset.setVisible(true);
            frame.revalidate();
            frame.repaint();
        });

        reset.addActionListener(e -> {
            frame.setSize(300,300);
            gameBehaviorPanel.resetGame();
            frame.remove(gameBehaviorPanel);
            buttonPanel.add(start);
            player1Field.setEditable(true);
            player2Field.setEditable(true);
            imageLabel.setVisible(true);
            player2Field.setText(" ");
            player1Field.setText(" ");
            reset.setVisible(false);
        });
        frame.add(playerPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(imageLabel,BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
