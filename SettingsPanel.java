import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel {
    private JFrame parentFrame;
    private MenuPanel menuPanel;

    public SettingsPanel(JFrame parentFrame, MenuPanel menuPanel) {
        this.parentFrame = parentFrame;
        this.menuPanel = menuPanel;
        setLayout(new GridLayout(6, 1, 10, 10));
        setBackground(new Color(30, 30, 30));

        JLabel titleLabel = new JLabel("Settings", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        titleLabel.setForeground(new Color(0, 255, 0));
        add(titleLabel);

        JLabel speedLabel = new JLabel("Snake Speed:", SwingConstants.CENTER);
        speedLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        speedLabel.setForeground(Color.WHITE);
        add(speedLabel);

        String[] speeds = { "Slow", "Medium", "Fast" };
        JComboBox<String> speedComboBox = new JComboBox<>(speeds);
        speedComboBox.setFont(new Font("Serif", Font.PLAIN, 24));
        speedComboBox.setSelectedIndex(1);
        speedComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (speedComboBox.getSelectedIndex()) {
                    case 0: menuPanel.setSnakeSpeed(200); break;
                    case 1: menuPanel.setSnakeSpeed(140); break;
                    case 2: menuPanel.setSnakeSpeed(80); break;
                }
            }
        });
        add(speedComboBox);

        JLabel frequencyLabel = new JLabel("Food Frequency:", SwingConstants.CENTER);
        frequencyLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        frequencyLabel.setForeground(Color.WHITE);
        add(frequencyLabel);

        String[] frequencies = { "1", "2", "3" };
        JComboBox<String> frequencyComboBox = new JComboBox<>(frequencies);
        frequencyComboBox.setFont(new Font("Serif", Font.PLAIN, 24));
        frequencyComboBox.setSelectedIndex(0);
        frequencyComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuPanel.setFoodFrequency(frequencyComboBox.getSelectedIndex() + 1);
            }
        });
        add(frequencyComboBox);

        JButton backButton = new JButton("Back to Menu");
        backButton.setFont(new Font("Serif", Font.BOLD, 24));
        backButton.setBackground(new Color(0, 255, 0));
        backButton.setForeground(Color.BLACK);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.getContentPane().removeAll();
                parentFrame.add(menuPanel);
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });
        add(backButton);

        setPreferredSize(new Dimension(1000, 800));
    }
}
