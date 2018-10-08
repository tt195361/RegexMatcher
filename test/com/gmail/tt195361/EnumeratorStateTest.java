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
		boolean actual = enumState.equals(obj);
		assertEquals(message, expected, actual);
	}
}
