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

        frame.setLayout(new GridLayout(5, 2, 10, 10));
        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(emailLabel);
        frame.add(emailField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(registerButton);
        frame.add(new JLabel(""));
        frame.add(resultLabel);

        frame.setVisible(true);
    }
}
