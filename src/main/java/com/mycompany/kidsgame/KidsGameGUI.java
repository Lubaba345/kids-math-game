package com.mycompany.kidsgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * Swing GUI version of the Kids Math Game.
 * Supports single-player and multiplayer play with four game modes.
 */
public class KidsGameGUI extends JFrame {

    private CardLayout cardLayout;
    private JPanel rootPanel;

    private JButton singleBtn;
    private JButton multiBtn;
    private JSpinner playerCount;
    private JButton countNext;
    private JComboBox<String> modeCombo;
    private JButton modeNext;

    private JTextField[] nameFields;
    private JButton startBtn;

    private JLabel playerLabel;
    private JLabel modeLabel;
    private JLabel livesLabel;
    private JLabel questionLabel;
    private JTextField answerField;
    private JButton submitBtn;
    private JTextArea resultsArea;

    private int mode;
    private int numPlayers;
    private Player[] players;
    private int current;

    private int nQuestions;
    private int questionsLeft;
    private int lives;
    private int timeLimit;
    private long endTime;
    private long startTime;

    private final Random rand = new Random();
    private int opType;
    private int a;
    private int b;
    private String divCorrect;

    public KidsGameGUI() {
        setTitle("KidsGame");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        rootPanel = new JPanel(cardLayout);

        rootPanel.add(mainMenu(), "MENU");
        rootPanel.add(playerCountPanel(), "COUNT");
        rootPanel.add(modePanel(), "MODE");
        rootPanel.add(namePanel(), "NAMES");
        rootPanel.add(gamePanel(), "GAME");
        rootPanel.add(resultsPanel(), "RESULTS");

        add(rootPanel);
        cardLayout.show(rootPanel, "MENU");
        setVisible(true);
    }

    /**
     * Main menu where the user chooses single-player or multiplayer.
     */
    private JPanel mainMenu() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(new Color(255, 240, 220));

        JPanel panel = new JPanel(new GridLayout(3, 1, 20, 20));
        panel.setBackground(new Color(255, 240, 220));

