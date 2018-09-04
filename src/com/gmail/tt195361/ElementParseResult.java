package com.gmail.tt195361;

class ElementParseResult {

	private final RegexElement _element;
	private final boolean _removeLastElem;
	
	ElementParseResult(RegexElement element, boolean removeLastElem) {
		_element = element;
		_removeLastElem = removeLastElem;
	}
	
	RegexElement getElement() {
		return _element;
	}
	
	boolean removeLastElem() {
		return _removeLastElem;
	}
}
