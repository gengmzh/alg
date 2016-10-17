/**
 * 
 */
package com.github.myron.concurrent.sync;

/**
 * @author gengmaozhang01
 * @since 下午3:32:03
 */
public class Philosopher implements Runnable {

	private static final long MILLIS = 60 * 1000 + 1;

	private int number;
	private Object leftFork;
	private Object rightFork;

	public Philosopher(int number, Object leftFork, Object rightFork) {
		super();
		this.number = number;
		this.leftFork = leftFork;
		this.rightFork = rightFork;
	}

	@Override
	public void run() {
		long start = System.currentTimeMillis();
		long eatTimes = 0;
		long thinkTimes = 0;
		while (System.currentTimeMillis() - start < MILLIS) {
			// 就餐
			// 偶数位哲学家先拿左侧餐叉
			if (number % 2 == 0) {
				synchronized (leftFork) {
					synchronized (rightFork) {
						this.eat();
					}
				}

			}
			// 奇数位哲学家先拿右侧餐叉
			else {
				synchronized (rightFork) {
					synchronized (leftFork) {
						this.eat();
					}
				}
			}
			eatTimes++;
			// 思考
			this.think();
			thinkTimes++;
		}
		System.out.println(Thread.currentThread().getName() + ": eat " + eatTimes + ", think " + thinkTimes);
	}

	private void think() {
		String name = Thread.currentThread().getName();
		System.out.println(name + ": start thinking");
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(name + ": stop thinking");
	}

	private void eat() {
		String name = Thread.currentThread().getName();
		System.out.println(name + ": start eating");
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(name + ": stop eating");
	}

	public static void main(String[] args) {
		int count = 5;
		Object[] forks = new Object[count];
		for (int i = 0; i < count; i++) {
			forks[i] = new Object();
		}
		for (int i = 0; i < count; i++) {
			Thread phil = new Thread(new Philosopher(i, forks[i], forks[(i + 1) % count]), "p" + i);
			phil.start();
		}
	}

}
