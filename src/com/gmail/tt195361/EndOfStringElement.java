package com.gmail.tt195361;

class EndOfStringElement extends RegexElement {

	@Override
	boolean oneMatch(ElementBuffer elemBuffer, StringBuffer strBuffer) {
		return strBuffer.isEnd();
	}
	
	@Override
	public String toString() {
		return "EndOfString";
	}
}
