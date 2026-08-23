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
        // Validate empty input
        // Check whether a root category with the same name exists
        // Create Category
        // Add it to rootCategories
        // Refresh JTree
    }

    private boolean rootCategoryExists(String name) {
        // TODO:
        // Search rootCategories
        // Return true if root category already exists

        return false;
    }

    private void addChildCategory() {
        // TODO:
        // Get selected Category
        // Read child category name
        // Validate input
        // Check whether the selected category already has a child
        // with the same name
        // Create Category
        // Set its parent
        // Add it to parent's children
        // Refresh JTree
    }

    private boolean childNameExists(Category parent, String childName) {
        // TODO:
        // Create/use HashSet<String>
        // Add existing child names into the set
        // Check whether childName already exists

        return false;
    }

    private void removeSelectedCategory() {
        // TODO:
        // Get selected Category
        //
        // If it is a root category:
        // remove it from rootCategories
        //
        // Otherwise:
        // remove it from its parent's children
        //
        // Refresh tree
    }

    private Category findCategory(String name) {
        // TODO:
        // Search through every root category
        // Search all child categories recursively

        return null;
    }

    private Category findCategoryRecursive(Category category, String name) {
        // TODO:
        // Check current category
        // Search every child recursively

        return null;
    }

    private int getDirectChildrenCount(Category category) {
        // TODO:
        // Return number of direct children

        return 0;
    }

    private int countDescendants(Category category) {
        // TODO:
        // Count children
        // Also count children of children recursively

        return 0;
    }

    private int countAllCategories() {
        // TODO:
        // Count every root category
        // Count all descendants

        return 0;
    }

    private int calculateMaxDepth() {
        // TODO:
        // Find the deepest category level

        return 0;
    }

    private int calculateDepth(Category category) {
        // TODO

        return 0;
    }

    private String getCategoryPath(Category category) {
        // TODO:
        // Use parent references
        // Build complete path

        return "";
    }

    private void showSelectedCategoryInfo() {
        // TODO:
        // Get selected Category
        // Display:
        // name
        // parent
        // direct child count
        // descendant count
        // complete path
    }

    private void searchCategory() {
        // TODO:
        // Read search text
        // Find category
        // Show information if found
        // Show error if not found
    }

    private void printHierarchy() {
        // TODO:
        // Print all categories with indentation
    }

    private void printCategory(Category category, int level) {
        // TODO
    }
}
