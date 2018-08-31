package com.gmail.tt195361;

public class AnyCharElement extends RegexElement {

	@Override
	public boolean oneMatch(ElementBuffer elemBuffer, StringBuffer strBuffer) {
		strBuffer.moveNext();
		return true;
	}
	
	@Override
	public String toString() {
		return "AnyChar";
	}
}
