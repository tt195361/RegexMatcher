package com.gmail.tt195361;

import java.util.List;

/**
 * 正規表現の一つの要素を表わす {@link RegexElement} のオブジェクトを順に列挙する列挙子です。
 * 
 * <p>
 * 列挙子は、列挙する要素のコレクションを保持するとともに、その中の一つの要素を指し示す現在位置を管理します。
 * 以下のメソッドを用いて、列挙子の現在位置を取り扱い、コレクションの要素を列挙します。
 * <ul>
 *   <li>{@link hasCurrent} メソッドで、現在位置に要素があるかどうかを調べます。</li>
 *   <li>{@link getCurrent} メソッドで、現在位置の要素を取得します。</li>
 *   <li>{@link moveNext} メソッドで、現在位置を次の要素に移動します。</li>
 * </ul>
 * <p>
 * 列挙子の状態を保存し、保存した状態に復元するには、以下のメソッドを使います。
 * <ul>
 *   <li>{@link saveState} メソッドで、列挙子の現在の状態を保存します。</li>
 *   <li>{@link restoreState} メソッドで、列挙子を保存された状態に戻します。</li>
 * </ul>
 */
class ElementEnumerator {

	// メンバー変数: 列挙する正規表現要素の配列、その配列の長さ、現在位置です。
	private final RegexElement[] _elements;
	private final int _length;
	private int _currentIndex;
	
	/**
	 * 指定のリストの正規表現要素を列挙する列挙子を作成します。
	 * 
	 * @param elemList 列挙する正規表現要素を格納したリストです。
	 */
	ElementEnumerator(List<RegexElement> elemList) {
		this(elemList.toArray(new RegexElement[0]));
	}
	
	private ElementEnumerator(RegexElement[] elements) {
		_elements = elements;
		_length = elements.length;
		_currentIndex = 0;
	}
	
	/**
	 * 現在位置に要素があるかどうかを調べます。
	 * 
	 * @return 現在位置に要素がある場合は {@code true} を、要素がない場合は {@code false} を返します。
	 */
	boolean hasCurrent() {
		return 0 <= _currentIndex && _currentIndex < _length;
	}
	
	/**
	 * 現在位置を次の要素に移動します。現在位置に要素がない場合は、現在位置をそれ以上移動しません。
	 */
	void moveNext() {
		if (hasCurrent()) {
			++_currentIndex;
		}
	}
	
	/**
	 * 現在位置の要素を取得します。
	 * 
	 * @return 現在位置の要素を返します。現在位置に要素がない場合は {@code null} を返します。
	 */
	RegexElement getCurrent() {
		if (!hasCurrent()) {
			return null;
		}
		else {
			return _elements[_currentIndex];
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
	 * {@link ElementEnumerator} クラスのオブジェクトの文字列表現を返します。
	 */
	@Override
	public String toString() {
		RegexElement current = getCurrent();
		String currentStr = (current == null) ? "NULL" : current.toString();
		return String.format("_index=%d, current=%s", _currentIndex, currentStr);
	}
	
	/**
	 * 指定の正規表現要素を列挙する列挙子を作成します、単体テストで使うためのメソッドです。
	 * 
	 * @param elements 列挙する正規表現要素を指定します。
	 * @return 作成した列挙子を返します。
	 */
	static ElementEnumerator makeForUnitTest(RegexElement...elements) {
		return new ElementEnumerator(elements);
	}
}
