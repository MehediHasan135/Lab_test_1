import java.util.Stack;
import java.util.Random;

class Threadstack<T> {
    private final Stack<T> stack = new Stack<>();
    private static final int MAX_SIZE = 10;

    public synchronized void push(T data) throws InterruptedException {
        while (stack.size() >= MAX_SIZE) {
            wait();
        }
        stack.push(data);
        notifyAll();
    }

    public synchronized T pop() throws InterruptedException {
        while (stack.isEmpty()) {
            wait();
        }
        T data = stack.pop();
        notifyAll();
        return data;
    }

    public synchronized boolean isEmpty() {
        return stack.isEmpty();
    }

    public synchronized int size() {
        return stack.size();
    }
}

class Producer implements Runnable {
    private final Threadstack<Integer> stack;
    private final Random random = new Random();

    public Producer(Threadstack<Integer> stack) {
        this.stack = stack;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                int value = random.nextInt(100) + 1;
                stack.push(value);
                System.out.println("Produced: " + value);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class Consumer implements Runnable {
    private final Threadstack<Integer> stack;

    public Consumer(Threadstack<Integer> stack) {
        this.stack = stack;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                int value = stack.pop();
                System.out.println("Consumed: " + value);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

public class ThreadsStack {
    public static void main(String[] args) {
        Threadstack<Integer> stack = new Threadstack<>();

        Thread producerThread = new Thread(new Producer(stack));
        Thread consumerThread = new Thread(new Consumer(stack));

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}