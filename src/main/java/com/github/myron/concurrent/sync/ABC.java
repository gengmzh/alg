/**
 * 
 */
package com.github.myron.concurrent.sync;

/**
 * @author gengmaozhang01
 * @since 上午11:14:51
 */
public class ABC implements Runnable {

	private static final int COUNT = 10;
	private static final Object LOCK = new Object();
	private static String flag = "A";

	private String name;
	private String next;

	public ABC(String name, String next) {
		this.name = name;
		this.next = next;
	}

	@Override
	public void run() {
		int i = 0;
		while (i < COUNT) {
			synchronized (LOCK) {
				while (!name.equals(flag)) {
					// System.out.print("[" + name + "]");
					try {
						LOCK.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.print(name + (name.equals("C") ? '\n' : ' '));
				flag = next;
				LOCK.notifyAll();
			}
			i++;
		}
	}

	public static void main(String[] args) {
		ABC.flag = "A";
		Thread a = new Thread(new ABC("A", "B"));
		Thread b = new Thread(new ABC("B", "C"));
		Thread c = new Thread(new ABC("C", "A"));

		a.start();
		b.start();
		c.start();
	}

}
