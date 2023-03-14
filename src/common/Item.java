package common;

public class Item implements Comparable<Item> {
    public Key itemKey;

    public String itemName;
    public Item(String itemName, Key value) {
        this.itemKey = value;
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    @Override
    public int compareTo(Item that) {
        if(this.itemKey.value > that.itemKey.value) {
            return 1;
        }
        if(this.itemKey.value < that.itemKey.value) {
            return -1;
        }
        return 0;
    }
}

