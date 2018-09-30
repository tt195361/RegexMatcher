package com.gmail.tt195361;

/**
 * 正規表現を構成する一つの要素を表わす抽象クラスです。
 */
abstract class RegexElement {
	
	/**
	 * {@link RegexElement} クラスのオブジェクトを作成します。
	 */
	protected RegexElement() {
		//
	}
	
	/**
	 * この正規表現要素が文字列の現在位置から一致するかどうかを調べます。
	 * 
	 * @param elemEnum 正規表現要素の列挙子です。呼び出し時の現在位置は、この正規表現要素であるものとします。
	 * 				呼び出し後の現在位置は、最後に探索した正規表現要素になります。
	 * @param strEnum 文字列の列挙子です。呼び出し後の現在位置は、
	 * 				この正規表現要素と一致した部分の次の位置に移動します。
	 * @return 一致に成功した場合は {@code true} を、失敗した場合は {@code false} を返します。
	 */
	abstract boolean oneMatch(ElementEnumerator elemEnum, StringEnumerator strEnum);
}
