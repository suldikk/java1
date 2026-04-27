import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createRegistrationForm);
    }

    private static void createRegistrationForm() {
        JFrame frame = new JFrame("Регистрация");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JLabel nameLabel = new JLabel("Имя:");
        JTextField nameField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Пароль:");
        JPasswordField passwordField = new JPasswordField();

        JButton registerButton = new JButton("Зарегистрироваться");
        JLabel resultLabel = new JLabel("");

        registerButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                resultLabel.setText("Заполните все поля");
            } else {
                resultLabel.setText("Пользователь " + name + " зарегистрирован!");
            }
        });

        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        frame.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        frame.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        frame.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        frame.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        frame.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        frame.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        frame.add(registerButton, gbc);

        gbc.gridy = 4;
        frame.add(resultLabel, gbc);

        frame.setVisible(true);
    }
}
