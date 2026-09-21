import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class myForm {

    private static String currentMode = "MAIN_MANAGER";

    public static void main(String[] args) {
        // Ensure default seed data exists so logins don't hit empty lists
        ensureSeedData();

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Taxi Fleet Portal - Login");
            frame.setSize(550, 420);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setLayout(new BorderLayout());

            // Header Banner
            JPanel headerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
            headerPanel.setBackground(new Color(33, 43, 54));
            headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JLabel titleLabel = new JLabel("Taxi Fleet Management System", SwingConstants.CENTER);
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
            titleLabel.setForeground(Color.WHITE);

            JLabel subtitleLabel = new JLabel("Main Manager Login", SwingConstants.CENTER);
            subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            subtitleLabel.setForeground(new Color(200, 200, 200));

            headerPanel.add(titleLabel);
            headerPanel.add(subtitleLabel);
            frame.add(headerPanel, BorderLayout.NORTH);

            // Form Fields
            JPanel formPanel = new JPanel(new GridBagLayout());
            formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(8, 8, 8, 8);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel userLabel = new JLabel("Username:");
            JTextField userField = new JTextField(15);

            JLabel passLabel = new JLabel("Password:");
            JPasswordField passField = new JPasswordField(15);

            JLabel idLabel = new JLabel("Manager ID:");
            JTextField idField = new JTextField(15);

            JLabel subLabel = new JLabel("Subscriber ID:");
            JTextField subField = new JTextField(15);

            // Main Manager controls (Row 0 & 1)
            gbc.gridx = 0; gbc.gridy = 0; formPanel.add(userLabel, gbc);
            gbc.gridx = 1; formPanel.add(userField, gbc);
            gbc.gridx = 0; gbc.gridy = 1; formPanel.add(passLabel, gbc);
            gbc.gridx = 1; formPanel.add(passField, gbc);

            // Manager control (Row 2)
            gbc.gridx = 0; gbc.gridy = 2; formPanel.add(idLabel, gbc);
            gbc.gridx = 1; formPanel.add(idField, gbc);

            // Subscriber control (Row 3)
            gbc.gridx = 0; gbc.gridy = 3; formPanel.add(subLabel, gbc);
            gbc.gridx = 1; formPanel.add(subField, gbc);

            // Initial visibility
            idLabel.setVisible(false); idField.setVisible(false);
            subLabel.setVisible(false); subField.setVisible(false);

            frame.add(formPanel, BorderLayout.CENTER);

            // Button & Switch Panel
            JPanel bottomPanel = new JPanel(new GridLayout(2, 1, 10, 10));
            bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));

            JButton loginBtn = new JButton("Sign In");
            loginBtn.setBackground(new Color(24, 119, 242));
            loginBtn.setForeground(Color.WHITE);
            loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            loginBtn.setFocusPainted(false);

            // Switch Bar
            JPanel switchBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
            JButton toAdminBtn = new JButton("Admin Portal");
            JButton toManagerBtn = new JButton("Manager Portal");
            JButton toSubBtn = new JButton("Subscriber Portal");

            switchBar.add(toAdminBtn);
            switchBar.add(toManagerBtn);
            switchBar.add(toSubBtn);

            bottomPanel.add(loginBtn);
            bottomPanel.add(switchBar);
            frame.add(bottomPanel, BorderLayout.SOUTH);

            // Switch Actions
            Runnable refreshViews = () -> {
                userLabel.setVisible(currentMode.equals("MAIN_MANAGER"));
                userField.setVisible(currentMode.equals("MAIN_MANAGER"));
                passLabel.setVisible(currentMode.equals("MAIN_MANAGER"));
                passField.setVisible(currentMode.equals("MAIN_MANAGER"));

                idLabel.setVisible(currentMode.equals("MANAGER"));
                idField.setVisible(currentMode.equals("MANAGER"));

                subLabel.setVisible(currentMode.equals("SUBSCRIBER"));
                subField.setVisible(currentMode.equals("SUBSCRIBER"));

                formPanel.revalidate();
                formPanel.repaint();
            };

            toAdminBtn.addActionListener(e -> {
                currentMode = "MAIN_MANAGER";
                subtitleLabel.setText("Main Manager Login");
                refreshViews.run();
            });

            toManagerBtn.addActionListener(e -> {
                currentMode = "MANAGER";
                subtitleLabel.setText("Regular Manager Login (e.g. ID: M1)");
                refreshViews.run();
            });

            toSubBtn.addActionListener(e -> {
                currentMode = "SUBSCRIBER";
                subtitleLabel.setText("Subscriber Login (e.g. Code: S1)");
                refreshViews.run();
            });

            // Login Trigger
            loginBtn.addActionListener((ActionEvent e) -> {
                var db = systemDataBase.getInstance();

                if (currentMode.equals("MAIN_MANAGER")) {
                    String username = userField.getText().trim();
                    String password = new String(passField.getPassword()).trim();
                    MainManager admin = db.getAdministrator();

                    if (admin != null && admin.getUserName().equalsIgnoreCase(username) && admin.getPassword().equals(password)) {
                        MainManagerUI.openMainManagerFrame(admin);
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid Admin credentials.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (currentMode.equals("MANAGER")) {
                    String id = idField.getText().trim();
                    Manager found = db.getManagers().stream()
                            .filter(m -> !(m instanceof MainManager) && m.getId().equalsIgnoreCase(id))
                            .findFirst().orElse(null);

                    if (found != null) {
                        ManagerUI.openManagerFrame(found);
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Manager ID not found. Try 'M1' or add via Admin.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (currentMode.equals("SUBSCRIBER")) {
                    String code = subField.getText().trim();
                    Subscription found = db.getSubscriptions().stream()
                            .filter(s -> s.getSubCode().equalsIgnoreCase(code))
                            .findFirst().orElse(null);

                    if (found != null) {
                        SubscriberUI.openSubscriberFrame(found);
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Subscriber Code not found. Try 'S1' or import members.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            frame.setVisible(true);
        });
    }

    private static void ensureSeedData() {
        systemDataBase db = systemDataBase.getInstance();
        if (db.getManagers().size() <= 1) { // Only admin exists
            db.addManager(new Manager("M1", "Mike", "Hannigan", "0500000001", "NYC"));
            db.addManager(new Manager("M2", "Janice", "Hosenstein", "0500000002", "Brooklyn"));
        }
        if (db.getSubscriptions().isEmpty()) {
            db.addSubscription(new Subscription("S1", "Rachel", "Green", "0501111111", "Soho"));
            db.addSubscription(new Subscription("S2", "Monica", "Geller", "0501111112", "West Village"));
        }
        if (db.getTaxis().isEmpty()) {
            Taxi t1 = new Taxi("T1", true, 35.0);
            Taxi t2 = new ExpressTaxi("T2", true, 45.0, true, 10.0);
            db.addTaxi(t1);
            db.addTaxi(t2);
            db.getManagers().get(1).addTaxi(t1);
        }
    }
}

