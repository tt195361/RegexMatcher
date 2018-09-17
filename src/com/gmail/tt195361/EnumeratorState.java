package com.gmail.tt195361;

class EnumeratorState {

	private final int _index;
	
	EnumeratorState(int index) {
		_index = index;
	}
	
	int getIndex() {
		return _index;
	}
	
	@Override
	public String toString() {
		return String.format("_index=%d", _index);
	}
}
