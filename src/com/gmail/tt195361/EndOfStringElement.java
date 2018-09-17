package com.gmail.tt195361;

class EndOfStringElement extends RegexElement {

	@Override
	boolean oneMatch(ElementEnumerator elemEnum, StringEnumerator strEnum) {
		return strEnum.isEnd();
	}
	
	@Override
	public String toString() {
		return "EndOfString";
	}
}
