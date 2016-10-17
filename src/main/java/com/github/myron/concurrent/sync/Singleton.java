/**
 * 
 */
package com.github.myron.concurrent.sync;

/**
 * @author gengmaozhang01
 * @since 下午5:42:57
 */
// final类，以免继承者破坏单例
public final class Singleton {

	// 私有构造子，以免外部创建示例
	private Singleton() {
	}

	private static Singleton INSTANCE;

	// double-check locking
	public static Singleton getInstance() {
		if (INSTANCE == null) {
			synchronized (Singleton.class) {
				if (INSTANCE == null) {
					INSTANCE = new Singleton();
				}
			}
		}
		return INSTANCE;
	}

	/**
	 * @author gengmaozhang01
	 * @since 下午5:42:57
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
