
import javax.swing.*;
import java.awt.*;

public class TicTacToe extends JFrame {

    JButton[] buttons = new JButton[9];
    char[] board = new char[9];

    char human = 'X';
    char ai = 'O';

    JLabel status;

    TicTacToe() {

        setTitle("Tic Tac Toe AI");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        status = new JLabel("Your Turn (X)", JLabel.CENTER);
        status.setFont(new Font("Arial", Font.BOLD, 20));
        add(status, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(3, 3));

        for (int i = 0; i < 9; i++) {

            board[i] = ' ';
            buttons[i] = new JButton("");

            buttons[i].setFont(new Font("Arial", Font.BOLD, 40));

            final int position = i;

            buttons[i].addActionListener(e -> humanMove(position));

            panel.add(buttons[i]);
        }

        add(panel, BorderLayout.CENTER);

        JButton restart = new JButton("Restart");
        restart.addActionListener(e -> resetGame());

        add(restart, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Human move
    void humanMove(int pos) {

        if (board[pos] != ' ') return;

        board[pos] = human;
        buttons[pos].setText("X");

        if (win(human)) {
            status.setText("You Win!");
            disableButtons();
            return;
        }

        if (draw()) {
            status.setText("Game Draw!");
            return;
        }

        aiMove();
    }

    // AI move
    void aiMove() {

        int bestScore = -1000;
        int bestMove = -1;

        for (int i = 0; i < 9; i++) {

            if (board[i] == ' ') {

                board[i] = ai;

                int score = minimax(false);

                board[i] = ' ';

                if (score > bestScore) {
                    bestScore = score;
                    bestMove = i;
                }
            }
        }

        if (bestMove != -1) {

            board[bestMove] = ai;
            buttons[bestMove].setText("O");
        }

        if (win(ai)) {
            status.setText("AI Wins!");
            disableButtons();
        }
        else if (draw()) {
            status.setText("Game Draw!");
        }
        else {
            status.setText("Your Turn (X)");
        }
    }

    // Minimax Algorithm
    int minimax(boolean isMax) {

        if (win(ai)) return 1;
        if (win(human)) return -1;
        if (draw()) return 0;

        int best;

        if (isMax) {

            best = -1000;

            for (int i = 0; i < 9; i++) {

                if (board[i] == ' ') {

                    board[i] = ai;

                    best = Math.max(best, minimax(false));

                    board[i] = ' ';
                }
            }

        } else {

            best = 1000;

            for (int i = 0; i < 9; i++) {

                if (board[i] == ' ') {

                    board[i] = human;

                    best = Math.min(best, minimax(true));

                    board[i] = ' ';
                }
            }
        }

        return best;
    }

    // Check winner
    boolean win(char p) {

        int[][] w = {
            {0,1,2}, {3,4,5}, {6,7,8},
            {0,3,6}, {1,4,7}, {2,5,8},
            {0,4,8}, {2,4,6}
        };

        for (int[] a : w) {

            if (board[a[0]] == p &&
                board[a[1]] == p &&
                board[a[2]] == p) {
                return true;
            }
        }

        return false;
    }

    // Check draw
    boolean draw() {

        for (char c : board) {
            if (c == ' ') return false;
        }

        return true;
    }

    // Disable buttons
    void disableButtons() {

        for (JButton b : buttons) {
            b.setEnabled(false);
        }
    }

    // Restart game
    void resetGame() {

        for (int i = 0; i < 9; i++) {

            board[i] = ' ';
            buttons[i].setText("");
            buttons[i].setEnabled(true);
        }

        status.setText("Your Turn (X)");
    }

    public static void main(String[] args) {

        new TicTacToe();
    }
}
