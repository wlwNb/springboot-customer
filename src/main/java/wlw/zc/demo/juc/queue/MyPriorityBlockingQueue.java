package wlw.zc.demo.juc.queue;

import wlw.zc.demo.system.entity.Task;

import java.util.HashMap;
import java.util.concurrent.PriorityBlockingQueue;

public class MyPriorityBlockingQueue  extends PriorityBlockingQueue<Task> {
    public static void main(String[] args) throws InterruptedException {
        HashMap<String, Task> map = new HashMap<>();
        MyPriorityBlockingQueue queue = new MyPriorityBlockingQueue();
        Task product1 = new Task();
        queue.add(product1);
        queue.take();

    }
}
