package com.gmail.tt195361;

/**
 * 列挙子の状態を格納します。
 */
class EnumeratorState {

	// メンバー変数: 列挙子の現在位置です。
	private final int _currentIndex;
	
	/**
	 * パラメータで指定の列挙子の状態を格納する {@link EnumeratorState} クラスのオブジェクトを作成します。
	 * 
	 * @param currentIndex 状態を格納する列挙子の現在位置の値です。
	 */
	EnumeratorState(int currentIndex) {
		_currentIndex = currentIndex;
	}
	
	/**
	 * 保存した列挙子の現在位置の値を取得します。
	 * 
	 * @return　列挙子の現在位置の値を返します。
	 */
	int getCurrentIndex() {
		return _currentIndex;
	}
	
	/**
	 * このオブジェクトと他のオブジェクトが等しいかどうかを比較します。
	 * {@link EnumeratorState} クラスのオブジェクトは、別のオブジェクトでも
	 * 保持する状態が同じならば等しいものとします。
	 * 
	 * @return　このオブジェクトが指定のオブジェクトと等しい場合は {@code true}、
	 * 		等しくない場合は {@code false} を返します。
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (!(obj instanceof EnumeratorState)) {
			return false;
		}
		
		EnumeratorState that = (EnumeratorState)obj;
		return this._currentIndex == that._currentIndex;
	}
	
	/**
	 * オブジェクトのハッシュコードの値を取得します。
	 * {@link equals} メソッドが {@code true} を返すオブジェクトは、同じハッシュコードを返す
	 * ようにします。そのため保持する状態に基づいてハッシュコードを作成します。
	 * 
	 * @return このオブジェクトのハッシュコードの値を返します。
	 */
	@Override
	public int hashCode() {
		return _currentIndex;
	}
	
	/**
	 * {@link EnumeratorState} クラスのオブジェクトの文字列表現を返します。
	 */
	@Override
	public String toString() {
		return String.format("_index=%d", _currentIndex);
	}
}
