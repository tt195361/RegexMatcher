package com.gmail.tt195361;

public class RegexMatcher {
	
	private final ElementBuffer _elemBuffer;
	private final BufferState _initialElemBufferState;
	
	public RegexMatcher(String pattern) {
		_elemBuffer = RegexParser.parse(pattern);
		_initialElemBufferState = _elemBuffer.saveState();
	}
	
	public MatchResult match(String str) {
		for (int startIndex = 0; startIndex < str.length(); ++startIndex) {
			_elemBuffer.restoreState(_initialElemBufferState);
			StringBuffer strBuffer = new StringBuffer(str, startIndex);
			
			if (doMatch(_elemBuffer, strBuffer)) {
				String matchString = strBuffer.getMatchString();
				return MatchResult.makeSuccessResult(startIndex, matchString);
			}
		}
		
		return MatchResult.makeFailResult();
	}
	
	static boolean doMatch(ElementBuffer elemBuffer, StringBuffer strBuffer) {
		while (elemBuffer.hasCurrent()) {
			if (!strBuffer.hasCurrent()) {
				return false;
			}
			
			RegexElement element = elemBuffer.getCurrent();
			if (!element.oneMatch(elemBuffer, strBuffer)) {
				return false;
			}
			
			elemBuffer.moveNext();
		}

		return true;
	}
}
