package com.gmail.tt195361;

class OneCharElement extends RegexElement {
	
	private final char _specifiedCh;
	
	OneCharElement(char ch) {
		_specifiedCh = ch;
	}
	
	char getExpectedChar() {
		return _specifiedCh;
	}
	
	@Override
	boolean oneMatch(ElementEnumerator elemEnum, StringEnumerator strEnum) {
		char actualCh = strEnum.getCurrent();
		if (actualCh != _specifiedCh) {
			return false;
		} 
		else {
			strEnum.moveNext();
			return true;
		}
	}
	
	@Override
	public String toString() {
		return "OneChar: " + Character.toString(_specifiedCh);
	}
}
