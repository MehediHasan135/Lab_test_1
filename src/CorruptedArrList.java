import java.util.ArrayList;

public class CorruptedArrList {
    private final ArrayList<Integer> list = new ArrayList<>();

    public void addElement(int element) {

        list.add(element);

    }

    public void removeElement() {

        if (!list.isEmpty()) {
            list.removeFirst();

        }
    }

    public void printList() {

        System.out.println(list);

    }
}
class Runner{
    public static void main(String[] args) {
        CorruptedArrList corruptedList = new CorruptedArrList();

        Runnable addTask = () -> {
            for (int i = 0; i < 1000; i++) {
                corruptedList.addElement(i);
            }
        };

        Runnable removeTask = () -> {
            for (int i = 0; i < 1000; i++) {
                corruptedList.removeElement();
            }
        };

        Thread thread1 = new Thread(addTask);
        Thread thread2 = new Thread(removeTask);
        Thread thread3 = new Thread(addTask);
        Thread thread4 = new Thread(removeTask);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        corruptedList.printList();
    }
}