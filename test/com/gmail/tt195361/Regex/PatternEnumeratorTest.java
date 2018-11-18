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
		
		_threePatEnum = PatternEnumerator.makeForUnitTest(_pat1, _pat2, _pat3);
		_emptyPatEnum = PatternEnumerator.makeForUnitTest();
	}
	
	@Test
	public void hasCurrent() {
		checkHasCurrent(
				_threePatEnum, new Boolean[] { true, true, true, false },
				"���݈ʒu�ɕ��^������� true");
		checkHasCurrent(
				_emptyPatEnum, new Boolean[] { false },
				"��̗񋓎q�͍ŏ����當�^���Ȃ�");
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
				"���݈ʒu�̕��^���擾����A���^���Ȃ���� null ��Ԃ�");
		checkGetCurrent(
				_emptyPatEnum, new RegexPattern[] { null },
				"��̗񋓎q�͍ŏ����當�^���Ȃ�");
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
		
		assertFalse("expected �ɕ��^���Ȃ��͂�: " + message, expected.hasCurrent());
		assertFalse("actual �ɕ��^���Ȃ��͂�:" + message, actual.hasCurrent());
	}
}
