import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class CategoryManagerFrame extends JFrame {

    private final ArrayList<Category> rootCategories = new ArrayList<>();

    private JTree categoryTree;
    private DefaultMutableTreeNode treeRoot;
    private DefaultTreeModel treeModel;

    private JTextField rootCategoryNameField;
    private JTextField childCategoryNameField;
    private JTextField searchCategoryNameField;

    private JTextArea categoryInfoArea;
    private JTextArea statisticsArea;
    private JTextArea hierarchyArea;

    public CategoryManagerFrame() {
        setTitle("Category Manager");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createComponents();
        refreshTree();
        updateStatistics();
    }

    private void createComponents() {
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(createTreePanel());
        splitPane.setRightComponent(createManagementPanel());
        splitPane.setDividerLocation(330);

        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel createTreePanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Categories"));

        treeRoot = new DefaultMutableTreeNode("Categories");
        treeModel = new DefaultTreeModel(treeRoot);
        categoryTree = new JTree(treeModel);
        categoryTree.setRootVisible(false);
        categoryTree.addTreeSelectionListener(event -> showSelectedCategoryInfo());

        JPanel buttonPanel = new JPanel();

        JButton expandButton = new JButton("Expand All");
        expandButton.addActionListener(event -> expandAllTreeRows());

        JButton collapseButton = new JButton("Collapse All");
        collapseButton.addActionListener(event -> collapseAllTreeRows());

        buttonPanel.add(expandButton);
        buttonPanel.add(collapseButton);

        panel.add(new JScrollPane(categoryTree), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JPanel formPanel = new JPanel(new GridBagLayout());

        rootCategoryNameField = new JTextField(18);
        childCategoryNameField = new JTextField(18);
        searchCategoryNameField = new JTextField(18);

        JButton addRootButton = new JButton("Add Root");
        addRootButton.addActionListener(event -> addRootCategory());

        JButton addChildButton = new JButton("Add Child");
        addChildButton.addActionListener(event -> addChildCategory());

        JButton removeButton = new JButton("Remove Selected");
        removeButton.addActionListener(event -> removeSelectedCategory());

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(event -> searchCategory());

        JButton infoButton = new JButton("Show Selected Info");
        infoButton.addActionListener(event -> showSelectedCategoryInfo());

        JButton printButton = new JButton("Print Hierarchy");
        printButton.addActionListener(event -> printHierarchy());

        addFormRow(formPanel, "Root Category Name:", rootCategoryNameField, addRootButton, 0);
        addFormRow(formPanel, "Child Category Name:", childCategoryNameField, addChildButton, 1);
        addButtonRow(formPanel, removeButton, infoButton, 2);
        addFormRow(formPanel, "Search Category Name:", searchCategoryNameField, searchButton, 3);
        addButtonRow(formPanel, printButton, null, 4);

        categoryInfoArea = new JTextArea(8, 30);
        categoryInfoArea.setEditable(false);

        statisticsArea = new JTextArea(5, 30);
        statisticsArea.setEditable(false);

        hierarchyArea = new JTextArea(8, 30);
        hierarchyArea.setEditable(false);

        JPanel displayPanel = new JPanel(new GridBagLayout());
        addDisplayArea(displayPanel, "Selected Category Information", categoryInfoArea, 0);
        addDisplayArea(displayPanel, "Statistics", statisticsArea, 1);
        addDisplayArea(displayPanel, "Hierarchy Output", hierarchyArea, 2);

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(displayPanel, BorderLayout.CENTER);

        return panel;
    }

    private void addFormRow(JPanel panel, String label, JTextField field, JButton button, int row) {
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = row;
        labelConstraints.anchor = GridBagConstraints.WEST;
        labelConstraints.insets = new Insets(5, 5, 5, 5);
        panel.add(new JLabel(label), labelConstraints);

        GridBagConstraints fieldConstraints = new GridBagConstraints();
        fieldConstraints.gridx = 1;
        fieldConstraints.gridy = row;
        fieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        fieldConstraints.weightx = 1.0;
        fieldConstraints.insets = new Insets(5, 5, 5, 5);
        panel.add(field, fieldConstraints);

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 2;
        buttonConstraints.gridy = row;
        buttonConstraints.insets = new Insets(5, 5, 5, 5);
        panel.add(button, buttonConstraints);
    }

    private void addButtonRow(JPanel panel, JButton firstButton, JButton secondButton, int row) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = row;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);
        panel.add(firstButton, constraints);

        if (secondButton != null) {
            GridBagConstraints secondConstraints = new GridBagConstraints();
            secondConstraints.gridx = 2;
            secondConstraints.gridy = row;
            secondConstraints.insets = new Insets(5, 5, 5, 5);
            panel.add(secondButton, secondConstraints);
        }
    }

    private void addDisplayArea(JPanel panel, String title, JTextArea textArea, int row) {
        JPanel areaPanel = new JPanel(new BorderLayout());
        areaPanel.setBorder(BorderFactory.createTitledBorder(title));
        areaPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = row;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(4, 4, 4, 4);
        panel.add(areaPanel, constraints);
    }

    private Category getSelectedCategory() {
        TreePath path = categoryTree.getSelectionPath();

        if (path == null) {
            return null;
        }

        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
        Object value = selectedNode.getUserObject();

        if (value instanceof Category) {
            return (Category) value;
        }

        return null;
    }

    private void refreshTree() {
        treeRoot.removeAllChildren();

        for (int i = 0; i < rootCategories.size(); i++) {
            Category category = rootCategories.get(i);
            treeRoot.add(createTreeNode(category));
        }

        treeModel.reload();
        updateStatistics();
    }

    private DefaultMutableTreeNode createTreeNode(Category category) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(category);

        ArrayList<Category> children = category.getChildren();
        for (int i = 0; i < children.size(); i++) {
            node.add(createTreeNode(children.get(i)));
        }

        return node;
    }

    private void expandAllTreeRows() {
        for (int i = 0; i < categoryTree.getRowCount(); i++) {
            categoryTree.expandRow(i);
        }
    }

    private void collapseAllTreeRows() {
        for (int i = categoryTree.getRowCount() - 1; i >= 0; i--) {
            categoryTree.collapseRow(i);
        }
    }

    private void updateStatistics() {
        statisticsArea.setText(
                "Root Categories: " + rootCategories.size() + "\n"
                        + "Total Categories: " + countAllCategories() + "\n"
                        + "Maximum Tree Depth: " + calculateMaxDepth()
        );
    }

    private void addRootCategory() {
        // TODO:
        // Read category name from UI
        String rootName = rootCategoryNameField.getText();

        // Validate empty input
        if (rootName.isEmpty()){
            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in field.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Check whether a root category with the same name exists
        for (Category category : rootCategories){
            if (rootName.equals(category.getName())){
                JOptionPane.showMessageDialog(
                        this,
                        "A category already exists.",
                        "Duplicate name",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
        }

        // Create Category
        Category category = new Category(rootName ,null);

        // Add it to rootCategories
        rootCategories.add(category);

        // Refresh JTree
        refreshTree();
    }

    private boolean rootCategoryExists(String name) {
        // TODO:
        // Search rootCategories
        // Return true if root category already exists
        for (Category category : rootCategories){
            if (category.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    private void addChildCategory() {
        Category selectedParent = getSelectedCategory();
        if (selectedParent == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a parent category from the tree.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String childName = childCategoryNameField.getText().trim();
        if (childName.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in field.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (childNameExists(selectedParent, childName)) {
            JOptionPane.showMessageDialog(
                    this,
                    "A child category with this name already exists.",
                    "Duplicate name",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Category child = new Category(childName, selectedParent);
        selectedParent.getChildren().add(child);
        refreshTree();
        childCategoryNameField.setText("");
    }

    private boolean childNameExists(Category parent, String childName) {
        HashSet<String> childNames = new HashSet<>();
        for (Category child : parent.getChildren()) {
            childNames.add(child.getName());
        }
        return childNames.contains(childName);
    }

    private void removeSelectedCategory() {
        Category selected = getSelectedCategory();
        if (selected == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a category to remove.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (selected.getParent() == null) {
            rootCategories.remove(selected);
        } else {
            selected.getParent().getChildren().remove(selected);
        }

        refreshTree();
        categoryInfoArea.setText("");
    }

    private Category findCategory(String name) {
        for (Category root : rootCategories) {
            Category found = findCategoryRecursive(root, name);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    private Category findCategoryRecursive(Category category, String name) {
        if (category.getName().equalsIgnoreCase(name)) {
            return category;
        }
        for (Category child : category.getChildren()) {
            Category found = findCategoryRecursive(child, name);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    private int getDirectChildrenCount(Category category) {
        return category.getChildren().size();
    }

    private int countDescendants(Category category) {
        int count = category.getChildren().size();
        for (Category child : category.getChildren()) {
            count += countDescendants(child);
        }
        return count;
    }

    private int countAllCategories() {
        int total = rootCategories.size();
        for (Category root : rootCategories) {
            total += countDescendants(root);
        }
        return total;
    }

    private int calculateMaxDepth() {
        int maxDepth = 0;
        for (Category root : rootCategories) {
            int depth = calculateDepth(root);
            if (depth > maxDepth) {
                maxDepth = depth;
            }
        }
        return maxDepth;
    }

    private int calculateDepth(Category category) {
        if (category.getChildren().isEmpty()) {
            return 1;
        }
        int maxChildDepth = 0;
        for (Category child : category.getChildren()) {
            int depth = calculateDepth(child);
            if (depth > maxChildDepth) {
                maxChildDepth = depth;
            }
        }
        return 1 + maxChildDepth;
    }

    private String getCategoryPath(Category category) {
        StringBuilder path = new StringBuilder(category.getName());
        Category current = category.getParent();
        while (current != null) {
            path.insert(0, current.getName() + " > ");
            current = current.getParent();
        }
        return path.toString();
    }

    private void showSelectedCategoryInfo() {
        Category selected = getSelectedCategory();
        if (selected == null) {
            categoryInfoArea.setText("No category selected.");
            return;
        }

        String parentName = (selected.getParent() != null) ? selected.getParent().getName() : "None (Root)";
        categoryInfoArea.setText(
                "Name: " + selected.getName() + "\n" +
                        "Parent: " + parentName + "\n" +
                        "Direct Children: " + getDirectChildrenCount(selected) + "\n" +
                        "Total Descendants: " + countDescendants(selected) + "\n" +
                        "Path: " + getCategoryPath(selected)
        );
    }

    private void searchCategory() {
        String query = searchCategoryNameField.getText().trim();
        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a category name to search.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Category found = findCategory(query);
        if (found != null) {
            DefaultMutableTreeNode node = findTreeNode(treeRoot, found);
            if (node != null) {
                TreePath path = new TreePath(node.getPath());
                categoryTree.scrollPathToVisible(path);
                categoryTree.setSelectionPath(path);
            }
            showSelectedCategoryInfo();
            JOptionPane.showMessageDialog(this, "Category found: " + found.getName(), "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Category not found.", "Not Found", JOptionPane.ERROR_MESSAGE);
        }
    }

    private DefaultMutableTreeNode findTreeNode(DefaultMutableTreeNode parent, Category target) {
        if (parent.getUserObject() == target) {
            return parent;
        }
        for (int i = 0; i < parent.getChildCount(); i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) parent.getChildAt(i);
            DefaultMutableTreeNode result = findTreeNode(child, target);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private void printHierarchy() {
        StringBuilder sb = new StringBuilder();
        for (Category root : rootCategories) {
            printCategory(root, 0, sb);
        }
        hierarchyArea.setText(sb.toString());
    }

    private void printCategory(Category category, int level, StringBuilder sb) {
        for (int i = 0; i < level; i++) {
            sb.append("  ");
        }
        sb.append("- ").append(category.getName()).append("\n");
        for (Category child : category.getChildren()) {
            printCategory(child, level + 1, sb);
        }
    }

}
