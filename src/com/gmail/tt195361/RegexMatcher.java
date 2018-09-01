package com.gmail.tt195361;

public class RegexMatcher {
	
	private final ElementBuffer _elemBuffer;
	private String _matchString;
	
	public RegexMatcher(ElementBuffer elemBuffer) {
		_elemBuffer = elemBuffer;
	}
	
	public boolean match(String str) {
		_matchString = null;
		
		for (int index = 0; index < str.length(); ++index) {
			StringBuffer strBuffer = new StringBuffer(str, index);
			if (doMatch(_elemBuffer, strBuffer)) {
				_matchString = strBuffer.getMatchString();
				return true;
			}
		}
		
		return false;
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
	
	public String getMatchString() {
		return _matchString;
	}
}
