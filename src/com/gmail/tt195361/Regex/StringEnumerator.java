package com.gmail.tt195361.Regex;

/**
 * 文字列中の文字を列挙する列挙子です。
 * 
 * <p>
 * 列挙子の要素の列挙と状態の保存と復元については、{@link ElementEnumerator} の
 * 説明を参照してください。
 * <p>
 * 文字列の列挙子の現在位置は、次に説明するように、それぞれの文字を順に列挙したあと、
 * 文字列の終わりに移動し、次に終わりを越えた位置に移動します。
 * <ul>
 *   <li>現在位置は文字列中のそれぞれの文字を順に移動します。
 *   	この位置では {@link #hasCurrent} と {@link #hasCurrentOrEnd} が
 *   	{@code true} を返します。また、最初の文字では {@link #isStart} が、
 *   	最後の文字では {@link #isLast} が {@code true} を返します。</li>
 *   <li>すべての文字を列挙したあとは、文字列の終わりに移動します。
 *   	この位置では {@link #isEnd} と {@link #hasCurrentOrEnd} が
 *   	{@code true} を返します。</li>
 *   <li>さらに移動すると、文字列の終わりを越えた位置に移動します。
 *   	この位置では  {@link #hasCurrent}, {@link #isEnd}, 
 *   	{@link #hasCurrentOrEnd} のすべてが {@code false} を返します。</li>
 * </ul>
 * <p>
 * {@link #getCurrentIndex} は、現在位置の値を取得します。
 * また {@link #getCurrent} は、現在位置の文字を返します。
 * <p>
 * 文字列 "abc" を、開始位置 0 から列挙する場合、{@link #moveNext} メソッドで
 * 現在位置を移動するたびに、それぞれのメソッドが返す値は以下のようになります。
 * <table
 * 		border="1" rules="all"
 * 		summary="{@link #moveNext} の呼び出しと各メソッドの戻り値">
 *   <tr　align="center">
 *     <th>{@link #moveNext}<br>呼び出し回数</th>
 *     <th>{@link #hasCurrent}</th>
 *     <th>{@link #hasCurrentOrEnd}</th>
 *     <th>{@link #isStart}</th>
 *     <th>{@link #isLast}</th>
 *     <th>{@link #isEnd}</th>
 *     <th>{@link #getCurrentIndex}</th>
 *     <th>{@link #getCurrent}</th>
 *   </tr>
 *   <tr align="center">
 *     <td>0 回</td>
 *     <td>{@code true}</td>
 *     <td>{@code true}</td>
 *     <td>{@code true}</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *     <td>0</td>
 *     <td>'a'</td>
 *   </tr>
 *   <tr align="center">
 *     <td>1 回</td>
 *     <td>{@code true}</td>
 *     <td>{@code true}</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *     <td>1</td>
 *     <td>'b'</td>
 *   </tr>
 *   <tr align="center">
 *     <td>2 回</td>
 *     <td>{@code true}</td>
 *     <td>{@code true}</td>
 *     <td>{@code false}</td>
 *     <td>{@code true}</td>
 *     <td>{@code false}</td>
 *     <td>2</td>
 *     <td>'c'</td>
 *   </tr>
 *   <tr align="center">
 *     <td>3 回</td>
 *     <td>{@code false}</td>
 *     <td>{@code true}</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *     <td>{@code true}</td>
 *     <td>3</td>
 *     <td>{@code '\0'}</td>
 *   </tr>
 *   <tr align="center">
 *     <td>4 回</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *     <td>4</td>
 *     <td>{@code '\0'}</td>
 *   </tr>
 * </table>
 */
class StringEnumerator {
	
	// メンバー変数: 文字列、開始位置、文字列の長さ、現在位置を保持します。
	private final String _str;
	private final int _startIndex;
	private final int _length;
	private int _currentIndex;
	
	/**
	 * 指定の文字列のそれぞれの文字を指定の開始位置から列挙する列挙子を作成します。
	 * 
	 * @param str それぞれの文字を列挙する文字列です。
	 * @param startIndex 列挙の開始位置を指定します。文字列の最初の文字の位置は 0 になります。
	 */
	StringEnumerator(String str, int startIndex) {
		_str = str;
		_startIndex = startIndex;
		_length = str.length();
		_currentIndex = startIndex;
	}
	
