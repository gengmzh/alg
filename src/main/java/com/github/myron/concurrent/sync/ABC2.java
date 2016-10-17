/**
 * 
 */
package com.github.myron.concurrent.sync;

/**
 * @author gengmaozhang01
 * @since 上午11:14:51
 */
public class ABC2 implements Runnable {

	private static final int COUNT = 10;
	private static String flag = "A";

	private String name;

	private final Object LOCK = new Object();
	private ABC2 next;

	public ABC2(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		int i = 0;
		while (i < COUNT) {
			i++;
			synchronized (LOCK) {
				while (!name.equals(flag)) {
					try {
						// System.out.print("[" + name + "]");
						LOCK.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				synchronized (next.LOCK) {
					System.out.print(name + (name.equals("C") ? '\n' : ' '));
					flag = next.name;
					next.LOCK.notify();
				}
				if (i < COUNT) {
					try {
						LOCK.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		ABC2.flag = "A";
		ABC2 a = new ABC2("A");
		ABC2 b = new ABC2("B");
		ABC2 c = new ABC2("C");
		a.next = b;
		b.next = c;
		c.next = a;

		new Thread(a).start();
		new Thread(b).start();
		new Thread(c).start();
	}

}
