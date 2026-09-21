import javax.swing.*;
import java.awt.*;

public class Forms {
    public static void addSubscriptionForm() {
        JTextField idField = new JTextField(15);
        JTextField firstNameField = new JTextField(15);
        JTextField lastNameField = new JTextField(15);
        JTextField addressField = new JTextField(15);
        JTextField phoneField = new JTextField(15);

        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.add(new JLabel("Subscription ID:")); panel.add(idField);
        panel.add(new JLabel("First Name:")); panel.add(firstNameField);
        panel.add(new JLabel("Last Name:")); panel.add(lastNameField);
        panel.add(new JLabel("Address:")); panel.add(addressField);
        panel.add(new JLabel("Phone:")); panel.add(phoneField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add New Subscription", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String id = idField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String address = addressField.getText();
            String phone = phoneField.getText();

            Subscription newSub = new Subscription(id, firstName, lastName, address, phone);
            systemDataBase.getInstance().addSubscription(newSub);
            JOptionPane.showMessageDialog(null, "Subscription added successfully.");
        }
    }

    public static void addManagerForm() {
        String[] options = {"General Manager", "Main Manager"};
        int type = JOptionPane.showOptionDialog(null, "Select Manager Type:", "Manager Type",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (type == -1) return;

        JTextField idField = new JTextField(15);
        JTextField firstNameField = new JTextField(15);
        JTextField lastNameField = new JTextField(15);
        JTextField phoneField = new JTextField(15);
        JTextField addressField = new JTextField(15);
        JTextField userField = new JTextField(15);
        JTextField passField = new JTextField(15);

        JPanel panel = new JPanel(new GridLayout(type == 1 ? 7 : 5, 2, 5, 5));
        panel.add(new JLabel("ID:")); panel.add(idField);
        panel.add(new JLabel("First Name:")); panel.add(firstNameField);
        panel.add(new JLabel("Last Name:")); panel.add(lastNameField);
        panel.add(new JLabel("Phone:")); panel.add(phoneField);
        panel.add(new JLabel("Address:")); panel.add(addressField);

        if (type == 1) {
            panel.add(new JLabel("Username:")); panel.add(userField);
            panel.add(new JLabel("Password:")); panel.add(passField);
        }

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Manager", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String id = idField.getText();
            String fn = firstNameField.getText();
            String ln = lastNameField.getText();
            String phone = phoneField.getText();
            String address = addressField.getText();

            if (type == 1) {
                String user = userField.getText();
                String pass = passField.getText();
                MainManager m = new MainManager(id, fn, ln, phone, address, user, pass);
                systemDataBase.getInstance().addManager(m);
            } else {
                Manager m = new Manager(id, fn, ln, phone, address);
                systemDataBase.getInstance().addManager(m);
            }

            JOptionPane.showMessageDialog(null, "Manager added successfully.");
        }
    }

    public static void addTaxiForm() {
        String[] options = {"General Taxi", "Express Taxi", "Intercity Taxi"};
        int type = JOptionPane.showOptionDialog(null, "Select Taxi Type:", "Taxi Type",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (type == -1) return;

        JTextField codeField = new JTextField(15);
        JCheckBox availableBox = new JCheckBox("Available", true);
        JTextField priceField = new JTextField(15);

        JTextField extraField = new JTextField(15);
        JTextField boolField = new JTextField(15);
        JTextField hoursField = new JTextField(15);

        JPanel panel = new JPanel(new GridLayout(type == 0 ? 3 : 5, 2, 5, 5));
        panel.add(new JLabel("Taxi Code:")); panel.add(codeField);
        panel.add(new JLabel("Min Price:")); panel.add(priceField);
        panel.add(new JLabel("")); panel.add(availableBox);

        if (type == 1) {
            panel.add(new JLabel("City Taxi (true/false):")); panel.add(boolField);
            panel.add(new JLabel("Extra Price:")); panel.add(extraField);
        } else if (type == 2) {
            panel.add(new JLabel("Extra Price:")); panel.add(extraField);
            panel.add(new JLabel("Max Hours:")); panel.add(hoursField);
        }

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Taxi", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String code = codeField.getText();
            boolean available = availableBox.isSelected();
            double minPrice = Double.parseDouble(priceField.getText());


            if (type == 0) {
                Taxi t = new Taxi(code, available, minPrice);
                systemDataBase.getInstance().addTaxi(t);
            } else if (type == 1) {
                boolean city = Boolean.parseBoolean(boolField.getText());
                double extra = Double.parseDouble(extraField.getText());
                ExpressTaxi t = new ExpressTaxi(code, available, minPrice, city, extra);
                systemDataBase.getInstance().addTaxi(t);
            } else if (type == 2) {
                double extra = Double.parseDouble(extraField.getText());
                int max = Integer.parseInt(hoursField.getText());
                IntercityTaxi t = new IntercityTaxi(code, available, minPrice, extra, max);
                systemDataBase.getInstance().addTaxi(t);
            }
            JOptionPane.showMessageDialog(null, "Taxi added successfully.");
        }
    }

    public static void assignTaxiToManagerForm() {
        JTextField taxiCodeField = new JTextField(15);
        JTextField managerCodeField = new JTextField(15);

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.add(new JLabel("Taxi Code:")); panel.add(taxiCodeField);
        panel.add(new JLabel("Manager ID:")); panel.add(managerCodeField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Assign Taxi to Manager", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String taxiCode = taxiCodeField.getText();
            String managerCode = managerCodeField.getText();


            Taxi taxi = systemDataBase.getInstance().getTaxis().stream().filter(t -> t.getTaxiCode().equals(taxiCode)).findFirst().orElse(null);
            Manager manager = systemDataBase.getInstance().getManagers().stream().filter(m -> m.getId().equals(managerCode)).findFirst().orElse(null);

            if (taxi != null && manager != null) {
                manager.addTaxi(taxi);
                JOptionPane.showMessageDialog(null, "Taxi assigned to manager successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Taxi or Manager not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}


