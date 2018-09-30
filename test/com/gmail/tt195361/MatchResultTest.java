package com.gmail.tt195361;

import org.junit.Test;
import static org.junit.Assert.*;

public class MatchResultTest {
	
	@Test
	public void testSuccessResult() {
		final int StartIndex = 3;
		final String MatchString = "abcde";
		
		MatchResult result = MatchResult.makeSuccessResult(StartIndex, MatchString);
		check(
				result, true, StartIndex, MatchString,
				"¬Œ÷‚ÌŒ‹‰Ê -> isSuccess=true, startIndex ‚Æ matchString ‚Íˆø”‚ÅŽw’è‚Ì’l");
	}
	
	@Test
	public void testFailResult() {
		MatchResult result = MatchResult.makeFailResult();
		check(
				result, false, -1, null,
				"Ž¸”s‚ÌŒ‹‰Ê -> isSuccess=false, startIndex=-1, matchString=null");
	}

	private void check(
			MatchResult result, boolean expectedIsSuccess, int expectedStartIndex,
			String expectedMatchString, String message) {
		boolean actualIsSuccess = result.isSucceess();
		assertEquals("isSuccess: " + message,  expectedIsSuccess, actualIsSuccess);
		
		int actualStartIndex = result.getStartIndex();
		assertEquals("startIndex: " + message, expectedStartIndex, actualStartIndex);
		
		String actualMatchString = result.getMatchString();
		assertEquals("matchString: " + message, expectedMatchString, actualMatchString);
	}
}
