/**
 * 
 */
package com.github.myron.spi;

import java.util.ServiceLoader;

/**
 * @author gengmaozhang01
 * @since 下午3:40:17
 */
public class HashSPI {

	public static void main(String[] args) {
		ServiceLoader<Hash> hashServices = ServiceLoader.load(Hash.class);

		String value = "A simple service-provider loading facility.";
		for (Hash hash : hashServices) {
			String result = hash.hash(value);
			System.out.println(hash.getClass().getSimpleName() + ": " + result);
		}
	}

}
