package com.gmail.tt195361;

public class StringBuffer {

	private final String _str;
	private final int _length;
	private int _index;
	
	public StringBuffer(String str) {
		_str = str;
		_length = str.length();
		_index = 0;
	}
	
	public boolean hasCurrent() {
		return 0 <= _index && _index < _length;
	}

	public void moveNext() {
		if (hasCurrent()) {
			++_index;
		}
	}

	public char getCurrent() {
		return _str.charAt(_index);
	}
	
	public BufferState saveState() {
		return new BufferState(_index);
	}

	public void restoreState(BufferState state) {
		_index = state.getIndex();
	}
	
	@Override
	public String toString() {
		if (!hasCurrent()) {
			return "";
		}
		else {
			return "StringBuffer: '" + _str.substring(_index) + "'";
		}
	}
}
