package com.gmail.tt195361;

import static org.junit.Assert.*;
import org.junit.Test;

public class EnumeratorStateTest {

	@Test
	public void testEquals() {
		final int CurrentIndex = 1;
		final int DifferentIndex = 2;
		
		EnumeratorState enumState = new EnumeratorState(CurrentIndex);
		EnumeratorState sameState = new EnumeratorState(CurrentIndex);
		Object differentClass = new Object();
		EnumeratorState differneState = new EnumeratorState(DifferentIndex);
		
		checkEquals(enumState, enumState, true, "同じオブジェクト -> 等しい");
		checkEquals(enumState, sameState, true, "異なるオブジェクトで同じ状態 -> 等しい");
		checkEquals(enumState, differneState, false, "異なる状態 -> 等しくない");
		checkEquals(enumState, differentClass, false, "異なるクラス -> 等しくない");
		checkEquals(enumState, null, false, "null -> 等しくない");
	}
	
	private void checkEquals(
			EnumeratorState enumState, Object obj, boolean expected, String message) {
		final boolean actual = enumState.equals(obj);
		assertEquals(message, expected, actual);
	}
	
	@Test
	public void testHashCode() {
		checkHashCode(1, "currentIndex の値が hashCode: 1");
		checkHashCode(2, "currentIndex の値が hashCode: 2");
		checkHashCode(3, "currentIndex の値が hashCode: 3");
	}
	
	private void checkHashCode(int currentIndex, String message) {
		EnumeratorState enumState = new EnumeratorState(currentIndex);
		final int expected = currentIndex;
		final int actual = enumState.hashCode();
		assertEquals(message, expected, actual);
	}
}
