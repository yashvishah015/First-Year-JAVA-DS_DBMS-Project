
package Stadium;
public class Category {
    private int id;
    private String name;
    private double price;
    private boolean hasAc;

    public Category(int id, String name, double price, boolean hasAc) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.hasAc = hasAc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isHasAc() {
        return hasAc;
    }

    public void setHasAc(boolean hasAc) {
        this.hasAc = hasAc;
    }
}
