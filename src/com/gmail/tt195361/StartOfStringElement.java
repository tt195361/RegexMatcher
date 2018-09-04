package com.gmail.tt195361;

class StartOfStringElement extends RegexElement {

	@Override
	boolean oneMatch(ElementBuffer elemBuffer, StringBuffer strBuffer) {
		return strBuffer.isStart();
	}
	
	@Override
	public String toString() {
		return "StartOfString";
	}
}
