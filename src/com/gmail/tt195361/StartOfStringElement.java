package com.gmail.tt195361;

class StartOfStringElement extends RegexElement {

	@Override
	boolean oneMatch(ElementEnumerator elemEnum, StringEnumerator strEnum) {
		return strEnum.isStart();
	}
	
	@Override
	public String toString() {
		return "StartOfString";
	}
}
