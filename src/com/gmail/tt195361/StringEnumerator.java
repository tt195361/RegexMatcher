package com.gmail.tt195361;

class StringEnumerator {
	
	private final String _str;
	private final int _startIndex;
	private final int _length;
	private int _currentIndex;
	
	StringEnumerator(String str, int startIndex) {
		_str = str;
		_startIndex = startIndex;
		_length = str.length();
		_currentIndex = startIndex;
	}
	
	boolean isStart() {
		return _currentIndex == 0;
	}
	
	boolean isLast() {
		return _currentIndex == _length - 1;
	}
	
	boolean isEnd() {
		return _length <= _currentIndex;
	}
	
	String getMatchString() {
		return _str.substring(_startIndex, _currentIndex);
	}
	
	boolean hasCurrent() {
		return 0 <= _currentIndex && _currentIndex < _length;
	}

	void moveNext() {
		if (hasCurrent()) {
			++_currentIndex;
		}
	}

	char getCurrent() {
		return _str.charAt(_currentIndex);
	}
	
	EnumeratorState saveState() {
		return new EnumeratorState(_currentIndex);
	}

	void restoreState(EnumeratorState state) {
		_currentIndex = state.getIndex();
	}
	
	@Override
	public String toString() {
		if (!hasCurrent()) {
			return "";
		}
		else {
			return _str.substring(_currentIndex);
		}
	}
}
