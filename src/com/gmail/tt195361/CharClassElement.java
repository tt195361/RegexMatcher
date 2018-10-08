package com.gmail.tt195361;

import java.util.HashSet;

/**
 * 指定の文字が含まれているかどうかを取り扱う文字クラスを表わす正規表現要素です。
 */
class CharClassElement extends RegexElement {

	// メンバー変数: 含まれていないの指定と指定の文字を格納します。
	private final boolean _notContains;
	private final HashSet<Character> _charSet;
	
	/**
	 * パラメータで指定の内容を表わす {@link CharClassElement} クラスのオブジェクトを作成します。
	 * 
	 * @param notContains 指定の文字が含まれていないことを指定します。
	 * @param charSet 指定の文字を格納する {@link HashSet} です。
	 */
	CharClassElement(boolean notContains, HashSet<Character> charSet) {
		_notContains = notContains;
		_charSet = charSet;
	}
	
	/**
	 * 指定の文字が含まれていないことを示す値を取得します。
	 * 
	 * @return 指定の文字が含まれていないことを示す値を返します。
	 */
	boolean getNotContains() {
		return _notContains;
	}
	
	/**
	 * 指定の文字を格納する {@link HashSet} を取得します。
	 * 
	 * @return 指定の文字を格納する {@link HashSet} を返します。
	 */
	HashSet<Character> getCharSet() {
		return _charSet;
	}
	
	/**
	 * この正規表現要素が文字列の現在位置から一致するかどうかを調べます。
	 * 文字クラスは、現在位置の文字が指定の文字に含まれているかどうかで一致を判断します。
	 * 
	 * @param elemEnum 正規表現要素の列挙子です。呼び出し後の現在位置は移動しません。
	 * @param strEnum 文字列の列挙子です。一致に成功した場合、呼び出し後の現在位置は次の文字に移動します。
	 * @return 一致に成功した場合は {@code true} を、失敗した場合は {@code false} を返します。
	 */
	@Override
	boolean oneMatch(ElementEnumerator elemEnum, StringEnumerator strEnum) {
		char ch = strEnum.getCurrent();
		
		// ^ は排他的論理和で、"true ^ false" あるいは "false ^ true" のとき、結果は true になります。
		if (_charSet.contains(ch) ^ _notContains) {
			strEnum.moveNext();
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * {@link CharClassElement} クラスのオブジェクトの文字列表現を返します。
	 */
	@Override
	public String toString() {
		return String.format(
				"CharClassElement: _notContains=%s, _charSet=%s",
				Boolean.toString(_notContains), _charSet.toString());
	}
}
