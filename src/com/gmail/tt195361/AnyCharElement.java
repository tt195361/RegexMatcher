package com.gmail.tt195361;

class AnyCharElement extends RegexElement {

	@Override
	boolean oneMatch(ElementBuffer elemBuffer, StringBuffer strBuffer) {
		strBuffer.moveNext();
		return true;
	}
	
	@Override
	public String toString() {
		return "AnyChar";
	}
}
