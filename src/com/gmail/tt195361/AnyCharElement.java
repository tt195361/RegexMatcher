package com.gmail.tt195361;

/**
 * 任意の一文字を表わす正規表現要素です。
 */
class AnyCharElement extends RegexElement {

	/**
	 * {@link AnyCharElement} クラスのオブジェクトを作成します。
	 */
	AnyCharElement() {
		//
	}
	
	/**
	 * この正規表現要素が文字列の現在位置と一致するかどうか調べます。
	 * 任意の一文字は、現在位置が文字列の終わりでなければ、どの文字とも一致します。
	 * 
	 * @param elemEnum 正規表現要素の列挙子です。呼び出し後の現在位置は移動しません。
	 * @param strEnum 文字列の列挙子です。一致に成功した場合、呼び出し後の現在位置は次の文字に移動します。
	 * @return 一致に成功した場合は {@code true} を、失敗した場合は {@code false} を返します。
	 */
	@Override
	boolean oneMatch(ElementEnumerator elemEnum, StringEnumerator strEnum) {
		if (strEnum.isEnd()) {
			return false;
		}
		else {
			strEnum.moveNext();
			return true;
		}
	}
	
	/**
	 * {@link AnyCharElement} クラスのオブジェクトの文字列表現を返します。
	 */
	@Override
	public String toString() {
		return "AnyChar";
	}
}
