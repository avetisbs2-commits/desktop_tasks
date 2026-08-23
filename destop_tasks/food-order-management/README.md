# Food Order Management Desktop App - Student Tasks

This module is a Java Swing desktop application for practicing Java Collections.

The app already contains the user interface, model classes, and button connections. Your task is to complete the missing collection and business logic in `src/FoodOrderFrame.java`.

## Run This Module

From this folder:

```bash
javac src/*.java
java -cp src Main
```

## Collections

Use these collections:

```java
private final HashMap<Integer, MenuItem> menu = new HashMap<>();
private final Queue<Order> orderQueue = new LinkedList<>();
private final ArrayList<Order> completedOrders = new ArrayList<>();
```

Purpose:

- `HashMap` stores menu items.
- `Queue` stores waiting orders.
- `ArrayList` stores completed orders.

## Rules

Do not use databases, Spring, Hibernate, file storage, Stream API, or external frameworks.

## Tasks

### 1. Add Menu Item

Complete `addMenuItem()`.

Practice:

- `put()`
- `containsKey()`

### 2. Find Menu Item By ID

Complete `findMenuItemById(int id)`.

Practice:

- `get()`

### 3. Remove Menu Item

Complete `removeMenuItem(int id)`.

Practice:

- `remove()`
- `containsKey()`

### 4. Show All Menu Items

Complete `showMenu()`.

Practice:

- `entrySet()`
- `values()`
- `keySet()`

### 5. Place Order

Complete `placeOrder()`.

Practice:

- `offer()`

### 6. Show Next Order

Complete `getNextOrder()`.

Practice:

- `peek()`

### 7. Take Next Order

Complete `takeNextOrder()`.

Practice:

- `poll()`

### 8. Process Next Order

Complete `processNextOrder()`.

Practice:

- `Queue`
- `ArrayList`

### 9. Process All Orders

Complete `processAllOrders()`.

Practice:

- `isEmpty()`
- `poll()`

### 10. Show Completed Orders

Complete `showCompletedOrders()`.

### 11. Calculate Order Total

Complete `calculateOrderTotal(Order order)`.

Calculate:

```text
menu item price * quantity
```

### 12. Calculate Total Revenue

Complete `calculateTotalRevenue()`.

Use the `completedOrders` ArrayList.

### 13. Find Most Expensive Completed Order

Complete `findMostExpensiveOrder()`.

Do not use `Collections.max()` or Stream API.

### 14. Search Completed Orders By Customer

Complete `findOrdersByCustomer(String customerName)`.

### 15. Count Waiting Orders

Complete `getWaitingOrderCount()`.

Practice:

- `size()`

### 16. Count Completed Orders

Complete `getCompletedOrderCount()`.

### Optional Harder Task

Complete `cancelOrder(int orderId)`.

## Practice Topics

- `HashMap<Integer, MenuItem>`
- `Queue<Order>`
- `LinkedList`
- `ArrayList<Order>`
- `put()`
- `get()`
- `containsKey()`
- `remove()`
- `offer()`
- `peek()`
- `poll()`
- `isEmpty()`
- `size()`
- loops
- `if` statements
- searching
- basic calculations
