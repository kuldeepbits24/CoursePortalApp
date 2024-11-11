import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

class User {
    String username, password;
    String type;

    User(String username, String password, String type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }
}

class CourseRequest {
    String studentName;
    String course;
    boolean isApproved;

    CourseRequest(String studentName, String course) {
        this.studentName = studentName;
        this.course = course;
        this.isApproved = false;
    }
}

public class CoursePortal extends JFrame {
    private HashMap<String, User> users = new HashMap<>();
    private HashMap<String, ArrayList<CourseRequest>> teacherRequests = new HashMap<>();
    private HashMap<String, ArrayList<CourseRequest>> studentRequests = new HashMap<>();

    private JTextField usernameField, passwordField;
    private JButton loginButton, registerButton;
    private JComboBox<String> userTypeCombo;

    public CoursePortal() {
        setTitle("Course Portal - Register and Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        add(new JLabel("User Type:"));
        userTypeCombo = new JComboBox<>(new String[]{"Student", "Teacher"});
        add(userTypeCombo);

        loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginAction());
        add(loginButton);

        registerButton = new JButton("Register");
        registerButton.addActionListener(new RegisterAction());
        add(registerButton);

        setVisible(true);
    }

    private void registerUser(String username, String password, String type) {
        if (!users.containsKey(username)) {
            users.put(username, new User(username, password, type));
            teacherRequests.putIfAbsent(username, new ArrayList<>());
            studentRequests.putIfAbsent(username, new ArrayList<>());
            JOptionPane.showMessageDialog(this, "Registration successful!");
        } else {
            JOptionPane.showMessageDialog(this, "User already exists!");
        }
    }

    private void loginUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.password.equals(password)) {
            if (user.type.equals("Student")) {
                new StudentDashboard(username, this).setVisible(true);
            } else {
                new TeacherDashboard(username, this).setVisible(true);
            }
            this.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials!");
        }
    }

    private class RegisterAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String userType = (String) userTypeCombo.getSelectedItem();
            registerUser(username, password, userType);
        }
    }

    private class LoginAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = passwordField.getText();
            loginUser(username, password);
        }
    }

    private class StudentDashboard extends JFrame {
        private JPanel requestPanel;

        public StudentDashboard(String studentName, CoursePortal portal) {
            setTitle("Student Dashboard");
            setSize(400, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());

            JLabel welcomeLabel = new JLabel("Welcome, " + studentName);
            add(welcomeLabel, BorderLayout.NORTH);

            JPanel controlsPanel = new JPanel();
            JComboBox<String> teacherCombo = new JComboBox<>();
            for (String teacher : teacherRequests.keySet()) {
                teacherCombo.addItem(teacher);
            }

            JButton applyButton = new JButton("Apply for Research Pratice Course");
            applyButton.addActionListener(e -> {
                String selectedTeacher = (String) teacherCombo.getSelectedItem();
                CourseRequest request = new CourseRequest(studentName, "Requested Course");
                teacherRequests.get(selectedTeacher).add(request);
                studentRequests.get(studentName).add(request);
                refreshStatus(studentName);
                JOptionPane.showMessageDialog(this, "Request sent to " + selectedTeacher);
            });

            JButton refreshButton = new JButton("Refresh Status");
            refreshButton.addActionListener(e -> refreshStatus(studentName));

            JButton logoutButton = new JButton("Logout");
            logoutButton.addActionListener(e -> {
                portal.setVisible(true);
                this.dispose();
            });

            controlsPanel.add(teacherCombo);
            controlsPanel.add(applyButton);
            controlsPanel.add(refreshButton);
            controlsPanel.add(logoutButton);
            add(controlsPanel, BorderLayout.SOUTH);

            requestPanel = new JPanel();
            requestPanel.setLayout(new BoxLayout(requestPanel, BoxLayout.Y_AXIS));
            add(requestPanel, BorderLayout.CENTER);

            refreshStatus(studentName);
        }

        private void refreshStatus(String studentName) {
            requestPanel.removeAll();
            ArrayList<CourseRequest> requests = studentRequests.get(studentName);
            for (CourseRequest req : requests) {
                String statusText = req.course + " - " + (req.isApproved ? "Approved" : "Pending...");
                requestPanel.add(new JLabel(statusText));
            }
            requestPanel.revalidate();
            requestPanel.repaint();
        }
    }

    private class TeacherDashboard extends JFrame {
        private JPanel requestPanel;

        public TeacherDashboard(String teacherName, CoursePortal portal) {
            setTitle("Teacher Dashboard");
            setSize(400, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());

            JLabel welcomeLabel = new JLabel("Welcome, Prof." + teacherName);
            add(welcomeLabel, BorderLayout.NORTH);

            requestPanel = new JPanel();
            requestPanel.setLayout(new BoxLayout(requestPanel, BoxLayout.Y_AXIS));
            add(requestPanel, BorderLayout.CENTER);

            JButton logoutButton = new JButton("Logout");
            logoutButton.addActionListener(e -> {
                portal.setVisible(true);
                this.dispose();
            });
            add(logoutButton, BorderLayout.SOUTH);

            refreshDashboard(teacherName);
        }

        private void refreshDashboard(String teacherName) {
            requestPanel.removeAll();
            ArrayList<CourseRequest> requests = teacherRequests.get(teacherName);

            for (CourseRequest req : new ArrayList<>(requests)) {
                JPanel panel = new JPanel();
                panel.add(new JLabel("Student: " + req.studentName));

                JButton approveButton = new JButton("Approve");
                approveButton.addActionListener(e -> {
                    req.isApproved = true;
                    JOptionPane.showMessageDialog(this, "Approved request for " + req.studentName);
                    requests.remove(req);
                    refreshDashboard(teacherName);
                });

                panel.add(approveButton);
                requestPanel.add(panel);
            }

            requestPanel.revalidate();
            requestPanel.repaint();
        }
    }

    public static void main(String[] args) {
        new CoursePortal();
    }
}
