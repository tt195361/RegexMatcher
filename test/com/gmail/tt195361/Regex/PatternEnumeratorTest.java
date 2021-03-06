package com.gmail.tt195361.Regex;

import java.util.function.*;
import org.junit.*;
import static org.junit.Assert.*;

public class PatternEnumeratorTest {
	
	RegexPattern _pat1;
	RegexPattern _pat2;
	RegexPattern _pat3;
	PatternEnumerator _threePatEnum;
	PatternEnumerator _emptyPatEnum;
	
	@Before
	public void setUp() {
		_pat1 = new AnyCharPattern();
		_pat2 = new OneCharPattern('a');
		_pat3 = new EndOfStringPattern();
		
		_threePatEnum = PatternEnumeratorTest.make(_pat1, _pat2, _pat3);
		_emptyPatEnum = PatternEnumeratorTest.make();
	}
	
	@Test
	public void hasCurrent() {
		checkHasCurrent(
				_threePatEnum, new Boolean[] { true, true, true, false },
				"現在位置に文型があれば true");
		checkHasCurrent(
				_emptyPatEnum, new Boolean[] { false },
				"空の列挙子は最初から文型がない");
	}
	
	private void checkHasCurrent(
			PatternEnumerator patEnum, Boolean[] expectedArray, String message) {
		checkFunction(
				(ee) -> (Boolean)ee.hasCurrent(),
				patEnum, expectedArray, message);
	}
	
	@Test
	public void getCurrent() {
		checkGetCurrent(
				_threePatEnum, new RegexPattern[] { _pat1, _pat2, _pat3, null },
				"現在位置の文型を取得する、文型がなければ null を返す");
		checkGetCurrent(
				_emptyPatEnum, new RegexPattern[] { null },
				"空の列挙子は最初から文型がない");
	}
	
	private void checkGetCurrent(
			PatternEnumerator patEnum, RegexPattern[] expectedArray, String message) {
		checkFunction(
				(ee) -> ee.getCurrent(),
				patEnum, expectedArray, message);
	}
	
	private <R> void checkFunction(
			Function<PatternEnumerator, R> checkFunc,
			PatternEnumerator patEnum, R[] expectedArray, String message) {
		for	(R expected: expectedArray) {
			R actual = checkFunc.apply(patEnum);
			assertEquals(message, expected, actual);
			
			patEnum.moveNext();
		}
	}
	
	static void check(PatternEnumerator expected, PatternEnumerator actual, String message) {
		while (expected.hasCurrent() && actual.hasCurrent()) {
			RegexPattern expectedPattern = expected.getCurrent();
			RegexPattern actualPattern = actual.getCurrent();
			
			TestUtils.areSameClass(expectedPattern, actualPattern, message);
			RegexPatternTest.check(expectedPattern, actualPattern, message);
			
			expected.moveNext();
			actual.moveNext();
		}
		
		assertFalse("expected に文型がないはず: " + message, expected.hasCurrent());
		assertFalse("actual に文型がないはず:" + message, actual.hasCurrent());
	}

	static PatternEnumerator make(RegexPattern...patterns) {
		return new PatternEnumerator(patterns);
	}
}
