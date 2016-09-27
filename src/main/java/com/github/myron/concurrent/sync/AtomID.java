/**
 * 
 */
package com.github.myron.concurrent.sync;

/**
 * @author gengmaozhang01
 * @since 上午10:01:43
 */
public class AtomID {

	private long id = 0;

	public synchronized long gen() {
		return ++id;
	}

	public long gen(long incr) {
		if (incr <= 0) {
			throw new IllegalArgumentException("increment should be gt 0");
		}
		synchronized (this) {
			this.id += incr;
			return this.id;
		}
	}

	public static void main(String[] args) {
		AtomID atomID = new AtomID();
		System.out.println(atomID.gen());
		System.out.println(atomID.gen(2));
		System.out.println(atomID.gen());
	}

}
