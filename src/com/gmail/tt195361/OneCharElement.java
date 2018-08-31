package com.gmail.tt195361;

public class OneCharElement extends RegexElement {
	
	private final char _expectedChar;
	private String _matchString;
	
	public OneCharElement(char ch) {
		_expectedChar = ch;
	}
	
	@Override
	public boolean oneMatch(ElementBuffer elemBuffer, StringBuffer strBuffer) {
		char actualChar = strBuffer.getCurrent();
		if (actualChar != _expectedChar) {
			return false;
		} 
		else {
			_matchString = Character.toString(actualChar);
			strBuffer.moveNext();
			return true;
		}
	}
	
	@Override
	public String toString() {
		return "OneChar: " + Character.toString(_expectedChar);
	}
}
