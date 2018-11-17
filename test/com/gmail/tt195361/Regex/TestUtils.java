package com.gmail.tt195361.Regex;

import java.util.HashSet;
import static org.junit.Assert.*;

class TestUtils {
	
	static void areSameClass(Object expected, Object actual, String message) {
		Class<?> expectedClass = expected.getClass();
		Class<?> actualClass = actual.getClass();
		assertSame("Class: " + message, expectedClass, actualClass);
	}
	
	static <T> void checkHashSet(
			String message, HashSet<T> expected, HashSet<T> actual) {
		// サイズが同じで、expected の項目がすべて actual に含まれている。
		assertEquals("size: " + message, expected.size(), actual.size());
		for (T item : expected) {
			assertTrue("contains: " + message, actual.contains(item));
		}
	}
	
	@SuppressWarnings("unchecked")
	static <T_ARG, T_HASH> HashSet<T_HASH> makeHashSet(T_ARG...args) {
		HashSet<T_HASH> hashSet = new HashSet<T_HASH>();
		for (T_ARG arg : args) {
			// arg の T_HASH 型へのキャストで警告になるが、出力されないようにする。
			hashSet.add((T_HASH)arg);
		}
		return hashSet;
	}
}
