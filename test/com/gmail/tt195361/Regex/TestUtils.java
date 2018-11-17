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
		// �T�C�Y�������ŁAexpected �̍��ڂ����ׂ� actual �Ɋ܂܂�Ă���B
		assertEquals("size: " + message, expected.size(), actual.size());
		for (T item : expected) {
			assertTrue("contains: " + message, actual.contains(item));
		}
	}
	
	@SuppressWarnings("unchecked")
	static <T_ARG, T_HASH> HashSet<T_HASH> makeHashSet(T_ARG...args) {
		HashSet<T_HASH> hashSet = new HashSet<T_HASH>();
		for (T_ARG arg : args) {
			// arg �� T_HASH �^�ւ̃L���X�g�Ōx���ɂȂ邪�A�o�͂���Ȃ��悤�ɂ���B
			hashSet.add((T_HASH)arg);
		}
		return hashSet;
	}
}
