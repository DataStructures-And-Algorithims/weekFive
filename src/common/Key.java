package common;

public class Key implements Comparable<Key> {
    public int value;

    public Key(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public int compareTo(Key that) {
        if(this.value > that.value) {
            return 1;
        }
        if(this.value < that.value) {
            return -1;
        }
        return 0;
    }
}