        JLabel title = new JLabel("KidsGame", SwingConstants.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 36));

        singleBtn = new JButton("Single Player");
        multiBtn = new JButton("Multiplayer");

        singleBtn.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
        multiBtn.setFont(new Font("Comic Sans MS", Font.BOLD, 26));

        singleBtn.addActionListener(e -> {
            numPlayers = 1;
            cardLayout.show(rootPanel, "MODE");
        });

        multiBtn.addActionListener(e -> cardLayout.show(rootPanel, "COUNT"));

        panel.add(title);
        panel.add(singleBtn);
        panel.add(multiBtn);

        outer.add(panel);
        return outer;
    }

    /**
     * Multiplayer player-count selection panel.
     */
    private JPanel playerCountPanel() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(new Color(255, 230, 230));

        JPanel panel = new JPanel(new GridLayout(3, 1, 20, 20));
        panel.setBackground(new Color(255, 230, 230));

        JLabel title = new JLabel("How many players?", SwingConstants.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 32));

        playerCount = new JSpinner(new SpinnerNumberModel(2, 2, 10, 1));
        playerCount.setFont(new Font("Comic Sans MS", Font.BOLD, 26));

        countNext = new JButton("Next");
        countNext.setFont(new Font("Comic Sans MS", Font.BOLD, 26));

        countNext.addActionListener(e -> {
            numPlayers = (Integer) playerCount.getValue();
            cardLayout.show(rootPanel, "MODE");
        });

        panel.add(title);
        panel.add(playerCount);
        panel.add(countNext);

        outer.add(panel);
        return outer;
    }

    /**
     * Game mode selection panel.
     */
    private JPanel modePanel() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(new Color(200, 240, 255));

        JPanel panel = new JPanel(new GridLayout(2, 1, 20, 20));
        panel.setBackground(new Color(200, 240, 255));

        Font font = new Font("Comic Sans MS", Font.BOLD, 22);

        JPanel modeRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        modeRow.setBackground(new Color(200, 240, 255));

        JLabel modeLabelText = new JLabel("Choose Mode: ");
        modeLabelText.setFont(font);

        modeCombo = new JComboBox<>(new String[]{
                "1) Make a Wish",
                "2) No Mistakes",
                "3) Take Chances (3 Lives)",
                "4) Time Trial"
        });
        modeCombo.setFont(font);

        modeRow.add(modeLabelText);
        modeRow.add(modeCombo);

        modeNext = new JButton("Next");
        modeNext.setFont(font);
        modeNext.addActionListener(e -> chooseMode());

        panel.add(modeRow);
        panel.add(modeNext);

        outer.add(panel);
        return outer;
    }

    /**
     * Saves the selected mode and asks for mode-specific settings.
     */
    private void chooseMode() {
        mode = modeCombo.getSelectedIndex() + 1;

        if (mode == 1) {
            while (true) {
                String input = JOptionPane.showInputDialog(
                        this,
                        "Enter number of questions:"
                );

                if (input == null) {
                    return;
                }

                try {
                    int questions = Integer.parseInt(input.trim());

                    if (questions <= 0) {
                        throw new NumberFormatException();
                    }

                    nQuestions = questions;
                    break;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Please enter a positive whole number."
                    );
                }
            }
        }

        if (mode == 4) {
            while (true) {
                String input = JOptionPane.showInputDialog(
                        this,
                        "Enter time limit in seconds:"
                );

                if (input == null) {
                    return;
                }

                try {
                    int seconds = Integer.parseInt(input.trim());

                    if (seconds <= 0) {
                        throw new NumberFormatException();
                    }

                    timeLimit = seconds;
                    break;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Please enter a positive whole number of seconds."
                    );
                }
            }
        }

        loadNameInputs();
        cardLayout.show(rootPanel, "NAMES");
    }

    /**
     * Player name entry panel.
     */
    private JPanel namePanel() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(new Color(255, 240, 225));

        JLabel title = new JLabel("Enter Player Names", SwingConstants.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 32));

        JPanel form = new JPanel(new GridLayout(10, 2, 10, 10));
        form.setBackground(new Color(255, 240, 225));

        JScrollPane scroll = new JScrollPane(form);
        scroll.getViewport().setBackground(new Color(255, 240, 225));

        nameFields = new JTextField[10];

        startBtn = new JButton("Start Game");
        startBtn.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        startBtn.addActionListener(e -> startGame());

        outer.add(title, BorderLayout.NORTH);
        outer.add(scroll, BorderLayout.CENTER);
        outer.add(startBtn, BorderLayout.SOUTH);

        return outer;
    }

    /**
     * Rebuilds the player-name fields based on the selected player count.
     */
    private void loadNameInputs() {
        JPanel namesPanel = (JPanel) rootPanel.getComponent(3);
        JScrollPane scroll = (JScrollPane) namesPanel.getComponent(1);
        JPanel form = (JPanel) scroll.getViewport().getView();

        form.removeAll();
        nameFields = new JTextField[numPlayers];

        Font font = new Font("Comic Sans MS", Font.BOLD, 22);

        for (int i = 0; i < numPlayers; i++) {
            JLabel nameLabel = new JLabel("Player " + (i + 1) + ": ");
            nameLabel.setFont(font);

            JTextField field = new JTextField();
            field.setFont(font);
            nameFields[i] = field;

            form.add(nameLabel);
            form.add(field);
        }

        form.revalidate();
        form.repaint();
    }

    /**
     * Creates the Player objects and starts the first player's round.
     */
    private void startGame() {
        players = new Player[numPlayers];

        for (int i = 0; i < numPlayers; i++) {
            String name = nameFields[i].getText().trim();

            if (name.isEmpty()) {
                name = "Player" + (i + 1);
            }

            players[i] = new Player(name);
        }

        current = 0;
        cardLayout.show(rootPanel, "GAME");
        startNextPlayer();
    }

    /**
     * Main gameplay panel.
     */
    private JPanel gamePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 245, 235));

        JPanel top = new JPanel(new GridLayout(1, 3));
        top.setBackground(new Color(180, 220, 255));

        Font headerFont = new Font("Comic Sans MS", Font.BOLD, 20);

        playerLabel = new JLabel("Player:", SwingConstants.CENTER);
        modeLabel = new JLabel("Mode:", SwingConstants.CENTER);
        livesLabel = new JLabel("Lives:", SwingConstants.CENTER);

        playerLabel.setFont(headerFont);
        modeLabel.setFont(headerFont);
        livesLabel.setFont(headerFont);

        top.add(playerLabel);
        top.add(modeLabel);
        top.add(livesLabel);

        panel.add(top, BorderLayout.NORTH);

        questionLabel = new JLabel("Question", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
        questionLabel.setForeground(Color.RED);

        panel.add(questionLabel, BorderLayout.CENTER);

        JPanel answerPanel = new JPanel(new BorderLayout());
        answerPanel.setBackground(new Color(240, 255, 240));

        JLabel answerLabel = new JLabel("Your Answer:");
        answerLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));

        answerField = new JTextField();
        answerField.setFont(new Font("Comic Sans MS", Font.BOLD, 26));

        submitBtn = new JButton("Submit");
        submitBtn.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
        submitBtn.addActionListener(e -> submitAnswer());

        answerPanel.add(answerLabel, BorderLayout.WEST);
        answerPanel.add(answerField, BorderLayout.CENTER);
        answerPanel.add(submitBtn, BorderLayout.EAST);

        panel.add(answerPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Starts a round for the next player.
     */
    private void startNextPlayer() {
        if (current >= numPlayers) {
            showResults();
            cardLayout.show(rootPanel, "RESULTS");
            return;
        }

        Player player = players[current];
        player.setScore(0);

        if (mode == 1) {
            questionsLeft = nQuestions;
        }

        if (mode == 3) {
            lives = 3;
        }

        if (mode == 4) {
            endTime = System.currentTimeMillis() + timeLimit * 1000L;
        }

        startTime = System.currentTimeMillis();

        playerLabel.setText("Player: " + player.getName());
        modeLabel.setText("Mode: " + modeCombo.getSelectedItem());

        if (mode == 3) {
            updateLivesLabel();
            livesLabel.setVisible(true);
        } else {
            livesLabel.setVisible(false);
        }

        generateQuestion();
    }

    /**
     * Generates a random addition, subtraction, multiplication,
     * or division question.
     */
    private void generateQuestion() {
        if (mode == 4 && System.currentTimeMillis() >= endTime) {
            finishPlayer("Time up!");
            return;
        }

        opType = rand.nextInt(4);

        if (opType == 0) {
            a = rand.nextInt(20);
            b = rand.nextInt(20);
            questionLabel.setText(a + " + " + b + " = ?");
        } else if (opType == 1) {
            int x = rand.nextInt(20);
            int y = rand.nextInt(20);
            a = Math.max(x, y);
            b = Math.min(x, y);
            questionLabel.setText(a + " - " + b + " = ?");
        } else if (opType == 2) {
            a = rand.nextInt(12) + 1;
            b = rand.nextInt(12) + 1;
            questionLabel.setText(a + " × " + b + " = ?");
        } else {
            a = rand.nextInt(90) + 10;
            b = rand.nextInt(10) + 1;
            divCorrect = String.format("%.2f", (double) a / b);
            questionLabel.setText(a + " / " + b + " = ? (2 decimals)");
        }

        answerField.setText("");
        answerField.requestFocusInWindow();
    }

    /**
     * Checks the current answer and updates the player's score.
     */
    private void submitAnswer() {
        Player player = players[current];
        String answer = answerField.getText().trim();
        boolean correct;

        if (answer.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an answer.");
            return;
        }

        try {
            if (opType == 0) {
                correct = Integer.parseInt(answer) == (a + b);
            } else if (opType == 1) {
                correct = Integer.parseInt(answer) == (a - b);
            } else if (opType == 2) {
                correct = Integer.parseInt(answer) == (a * b);
            } else {
                if (!answer.matches("-?\\d+\\.\\d{2}")) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Use 2 decimals, for example 2.50"
                    );
                    return;
                }

                correct = answer.equals(divCorrect);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number!");
            return;
        }

        if (correct) {
            player.setScore(player.getScore() + 1);
        } else {
            JOptionPane.showMessageDialog(this, "BOO! You got it wrong.");
        }

        handleMode(correct);
    }

    /**
     * Applies the rules for the selected game mode.
     */
    private void handleMode(boolean correct) {
        if (mode == 1) {
            questionsLeft--;

            if (questionsLeft <= 0) {
                finishPlayer("Finished!");
            } else {
                generateQuestion();
            }
        } else if (mode == 2) {
            if (!correct) {
                finishPlayer("Wrong answer!");
            } else {
                generateQuestion();
            }
        } else if (mode == 3) {
            if (!correct) {
                lives--;

                if (lives <= 0) {
                    finishPlayer("No lives left!");
                    return;
                }

                updateLivesLabel();
            }

            generateQuestion();
        } else if (mode == 4) {
            if (System.currentTimeMillis() >= endTime) {
                finishPlayer("Time up!");
            } else {
                generateQuestion();
            }
        }
    }

    /**
     * Updates the visual lives counter.
     */
    private void updateLivesLabel() {
        StringBuilder livesText = new StringBuilder();

        for (int i = 0; i < lives; i++) {
            livesText.append("|");
        }

        livesLabel.setText("Lives: " + livesText);
    }

    /**
     * Ends the current player's round and moves to the next player.
     */
    private void finishPlayer(String message) {
        Player player = players[current];
        long timeUsed = (System.currentTimeMillis() - startTime) / 1000;

        JOptionPane.showMessageDialog(
                this,
                player.getName() + "\n"
                        + message + "\n"
                        + "Score: " + player.getScore() + "\n"
                        + "Time: " + timeUsed + "s"
        );

        current++;
        startNextPlayer();
    }

    /**
     * Final results panel.
     */
    private JPanel resultsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 245, 235));

        JLabel title = new JLabel("Final Results", SwingConstants.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 36));

        resultsArea = new JTextArea();
        resultsArea.setEditable(false);
        resultsArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));

        JButton back = new JButton("Back to Menu");
        back.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
        back.addActionListener(e -> cardLayout.show(rootPanel, "MENU"));

        panel.add(title, BorderLayout.NORTH);
        panel.add(new JScrollPane(resultsArea), BorderLayout.CENTER);
        panel.add(back, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Sorts players by score and displays the final result.
     */
    private void showResults() {
        for (int i = 0; i < players.length - 1; i++) {
            for (int j = i + 1; j < players.length; j++) {
                if (players[j].getScore() > players[i].getScore()) {
                    Player temp = players[i];
                    players[i] = players[j];
                    players[j] = temp;
                }
            }
        }

        StringBuilder result = new StringBuilder();

        if (numPlayers == 1) {
            result.append("Single Player Result\n\n");
            result.append(players[0].getName())
                    .append("\nScore: ")
                    .append(players[0].getScore());
        } else {
            result.append("Winner: ")
                    .append(players[0].getName())
                    .append("\n\n");

            for (Player player : players) {
                result.append(player.getName())
                        .append(" : ")
                        .append(player.getScore())
                        .append("\n");
            }
        }

        resultsArea.setText(result.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(KidsGameGUI::new);
    }
}
