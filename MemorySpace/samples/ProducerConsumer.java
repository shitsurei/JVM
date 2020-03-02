package MemorySpace.samples;

import java.util.ArrayList;
import java.util.List;

/**
 * 生产者消费者模型
 */
public class ProducerConsumer {
    private static List<String> product = new ArrayList<>();

    public boolean isFull() {
        return product.size() == 5;
    }

    public synchronized void consume() {
        while (product.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        product.remove(0);
        System.out.println(Thread.currentThread() + "---consume---size:" + product.size());
        notifyAll();
    }

    public synchronized void produce() {
        while (isFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        product.add("hello");
        System.out.println(Thread.currentThread() + "---produce---size:" + product.size());
        notifyAll();
    }

    public static void main(String[] args) {
        ProducerConsumer producerConsumer = new ProducerConsumer();
        while (true) {
            new Thread(() -> producerConsumer.produce()).start();
            new Thread(() -> producerConsumer.consume()).start();
        }
    }
}
