/**
 * 
 */
package com.github.myron.concurrent.sync;

/**
 * @author gengmaozhang01
 * @since 下午3:32:03
 */
public class Philosopher2 implements Runnable {

	private int number;
	private Forks forks;

	public Philosopher2(int number, Forks forks) {
		super();
		this.number = number;
		this.forks = forks;
	}

	@Override
	public void run() {
		int i = 0;
		while (i < 10) {
			this.think();
			forks.take(number);
			this.eat();
			forks.drop(number);
			i++;
		}
	}

	private void think() {
		String name = Thread.currentThread().getName();
		System.out.println(name + ": 开始思考");
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(name + ": 思考结束");
	}

	private void eat() {
		String name = Thread.currentThread().getName();
		System.out.println(name + ": 开始就餐");
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(name + ": 就餐结束");
	}

	static class Forks {

		private boolean[] flags;

		public Forks(int count) {
			this.flags = new boolean[count];
		}

		public synchronized void take(int number) {
			int left = number;
			int right = (number + 1) % flags.length;
			while (flags[left] || flags[right]) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			flags[left] = true;
			flags[right] = true;
		}

		public synchronized void drop(int number) {
			int left = number;
			int right = (number + 1) % flags.length;
			flags[left] = false;
			flags[right] = false;
			this.notifyAll();
		}

	}

	public static void main(String[] args) {
		int count = 5;
		Forks forks = new Forks(count);
		for (int i = 0; i < count; i++) {
			Thread phil = new Thread(new Philosopher2(i, forks), "p" + i);
			phil.start();
		}
	}

}
