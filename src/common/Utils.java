package common;

public class Utils<any extends Comparable> {

    // If true it means a is less than b
    // If false it means that a is greater than or equal to b

    public static boolean less(Comparable a, Comparable b) { return a.compareTo(b) < 0; }
    public static void exch(Comparable[] a, int i, int j) {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    public static void exch(Integer[] a, int i, int j) {
        Integer swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    public static Item[] resize(int capacity, Item[] x) {
        // At this stage x[] still has the old capacity
        Item[] copy = new Item[capacity];
        // Fill copy with placeholders to prevent null pointer exceptions
        for(int i = 0; i < copy.length; i++)
            copy[i] = new Item("null",new Key(-0));

        for(int i = 0; i < x.length; i++) // We are copying all the elements in x into copy
            copy[i]  = x[i];
        // Finally we are setting x to copy which has the calculates capacity
        x = copy;
        return x;
    }

    // This resize method is biased in that it starts inserting elements into the resized array from index 1 not 0
    public Integer[] resize(int capacity,Integer[] x){
        // At this stage x[] still has the old capacity
        Integer[] copy = new Integer[capacity];
        // Fill copy with placeholders to prevent null pointer exceptions
        for(int i = 0; i < copy.length; i++)
            copy[i] = null;
        for(int i = 0; i < x.length; i++) // We are copying all the elements in x into copy
            copy[i + 1]  = x[i];
        // Finally we are setting x to copy which has the calculates capacity
        x = copy;
        return x;
    }


}
