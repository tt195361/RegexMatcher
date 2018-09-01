package com.gmail.tt195361;

class StringBuffer {

	private final String _str;
	private final int _startIndex;
	private final int _length;
	private int _index;
	
	StringBuffer(String str, int startIndex) {
		_str = str;
		_startIndex = startIndex;
		_length = str.length();
		_index = startIndex;
	}
	
	String getMatchString() {
		int matchLength = _index - _startIndex + 1;
		return _str.substring(_startIndex, matchLength);
	}
	
	boolean hasCurrent() {
		return 0 <= _index && _index < _length;
	}

	void moveNext() {
		if (hasCurrent()) {
			++_index;
		}
	}

	char getCurrent() {
		return _str.charAt(_index);
	}
	
	BufferState saveState() {
		return new BufferState(_index);
	}

	void restoreState(BufferState state) {
		_index = state.getIndex();
	}
	
	@Override
	public String toString() {
		if (!hasCurrent()) {
			return "";
		}
		else {
			return _str.substring(_index);
		}
	}
}
