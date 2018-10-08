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
	
	/**
	 * {@link ElementParseResult} クラスのオブジェクトの文字列表現を返します。
	 */
	@Override
	public String toString() {
		return String.format(
				"_element=%s, _removeLastElem=%s",
				_element.toString(), Boolean.toString(_removeLastElem));
	}
}
