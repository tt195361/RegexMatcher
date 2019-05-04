package com.gmail.tt195361.Regex;

import java.util.HashSet;

/**
 * 文字クラスを表わす正規表現文型です。文字クラスは
 * 指定の文字が含まれているかどうかを取り扱います。
 * 
 * <h1 id="メンバー変数">メンバー変数</h1>
 * <p>
 * {@link CharClassPattern} クラスは、以下のメンバー変数を使います。
 * <div align="center">
 * <table
 * 		border="1" rules="all"
 * 		summary="StringEnumerator クラスのメンバー変数">
 *   <tr bgcolor="lightgray">
 *     <th align="center">名前</th>
 *     <th align="center">型</th>
 *     <th align="center">説明</th>
 *   </tr>
 *   <tr>
 *     <td>{@code _notContains}</td>
 *     <td>{@code final boolean}</td>
 *     <td>指定の文字が含まれていないことを示します。
 *         {@code [ ]} の場合は {@code false} に、
 *         {@code [^ ]} の場合は {@code true} になります。
 *     	   この変数の値は、コンストラクタで最初に設定したあとは変更しないので、
 *     	   {@code final} にします。
 *         <p>
 *         この変数の名前ですが、何も指定しない
 *         デフォルトの値が {@code false} になるようにしました。
 *         ここでは {@code [ ]} の場合に
 *         値が {@code false} になるよう、変数名を {@code _contains}
 *         ではなく {@code _notContains} にしています。</td>
 *   </tr>
 *   <tr>
 *     <td>{@code _charSet}</td>
 *     <td>{@code final HashSet<Character>}</td>
 *     <td>{@code [ ]} 内で指定された文字を
 *         格納する {@code Character} の {@link java.util.HashSet} です。
 *         この値も、コンストラクタで
 *         最初に設定したあとは変更しないので、{@code final} にします。</td>
 *   </tr>
 * </table>
 * </div>
 * 
 * <h1 id="文字クラスの一致">文字クラスの一致</h1>
 * <p>
 * 文字クラスが一致するのは、現在位置に文字があり、かつ、
 * メンバー変数 {@code _notContains} と {@code _charSet} が
 * あとで説明する条件を満たす場合です。
 * この条件を調べるメソッドを {@code matchConditionSatisfied()} とすると、
 * {@link #oneMatch(PatternEnumerator, StringEnumerator)} メソッドは、
 * 次のようになります。
 * <pre>
        &#64;Override
        boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum) {
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
 * </pre>
 * <p>
 * メンバー変数 {@code _notContains} と {@code _charSet}、
 * および現在位置の文字 {@code ch} が一致のために満たす条件は、
 * 次のようになります。
 * <div align="center">
 * <table
 * 		border="1" rules="all"
 * 		summary="一致の条件">
 *   <tr bgcolor="lightgray" align="center">
 *     <th>{@code _notContains}</th>
 *     <th>{@code _charSet.contains(ch)}</th>
 *     <th>説明</th>
 *   </tr>
 *   <tr>
 *     <td align="center">{@code false}</td>
 *     <td align="center">{@code true}</td>
 *     <td>{@code [ ]} の場合は、現在位置の文字が {@code _charSet} に
 *         含まれていれば条件を満たします。</td>
 *   </tr>
 *   <tr>
 *     <td align="center">{@code true}</td>
 *     <td align="center">{@code false}</td>
 *     <td>{@code [^ ]} の場合は、現在位置の文字が {@code _charSet} に
 *         含まれていなければ条件を満たします。</td>
 *   </tr>
 * </table>
 * </div>
 * <p>
 * この条件は、排他的論理和 (XOR) を使って記述できます。
 * {@code matchConditionSatisfied()} メソッドは、
 * 以下のようになります。
 * <pre>
        private boolean matchConditionSatisfied(char ch) {
            // ^ は排他的論理和で、"false ^ true" あるいは "true ^ false" のとき、
            // 結果は true になります。
            return _notContains ^ _charSet.contains(ch);
        }
 * </pre>
 */
class CharClassPattern extends RegexPattern {

	// メンバー変数: 含まれていないの指定と指定の文字を格納します。
	private final boolean _notContains;
	private final HashSet<Character> _charSet;
	
	/**
	 * パラメータで指定の内容を表わす {@link CharClassPattern} クラスのオブジェクトを
	 * 作成します。
	 * 
	 * @param notContains 指定の文字が含まれていないことを指定します。
	 * @param charSet 指定の文字を格納する {@link HashSet} です。
	 */
	CharClassPattern(boolean notContains, HashSet<Character> charSet) {
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
	 * この正規表現文型が文字列の現在位置から一致するかどうかを調べます。
	 * 文字クラスは、現在位置に文字があり、かつ、現在位置の文字が 
	 * {@link #getNotContains} と {@link #getCharSet} の返す値と、
	 * 以下のいずれかの条件を満たす場合に一致します。
	 * <ul>
	 *   <li>{@link #getNotContains} が {@code false} を返す場合、
	 *   	現在位置の文字が {@link #getCharSet} の返す {@link HashSet} に
	 *      含まれている。</li>
	 *   <li>{@link #getNotContains} が {@code true} を返す場合、
	 *   	現在位置の文字が {@link #getCharSet} の返す {@link HashSet} に
	 *      含まれていない。</li>
	 * </ul>
	 * 
	 * @param patEnum 正規表現文型の列挙子です。呼び出し後の現在位置は移動しません。
	 * @param strEnum 文字列の列挙子です。一致に成功した場合、呼び出し後の現在位置は
	 * 				次の文字に移動します。
	 * @return 一致に成功した場合は {@code true} を、
	 * 			失敗した場合は {@code false} を返します。
	 */
	@Override
	boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum) {
		if (!strEnum.hasChar()) {
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
		// ^ は排他的論理和で、"false ^ true" あるいは "true ^ false" のとき、
		// 結果は true になります。
		return _notContains ^ _charSet.contains(ch);
	}
	
	/**
	 * {@link CharClassPattern} クラスのオブジェクトの文字列表現を返します。
	 */
	@Override
	public String toString() {
		return String.format(
				"CharClassPattern: _notContains=%s, _charSet=%s",
				Boolean.toString(_notContains), _charSet.toString());
	}
}
