import javax.swing.*;
import java.awt.*;
import java.util.*;

public class NQueensGUI {

    static ArrayList<char[][]> solutions = new ArrayList<>();
    static int current = 0;
    static JLabel[][] cells;

    static boolean isSafe(int r, int c, int n, char[][] board) {

        for (int i = 0; i < c; i++)
            if (board[r][i] == 'Q')
                return false;

        for (int i = r, j = c; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] == 'Q')
                return false;

        for (int i = r, j = c; i < n && j >= 0; i++, j--)
            if (board[i][j] == 'Q')
                return false;

        return true;
    }

    static void solve(char[][] board, int c, int n) {

        if (c == n) {

            char[][] temp = new char[n][n];

            for (int i = 0; i < n; i++)
                temp[i] = board[i].clone();

            solutions.add(temp);
            return;
        }

        for (int i = 0; i < n; i++) {

            if (isSafe(i, c, n, board)) {

                board[i][c] = 'Q';

                solve(board, c + 1, n);

                board[i][c] = '.';
            }
        }
    }

    static void updateBoard(int n) {

        char[][] board = solutions.get(current);

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (board[i][j] == 'Q')
                    cells[i][j].setText("\u265B");
                else
                    cells[i][j].setText("");
    }

    static void createBoard(int n) {

        JFrame frame = new JFrame("N Queens Visualizer");
        frame.setSize(600, 650);
        frame.setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(n, n));
        cells = new JLabel[n][n];

        for (int i = 0; i < n; i++) {

            for (int j = 0; j < n; j++) {

                cells[i][j] = new JLabel("", SwingConstants.CENTER);
                cells[i][j].setFont(new Font("Segoe UI Symbol", Font.BOLD, 40));
                cells[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));

                if ((i + j) % 2 == 0)
                    cells[i][j].setBackground(Color.WHITE);
                else
                    cells[i][j].setBackground(Color.LIGHT_GRAY);

                cells[i][j].setOpaque(true);

                boardPanel.add(cells[i][j]);
            }
        }

        JButton next = new JButton("Next Solution");

        next.addActionListener(e -> {

            current++;

            if (current >= solutions.size())
                current = 0;

            updateBoard(n);
        });

        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(next, BorderLayout.SOUTH);

        updateBoard(n);

        frame.setVisible(true);
    }

    public static void main(String[] args) {

        String input = JOptionPane.showInputDialog("Enter N value");

        int n = Integer.parseInt(input);

        char[][] board = new char[n][n];

        for (int i = 0; i < n; i++)
            Arrays.fill(board[i], '.');

        solve(board, 0, n);

        if (solutions.size() > 0)
            createBoard(n);
        else
            JOptionPane.showMessageDialog(null, "No Solution");
    }
}