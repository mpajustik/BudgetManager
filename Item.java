
package budget;

public class Item {
    private String itemName;
    private Double itemPrice;

    public Item() {
    }

    public Item(String itemName, Double itemPrice) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

}