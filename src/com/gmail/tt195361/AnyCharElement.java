package com.gmail.tt195361;

class AnyCharElement extends RegexElement {

	@Override
	boolean oneMatch(ElementEnumerator elemEnum, StringEnumerator strEnum) {
		strEnum.moveNext();
		return true;
	}
	
	@Override
	public String toString() {
		return "AnyChar";
	}
}
