package ua.nure.koshova.module2;

import java.util.Iterator;

public class Test {
    public static void main(String[] args) {
        LimitedQueue<Integer> queue = new LimitedQueue<>(10);
        queue.printState();
        queue.add(0);
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        queue.add(5);
        queue.add(6);
        queue.add(7);
        queue.add(8);
        queue.add(9);
        queue.printState();
        Iterator<Integer> iterator = queue.iterator();
        while (iterator.hasNext()){
            System.out.print(iterator.next() + " ");
        }
        //String Queue
        LimitedQueue<String>.StringQueue stringQ = new LimitedQueue<String>().new StringQueue(5);
        System.out.println();
        stringQ.addWithRegex("Іван");
        stringQ.addWithRegex("Єдем");
        stringQ.printState();
        stringQ.addWithRegex("Їжак");
        stringQ.addWithRegex("Мєр");
        stringQ.addWithRegex("Аліса");
        stringQ.printState();
        stringQ.printInOrder();


    }
}
