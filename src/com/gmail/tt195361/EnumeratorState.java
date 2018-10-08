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
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (!(obj instanceof EnumeratorState)) {
			return false;
		}
		
		EnumeratorState that = (EnumeratorState)obj;
		return this._index == that._index;
	}
	
	@Override
	public String toString() {
		return String.format("_index=%d", _index);
	}
}
