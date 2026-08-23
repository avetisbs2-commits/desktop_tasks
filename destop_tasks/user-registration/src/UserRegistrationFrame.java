import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class UserRegistrationFrame extends JFrame {

    private final ArrayList<User> users = new ArrayList<>();

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField ageField;

    private JTextField loginEmailField;
    private JPasswordField loginPasswordField;

    private JTextArea userInfoArea;

    public UserRegistrationFrame() {
        setTitle("Student User Management");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createComponents();
    }

    private void createComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formsPanel = new JPanel(new GridBagLayout());
        formsPanel.add(createRegistrationPanel(), createFormConstraints(0));
        formsPanel.add(createLoginPanel(), createFormConstraints(1));

        mainPanel.add(formsPanel, BorderLayout.NORTH);
        mainPanel.add(createUserInfoPanel(), BorderLayout.CENTER);
        mainPanel.add(createActionPanel(), BorderLayout.SOUTH);

        add(mainPanel);
    }

    private GridBagConstraints createFormConstraints(int column) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = column;
        constraints.gridy = 0;
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(0, 5, 0, 5);
        return constraints;
    }

    private JPanel createRegistrationPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Registration"));

        firstNameField = new JTextField(18);
        lastNameField = new JTextField(18);
        emailField = new JTextField(18);
        passwordField = new JPasswordField(18);
        ageField = new JTextField(18);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(event -> registerUser());

        addFormRow(panel, "First Name:", firstNameField, 0);
        addFormRow(panel, "Last Name:", lastNameField, 1);
        addFormRow(panel, "Email:", emailField, 2);
        addFormRow(panel, "Password:", passwordField, 3);
        addFormRow(panel, "Age:", ageField, 4);

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 1;
        buttonConstraints.gridy = 5;
        buttonConstraints.anchor = GridBagConstraints.EAST;
        buttonConstraints.insets = new Insets(8, 5, 5, 5);
        panel.add(registerButton, buttonConstraints);

        return panel;
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Login"));

        loginEmailField = new JTextField(18);
        loginPasswordField = new JPasswordField(18);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(event -> login());

        addFormRow(panel, "Email:", loginEmailField, 0);
        addFormRow(panel, "Password:", loginPasswordField, 1);

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 1;
        buttonConstraints.gridy = 2;
        buttonConstraints.anchor = GridBagConstraints.EAST;
        buttonConstraints.insets = new Insets(8, 5, 5, 5);
        panel.add(loginButton, buttonConstraints);

        return panel;
    }

    private void addFormRow(JPanel panel, String labelText, JTextField field, int row) {
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = row;
        labelConstraints.anchor = GridBagConstraints.WEST;
        labelConstraints.insets = new Insets(5, 5, 5, 5);
        panel.add(new JLabel(labelText), labelConstraints);

        GridBagConstraints fieldConstraints = new GridBagConstraints();
        fieldConstraints.gridx = 1;
        fieldConstraints.gridy = row;
        fieldConstraints.weightx = 1.0;
        fieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        fieldConstraints.insets = new Insets(5, 5, 5, 5);
        panel.add(field, fieldConstraints);
    }

    private JPanel createUserInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("User Information"));

        userInfoArea = new JTextArea();
        userInfoArea.setEditable(false);
        userInfoArea.setLineWrap(true);
        userInfoArea.setWrapStyleWord(true);

        panel.add(new JScrollPane(userInfoArea), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createActionPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton showAllUsersButton = new JButton("Show All Users");
        showAllUsersButton.addActionListener(event -> showAllUsers());

        JButton oldestUserButton = new JButton("Find Oldest User");
        oldestUserButton.addActionListener(event -> {
            User oldestUser = findOldestUser();
            if (oldestUser == null) {
                JOptionPane.showMessageDialog(this, "No user found.");
            } else {
                userInfoArea.setText(getUserInfo(oldestUser));
            }
        });

        JButton averageAgeButton = new JButton("Calculate Average Age");
        averageAgeButton.addActionListener(event -> {
            double averageAge = calculateAverageAge();
            userInfoArea.setText("Average age: " + averageAge);
        });

        JButton removeUserButton = new JButton("Remove Login Email User");
        removeUserButton.addActionListener(event -> {
            String email = loginEmailField.getText();
            boolean removed = removeUserByEmail(email);
            if (removed) {
                JOptionPane.showMessageDialog(this, "User removed.");
            } else {
                JOptionPane.showMessageDialog(this, "User was not removed.");
            }
        });

        panel.add(showAllUsersButton);
        panel.add(oldestUserButton);
        panel.add(averageAgeButton);
        panel.add(removeUserButton);

        return panel;
    }

    private void registerUser() {
        // TODO:
        // 1. Read values from registration fields
        // 2. Validate empty fields
        // 3. Validate age
        // 4. Check whether a user with the same email already exists
        // 5. Create User object
        // 6. Add User into users ArrayList
        // 7. Clear registration fields
    }

    private boolean emailExists(String email) {
        // TODO:
        // Search users ArrayList
        // Return true if a user with this email exists

        return false;
    }

    private void login() {
        // TODO:
        // 1. Read email
        // 2. Read password
        // 3. Find user by email
        // 4. Check password
        // 5. Show success or error message
    }

    private User findUserByEmail(String email) {
        // TODO:
        // Search users ArrayList
        // Return matching User
        // Return null if user does not exist

        return null;
    }

    private String getUserInfo(User user) {
        // TODO:
        // Return user information as text

        return "";
    }

    private void showAllUsers() {
        // TODO:
        // Go through users ArrayList
        // Display all registered users
    }

    private User findOldestUser() {
        // TODO:
        // Find the user with the highest age

        return null;
    }

    private double calculateAverageAge() {
        // TODO:
        // Calculate average age of all registered users

        return 0;
    }

    private boolean removeUserByEmail(String email) {
        // TODO:
        // Find a user by email
        // Remove the user from ArrayList
        // Return true if removed

        return false;
    }
}
