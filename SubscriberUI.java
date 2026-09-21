import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class SubscriberUI {

    private static final Color PRIMARY = new Color(6, 78, 59);        // Deep Emerald
    private static final Color ACCENT = new Color(16, 185, 129);      // Bright Emerald
    private static final Color BG_LIGHT = new Color(240, 253, 244);   // Soft Mint Tint
    private static final Font FONT_HEADER = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font FONT_REG = new Font("Segoe UI", Font.PLAIN, 13);

    public static void openSubscriberFrame(Subscription sub) {
        JFrame frame = new JFrame("Subscriber Portal — Welcome " + sub.getFirstName() + " " + sub.getLastName());
        frame.setSize(880, 560);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(BG_LIGHT);
        frame.setLayout(new BorderLayout());

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PRIMARY);
        header.setBorder(new EmptyBorder(16, 24, 16, 24));

        JLabel title = new JLabel("MY TRAVEL DASHBOARD");
        title.setFont(FONT_HEADER);
        title.setForeground(Color.WHITE);

        JButton logoutBtn = MainManagerUI.createStyledButton("Sign Out", new Color(220, 38, 38));
        logoutBtn.addActionListener(e -> {
            frame.dispose();
            myForm.main(null);
        });

        header.add(title, BorderLayout.WEST);
        header.add(logoutBtn, BorderLayout.EAST);
        frame.add(header, BorderLayout.NORTH);

        // Center Content
        JPanel center = new JPanel(new BorderLayout(15, 15));
        center.setOpaque(false);
        center.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Member Profile Card
        JPanel profileCard = new JPanel(new GridLayout(1, 4, 15, 0));
        profileCard.setBackground(Color.WHITE);
        profileCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(187, 247, 208), 1),
                new EmptyBorder(15, 20, 15, 20)
        ));

        profileCard.add(createProfileItem("ACCOUNT HOLDER", sub.getFirstName() + " " + sub.getLastName()));
        profileCard.add(createProfileItem("MEMBER CODE", sub.getSubCode()));
        profileCard.add(createProfileItem("PHONE NUMBER", sub.getPhone()));
        profileCard.add(createProfileItem("HOME ADDRESS", sub.getAddress()));

        center.add(profileCard, BorderLayout.NORTH);

        // My Orders Table
        String[] cols = {"Booking #", "Vehicle Code", "Vehicle Category", "Scheduled Date", "Billed Fare"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        ArrayList<Order> orders = systemDataBase.getInstance().getOrdersPerSub().get(sub.getSubCode());
        if (orders != null) {
            for (Order o : orders) {
                model.addRow(new Object[]{
                        "#" + o.getOrderNum(),
                        o.getTaxi().getTaxiCode(),
                        o.getTaxi().getClass().getSimpleName(),
                        o.getDay() + "/" + o.getMonth() + " at " + o.getHour() + ":00",
                        "$" + String.format("%.2f", o.getOrderPrice())
                });
            }
        }

        JTable table = MainManagerUI.styleTable(new JTable(model));
        center.add(new JScrollPane(table), BorderLayout.CENTER);

        frame.add(center, BorderLayout.CENTER);

        // Bottom Bar
        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        bottomBar.setBackground(Color.WHITE);
        bottomBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(226, 232, 240)));

        JButton updateBtn = MainManagerUI.createStyledButton("Update Contact Details", PRIMARY);
        updateBtn.addActionListener(e -> {
            JTextField phoneFld = new JTextField(sub.getPhone(), 15);
            JTextField addrFld = new JTextField(sub.getAddress(), 15);

            JPanel editPanel = new JPanel(new GridLayout(2, 2, 8, 8));
            editPanel.add(new JLabel("New Phone:")); editPanel.add(phoneFld);
            editPanel.add(new JLabel("New Address:")); editPanel.add(addrFld);

            int res = JOptionPane.showConfirmDialog(frame, editPanel, "Update Profile", JOptionPane.OK_CANCEL_OPTION);
            if (res == JOptionPane.OK_OPTION) {
                sub.setPhone(phoneFld.getText().trim());
                sub.setAddress(addrFld.getText().trim());
                JOptionPane.showMessageDialog(frame, "Contact profile updated.");
                frame.dispose();
                openSubscriberFrame(sub);
            }
        });

        bottomBar.add(updateBtn);
        frame.add(bottomBar, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private static JPanel createProfileItem(String label, String value) {
        JPanel p = new JPanel(new GridLayout(2, 1, 2, 2));
        p.setOpaque(false);
        JLabel l = new JLabel(label);
        l.setFont(new Font("Segoe UI", Font.BOLD, 10));
        l.setForeground(new Color(100, 116, 139));

        JLabel v = new JLabel(value);
        v.setFont(FONT_BOLD);
        v.setForeground(PRIMARY);

        p.add(l);
        p.add(v);
        return p;
    }
}
