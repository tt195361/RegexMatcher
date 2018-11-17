package com.gmail.tt195361.Regex;

/**
 * 正規表現が文字列と一致するかどうかを調べた結果を格納します。
 */
public class MatchResult {

	/**
	 * 一致に成功した場合の結果を作成します。
	 * 
	 * @param startIndex 一致が開始した文字列のインデックスです。
	 * @param matchString 正規表現が一致した部分の文字列です。
	 * @return パラメータで指定した内容を持つ一致に成功した結果を格納する {@link MatchResult} クラスの
	 * 		オブジェクトを作成して返します。
	 */
	static MatchResult makeSuccessResult(int startIndex, String matchString) {
		return new MatchResult(true, startIndex, matchString);
	}

	/**
	 * 一致に失敗した場合の結果を作成します。
	 * 
	 * @return 一致に失敗した結果を格納する {@link MatchResult} クラスのオブジェクトを作成して返します。
	 */
	static MatchResult makeFailResult() {
		return new MatchResult(false, -1, null);
	}
	
	private final boolean _isSuccess;
	private final int _startIndex;
	private final String _matchString;
	
	// スコープを private にして 直接作成させないようにします。
	// makeSuccessResult() あるいは makeFailResult() で作成します。
	private MatchResult(boolean isSuccess, int startIndex, String matchString) {
		_isSuccess = isSuccess;
		_startIndex = startIndex;
		_matchString = matchString;
	}
	
	/**
	 * 正規表現が文字列との一致に成功したかどうかを取得します。
	 * 
	 * @return　一致に成功した場合は {@code true} を、失敗した場合は {@code false} を返します。
	 */
	public boolean isSucceess() {
		return _isSuccess;
	}
	
	/**
	 * 一致が開始した文字列のインデックスを取得します。
	 * 
	 * @return 一致が開始した文字列のインデックスを返します。一致に失敗した場合は {@code -1} を返します。
	 */
	public int getStartIndex() {
		return _startIndex;
	}
	
	/**
	 * 正規表現が一致した部分の文字列を取得します。
	 * 
	 * @return 正規表現が一致した部分の文字列を返します。一致に失敗した場合は {@code null} を返します。
	 */
	public String getMatchString() {
		return _matchString;
	}
	
	/**
	 * {@link MatchResult} クラスのオブジェクトの文字列表現を返します。
	 */
	@Override
	public String toString() {
		return String.format(
				"_isSuccess=%s, _startIndex=%d, _matchString=%s",
				Boolean.toString(_isSuccess), _startIndex, _matchString);
	}
}
