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
		
		checkEquals(enumState, enumState, true, "�����I�u�W�F�N�g -> ������");
		checkEquals(enumState, sameState, true, "�قȂ�I�u�W�F�N�g�œ������ -> ������");
		checkEquals(enumState, differneState, false, "�قȂ��� -> �������Ȃ�");
		checkEquals(enumState, differentClass, false, "�قȂ�N���X -> �������Ȃ�");
		checkEquals(enumState, null, false, "null -> �������Ȃ�");
	}
	
	private void checkEquals(
			EnumeratorState enumState, Object obj, boolean expected, String message) {
		boolean actual = enumState.equals(obj);
		assertEquals(message, expected, actual);
	}
}
