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
        String idText = menuItemIdField.getText();
        String name = itemNameField.getText();
        String priceText = priceField.getText();

        if (idText.isEmpty() || name.isEmpty() || priceText.isEmpty()){
            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int id;
        double price;
        try {
            id = Integer.parseInt(idText);
            price = Double.parseDouble(priceText);

            if (price < 0) {
                JOptionPane.showMessageDialog(this, "Price cannot be negative.", "Invalid Price", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid numeric ID and price.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (menu.containsKey(id)) {
            JOptionPane.showMessageDialog(
                    this,
                    "A menu item with this ID already exists.",
                    "Duplicate ID",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        MenuItem newMenuItem = new MenuItem(id, name, price);
        menu.put(id, newMenuItem);
        refreshMenuDropdown();

        menuItemIdField.setText("");
        itemNameField.setText("");
        priceField.setText("");

        JOptionPane.showMessageDialog(
                this,
                "Menu item added successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private MenuItem findMenuItemById(int id) {
        return menu.get(id);
    }

    private boolean removeMenuItem(int id) {
        if (!menu.containsKey(id)){
            return false;
        } else {
            menu.remove(id);
            return true;
        }
    }

    private void showMenu() {
        if (menu.isEmpty()){
            JOptionPane.showMessageDialog(
                    this,
                    "No Item found.",
                    "Menu",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        StringBuilder sb = new StringBuilder("Menu:\n\n");
        for (MenuItem menuItem : menu.values()) {
            sb.append("ID: ").append(menuItem.getId())
                    .append(" | Item: ").append(menuItem.getName()).append(" ")
                    .append(" | Price: ").append(menuItem.getPrice())
                    .append("\n-----------------------------------\n");
        }

        menuArea.setText(sb.toString());
    }

    private void placeOrder() {
        String customerName = customerNameField.getText();
        MenuItem selectedItem = (MenuItem) menuItemComboBox.getSelectedItem();
        String quantityText = quantityField.getText();

        if (customerName.isEmpty() || quantityText.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all order fields.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (selectedItem == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a menu item.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Quantity must be at least 1.",
                        "Invalid Quantity",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid numeric quantity.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        int orderId = orderQueue.size() + completedOrders.size() + 1;
        Order newOrder = new Order(orderId, customerName, selectedItem, quantity);

        orderQueue.add(newOrder);
        updateCounts();

        customerNameField.setText("");
        quantityField.setText("");
        menuItemComboBox.setSelectedIndex(-1);

        JOptionPane.showMessageDialog(
                this,
                "Order placed successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private Order getNextOrder() {
        if (orderQueue.isEmpty()){
            return null;
        }
        return orderQueue.peek();
    }

    private Order takeNextOrder() {
        if (orderQueue.isEmpty()){
            return null;
        }
        return orderQueue.poll();
    }

    private void processNextOrder() {
        Order nextOrder = takeNextOrder();

        if (nextOrder == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "No waiting orders to process.",
                    "Queue Empty",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        completedOrders.add(nextOrder);
        updateCounts();
    }

    private void processAllOrders() {
        if (orderQueue.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "No waiting orders to process.",
                    "Queue Empty",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        while (!orderQueue.isEmpty()) {
            completedOrders.add(orderQueue.poll());
        }

        updateCounts();
        JOptionPane.showMessageDialog(
                this,
                "All waiting orders have been processed!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showCompletedOrders() {
        if (completedOrders.isEmpty()){
            JOptionPane.showMessageDialog(
                    this,
                    "No completed order yet.",
                    "Completed orders",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        StringBuilder sb = new StringBuilder("Completed Orders:\n\n");
        for (Order order : completedOrders){
            sb.append("Customer: ").append(order.getCustomerName())
                    .append(" ")
                    .append(order.getQuantity())
                    .append("-")
                    .append(order.getMenuItem().getName())
                    .append(" | Price: $").append(calculateOrderTotal(order))
                    .append("\n-----------------------------------\n");
        }

        completedOrdersArea.setText(sb.toString());
    }

    private double calculateOrderTotal(Order order) {
        double totalPrice = order.getMenuItem().getPrice() * order.getQuantity();
        return totalPrice;
    }

    private double calculateTotalRevenue() {
        if (completedOrders.isEmpty()){
            return 0.0;
        }

        double totalRevenue = 0.0;
        for (Order order : completedOrders){
            totalRevenue += calculateOrderTotal(order);
        }
        return totalRevenue;
    }

    private Order findMostExpensiveOrder() {
        if (completedOrders.isEmpty()){
            return null;
        }

        Order mostExpensiveOrder = completedOrders.get(0);
        for (Order order : completedOrders){
            if (calculateOrderTotal(order) > calculateOrderTotal(mostExpensiveOrder)){
                mostExpensiveOrder = order;
            }
        }
        return mostExpensiveOrder;
    }

    private ArrayList<Order> findOrdersByCustomer(String customerName) {
        if (completedOrders.isEmpty()){
            return new ArrayList<>();
        }

        ArrayList<Order> customerOrders = new ArrayList<>();
        for (Order order : completedOrders){
            if (order.getCustomerName().equalsIgnoreCase(customerName)){
                customerOrders.add(order);
            }
        }
        return customerOrders;
    }

    private int getWaitingOrderCount() {
        return orderQueue.size();
    }

    private int getCompletedOrderCount() {
        return completedOrders.size();
    }

    private boolean cancelOrder(int orderId) {
        for (Order order : orderQueue){
            if (orderId == order.getId()){
                orderQueue.remove(order);
                return true;
            }
        }
        return false;
    }
}