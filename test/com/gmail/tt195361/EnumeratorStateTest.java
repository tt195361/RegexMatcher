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
		final boolean actual = enumState.equals(obj);
		assertEquals(message, expected, actual);
	}
	
	@Test
	public void testHashCode() {
		checkHashCode(1, "currentIndex �̒l�� hashCode: 1");
		checkHashCode(2, "currentIndex �̒l�� hashCode: 2");
		checkHashCode(3, "currentIndex �̒l�� hashCode: 3");
	}
	
	private void checkHashCode(int currentIndex, String message) {
		EnumeratorState enumState = new EnumeratorState(currentIndex);
		final int expected = currentIndex;
		final int actual = enumState.hashCode();
		assertEquals(message, expected, actual);
	}
}
