package com.gmail.tt195361.Regex;

/**
 * 要素の並びを列挙する列挙子を表わす抽象クラスです。
 *
 * @param <TElement> 列挙する要素の型を指定します。
 */
abstract class Enumerator<TElement> {
	
	// メンバー変数: 列挙する要素の数と現在位置です。
	protected final int _count;
	private int _currentIndex;
	
	/**
	 * 指定の数の要素を列挙する列挙子を作成します。
	 * 
	 * @param count 列挙する要素の数です。
	 */
	protected Enumerator(int count) {
		_count = count;
		setCurrentIndex(0);
	}
	
	/**
	 * 指定の位置にある要素を取得します。
	 * それぞれの派生クラスで定義する抽象メソッドです。
	 * 
	 * @param index 取得する要素の位置を指定します。
	 * @return 指定の位置の要素を返します。
	 */
	protected abstract TElement getElementAt(int index);

	/**
	 * 存在しない要素を表わす値を取得します。
	 * それぞれの派生クラスで定義する抽象メソッドです。
	 * 
	 * @return 存在しない要素を表わす値を返します。
	 */
	protected abstract TElement getNullElement();
	
	/**
	 * 現在位置の値を設定します。
	 * 
	 * @param value 設定する現在位置の値です。
	 */
	protected void setCurrentIndex(int value) {
		_currentIndex = value;
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
	 * 現在位置に要素があるかどうかを調べます。
	 * 
	 * @return 現在位置に要素がある場合は {@code true} を、
	 * 		要素がない場合は {@code false} を返します。
	 */
	boolean hasCurrent() {
		return 0 <= _currentIndex && _currentIndex < _count;
	}
	
	/**
	 * 現在位置の要素を取得します。
	 * 
	 * @return 現在位置の要素を返します。現在位置に要素がない場合は
	 * 		{@link #getNullElement()} で取得した値を返します。
	 */
	TElement getCurrent() {
		if (!hasCurrent()) {
			return getNullElement();
		}
		else {
			return getElementAt(_currentIndex);
		}
	}
	
	/**
	 * 現在位置を次の要素に移動します。現在位置に要素がない場合は、
	 * 現在位置をそれ以上移動しません。
	 */
	void moveNext() {
		if (hasCurrent()) {
			setCurrentIndex(_currentIndex + 1);
		}
	}

	/**
	 * 列挙子の現在の状態を保存した {@link EnumeratorState} クラスの
	 * オブジェクトを作成します。
	 * 
	 * @return 列挙子の現在の状態を保存した {@link EnumeratorState} クラスの
	 * 		オブジェクトを返します。
	 */
	EnumeratorState saveState() {
		return new EnumeratorState(_currentIndex);
	}

	/**
	 * 列挙子の状態を指定の {@link EnumeratorState} クラスのオブジェクトが
	 * 保存した状態に戻します。
	 * 
	 * @param state 列挙子を状態を保存した {@link EnumeratorState} クラスの
	 * 		オブジェクトです。
	 */
	void restoreState(EnumeratorState state) {
		int current_index = state.getCurrentIndex();
		setCurrentIndex(current_index);
	}
}
