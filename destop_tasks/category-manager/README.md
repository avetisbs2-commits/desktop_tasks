# Category Manager Desktop App - Student Tasks

This module is a Java Swing desktop application for practicing hierarchical structures, `ArrayList`, `HashSet`, recursion, `JTree`, and basic object-oriented programming.

The app already contains the user interface, the `Category` model class, and button connections. Your task is to complete the missing business logic in `src/CategoryManagerFrame.java`.

## Run This Module

From this folder:

```bash
javac src/*.java
java -cp src Main
```

## Data Storage

All data exists only in memory.

Root categories are stored in:

```java
private final ArrayList<Category> rootCategories = new ArrayList<>();
```

Use a `HashSet<String>` when checking duplicate child names under the same parent.

## Rules

Do not use databases, Spring, Hibernate, file storage, Stream API, or external frameworks.

## Tasks

### 1. Add Root Category

Complete `addRootCategory()`.

Practice:

- `ArrayList`
- `add()`
- loops
- `if`

### 2. Check Root Category Name

Complete `rootCategoryExists(String name)`.

Do not use streams.

### 3. Add Child Category

Complete `addChildCategory()`.

The child should be added under the currently selected tree node.

### 4. Check Duplicate Child Names

Complete `childNameExists(Category parent, String childName)`.

Practice:

- `HashSet`
- `add()`
- `contains()`

### 5. Remove Selected Category

Complete `removeSelectedCategory()`.

Remove root categories from `rootCategories`. Remove child categories from their parent's children list.

### 6. Find Category By Name

Complete `findCategory(String name)`.

Search all root categories and child categories.

### 7. Recursive Category Search

Complete `findCategoryRecursive(Category category, String name)`.

This is one of the main recursion exercises.

### 8. Count Direct Children

Complete `getDirectChildrenCount(Category category)`.

### 9. Count All Descendants

Complete `countDescendants(Category category)`.

Use recursion to count children and children of children.

### 10. Count All Categories

Complete `countAllCategories()`.

Count every root category and all descendants.

### 11. Calculate Maximum Tree Depth

Complete:

```java
private int calculateMaxDepth()
private int calculateDepth(Category category)
```

Use recursion to find the deepest category level.

### 12. Get Category Path

Complete `getCategoryPath(Category category)`.

Example:

```text
Products > Electronics > Computers > Laptops
```

### 13. Show Selected Category Information

Complete `showSelectedCategoryInfo()`.

Display:

- name
- parent
- direct child count
- descendant count
- complete path

### 14. Search From UI

Complete `searchCategory()`.

### 15. Print Hierarchy

Complete:

```java
private void printHierarchy()
private void printCategory(Category category, int level)
```

Use recursion and indentation.

## Practice Topics

- hierarchical data
- parent and child references
- `ArrayList<Category>`
- `HashSet<String>`
- recursion
- `JTree`
- loops
- `if` statements
- object-oriented programming