	/**
	 * 現在位置を次の文字に移動します。現在位置が文字列の終わりを越えている場合、
	 * 現在位置はそれ以上移動しません。
	 */
	void moveNext() {
		if (hasCurrentOrEnd()) {
			++_currentIndex;
		}
	}
	
	/**
	 * 現在位置に文字があるかどうかを調べます。
	 * 
	 * @return 現在位置に文字があれば {@code true} を、文字がなければ {@code false} を返します。
	 */
	boolean hasCurrent() {
		return 0 <= _currentIndex && _currentIndex < _length;
	}
	
	/**
	 * 現在位置に文字があるか、あるいは、現在位置が文字列の終わりかを調べます。
	 * 
	 * @return　現在位置に文字があるか、あるいは、文字列の終わりである場合は {@code true} を、
	 * 		それ以外の場合は {@code false} を返します。
	 */
	boolean hasCurrentOrEnd() {
		return hasCurrent() || isEnd();
	}
	
	/**
	 * 現在位置が文字列の最初かどうかを調べます。
	 * 
	 * @return 現在位置が文字列の最初ならば {@code true} を、
	 * 		最初でなければ {@code false} を返します。
	 */
	boolean isStart() {
		return _currentIndex == 0;
	}
	
	/**
	 * 現在位置が文字列の最後の文字かどうかを調べます。
	 * 
	 * @return 現在位置が文字列の最後の文字ならば {@code true} を、
	 * 		最後でなければ {@code false} を返します。
	 */
	boolean isLast() {
		return _currentIndex == _length - 1;
	}
	
	/**
	 * 現在位置が文字列の終わりかどうかを調べます。
	 * 
	 * @return 現在位置が文字列の終わりならば {@code true} を、
	 * 		終わりでなければ {@code false} を返します。
	 */
	boolean isEnd() {
		return _length == _currentIndex;
	}
	
	/**
	 * 指定の文字列の終了位置の値を取得します。
	 * 
	 * @param str 終了位置の値を取得する文字列です。
	 * @return 指定の文字列の終了位置の値を返します。
	 */
	static int getEndIndex(String str) {
		return str.length();
	}

	/**
	 * 現在位置の値を取得します。
	 * 
	 * @return 現在位置の値を返します。
	 */
	int getCurrentIndex() {
		return _currentIndex;
	}

	/**
	 * 現在位置の文字を取得します。
	 * 
	 * @return 現在位置の文字を返します。現在位置が文字列の終わり、あるいは終わりを越えた位置にあり、
	 * 		その位置に文字がない場合は {@code '\0'} を返します。
	 */
	char getCurrent() {
		if (!hasCurrent()) {
			return '\0';
		}
		else {
			return _str.charAt(_currentIndex);
		}
	}
	
	/**
	 * 開始位置から現在位置までの部分文字列を取得します。
	 * 
	 * @return 開始位置から現在位置までの部分文字列を返します。
	 */
	String getSubstring() {
		return _str.substring(_startIndex, getEndIndex());
	}
	
	/**
	 * 列挙子の現在の状態を保存した {@link EnumeratorState} クラスのオブジェクトを作成します。
	 * 
	 * @return 列挙子の現在の状態を保存した {@link EnumeratorState} クラスのオブジェクトを返します。
	 */
	EnumeratorState saveState() {
		return new EnumeratorState(_currentIndex);
	}

	/**
	 * 列挙子の状態を指定の {@link EnumeratorState} クラスのオブジェクトが保存した状態に戻します。
	 * 
	 * @param state 列挙子を状態を保存した {@link EnumeratorState} クラスのオブジェクトです。
	 */
	void restoreState(EnumeratorState state) {
		_currentIndex = state.getCurrentIndex();
	}
	
	/**
	 * {@link StringEnumerator} クラスのオブジェクトの文字列表現を返します。
	 */
	@Override
	public String toString() {
		return String.format("_currentIndex=%d, rest=%s", _currentIndex, getRest());
	}
	
	private String getRest() {
		return _str.substring(getEndIndex());
	}
	
	private int getEndIndex() {
		return Math.min(_currentIndex, _length);
	}
}
