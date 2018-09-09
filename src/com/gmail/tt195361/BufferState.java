package com.gmail.tt195361;

class BufferState {

	private final int _index;
	
	BufferState(int index) {
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
