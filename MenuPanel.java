import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {
    private JFrame parentFrame;
    private int snakeSpeed = 140;
    private int foodFrequency = 1;

    public MenuPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 30));

        JLabel titleLabel = new JLabel("Snake Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 48));
        titleLabel.setForeground(new Color(0, 255, 0));
        add(titleLabel, BorderLayout.NORTH);

        JTextArea instructions = new JTextArea();
        instructions.setText("Instructions:\nUse arrow keys to move the snake.\nEat the food to grow.\nAvoid hitting the walls or yourself.\nPress 'P' to pause/resume.");
        instructions.setEditable(false);
        instructions.setFont(new Font("Serif", Font.PLAIN, 20));
        instructions.setBackground(new Color(30, 30, 30));
        instructions.setForeground(Color.WHITE);
        add(instructions, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 10));
        buttonPanel.setBackground(new Color(30, 30, 30));

        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Serif", Font.BOLD, 24));
        startButton.setBackground(new Color(0, 255, 0));
        startButton.setForeground(Color.BLACK);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GamePanel gamePanel = new GamePanel(parentFrame, snakeSpeed, foodFrequency);
                parentFrame.getContentPane().removeAll();
                parentFrame.add(gamePanel);
                parentFrame.revalidate();
                parentFrame.repaint();
                gamePanel.requestFocusInWindow();
            }
        });

        JButton settingsButton = new JButton("Settings");
        settingsButton.setFont(new Font("Serif", Font.BOLD, 24));
        settingsButton.setBackground(new Color(0, 255, 0));
        settingsButton.setForeground(Color.BLACK);
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SettingsPanel settingsPanel = new SettingsPanel(parentFrame, MenuPanel.this);
                parentFrame.getContentPane().removeAll();
                parentFrame.add(settingsPanel);
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });

        buttonPanel.add(startButton);
        buttonPanel.add(settingsButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setPreferredSize(new Dimension(1000, 800));
    }

    public void setSnakeSpeed(int speed) {
        this.snakeSpeed = speed;
    }

    public void setFoodFrequency(int frequency) {
        this.foodFrequency = frequency;
    }
}
