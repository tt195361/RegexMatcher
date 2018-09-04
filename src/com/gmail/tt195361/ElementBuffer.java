package com.gmail.tt195361;

import java.util.List;

class ElementBuffer {

	private final RegexElement[] _elements;
	private final int _length;
	private int _index;
	
	ElementBuffer(List<RegexElement> elemList) {
		this(elemList.toArray(new RegexElement[0]));
	}
	
	private ElementBuffer(RegexElement[] elements) {
		_elements = elements;
		_length = elements.length;
		_index = 0;
	}
	
	boolean hasCurrent() {
		return _index < _length;
	}
	
	void moveNext() {
		if (hasCurrent()) {
			++_index;
		}
	}
	
	RegexElement getCurrent() {
		return _elements[_index];
	}
	
	BufferState saveState() {
		return new BufferState(_index);
	}
	
	void restoreState(BufferState state) {
		_index = state.getIndex();
	}
	
	@Override
	public String toString() {
		RegexElement current = getCurrent();
		return String.format("_index=%d, current=%s", _index, current.toString());
	}
	
	static ElementBuffer makeForUnitTest(RegexElement...elements) {
		return new ElementBuffer(elements);
	}
}
