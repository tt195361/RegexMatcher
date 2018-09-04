package com.gmail.tt195361;

public class MatchResult {

	static MatchResult makeFailResult() {
		return new MatchResult(false, -1, null);
	}
	
	static MatchResult makeSuccessResult(int startIndex, String matchString) {
		return new MatchResult(true, startIndex, matchString);
	}
	
	private final boolean _isSucceeded;
	private final int _startIndex;
	private final String _matchString;
	
	private MatchResult(boolean isSucceeded, int startIndex, String matchString) {
		_isSucceeded = isSucceeded;
		_startIndex = startIndex;
		_matchString = matchString;
	}
	
	public boolean isSucceess() {
		return _isSucceeded;
	}
	
	public int getStartIndex() {
		return _startIndex;
	}
	
	public String getMatchString() {
		return _matchString;
	}
}
