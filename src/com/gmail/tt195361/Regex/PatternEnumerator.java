package com.gmail.tt195361.Regex;

/**
 * 正規表現の一つの文型を表わす {@link RegexPattern} のオブジェクトを管理し順に列挙する列挙子です。
 * 
 * <p>
 * 列挙子は、列挙する項目のコレクションを保持するとともに、その中の一つの項目を指し示す現在位置を管理します。
 * 以下のメソッドを用いて、列挙子の現在位置を取り扱い、コレクションの項目を列挙します。
 * <ul>
 *   <li>{@link #hasCurrent} メソッドで、現在位置に項目があるかどうかを調べます。</li>
 *   <li>{@link #getCurrent} メソッドで、現在位置の項目を取得します。</li>
 *   <li>{@link #moveNext} メソッドで、現在位置を次の項目に移動します。</li>
 * </ul>
 * <p>
 * 列挙子の状態を保存し、保存した状態に復元するには、以下のメソッドを使います。
 * <ul>
 *   <li>{@link #saveState} メソッドで、列挙子の現在の状態を保存します。</li>
 *   <li>{@link #restoreState} メソッドで、列挙子を保存された状態に戻します。</li>
 * </ul>
 */
class PatternEnumerator {

	// メンバー変数: 列挙する正規表現文型の配列、その現在位置です。
	private final RegexPattern[] _patterns;
	private int _currentIndex;
	
	/**
	 * 指定の正規表現文型を列挙する列挙子を作成します。
	 * 
	 * @param patterns 列挙する正規表現文型を格納する配列です。
	 */
	PatternEnumerator(RegexPattern[] patterns) {
		_patterns = patterns;
		_currentIndex = 0;
	}
	
	/**
	 * 現在位置に文型があるかどうかを調べます。
	 * 
	 * @return 現在位置に文型がある場合は {@code true} を、文型がない場合は {@code false} を返します。
	 */
	boolean hasCurrent() {
		return 0 <= _currentIndex && _currentIndex < _patterns.length;
	}
	
	/**
	 * 現在位置の文型を取得します。
	 * 
	 * @return 現在位置の文型を返します。現在位置に文型がない場合は {@code null} を返します。
	 */
	RegexPattern getCurrent() {
		if (!hasCurrent()) {
			return null;
		}
		else {
			return _patterns[_currentIndex];
		}
	}
	
	/**
	 * 現在位置を次の文型に移動します。現在位置に文型がない場合は、現在位置をそれ以上移動しません。
	 */
	void moveNext() {
		if (hasCurrent()) {
			++_currentIndex;
		}
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
	 * {@link PatternEnumerator} クラスのオブジェクトの文字列表現を返します。
	 */
	@Override
	public String toString() {
		RegexPattern current = getCurrent();
		String currentStr = (current == null) ? "NULL" : current.toString();
		return String.format("_index=%d, current=%s", _currentIndex, currentStr);
	}
}
