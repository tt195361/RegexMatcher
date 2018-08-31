package com.gmail.tt195361;

public class ElementBuffer {

	private final RegexElement[] _elements;
	private final int _length;
	private int _index;
	
	public ElementBuffer(RegexElement...elements) {
		_elements = elements;
		_length = elements.length;
		_index = 0;
	}
	
	public boolean hasCurrent() {
		return _index < _length;
	}
	
	public void moveNext() {
		if (hasCurrent()) {
			++_index;
		}
	}
	
	public RegexElement getCurrent() {
		return _elements[_index];
	}
	
	public BufferState saveState() {
		return new BufferState(_index);
	}
	
	public void restoreState(BufferState state) {
		_index = state.getIndex();
	}
	
	@Override
	public String toString() {
		RegexElement current = getCurrent();
		return String.format("ElementBuffer: %d: %s", _index, current.toString());
	}
}
