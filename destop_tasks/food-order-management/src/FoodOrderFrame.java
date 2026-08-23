import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FoodOrderFrame extends JFrame {

    private final HashMap<Integer, MenuItem> menu = new HashMap<>();
    private final Queue<Order> orderQueue = new LinkedList<>();
    private final ArrayList<Order> completedOrders = new ArrayList<>();

    private JTextField menuItemIdField;
    private JTextField itemNameField;
    private JTextField priceField;

    private JTextField customerNameField;
    private JComboBox<MenuItem> menuItemComboBox;
    private JTextField quantityField;

    private JTextField searchCustomerField;

    private JLabel waitingOrdersLabel;
    private JLabel completedOrdersLabel;

    private JTextArea menuArea;
    private JTextArea nextOrderArea;
    private JTextArea completedOrdersArea;

    public FoodOrderFrame() {
        setTitle("Food Order Management");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createComponents();
        updateCounts();
    }

    private void createComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        topPanel.add(createMenuPanel());
        topPanel.add(createOrderPanel());

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        bottomPanel.add(createWaitingOrdersPanel());
        bottomPanel.add(createCompletedOrdersPanel());

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(bottomPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Menu Management"));

        JPanel formPanel = new JPanel(new GridBagLayout());
        menuItemIdField = new JTextField(12);
        itemNameField = new JTextField(12);
        priceField = new JTextField(12);

        addFormRow(formPanel, "Menu Item ID:", menuItemIdField, 0);
        addFormRow(formPanel, "Item Name:", itemNameField, 1);
        addFormRow(formPanel, "Price:", priceField, 2);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton addButton = new JButton("Add Menu Item");
        addButton.addActionListener(event -> addMenuItem());

        JButton removeButton = new JButton("Remove Menu Item");
        removeButton.addActionListener(event -> removeMenuItemFromField());

        JButton showButton = new JButton("Show Menu");
        showButton.addActionListener(event -> showMenu());

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(showButton);

        menuArea = new JTextArea(8, 25);
        menuArea.setEditable(false);

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(new JScrollPane(menuArea), BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createOrderPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Create Order"));

        JPanel formPanel = new JPanel(new GridBagLayout());
        customerNameField = new JTextField(12);
        menuItemComboBox = new JComboBox<>();
        quantityField = new JTextField(12);

        addFormRow(formPanel, "Customer Name:", customerNameField, 0);
        addComboRow(formPanel, "Menu Item:", menuItemComboBox, 1);
        addFormRow(formPanel, "Quantity:", quantityField, 2);

        JButton placeOrderButton = new JButton("Place Order");
        placeOrderButton.addActionListener(event -> placeOrder());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(placeOrderButton);

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createWaitingOrdersPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Waiting Orders"));

        waitingOrdersLabel = new JLabel("Waiting orders: 0");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton showNextButton = new JButton("Show Next Order");
        showNextButton.addActionListener(event -> showNextOrder());

        JButton takeNextButton = new JButton("Take Next Order");
        takeNextButton.addActionListener(event -> processNextOrder());

        JButton processAllButton = new JButton("Process All Orders");
        processAllButton.addActionListener(event -> processAllOrders());

        buttonPanel.add(showNextButton);
        buttonPanel.add(takeNextButton);
        buttonPanel.add(processAllButton);

        nextOrderArea = new JTextArea();
        nextOrderArea.setEditable(false);

        panel.add(waitingOrdersLabel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(new JScrollPane(nextOrderArea), BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createCompletedOrdersPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Completed Orders"));

        completedOrdersLabel = new JLabel("Completed orders: 0");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton showCompletedButton = new JButton("Show Completed Orders");
        showCompletedButton.addActionListener(event -> showCompletedOrders());

        JButton revenueButton = new JButton("Total Revenue");
        revenueButton.addActionListener(event -> {
            double revenue = calculateTotalRevenue();
            completedOrdersArea.setText("Total revenue: " + revenue);
        });

        JButton mostExpensiveButton = new JButton("Most Expensive Order");
        mostExpensiveButton.addActionListener(event -> showMostExpensiveOrder());

        searchCustomerField = new JTextField(10);
        JButton searchButton = new JButton("Search Customer");
        searchButton.addActionListener(event -> searchOrdersByCustomer());

        buttonPanel.add(showCompletedButton);
        buttonPanel.add(revenueButton);
        buttonPanel.add(mostExpensiveButton);
        buttonPanel.add(new JLabel("Customer:"));
        buttonPanel.add(searchCustomerField);
        buttonPanel.add(searchButton);

        completedOrdersArea = new JTextArea();
        completedOrdersArea.setEditable(false);

        panel.add(completedOrdersLabel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(new JScrollPane(completedOrdersArea), BorderLayout.SOUTH);

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

    private void addComboRow(JPanel panel, String labelText, JComboBox<MenuItem> comboBox, int row) {
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
        panel.add(comboBox, fieldConstraints);
    }

    private void removeMenuItemFromField() {
        String idText = menuItemIdField.getText();

        try {
            int id = Integer.parseInt(idText);
            boolean removed = removeMenuItem(id);

            if (removed) {
                JOptionPane.showMessageDialog(this, "Menu item removed.");
                refreshMenuDropdown();
                showMenu();
            } else {
                JOptionPane.showMessageDialog(this, "Menu item was not removed.");
            }
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(this, "Enter a valid menu item ID.");
        }
    }

    private void showNextOrder() {
        Order order = getNextOrder();

        if (order == null) {
            nextOrderArea.setText("No waiting order.");
        } else {
            nextOrderArea.setText(getOrderText(order));
        }
    }

    private void showMostExpensiveOrder() {
        Order order = findMostExpensiveOrder();

        if (order == null) {
            completedOrdersArea.setText("No completed order found.");
        } else {
            completedOrdersArea.setText(getOrderText(order));
        }
    }

    private void searchOrdersByCustomer() {
        String customerName = searchCustomerField.getText();
        ArrayList<Order> orders = findOrdersByCustomer(customerName);

        if (orders.size() == 0) {
            completedOrdersArea.setText("No completed orders found for this customer.");
            return;
        }

        String text = "";
        for (int i = 0; i < orders.size(); i++) {
            text = text + getOrderText(orders.get(i)) + "\n";
        }

        completedOrdersArea.setText(text);
    }

    private String getOrderText(Order order) {
        if (order == null) {
            return "";
        }

        return "Order ID: " + order.getId() + "\n"
                + "Customer: " + order.getCustomerName() + "\n"
                + "Item: " + order.getMenuItem().getName() + "\n"
                + "Quantity: " + order.getQuantity() + "\n"
                + "Total: " + calculateOrderTotal(order) + "\n";
    }

    private void refreshMenuDropdown() {
        menuItemComboBox.removeAllItems();

        for (MenuItem item : menu.values()) {
            menuItemComboBox.addItem(item);
        }
    }

    private void updateCounts() {
        waitingOrdersLabel.setText("Waiting orders: " + getWaitingOrderCount());
        completedOrdersLabel.setText("Completed orders: " + getCompletedOrderCount());
    }

    private void addMenuItem() {
        // TODO:
        // Read menu item ID
        // Read name
        // Read price
        // Validate input
        // Check whether the ID already exists
        // Create MenuItem
        // Add it to the menu HashMap
        // Refresh menu display/dropdown
    }

    private MenuItem findMenuItemById(int id) {
        // TODO:
        // Find and return MenuItem from menu HashMap

        return null;
    }

    private boolean removeMenuItem(int id) {
        // TODO:
        // Remove menu item by ID
        // Return true if an item was removed

        return false;
    }

    private void showMenu() {
        // TODO:
        // Iterate through all menu items
        // Display ID, name and price
    }

    private void placeOrder() {
        // TODO:
        // Read customer name
        // Get selected menu item
        // Read quantity
        // Validate input
        // Create Order object
        // Add it to orderQueue
        // Update waiting order count
    }

    private Order getNextOrder() {
        // TODO:
        // Return the first waiting order
        // Do not remove it from the queue

        return null;
    }

    private Order takeNextOrder() {
        // TODO:
        // Remove and return the first order from orderQueue

        return null;
    }

    private void processNextOrder() {
        // TODO:
        // Take the next order from the queue
        // If no order exists, show a message
        // Add processed order to completedOrders
        // Update waiting count
        // Update completed count
    }

    private void processAllOrders() {
        // TODO:
        // Process all waiting orders one by one
        // Continue until orderQueue is empty
    }

    private void showCompletedOrders() {
        // TODO:
        // Display all orders from completedOrders
    }

    private double calculateOrderTotal(Order order) {
        // TODO:
        // Calculate:
        // menu item price * quantity

        return 0;
    }

    private double calculateTotalRevenue() {
        // TODO:
        // Calculate the total price of all completed orders

        return 0;
    }

    private Order findMostExpensiveOrder() {
        // TODO:
        // Find completed order with the highest total price

        return null;
    }

    private ArrayList<Order> findOrdersByCustomer(String customerName) {
        // TODO:
        // Find all completed orders belonging to this customer

        return new ArrayList<>();
    }

    private int getWaitingOrderCount() {
        // TODO:
        // Return number of orders currently waiting

        return 0;
    }

    private int getCompletedOrderCount() {
        // TODO:
        // Return number of completed orders

        return 0;
    }

    private boolean cancelOrder(int orderId) {
        // TODO:
        // Find a waiting order by ID
        // Remove it from the queue

        return false;
    }
}
