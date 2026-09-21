import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ManagerUI {

    private static final Color PRIMARY_COLOR = new Color(15, 23, 42);      // Slate 900
    private static final Color ACCENT_COLOR = new Color(13, 148, 136);     // Teal 600
    private static final Color BG_COLOR = new Color(241, 245, 249);        // Light Slate
    private static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font FONT_REG = new Font("Segoe UI", Font.PLAIN, 13);

    public static void openManagerFrame(Manager manager) {
        JFrame frame = new JFrame("Operations Portal — Branch Manager: " + manager.getFirstName() + " " + manager.getLastName());
        frame.setSize(950, 620);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(BG_COLOR);
        frame.setLayout(new BorderLayout());

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PRIMARY_COLOR);
        header.setBorder(new EmptyBorder(14, 20, 14, 20));

        JLabel title = new JLabel("BRANCH DISPATCH CONSOLE — " + manager.getAddress().toUpperCase());
        title.setFont(new Font("Segoe UI", Font.BOLD, 15));
        title.setForeground(Color.WHITE);

        JButton logoutBtn = MainManagerUI.createStyledButton("Logout", new Color(220, 38, 38));
        logoutBtn.addActionListener(e -> {
            frame.dispose();
            myForm.main(null);
        });

        header.add(title, BorderLayout.WEST);
        header.add(logoutBtn, BorderLayout.EAST);
        frame.add(header, BorderLayout.NORTH);

        // Center split layout: Left = Assigned Taxis, Right = Active Orders
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(420);
        splitPane.setResizeWeight(0.5);

        // Left Panel: Assigned Taxis
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        leftPanel.setBackground(Color.WHITE);

        JLabel leftTitle = new JLabel("My Assigned Taxis (" + manager.getTaxis().size() + ")");
        leftTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        leftPanel.add(leftTitle, BorderLayout.NORTH);

        String[] taxiCols = {"Code", "Type", "Status", "Min Price"};
        DefaultTableModel taxiModel = new DefaultTableModel(taxiCols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        for (Taxi t : manager.getTaxis()) {
            taxiModel.addRow(new Object[]{t.getTaxiCode(), t.getClass().getSimpleName(), t.isAvailable() ? "AVAILABLE" : "OCCUPIED", "$" + t.getMinPrice()});
        }
        leftPanel.add(new JScrollPane(MainManagerUI.styleTable(new JTable(taxiModel))), BorderLayout.CENTER);

        // Right Panel: Manager Orders
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        rightPanel.setBackground(Color.WHITE);

        JLabel rightTitle = new JLabel("Branch Orders");
        rightTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        rightPanel.add(rightTitle, BorderLayout.NORTH);

        String[] orderCols = {"Order #", "Sub Code", "Taxi", "Price", "Date/Time"};
        DefaultTableModel orderModel = new DefaultTableModel(orderCols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        for (Order o : manager.getOrders()) {
            orderModel.addRow(new Object[]{
                    o.getOrderNum(),
                    o.getSubCode(),
                    o.getTaxi().getTaxiCode(),
                    "$" + o.getOrderPrice(),
                    o.getDay() + "/" + o.getMonth() + " @ " + o.getHour() + ":00"
            });
        }
        rightPanel.add(new JScrollPane(MainManagerUI.styleTable(new JTable(orderModel))), BorderLayout.CENTER);

        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
        frame.add(splitPane, BorderLayout.CENTER);

        // Bottom Controls
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        footer.setBackground(Color.WHITE);
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(226, 232, 240)));

        JButton addOrderBtn = MainManagerUI.createStyledButton("+ Dispatch New Trip", ACCENT_COLOR);
        JButton changeTaxiBtn = MainManagerUI.createStyledButton("Reassign / Change Taxi", new Color(100, 116, 139));

        addOrderBtn.addActionListener(e -> {
            showAddOrderModal(frame, manager);
            frame.dispose();
            openManagerFrame(manager); // reload tables
        });

        changeTaxiBtn.addActionListener(e -> {
            showChangeTaxiModal(frame, manager);
            frame.dispose();
            openManagerFrame(manager); // reload tables
        });

        footer.add(changeTaxiBtn);
        footer.add(addOrderBtn);
        frame.add(footer, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private static void showAddOrderModal(JFrame parent, Manager manager) {
        JTextField orderNumField = new JTextField();
        JTextField subCodeField = new JTextField();
        JTextField taxiCodeField = new JTextField();
        JTextField dayField = new JTextField("15");
        JTextField monthField = new JTextField("10");
        JTextField hourField = new JTextField("14");

        JPanel form = new JPanel(new GridLayout(6, 2, 8, 8));
        form.add(new JLabel("Order ID:")); form.add(orderNumField);
        form.add(new JLabel("Subscriber ID:")); form.add(subCodeField);
        form.add(new JLabel("Taxi Code:")); form.add(taxiCodeField);
        form.add(new JLabel("Day (1-31):")); form.add(dayField);
        form.add(new JLabel("Month (1-12):")); form.add(monthField);
        form.add(new JLabel("Hour (0-23):")); form.add(hourField);

        int result = JOptionPane.showConfirmDialog(parent, form, "Dispatch New Order", JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) return;

        try {
            String oNum = orderNumField.getText().trim();
            String sCode = subCodeField.getText().trim();
            String tCode = taxiCodeField.getText().trim();
            int day = Integer.parseInt(dayField.getText().trim());
            int month = Integer.parseInt(monthField.getText().trim());
            int hour = Integer.parseInt(hourField.getText().trim());

            Subscription sub = systemDataBase.getInstance().getSubscriptions().stream()
                    .filter(s -> s.getSubCode().equalsIgnoreCase(sCode)).findFirst().orElse(null);
            if (sub == null) {
                JOptionPane.showMessageDialog(parent, "Subscriber does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Taxi taxi = manager.getTaxis().stream()
                    .filter(t -> t.getTaxiCode().equalsIgnoreCase(tCode)).findFirst().orElse(null);
            if (taxi == null) {
                JOptionPane.showMessageDialog(parent, "Taxi not found under your management.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!taxi.isAvailable()) {
                JOptionPane.showMessageDialog(parent, "Selected taxi is currently occupied.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Order newOrder = new Order(oNum, manager.getId(), day, month, hour, sCode, taxi, taxi.getMinPrice());
            taxi.setAvailable(false);
            if (systemDataBase.getInstance().addOrder(newOrder)) {
                JOptionPane.showMessageDialog(parent, "Order successfully created and dispatched.");
            } else {
                JOptionPane.showMessageDialog(parent, "Order ID already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parent, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void showChangeTaxiModal(JFrame parent, Manager manager) {
        String orderNum = JOptionPane.showInputDialog(parent, "Enter Order ID to reassign:");
        if (orderNum == null || orderNum.isBlank()) return;

        Order order = manager.getOrders().stream()
                .filter(o -> o.getOrderNum().equalsIgnoreCase(orderNum.trim())).findFirst().orElse(null);
        if (order == null) {
            JOptionPane.showMessageDialog(parent, "Order not found in your branch.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String newTaxiCode = JOptionPane.showInputDialog(parent, "Enter replacement Taxi Code (from your fleet):");
        if (newTaxiCode == null || newTaxiCode.isBlank()) return;

        Taxi newTaxi = manager.getTaxis().stream()
                .filter(t -> t.getTaxiCode().equalsIgnoreCase(newTaxiCode.trim())).findFirst().orElse(null);
        if (newTaxi == null) {
            JOptionPane.showMessageDialog(parent, "New taxi is not in your fleet.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!newTaxi.isAvailable()) {
            JOptionPane.showMessageDialog(parent, "Replacement taxi is occupied.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        order.getTaxi().setAvailable(true);
        newTaxi.setAvailable(false);
        order.setTaxi(newTaxi);
        order.setOrderPrice(newTaxi.getMinPrice());
        JOptionPane.showMessageDialog(parent, "Taxi re-assigned successfully to Order #" + order.getOrderNum());
    }
}
