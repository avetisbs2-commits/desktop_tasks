import java.util.ArrayList;

public class Category {

    private String name;
    private Category parent;
    private ArrayList<Category> children;

    public Category(String name, Category parent) {
        this.name = name;
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public ArrayList<Category> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Category> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return name;
    }
}
