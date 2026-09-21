import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MainManagerUI {

    private static final Color PRIMARY_COLOR = new Color(30, 41, 59);     // Dark Slate
    private static final Color ACCENT_COLOR = new Color(37, 99, 235);      // Royal Blue
    private static final Color BG_LIGHT = new Color(248, 250, 252);        // Soft White
    private static final Color CARD_BG = Color.WHITE;
    private static final Font FONT_HEADER = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font FONT_REGULAR = new Font("Segoe UI", Font.PLAIN, 13);

    public static void openMainManagerFrame(MainManager manager) {
        JFrame frame = new JFrame("Fleet Enterprise OS — Executive Dashboard [" + manager.getFirstName() + " " + manager.getLastName() + "]");
        frame.setSize(1150, 720);
        frame.setMinimumSize(new Dimension(1000, 650));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(BG_LIGHT);
        frame.setLayout(new BorderLayout());

        // Top Navigation Bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(PRIMARY_COLOR);
        topBar.setBorder(new EmptyBorder(12, 24, 12, 24));

        JLabel appTitle = new JLabel("TAXI FLEET MANAGEMENT — ADMIN CONSOLE");
        appTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        appTitle.setForeground(Color.WHITE);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        userPanel.setOpaque(false);
        JLabel userBadge = new JLabel("Logged in as: " + manager.getFirstName() + " (" + manager.getUserName() + ")");
        userBadge.setForeground(new Color(203, 213, 225));
        userBadge.setFont(FONT_REGULAR);

        JButton logoutBtn = createStyledButton("Logout", new Color(220, 38, 38));
        logoutBtn.addActionListener(e -> {
            frame.dispose();
            myForm.main(null);
        });

        userPanel.add(userBadge);
        userPanel.add(logoutBtn);
        topBar.add(appTitle, BorderLayout.WEST);
        topBar.add(userPanel, BorderLayout.EAST);
        frame.add(topBar, BorderLayout.NORTH);

        // Center Content with Tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(FONT_BOLD);
        tabbedPane.setBackground(Color.WHITE);

        // Tab 1: Fleet Overview & KPIs
        tabbedPane.addTab("Fleet & Taxis", createFleetPanel());

        // Tab 2: Subscriptions
        tabbedPane.addTab("Subscribers", createSubscribersPanel());

        // Tab 3: Branch Managers
        tabbedPane.addTab("Branch Managers", createManagersPanel());

        // Tab 4: System Data & File I/O
        tabbedPane.addTab("Backup & Persistence", createIOPanel(frame));

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static JPanel createFleetPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(BG_LIGHT);
        panel.setBorder(new EmptyBorder(15, 20, 20, 20));

        // Metric KPI cards
        JPanel kpiRow = new JPanel(new GridLayout(1, 4, 15, 0));
        kpiRow.setOpaque(false);
        var taxis = systemDataBase.getInstance().getTaxis();
        long availableCount = taxis.stream().filter(Taxi::isAvailable).count();

        kpiRow.add(createKpiCard("Total Fleet", String.valueOf(taxis.size()), new Color(59, 130, 246)));
        kpiRow.add(createKpiCard("Available Taxis", String.valueOf(availableCount), new Color(16, 185, 129)));
        kpiRow.add(createKpiCard("Dispatched / Active", String.valueOf(taxis.size() - availableCount), new Color(245, 158, 11)));
        kpiRow.add(createKpiCard("Stations", String.valueOf(systemDataBase.getInstance().getStations().size()), new Color(139, 92, 246)));
        panel.add(kpiRow, BorderLayout.NORTH);

        // Taxis Table
        String[] cols = {"Taxi Code", "Type", "Status", "Base / Min Rate", "Assigned Manager"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        for (Taxi t : taxis) {
            String responsible = "None";
            for (Manager m : systemDataBase.getInstance().getManagers()) {
                if (m.getTaxis().contains(t)) {
                    responsible = m.getFirstName() + " (ID: " + m.getId() + ")";
                    break;
                }
            }
            model.addRow(new Object[]{
                    t.getTaxiCode(),
                    t.getClass().getSimpleName(),
                    t.isAvailable() ? "AVAILABLE" : "OCCUPIED",
                    "$" + String.format("%.2f", t.getMinPrice()),
                    responsible
            });
        }

        JTable table = styleTable(new JTable(model));
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Actions toolbar
        JPanel actionToolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        actionToolbar.setOpaque(false);

        JButton addTaxiBtn = createStyledButton("+ Add New Taxi", ACCENT_COLOR);
        JButton assignTaxiBtn = createStyledButton("Assign Taxi to Manager", PRIMARY_COLOR);

        addTaxiBtn.addActionListener(e -> Forms.addTaxiForm());
        assignTaxiBtn.addActionListener(e -> Forms.assignTaxiToManagerForm());

        actionToolbar.add(addTaxiBtn);
        actionToolbar.add(assignTaxiBtn);
        panel.add(actionToolbar, BorderLayout.SOUTH);

        return panel;
    }

    private static JPanel createSubscribersPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(BG_LIGHT);
        panel.setBorder(new EmptyBorder(15, 20, 20, 20));

        String[] cols = {"Sub Code", "Full Name", "Phone", "Registered Address", "Total Orders"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        var subs = systemDataBase.getInstance().getSubscriptions();
        subs.sort(Comparator.comparing(Subscription::getLastName));
        var ordersPerSub = systemDataBase.getInstance().getOrdersPerSub();

        for (Subscription s : subs) {
            int ordersCount = ordersPerSub.containsKey(s.getSubCode()) ? ordersPerSub.get(s.getSubCode()).size() : 0;
            model.addRow(new Object[]{
                    s.getSubCode(),
                    s.getFirstName() + " " + s.getLastName(),
                    s.getPhone(),
                    s.getAddress(),
                    ordersCount
            });
        }

        JTable table = styleTable(new JTable(model));
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        actions.setOpaque(false);
        JButton addSubBtn = createStyledButton("+ Register Subscriber", ACCENT_COLOR);
        addSubBtn.addActionListener(e -> Forms.addSubscriptionForm());
        actions.add(addSubBtn);
        panel.add(actions, BorderLayout.SOUTH);

        return panel;
    }

    private static JPanel createManagersPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(BG_LIGHT);
        panel.setBorder(new EmptyBorder(15, 20, 20, 20));

        String[] cols = {"Manager ID", "Name", "Phone", "Assigned Territory / Address", "Managed Vehicles"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        var managers = systemDataBase.getInstance().getManagers();
        for (Manager m : managers) {
            model.addRow(new Object[]{
                    m.getId(),
                    m.getFirstName() + " " + m.getLastName() + (m instanceof MainManager ? " (Admin)" : ""),
                    m.getPhone(),
                    m.getAddress(),
                    m.getTaxis().size() + " Taxis"
            });
        }

        JTable table = styleTable(new JTable(model));
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        actions.setOpaque(false);
        JButton addMgrBtn = createStyledButton("+ Onboard Manager", ACCENT_COLOR);
        addMgrBtn.addActionListener(e -> Forms.addManagerForm());
        actions.add(addMgrBtn);
        panel.add(actions, BorderLayout.SOUTH);

        return panel;
    }

    private static JPanel createIOPanel(JFrame parent) {
        JPanel panel = new JPanel(new GridLayout(2, 2, 20, 20));
        panel.setBackground(BG_LIGHT);
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));

        panel.add(createIOCard("Load Members Dataset", "Import subscriber lists from local members.txt", "Import Subscribers", e -> {
            try {
                List<String> lines = Files.readAllLines(Paths.get("members.txt"));
                int count = 0;
                for (String line : lines) {
                    String[] parts = line.trim().split(" ");
                    if (parts.length == 5) {
                        Subscription sub = new Subscription(parts[0], parts[1], parts[2], parts[3], parts[4]);
                        if (systemDataBase.getInstance().addSubscription(sub)) count++;
                    }
                }
                JOptionPane.showMessageDialog(parent, "Import finished. " + count + " new subscribers registered.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(parent, "Error reading members.txt: " + ex.getMessage(), "I/O Failure", JOptionPane.ERROR_MESSAGE);
            }
        }));

        panel.add(createIOCard("Export Active Subscriptions", "Serialize all subscribers to members.txt", "Export Subscribers", e -> {
            try {
                List<Subscription> subs = systemDataBase.getInstance().getSubscriptions();
                List<String> lines = new ArrayList<>();
                for (Subscription s : subs) {
                    lines.add(s.getSubCode() + " " + s.getFirstName() + " " + s.getLastName() + " " + s.getAddress() + " " + s.getPhone());
                }
                Files.write(Paths.get("members.txt"), lines);
                JOptionPane.showMessageDialog(parent, "Export successful: " + lines.size() + " records saved.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(parent, "Failed to export: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }));

        panel.add(createIOCard("Export Taxis & Fleet", "Write all taxi records and assignments to Taxi.txt", "Export Fleet", e -> {
            try {
                var taxis = systemDataBase.getInstance().getTaxis();
                List<String> lines = new ArrayList<>();
                for (Taxi t : taxis) {
                    lines.add(t.getTaxiCode() + " | " + t.getClass().getSimpleName() + " | Available: " + t.isAvailable() + " | Rate: " + t.getMinPrice());
                }
                Files.write(Paths.get("Taxi.txt"), lines);
                JOptionPane.showMessageDialog(parent, "Exported " + lines.size() + " taxis successfully.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(parent, "Failed to write Taxi.txt: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }));

        panel.add(createIOCard("Export Trip Orders", "Save transaction logs and bookings to orders.txt", "Export Orders", e -> {
            try {
                var orders = systemDataBase.getInstance().getOrders();
                List<String> lines = new ArrayList<>();
                for (Order o : orders) {
                    lines.add("Order #" + o.getOrderNum() + " | Sub: " + o.getSubCode() + " | Taxi: " + o.getTaxi().getTaxiCode() + " | Price: " + o.getOrderPrice());
                }
                Files.write(Paths.get("orders.txt"), lines);
                JOptionPane.showMessageDialog(parent, "Orders exported successfully.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(parent, "Failed to export orders: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }));

        return panel;
    }

    private static JPanel createIOCard(String title, String desc, String btnText, java.awt.event.ActionListener action) {
        JPanel card = new JPanel(new BorderLayout(10, 15));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JPanel textPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        textPanel.setOpaque(false);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(FONT_HEADER);
        titleLabel.setForeground(PRIMARY_COLOR);

        JLabel descLabel = new JLabel(desc);
        descLabel.setFont(FONT_REGULAR);
        descLabel.setForeground(new Color(100, 116, 139));

        textPanel.add(titleLabel);
        textPanel.add(descLabel);

        JButton actionBtn = createStyledButton(btnText, ACCENT_COLOR);
        actionBtn.addActionListener(action);

        card.add(textPanel, BorderLayout.CENTER);
        card.add(actionBtn, BorderLayout.SOUTH);
        return card;
    }

    public static JPanel createKpiCard(String label, String value, Color accent) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 4, 0, 0, accent),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                        new EmptyBorder(12, 16, 12, 16)
                )
        ));

        JLabel valLbl = new JLabel(value);
        valLbl.setFont(new Font("Segoe UI", Font.BOLD, 22));
        valLbl.setForeground(PRIMARY_COLOR);

        JLabel descLbl = new JLabel(label.toUpperCase());
        descLbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
        descLbl.setForeground(new Color(100, 116, 139));

        card.add(valLbl, BorderLayout.CENTER);
        card.add(descLbl, BorderLayout.SOUTH);
        return card;
    }

    public static JTable styleTable(JTable table) {
        table.setRowHeight(32);
        table.setFont(FONT_REGULAR);
        table.setGridColor(new Color(241, 245, 249));
        table.setSelectionBackground(new Color(224, 242, 254));
        table.setSelectionForeground(Color.BLACK);

        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_BOLD);
        header.setBackground(new Color(241, 245, 249));
        header.setForeground(PRIMARY_COLOR);
        header.setPreferredSize(new Dimension(0, 36));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        return table;
    }

    public static JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BOLD);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(8, 16, 8, 16));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
