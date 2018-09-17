package com.gmail.tt195361;

import java.util.List;

class ElementEnumerator {

	private final RegexElement[] _elements;
	private final int _length;
	private int _index;
	
	ElementEnumerator(List<RegexElement> elemList) {
		this(elemList.toArray(new RegexElement[0]));
	}
	
	private ElementEnumerator(RegexElement[] elements) {
		_elements = elements;
		_length = elements.length;
		_index = 0;
	}
	
	boolean hasCurrent() {
		return 0 <= _index && _index < _length;
	}
	
	void moveNext() {
		if (hasCurrent()) {
			++_index;
		}
	}
	
	RegexElement getCurrent() {
		return _elements[_index];
	}
	
	EnumeratorState saveState() {
		return new EnumeratorState(_index);
	}
	
	void restoreState(EnumeratorState state) {
		_index = state.getIndex();
	}
	
	@Override
	public String toString() {
		RegexElement current = getCurrent();
		return String.format("_index=%d, current=%s", _index, current.toString());
	}
	
	static ElementEnumerator makeForUnitTest(RegexElement...elements) {
		return new ElementEnumerator(elements);
	}
}
