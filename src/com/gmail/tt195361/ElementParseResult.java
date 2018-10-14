package com.gmail.tt195361;

/**
 * 正規表現要素を表わす文字列を解釈した結果を格納します。
 */
class ElementParseResult {

	// メンバー変数: 正規表現要素と、最後の正規表現要素を削除するかどうかを保持します。
	private final RegexElement _element;
	private final boolean _removeLastElem;
	
	/**
	 * パラメータで指定の値を持つ {@link ElementParseResult} クラスのオブジェクトを作成します。
	 * 
	 * @param element 解釈した結果をもとに作成した正規表現要素を表わす
	 * 		{@link RegexElement} クラスのオブジェクトです。
	 * @param removeLastElem 最後の正規表現要素を削除するかどうかを指定する
	 * 		{@code boolean} の値です。
	 */
	ElementParseResult(RegexElement element, boolean removeLastElem) {
		_element = element;
		_removeLastElem = removeLastElem;
	}
	
	/**
	 * 解釈した結果をもとに作成した正規表現要素のオブジェクトを取得します。
	 * 
	 * @return 正規表現要素を表わす {@link RegexElement} クラスのオブジェクトを返します。
	 */
	RegexElement getElement() {
		return _element;
	}
	
	/**
	 * 最後の正規表現要素を削除するかどうかを指定する値を取得します。
	 * 
	 * @return 最後の正規表現要素を削除するかどうかを指定する {@code boolean} の値です。
	 */
	boolean removeLastElem() {
		return _removeLastElem;
	}
	
	/**
	 * {@link ElementParseResult} クラスのオブジェクトの文字列表現を返します。
	 */
	@Override
	public String toString() {
		return String.format(
				"_element=%s, _removeLastElem=%s",
				_element.toString(), Boolean.toString(_removeLastElem));
	}
}
