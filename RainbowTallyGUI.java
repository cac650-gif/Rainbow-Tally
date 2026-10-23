
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class RainbowTallyGUI extends JFrame {
    private Team[] teams;
    private Game game;

    private JLabel[] scoreLabels;
    private JLabel[] tallyLabels;
    private JLabel[] teamStatusLabels;
    private JLabel statusLabel;

    private int winningScore;
    private boolean gameOver = false;

    // A different color for each team
    private Color[] teamColors = {
        new Color(255, 220, 230),
        new Color(215, 235, 255),
        new Color(225, 245, 215),
        new Color(255, 235, 190),
        new Color(225, 215, 255)
    };

    public RainbowTallyGUI() {
        setTitle("Rainbow Tally");

        // Ask the user to set up the game
        if (setupGame()) {
            buildScoreboard();
        } else {
            dispose();
        }
    }

    // Gets the team names and winning score from the user
    private boolean setupGame() {
        String[] teamChoices = {"2", "3", "4", "5"};

        String teamChoice = (String) JOptionPane.showInputDialog(
            null,
            "How many teams are playing?",
            "Welcome to Rainbow Tally!",
            JOptionPane.QUESTION_MESSAGE,
            null,
            teamChoices,
            "2"
        );

        if (teamChoice == null) {
            return false;
        }

        int numberOfTeams = Integer.parseInt(teamChoice);
        teams = new Team[numberOfTeams];

        for (int i = 0; i < numberOfTeams; i++) {
            String name;

            while (true) {
                name = JOptionPane.showInputDialog(
                    null,
                    "Enter a name for Team " + (i + 1) + ":"
                );

                if (name == null) {
                    return false;
                }

                name = name.trim();

                if (!name.isEmpty()) {
                    break;
                }

                JOptionPane.showMessageDialog(
                    null,
                    "Please enter a team name!"
                );
            }

            teams[i] = new Team(name);
        }

        String[] scoreChoices = {"5", "10", "15", "20"};

        String scoreChoice = (String) JOptionPane.showInputDialog(
            null,
            "How many points are needed to win?",
            "Choose the Winning Score",
            JOptionPane.QUESTION_MESSAGE,
            null,
            scoreChoices,
            "10"
        );

        if (scoreChoice == null) {
            return false;
        }

        winningScore = Integer.parseInt(scoreChoice);
        game = new Game(teams);

        return true;
    }

    // Creates the scoreboard and its buttons
    private void buildScoreboard() {
        scoreLabels = new JLabel[teams.length];
        tallyLabels = new JLabel[teams.length];
        teamStatusLabels = new JLabel[teams.length];

        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(
            new BoxLayout(mainPanel, BoxLayout.Y_AXIS)
        );
        mainPanel.setBackground(new Color(255, 250, 245));
        mainPanel.setBorder(
            new EmptyBorder(18, 25, 18, 25)
        );

        JLabel titleLabel = new JLabel("Rainbow Tally");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(135, 75, 190));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        JLabel subtitleLabel = new JLabel(
            "Every point brings you closer to the rainbow!"
        );
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(subtitleLabel);

        mainPanel.add(Box.createVerticalStrut(15));

        // Create one scoreboard panel for each team
        for (int i = 0; i < teams.length; i++) {
            final int teamIndex = i;

            JPanel teamPanel = new JPanel(new BorderLayout(8, 5));
            teamPanel.setBackground(teamColors[i]);
            teamPanel.setBorder(new EmptyBorder(10, 12, 10, 12));
            teamPanel.setMaximumSize(new Dimension(650, 115));

            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(
                new BoxLayout(infoPanel, BoxLayout.Y_AXIS)
            );
            infoPanel.setOpaque(false);

            JLabel nameLabel = new JLabel(teams[i].getName());
            nameLabel.setFont(new Font("Arial", Font.BOLD, 18));

            scoreLabels[i] = new JLabel("Score: 0");
            scoreLabels[i].setFont(
                new Font("Arial", Font.BOLD, 15)
            );

            tallyLabels[i] = new JLabel("Tallies: -");
            tallyLabels[i].setFont(
                new Font("Monospaced", Font.PLAIN, 15)
            );

            teamStatusLabels[i] = new JLabel("Ready to play!");

            infoPanel.add(nameLabel);
            infoPanel.add(scoreLabels[i]);
            infoPanel.add(tallyLabels[i]);
            infoPanel.add(teamStatusLabels[i]);

            JPanel buttonPanel = new JPanel(
                new GridLayout(2, 1, 5, 5)
            );
            buttonPanel.setOpaque(false);

            JButton addButton = new JButton("+ Point");
            JButton undoButton = new JButton("Undo");

            // Add one point when clicked
            addButton.addActionListener(e -> {
                if (!gameOver) {
                    teams[teamIndex].addPoint();
                    updateScores();
                }
            });

            // Remove one point when clicked
            undoButton.addActionListener(e -> {
                if (!gameOver) {
                    teams[teamIndex].undoPoint();
                    updateScores();
                }
            });

            buttonPanel.add(addButton);
            buttonPanel.add(undoButton);

            teamPanel.add(infoPanel, BorderLayout.CENTER);
            teamPanel.add(buttonPanel, BorderLayout.EAST);

            mainPanel.add(teamPanel);
            mainPanel.add(Box.createVerticalStrut(8));
        }

        statusLabel = new JLabel(
            "First team to " + winningScore + " points wins!"
        );
        statusLabel.setFont(
            new Font("Arial", Font.BOLD, 16)
        );
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(statusLabel);

        mainPanel.add(Box.createVerticalStrut(10));

        JButton newGameButton = new JButton("Start a New Game");
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        newGameButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(
                () -> new RainbowTallyGUI()
            );
        });

        mainPanel.add(newGameButton);

        // Add a scrollbar when the scoreboard is too tall
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );
        scrollPane.setHorizontalScrollBarPolicy(
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane);

        updateScores();
        setVisible(true);
    }

    // Converts the score into tally marks grouped by five
    private String makeTallies(int score) {
        String tallies = "";

        for (int i = 0; i < score / 5; i++) {
            tallies += "||||/ ";
        }

        for (int i = 0; i < score % 5; i++) {
            tallies += "|";
        }

        if (score == 0) {
            return "-";
        }

        return tallies;
    }

    // Updates the scoreboard after a point is added or removed
    private void updateScores() {
        for (int i = 0; i < teams.length; i++) {
            scoreLabels[i].setText(
                "Score: " + teams[i].getScore()
            );

            tallyLabels[i].setText(
                "Tallies: " + makeTallies(teams[i].getScore())
            );

            teamStatusLabels[i].setText(" ");
            teamStatusLabels[i].setForeground(Color.BLACK);
        }

        if (!gameOver
                && !game.isTie()
                && game.getLeader().getScore() >= winningScore) {
            gameOver = true;
            showWinner();
            return;
        }

        if (game.isTie()) {
            statusLabel.setText("It's a tie! Keep scoring!");
            statusLabel.setForeground(new Color(190, 90, 140));

            for (int i = 0; i < teams.length; i++) {
                if (teams[i].getScore()
                        == game.getLeader().getScore()) {
                    teamStatusLabels[i].setText("TIED FOR LEAD!");
                }
            }
        } else {
            Team leader = game.getLeader();

            statusLabel.setText(
                "Current Leader: " + leader.getName()
            );
            statusLabel.setForeground(new Color(100, 70, 170));

            for (int i = 0; i < teams.length; i++) {
                if (teams[i] == leader) {
                    teamStatusLabels[i].setText("IN THE LEAD!");
                    teamStatusLabels[i].setForeground(
                        new Color(100, 70, 170)
                    );
                }
            }
        }
    }

    // Announces the winning team
   
    private void showWinner() {
        Team winner = game.getLeader();

        statusLabel.setText(
            "HOORAY! " + winner.getName() + " WINS!"
        );
        statusLabel.setForeground(new Color(135, 70, 190));
        statusLabel.setFont(new Font("Arial", Font.BOLD, 22));

        for (int i = 0; i < teams.length; i++) {
            if (teams[i] == winner) {
                teamStatusLabels[i].setText("RAINBOW CHAMPION!");
                teamStatusLabels[i].setForeground(
                    new Color(135, 70, 190)
                );
            }
        }

        JOptionPane.showMessageDialog(
            this,
            "🌈 🏆 HOORAY! 🏆 🌈\n\n"
                + winner.getName() + " WINS!\n\n"
                + "You reached " + winningScore + " points!\n\n"
                + "Amazing teamwork, Rainbow Champion!",
            "🌟 Rainbow Champion! 🌟",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

}

