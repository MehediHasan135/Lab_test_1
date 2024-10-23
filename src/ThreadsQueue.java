import java.util.LinkedList;
import java.util.Queue;

public class ThreadsQueue<T> {
    private final Queue<T> Queue = new LinkedList<>();

    public synchronized void Enqueue(T data) {
        Queue.add(data);
        notifyAll();
    }

    public synchronized T Dequeue() {
        while (Queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return Queue.poll();
    }

    public synchronized boolean isEmpty() {
        return Queue.isEmpty();
    }
}
class Runnerr{
    public static void main(String[] args) {
        ThreadsQueue<Integer> queue = new ThreadsQueue<>();

        Runnable EnqueueTask = () -> {
            for (int i = 0; i < 1000; i++) {
                queue.Enqueue(i);
            }
        };

        Runnable DequeueTask = () -> {
            for (int i = 0; i < 1000; i++) {
                queue.Dequeue();
            }
        };

        Thread thread1 = new Thread(EnqueueTask);
        Thread thread2 = new Thread(DequeueTask);


        thread1.start();
        thread2.start();


        try {
            thread1.join();
            thread2.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Queue is empty: " + queue.isEmpty());
    }
}
