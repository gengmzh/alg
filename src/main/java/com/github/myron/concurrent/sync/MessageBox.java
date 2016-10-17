/**
 * 
 */
package com.github.myron.concurrent.sync;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author gengmaozhang01
 * @since 下午11:07:24
 */
public class MessageBox {

	private static int bufferSize = 3;
	private static Queue<String> msgQueue = new LinkedList<String>();
	private static int totalSize = 8;
	private static int producedCount = 0, consumeCount = 0;

	static class Producer implements Runnable {

		@Override
		public void run() {
			while (true) {
				synchronized (msgQueue) {
					boolean done = false;
					while (msgQueue.size() >= bufferSize) {
						if ((done = isDone())) {
							break;
						}
						try {
							System.out.println(Thread.currentThread() + " 消息队列满，生产者等待");
							msgQueue.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					if (done || isDone()) {
						msgQueue.notify();
						break;
					}
					String message = "msg-" + System.currentTimeMillis();
					msgQueue.add(message);
					producedCount++;
					System.out.println(Thread.currentThread() + " 生产消息：" + message);
					msgQueue.notify();
				}
			}
		}

		private boolean isDone() {
			if (producedCount == totalSize) {
				System.out.println(Thread.currentThread() + " 生产完成");
				return true;
			}
			return false;
		}

	}

	static class Consumer implements Runnable {

		@Override
		public void run() {
			while (true) {
				synchronized (msgQueue) {
					boolean done = false;
					while (msgQueue.isEmpty()) {
						if ((done = isDone())) {
							break;
						}
						try {
							System.out.println(Thread.currentThread() + " 消息队列空，消费者等待");
							msgQueue.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					if (done || isDone()) {
						msgQueue.notify();
						break;
					}
					String message = msgQueue.poll();
					consumeCount++;
					System.out.println(Thread.currentThread() + " 消费消息：" + message);
					msgQueue.notify();
				}
			}
		}

		private boolean isDone() {
			if (consumeCount == totalSize) {
				System.out.println(Thread.currentThread() + " 消费完成");
				return true;
			}
			return false;
		}

	}

	public static void main(String[] args) {
		Thread producer = new Thread(new Producer(), "P1");
		Thread consumer = new Thread(new Consumer(), "C1");

		Thread producer2 = new Thread(new Producer(), "P2");
		Thread consumer2 = new Thread(new Consumer(), "C2");
		producer.start();
		producer2.start();
		consumer.start();
		consumer2.start();
	}

}
