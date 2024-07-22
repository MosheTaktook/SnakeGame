import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    private final int TILE_SIZE = 25;
    private final int GAME_WIDTH = 1000;
    private final int GAME_HEIGHT = 800;
    private final int GAME_UNITS = (GAME_WIDTH * GAME_HEIGHT) / (TILE_SIZE * TILE_SIZE);
    private int snakeSpeed;
    private int foodFrequency;
    private final int[] x = new int[GAME_UNITS];
    private final int[] y = new int[GAME_UNITS];
    private int bodyParts = 3;
    private int applesEaten;
    private int appleX;
    private int appleY;
    private char direction = 'R';
    private boolean running = false;
    private Timer timer;
    private Random random;
    private JFrame parentFrame;

    public GamePanel(JFrame parentFrame, int snakeSpeed, int foodFrequency) {
        this.parentFrame = parentFrame;
        this.snakeSpeed = snakeSpeed;
        this.foodFrequency = foodFrequency;
        random = new Random();
        setBackground(Color.black);
        setFocusable(true);
        setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(snakeSpeed, this);
        timer.start();
        requestFocusInWindow(); // Ensure the panel has focus
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            for (int i = 0; i < foodFrequency; i++) {
                g.setColor(Color.red);
                g.fillOval(appleX, appleY, TILE_SIZE, TILE_SIZE);
            }

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                } else {
                    g.setColor(new Color(45, 180, 0));
                }
                g.fillRect(x[i], y[i], TILE_SIZE, TILE_SIZE);
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (GAME_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
        } else {
            gameOver(g);
        }
    }

    public void newApple() {
        appleX = random.nextInt((int) (GAME_WIDTH / TILE_SIZE)) * TILE_SIZE;
        appleY = random.nextInt((int) (GAME_HEIGHT / TILE_SIZE)) * TILE_SIZE;
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U':
                y[0] = y[0] - TILE_SIZE;
                break;
            case 'D':
                y[0] = y[0] + TILE_SIZE;
                break;
            case 'L':
                x[0] = x[0] - TILE_SIZE;
                break;
            case 'R':
                x[0] = x[0] + TILE_SIZE;
                break;
        }
    }

    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollisions() {
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }

        if (x[0] < 0 || x[0] >= GAME_WIDTH || y[0] < 0 || y[0] >= GAME_HEIGHT) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (GAME_WIDTH - metrics1.stringWidth("Game Over")) / 2, GAME_HEIGHT / 2);

        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (GAME_WIDTH - metrics2.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());

        showGameOverOptions();
    }

    private void showGameOverOptions() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 30, 30));
        panel.setLayout(new GridLayout(4, 1, 10, 10));

        JLabel label = new JLabel("Game Over", SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 48));
        label.setForeground(new Color(255, 0, 0));
        panel.add(label);

        JLabel scoreLabel = new JLabel("Your Score: " + applesEaten, SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Serif", Font.PLAIN, 36));
        scoreLabel.setForeground(Color.WHITE);
        panel.add(scoreLabel);

        JButton restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Serif", Font.BOLD, 24));
        restartButton.setBackground(new Color(0, 255, 0));
        restartButton.setForeground(Color.BLACK);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.getContentPane().removeAll();
                parentFrame.add(new GamePanel(parentFrame, snakeSpeed, foodFrequency));
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });
        panel.add(restartButton);

        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setFont(new Font("Serif", Font.BOLD, 24));
        mainMenuButton.setBackground(new Color(0, 255, 0));
        mainMenuButton.setForeground(Color.BLACK);
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.getContentPane().removeAll();
                parentFrame.add(new MenuPanel(parentFrame));
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });
        panel.add(mainMenuButton);

        JOptionPane.showMessageDialog(this, panel, "Game Over", JOptionPane.PLAIN_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_P:
                    if (running) {
                        timer.stop();
                    } else {
                        timer.start();
                    }
                    running = !running;
                    break;
            }
        }
    }
}
