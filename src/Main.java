import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    private static final Path USERS_FILE = Path.of("users.csv");
    private static final Path USERS_TEXT_FILE = Path.of("users.txt");
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createRegistrationForm);
    }

    private static void createRegistrationForm() {
        JFrame frame = new JFrame("Регистрация");
        frame.setSize(520, 420);
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
        DefaultListModel<String> usersModel = new DefaultListModel<>();
        JList<String> usersList = new JList<>(usersModel);
        JScrollPane usersScrollPane = new JScrollPane(usersList);
        JLabel usersLabel = new JLabel("Зарегистрированные пользователи:");

        loadUsers(usersModel);

        registerButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                resultLabel.setText("Заполните все поля");
            } else if (!name.matches("[A-Za-z]+")) {
                resultLabel.setText("Имя должно быть только на английском");
            } else if (!email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+")) {
                resultLabel.setText("Email должен быть на английском и содержать @");
            } else if (password.length() < 6) {
                resultLabel.setText("Пароль должен быть минимум 6 символов");
            } else if (!password.matches("[A-Za-z0-9]+")) {
                resultLabel.setText("Пароль должен быть только на английском");
            } else {
                try {
                    saveUser(name, email);
                    usersModel.addElement(name + " - " + email);
                    resultLabel.setText("Пользователь " + name + " зарегистрирован!");
                    nameField.setText("");
                    emailField.setText("");
                    passwordField.setText("");
                } catch (IOException ex) {
                    resultLabel.setText("Ошибка сохранения пользователя");
                }
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

        gbc.gridy = 5;
        frame.add(usersLabel, gbc);

        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        frame.add(usersScrollPane, gbc);

        frame.setVisible(true);
    }

    private static void saveUser(String name, String email) throws IOException {
        String createdAt = LocalDateTime.now().format(DATE_FORMAT);
        String line = escapeCsv(name) + "," + escapeCsv(email) + "," + escapeCsv(createdAt);
        String textLine = "Имя: " + name + " | Email: " + email + " | Дата: " + createdAt;

        try (BufferedWriter writer = Files.newBufferedWriter(
                USERS_FILE,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        )) {
            writer.write(line);
            writer.newLine();
        }

        try (BufferedWriter writer = Files.newBufferedWriter(
                USERS_TEXT_FILE,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        )) {
            writer.write(textLine);
            writer.newLine();
        }
    }

    private static void loadUsers(DefaultListModel<String> usersModel) {
        if (!Files.exists(USERS_FILE)) {
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(USERS_FILE, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 3);
                if (parts.length >= 2) {
                    usersModel.addElement(unescapeCsv(parts[0]) + " - " + unescapeCsv(parts[1]));
                }
            }
        } catch (IOException ex) {
            usersModel.addElement("Не удалось загрузить список пользователей");
        }
    }

    private static String escapeCsv(String text) {
        return text.replace("\\", "\\\\").replace(",", "\\,");
    }

    private static String unescapeCsv(String text) {
        return text.replace("\\,", ",").replace("\\\\", "\\");
    }
}
