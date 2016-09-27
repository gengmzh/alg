/**
 * 
 */
package com.github.myron.concurrent.sync;

/**
 * @author gengmaozhang01
 * @since 下午9:38:34
 */
public class NofairSync implements Runnable {

	private static Object LOCK = new Object();

	@Override
	public void run() {
		Thread th = Thread.currentThread();
		System.out.println(th.toString() + " starts..., " + th.getState());
		synchronized (LOCK) {
			try {
				LOCK.wait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(th.toString() + " done, " + th.getState());
	}

	public static void main(String[] args) throws Exception {
		Thread[] threads = new Thread[3];
		for (int i = 0; i < threads.length; i++) {
			Thread th = new Thread(new NofairSync(), "T-" + i);
			th.setPriority(10 - i);
			th.start();
			threads[i] = th;
		}
		Thread.sleep(1000);
		for (Thread th : threads) {
			System.out.println(th.toString() + ", " + th.getState());
		}

		synchronized (LOCK) {
			Thread th = Thread.currentThread();
			System.out.println(th.toString() + " get lock, and will notify all");
			LOCK.notifyAll();
		}

		for (Thread th : threads) {
			th.join();
			System.out.println(th.toString() + ", " + th.getState());
		}
		System.out.println("all threads done");
	}

}
