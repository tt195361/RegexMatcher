package com.gmail.tt195361;

class OneCharElement extends RegexElement {
	
	private final char _expectedChar;
	
	OneCharElement(char ch) {
		_expectedChar = ch;
	}
	
	char getExpectedChar() {
		return _expectedChar;
	}
	
	@Override
	boolean oneMatch(ElementBuffer elemBuffer, StringBuffer strBuffer) {
		char actualChar = strBuffer.getCurrent();
		if (actualChar != _expectedChar) {
			return false;
		} 
		else {
			strBuffer.moveNext();
			return true;
		}
	}
	
	@Override
	public String toString() {
		return "OneChar: " + Character.toString(_expectedChar);
	}
}
