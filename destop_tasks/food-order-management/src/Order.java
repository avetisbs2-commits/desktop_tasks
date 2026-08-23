public class Order {

    private int id;
    private String customerName;
    private MenuItem menuItem;
    private int quantity;

    public Order(int id, String customerName, MenuItem menuItem, int quantity) {
        this.id = id;
        this.customerName = customerName;
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", menuItem=" + menuItem +
                ", quantity=" + quantity +
                '}';
    }
}
