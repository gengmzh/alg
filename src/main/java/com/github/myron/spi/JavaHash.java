/**
 * 
 */
package com.github.myron.spi;

import java.util.Objects;

/**
 * @author gengmaozhang01
 * @since 下午3:36:42
 */
public class JavaHash implements Hash {

	public JavaHash() {
	}

	@Override
	public String hash(String value) {
		Objects.requireNonNull(value, "value cann't be null");
		int hash = value.hashCode();
		return Integer.toHexString(hash);
	}

}
