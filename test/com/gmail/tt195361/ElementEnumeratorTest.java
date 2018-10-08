package com.gmail.tt195361;

import java.util.function.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ElementEnumeratorTest {
	
	RegexElement _elem1;
	RegexElement _elem2;
	RegexElement _elem3;
	ElementEnumerator _threeElemEnum;
	ElementEnumerator _emptyElemEnum;
	
	@Before
	public void setUp() {
		_elem1 = new AnyCharElement();
		_elem2 = new OneCharElement('a');
		_elem3 = new EndOfStringElement();
		
		_threeElemEnum = ElementEnumerator.makeForUnitTest(_elem1, _elem2, _elem3);
		_emptyElemEnum = ElementEnumerator.makeForUnitTest();
	}
	
	@Test
	public void hasCurrent() {
		checkHasCurrent(
				_threeElemEnum, new Boolean[] { true, true, true, false },
				"Œ»ÝˆÊ’u‚É—v‘f‚ª‚ ‚ê‚Î true");
		checkHasCurrent(
				_emptyElemEnum, new Boolean[] { false },
				"‹ó‚Ì—ñ‹“Žq‚ÍÅ‰‚©‚ç—v‘f‚ª‚È‚¢");
	}
	
	private void checkHasCurrent(
			ElementEnumerator elemEnum, Boolean[] expectedArray, String message) {
		checkFunction(
				(ee) -> (Boolean)ee.hasCurrent(),
				elemEnum, expectedArray, message);
	}
	
	@Test
	public void getCurrent() {
		checkGetCurrent(
				_threeElemEnum, new RegexElement[] { _elem1, _elem2, _elem3, null },
				"Œ»ÝˆÊ’u‚Ì—v‘f‚ðŽæ“¾‚·‚éA—v‘f‚ª‚È‚¯‚ê‚Î null ‚ð•Ô‚·");
		checkGetCurrent(
				_emptyElemEnum, new RegexElement[] { null },
				"‹ó‚Ì—ñ‹“Žq‚ÍÅ‰‚©‚ç—v‘f‚ª‚È‚¢");
	}
	
	private void checkGetCurrent(
			ElementEnumerator elemEnum, RegexElement[] expectedArray, String message) {
		checkFunction(
				(ee) -> ee.getCurrent(),
				elemEnum, expectedArray, message);
	}
	
	private <R> void checkFunction(
			Function<ElementEnumerator, R> checkFunc,
			ElementEnumerator elemEnum, R[] expectedArray, String message) {
		for	(R expected: expectedArray) {
			R actual = checkFunc.apply(elemEnum);
			assertEquals(message, expected, actual);
			
			elemEnum.moveNext();
		}
	}
	
	static void check(ElementEnumerator expected, ElementEnumerator actual, String message) {
		while (expected.hasCurrent() && actual.hasCurrent()) {
			RegexElement expectedElement = expected.getCurrent();
			RegexElement actualElement = actual.getCurrent();
			
			TestUtils.areSameClass(expectedElement, actualElement, message);
			RegexElementTest.check(expectedElement, actualElement, message);
			
			expected.moveNext();
			actual.moveNext();
		}
		
		assertFalse("expected ‚É—v‘f‚ª‚È‚¢‚Í‚¸: " + message, expected.hasCurrent());
		assertFalse("actual ‚É—v‘f‚ª‚È‚¢‚Í‚¸:" + message, actual.hasCurrent());
	}
}
