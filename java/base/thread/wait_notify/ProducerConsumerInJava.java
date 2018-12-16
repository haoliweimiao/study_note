package com.test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Simple Java program to demonstrate How to use wait, notify and notifyAll()
 * method in Java by solving producer consumer problem.
 * 下面我们提供一个使用wait和notify的范例程序。在这个程序里，我们使用了上文所述的一些代码规范。
 * 我们有两个线程，分别名为PRODUCER（生产者）和CONSUMER（消费者），他们分别继承了了Producer
 * 和Consumer类，而Producer和Consumer都继承了Thread类。Producer和Consumer想要实现的代码
 * 逻辑都在run()函数内。Main线程开始了生产者和消费者线程，并声明了一个LinkedList作为缓冲区队列
 * （在Java中，LinkedList实现了队列的接口）。生产者在无限循环中持续往LinkedList里插入随机整数
 * 直到LinkedList满。我们在while(queue.size == maxSize)循环语句中检查这个条件。请注意到我
 * 们在做这个检查条件之前已经在队列对象上使用了synchronized关键词，因而其它线程不能在我们检查条件
 * 时改变这个队列。如果队列满了，那么PRODUCER线程会在CONSUMER线程消耗掉队列里的任意一个整数，
 * 并用notify来通知PRODUCER线程之前持续等待。在我们的例子中，wait和notify都是使用在同一个共享对象上的。
 */
public class ProducerConsumerInJava {
	public static void main(String args[]) {
		System.out.println("How to use wait and notify method in Java");
		System.out.println("Solving Producer Consumper Problem");
		Queue<Integer> buffer = new LinkedList<>();
		int maxSize = 10;
		Thread producer = new Producer(buffer, maxSize, "PRODUCER");
		Thread consumer = new Consumer(buffer, maxSize, "CONSUMER");
		producer.start();
		consumer.start();
	}
}

/**
 * Producer Thread will keep producing values for Consumer to consumer. It will
 * use wait() method when Queue is full and use notify() method to send
 * notification to Consumer Thread.
 */
class Producer extends Thread {
	private Queue<Integer> queue;
	private int maxSize;

	public Producer(Queue<Integer> queue, int maxSize, String name) {
		super(name);
		this.queue = queue;
		this.maxSize = maxSize;
	}

	@Override
	public void run() {
		while (true) {
			synchronized (queue) {
				while (queue.size() == maxSize) {
					try {
						System.out.println("Queue is full, " + "Producer thread waiting for "
								+ "consumer to take something from queue");
						Thread.sleep(100);
						queue.wait();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}

				try {
					Thread.sleep(100);
				} catch (Exception e) {

				}
				// loop add item to queue until this number equal maxSize
				Random random = new Random();
				int i = random.nextInt();
				System.out.println("Producing add value : " + i);
				queue.add(i);
				queue.notifyAll();
				System.out.println("  Producer queue.notifyAll();");
			}
		}
	}
}

/**
 * Consumer Thread will consumer values form shared queue. It will also use
 * wait() method to wait if queue is empty. It will also use notify method to
 * send notification to producer thread after consuming values from queue.
 * 
 */
class Consumer extends Thread {
	private Queue<Integer> queue;
	private int maxSize;

	public Consumer(Queue<Integer> queue, int maxSize, String name) {
		super(name);
		this.queue = queue;
		this.maxSize = maxSize;
	}

	@Override
	public void run() {
		while (true) {
			synchronized (queue) {
				while (queue.isEmpty()) {
					try {
						System.out.println("Queue is empty," + "Consumer thread is waiting"
								+ " for producer thread to put something in queue");
						Thread.sleep(100);
						queue.wait();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				try {
					Thread.sleep(100);
				} catch (Exception e) {

				}
				System.out.println("Consuming remove value : " + queue.remove());
				queue.notifyAll();
				System.out.println("  Consuming queue.notifyAll();");
			}
		}
	}
}
