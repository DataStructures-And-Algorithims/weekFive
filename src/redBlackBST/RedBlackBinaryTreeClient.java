package redBlackBST;

import common.Item;
import common.Key;

import java.util.Scanner;

public class RedBlackBinaryTreeClient {
    private static Scanner itemNameScanner;
    private static Scanner itemKeyScanner;
    private static Scanner deleteMax;
    private static Scanner deleteMin;
    private static Scanner deleteItem;
    private static Scanner orderedOperations;

    private static Scanner floor;
    private static Scanner ceiling;
    private static Scanner motherBoard;
    private static Scanner end;

    public static void main(String[] args) {
        String[] alphabet = new String[] {"-","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        RedBlackBinarySearchTree<Item> testList = new RedBlackBinarySearchTree<>();
        itemNameScanner = new Scanner(System.in);
        itemKeyScanner = new Scanner(System.in);
        deleteMax = new Scanner(System.in);
        deleteMin = new Scanner(System.in);
        orderedOperations = new Scanner(System.in);
        deleteItem = new Scanner(System.in);
        floor = new Scanner(System.in);
        ceiling = new Scanner(System.in);
        motherBoard = new Scanner(System.in);
        end = new Scanner(System.in);
        String ended = "n";
        System.out.println("Binary Search Tree operations");
        while(!ended.equals("end")) {
            int keyValue;
            String keyName;
            System.out.print("Enter string Item name -> ");
            keyName = itemNameScanner.nextLine();
            System.out.println();
            System.out.print("Enter int key value -> ");
            keyValue = itemKeyScanner.nextInt();
            long start1 = System.nanoTime();
            testList.put(new Key(keyValue),new Item(keyName,new Key(keyValue)));
            long end1 = System.nanoTime();
            System.out.println();
            System.out.print("Binary tree inorder: ");
            testList.iteratorOrdered();
            System.out.println();
            System.out.print("Binary tree in reverse: ");
            testList.iteratorReverse();
            System.out.println();
            System.out.println("Heap size " + testList.size());
            System.out.println("Root item: " + testList.root());
            System.out.println("Minimum value " + testList.min().item.itemName);
            System.out.println("Maximum value " + testList.max().item.itemName);
            System.out.print("If you wish to delete max value type x : -  " );
            String choice = deleteMax.nextLine();
            if(choice.equals("x")) {
                testList.deleteMax();
                System.out.print("Binary heap: ");
                testList.iteratorOrdered();
                System.out.println();
                System.out.println("Heap size " + testList.size());
            }
            // Delete min
            System.out.print("If you wish to delete min value type y : -  " );
            String choiceMin = deleteMin.nextLine();
            if(choiceMin.equals("y")) {
                testList.deleteMin();
                System.out.print("Binary heap: ");
                testList.iteratorOrdered();
                System.out.println();
                System.out.println("Heap size " + testList.size());
            }
            System.out.print("If you wish to perform ordered operations type o : -  " );
            String choiceOrdered = orderedOperations.nextLine();
            if(choiceOrdered.equals("o")) {
                // Delete a specific item
                System.out.print("Enter item to delete: " );
                String item = deleteItem.nextLine();
                long start2 = System.nanoTime();
                testList.delete(new Key(returnKey(item,alphabet)));
                long end2 = System.nanoTime();
                System.out.print("Binary heap: ");
                testList.iteratorOrdered();
                System.out.println();
                System.out.println("Heap size " + testList.size());
                System.out.print("Enter value to get the floor of : ");
                String theFloor = floor.nextLine();
                System.out.println();
                System.out.print("Floor: " + testList.floor(new Key(returnKey(theFloor,alphabet))));
                System.out.println();
                System.out.print("Enter value to get the floor ceiling of : ");
                String theCeiling = ceiling.nextLine();
                System.out.println();
                System.out.print("Ceiling: " + testList.ceiling(new Key(returnKey(theCeiling,alphabet))));
                System.out.println();
                System.out.print("Enter item to find the rank of: " );
                String rank = motherBoard.nextLine();
                System.out.print("Rank : " + testList.rank(new Key(returnKey(rank,alphabet))));
                System.out.println();
            }
            System.out.print("If you wish to exit Binary heap operations type the word 'end' ->  ");
            ended = end.nextLine();
        }
    }
    public static int returnKey(String key,String[] a) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if      (key.compareTo(a[mid]) < 0) hi = mid - 1;
            else if (key.compareTo(a[mid]) > 0) lo = mid + 1;
            else return mid;
        }
        return -1;
    }
}

