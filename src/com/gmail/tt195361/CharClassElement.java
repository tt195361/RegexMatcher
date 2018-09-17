package com.gmail.tt195361;

import java.util.HashSet;

class CharClassElement extends RegexElement {

	private final boolean _notContains;
	private final HashSet<Character> _charSet;
	
	CharClassElement(boolean notContains, HashSet<Character> charSet) {
		_notContains = notContains;
		_charSet = charSet;
	}
	
	boolean getNotContained() {
		return _notContains;
	}
	
	HashSet<Character> getCharSet() {
		return _charSet;
	}
	
	@Override
	boolean oneMatch(ElementEnumerator elemEnum, StringEnumerator strEnum) {
		char ch = strEnum.getCurrent();
		if (_charSet.contains(ch) ^ _notContains) {
			strEnum.moveNext();
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return String.format(
				"CharClassElement: _notContains=%s, _charSet=%s",
				Boolean.toString(_notContains), _charSet.toString());
	}
}
