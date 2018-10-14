package com.gmail.tt195361;

/**
 * 文字列の先頭に一致する正規表現要素です。
 */
class StartOfStringElement extends RegexElement {
	
	/**
	 * {@link StartOfStringElement} クラスのオブジェクトを作成します。
	 */
	StartOfStringElement() {
		//
	}

	/**
	 * この正規表現要素が文字列の現在位置と一致するかどうか調べます。
	 * この正規表現要素は現在位置が文字列の先頭であれば、一致します。
	 * 
	 * @param elemEnum 正規表現要素の列挙子です。呼び出し後の現在位置は移動しません。
	 * @param strEnum 文字列の列挙子です。呼び出し後の現在位置は移動しません。
	 * @return 一致に成功した場合は {@code true} を、失敗した場合は {@code false} を返します。
	 */
	@Override
	boolean oneMatch(ElementEnumerator elemEnum, StringEnumerator strEnum) {
		return strEnum.isStart();
	}
	
	/**
	 * {@link StartOfStringElement} クラスのオブジェクトの文字列表現を返します。
	 */
	@Override
	public String toString() {
		return "StartOfString";
	}
}
