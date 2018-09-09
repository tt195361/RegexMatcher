package com.gmail.tt195361;

import java.util.HashSet;

class CharClassElement extends RegexElement {

	private final boolean _notContained;
	private final HashSet<Character> _charSet;
	
	CharClassElement(boolean notContained, HashSet<Character> charSet) {
		_notContained = notContained;
		_charSet = charSet;
	}
	
	boolean getNotContained() {
		return _notContained;
	}
	
	HashSet<Character> getCharSet() {
		return _charSet;
	}
	
	@Override
	boolean oneMatch(ElementBuffer elemBuffer, StringBuffer strBuffer) {
		char ch = strBuffer.getCurrent();
		if (_charSet.contains(ch) ^ _notContained) {
			strBuffer.moveNext();
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return String.format(
				"CharClassElement: _notContained=%s, _charSet=%s",
				Boolean.toString(_notContained), _charSet.toString());
	}
}
