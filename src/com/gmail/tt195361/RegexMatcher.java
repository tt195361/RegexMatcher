package com.gmail.tt195361;

public class RegexMatcher {
	
	private final ElementEnumerator _elemEnum;
	private final EnumeratorState _initialElemEnumState;
	
	public RegexMatcher(String pattern) {
		_elemEnum = RegexParser.parse(pattern);
		_initialElemEnumState = _elemEnum.saveState();
	}
	
	public MatchResult match(String str) {
		for (int startIndex = 0; startIndex < str.length(); ++startIndex) {
			_elemEnum.restoreState(_initialElemEnumState);
			StringEnumerator strEnum = new StringEnumerator(str, startIndex);
			
			if (doMatch(_elemEnum, strEnum)) {
				String matchString = strEnum.getMatchString();
				return MatchResult.makeSuccessResult(startIndex, matchString);
			}
		}
		
		return MatchResult.makeFailResult();
	}
	
	static boolean doMatch(ElementEnumerator elemEnum, StringEnumerator strEnum) {
		while (elemEnum.hasCurrent()) {
			if (!strEnum.hasCurrent()) {
				return false;
			}
			
			RegexElement element = elemEnum.getCurrent();
			if (!element.oneMatch(elemEnum, strEnum)) {
				return false;
			}
			
			elemEnum.moveNext();
		}

		return true;
	}
}
