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
	 * 指定の文字が含まれていないことを指定する値を取得します。
	 * 
	 * @return 指定の文字が含まれていないことを指定する値を返します。
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
	 * 文字クラスは、現在位置に文字があり、かつ、現在位置の文字が {@link #getNotContains} と
	 * {@link #getCharSet} の返す値と、以下のいずれかの条件を満たす場合に一致します。
	 * <ul>
	 *   <li>{@link #getNotContains} が {@code false} を返す場合、
	 *   	現在位置の文字が {@link #getCharSet} の返す {@link HashSet} に含まれている。</li>
	 *   <li>{@link #getNotContains} が {@code true} を返す場合、
	 *   	現在位置の文字が {@link #getCharSet} の返す {@link HashSet} に含まれていない。</li>
	 * </ul>
	 * 
	 * @param elemEnum 正規表現要素の列挙子です。呼び出し後の現在位置は移動しません。
	 * @param strEnum 文字列の列挙子です。一致に成功した場合、呼び出し後の現在位置は次の文字に移動します。
	 * @return 一致に成功した場合は {@code true} を、失敗した場合は {@code false} を返します。
	 */
	@Override
	boolean oneMatch(ElementEnumerator elemEnum, StringEnumerator strEnum) {
		if (!strEnum.hasCurrent()) {
			return false;
		}
		
		char ch = strEnum.getCurrent();
		if (!matchConditionSatisfied(ch)) {
			return false;
		}

		strEnum.moveNext();
		return true;
	}
	
	// 指定の文字が一致の条件を満たしているかどうかを調べます。
	private boolean matchConditionSatisfied(char ch) {
		// ^ は排他的論理和で、"false ^ true" あるいは "true ^ false" のとき、結果は true になります。
		return _notContains ^ _charSet.contains(ch);
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
