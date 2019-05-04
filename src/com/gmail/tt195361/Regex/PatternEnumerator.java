package com.gmail.tt195361.Regex;

/**
 * 正規表現の文型の並びを管理し順に列挙する列挙子です。
 * 
 * <h1 id="正規表現の表わし方">正規表現の表わし方</h1>
 * <p>
 * 正規表現をプログラムでどのように表現するか考えます。
 * ある正規表現は、それを構成する文型を順に並べたものです。
 * たとえば、正規表現 {@code "^[a-z]$"} は、
 * 文字列の最初、文字クラス: {@code a, b, ..., z}、文字列の最後、
 * の 3 つの文型を順に並べたものです。
 * <p>
 * このプログラムでは、正規表現を構成する文型の並びを管理するために
 * {@link PatternEnumerator} クラスを使います。
 * このクラスのオブジェクトは、正規表現の文型を表わす
 * {@link RegexPattern} オブジェクトの並びを保持し、
 * それらを順に列挙する役割を担います。
 * <p>
 * 次の図は、このプログラムで使用する正規表現を表わすクラスの構成を示します。
 * {@link PatternEnumerator} のオブジェクトは、
 * 0 個以上の {@link RegexPattern} のオブジェクトを保持します。
 * {@link RegexPattern} クラスを継承する {@link AnyCharPattern} や
 * {@link StartOfStringPattern} などの
 * クラスが具体的な文型を表わします。
 * <br>
 * <div align="center">
 *   <img
 *   	src="{@docRoot}/figures/regex-representation-classes.png"
 *   	alt="正規表現を表わすクラス">
 * </div>
 * <p>
 * 正規表現 {@code "^[a-z]$"} は、{@link PatternEnumerator} のオブジェクトが、
 * {@link StartOfStringPattern},
 * {@link CharClassPattern}: {@code (_charSet=a, b, ..., z)},
 * {@link EndOfStringPattern}　の 3 つのオブジェクトを
 * 保持するものとして表わせます。このオブジェクト図は、次のようになります。
 * <br>
 * <div align="center">
 *   <img
 *   	src="{@docRoot}/figures/example-regex-representation-instances.png"
 *   	alt="正規表現 &quot;^[a-z]$&quot; のインスタンス図">
 * </div>
 * 
 * <h1 id="列挙子">列挙子</h1>
 * <p>
 * <a href="RegexMatcher.html#一致を調べる手順">一致を調べる手順</a>
 * で説明したように、正規表現と文字列の一致を調べるには、
 * 正規表現を構成する文型と文字列中の文字を順に一つ一つ見てゆきます。
 * このためには、以下の変数が必要になります。
 * <div align="center">
 * <table
 *     border="1" rules="all"
 *     summary="文型と文字の列挙に必要な変数">
 *   <tr bgcolor="lightgray">
 *     <th align="center">役割</th>
 * 	   <th align="center">正規表現の文型</th>
 * 	   <th align="center">文字列</th>
 *   </tr>
 *   <tr>
 *     <td align="center" bgcolor="lightgray">列挙する項目の並びを格納する</td>
 *     <td align="center">文型を格納する配列</td>
 *     <td align="center">文字列を格納する変数</td>
 *   </tr>
 *   <tr>
 *     <td align="center" bgcolor="lightgray">項目の位置を示す</td>
 *     <td align="center">現在位置を示す変数</td>
 *     <td align="center">開始位置と現在位置を示す変数</td>
 *   </tr>
 * </table>
 * </div>
 * <p>
 * これらの変数は、プログラムの中でまとまって使われることになります。
 * 文型を一つずつ処理する場合は、以下のようになるでしょう。
 * <pre>
        RegexPattern[] patterns;        // 文型を格納する配列
        int patternCurrentIndex = 0;    // 文型の現在位置
        while (patternCurrentIndex < patterns.length) {            // 文型がある間、繰り返す。
            RegexPattern pattern = patterns[patternCurrentIndex];  // 現在位置の文型を取得する。
            // この文型が文字列と一致するか調べる、、、
            ++patternCurrentIndex;                                 // 次の文型に移動する。
        }
 * </pre>
 * <p>
 * これらの変数を別々にしておくのではなく、
 * クラスを作成しそのメンバーにします。
 * こうすると、関連する複数のものがまとめて扱えるので、
 * より簡単になります。
 * <p>
 * このプログラムでは、次のクラスを作成します。
 * これらのクラスを列挙子と呼ぶことにします。
 * <ul>
 *   <li>{@link PatternEnumerator} --
 *   	正規表現を構成する文型を列挙します。</li>
 *   <li>{@link StringEnumerator} --
 *      文字列中の文字を列挙します。</li>
 * </ul>
 * <p>
 * 列挙子は、列挙する項目の並びと、その中の一つを指し示す現在位置を保持します。
 * 以下のメソッドを用いて、現在位置を順に進め、項目の並びを列挙します。
 * <div align="center">
 * <table
 *     border="1" rules="all"
 *     summary="項目の並びを列挙するメソッド">
 *   <tr bgcolor="lightgray">
 * 	   <th align="center">説明</th>
 *     <th align="center">{@link PatternEnumerator}</th>
 * 	   <th align="center">{@link StringEnumerator}</th>
 *   </tr>
 *   <tr>
 *     <td align="center" bgcolor="lightgray">現在位置に項目があるかどうかを調べます。</td>
 *     <td align="center">{@link #hasCurrent() PatternEnumerator.hasCurrent()}</td>
 *     <td align="center">{@link StringEnumerator#hasCurrent()}</td>
 *   </tr>
 *   <tr>
 *     <td align="center" bgcolor="lightgray">現在位置の項目を取得します。</td>
 *     <td align="center">{@link #getCurrent() PatternEnumerator.getCurrent()}</td>
 *     <td align="center">{@link StringEnumerator#getCurrent()}</td>
 *   </tr>
 *   <tr>
 *     <td align="center" bgcolor="lightgray">現在位置を次の項目に移動します。</td>
 *     <td align="center">{@link #moveNext() PatternEnumerator.moveNext()}</td>
 *     <td align="center">{@link StringEnumerator#moveNext()}</td>
 *   </tr>
 * </table>
 * </div>
 * <p>
 * {@link PatternEnumerator} を使って、文型を一つずつ取り扱う
 * プログラムは、以下のような形になります。
 * <pre>
        PatternEnumerator patEnum;
        while (patEnum.hasCurrent()) {                    // 文型がある間、繰り返す。
            RegexPattern pattern = patEnum.getCurrent();  // 現在位置の文型を取得する。
            // この文型が文字列と一致するか調べる、、、
            patEnum.moveNext();                           // 次の文型に移動する。 
        }
 * </pre>
 */
class PatternEnumerator extends Enumerator<RegexPattern> {

	// メンバー変数: 列挙する正規表現文型の配列です。
	private final RegexPattern[] _patterns;
	
	/**
	 * 指定の正規表現文型を列挙する列挙子を作成します。
	 * 
	 * @param patterns 列挙する正規表現文型を格納する配列です。
	 */
	PatternEnumerator(RegexPattern[] patterns) {
		super(patterns.length);
		_patterns = patterns;
	}
	
	@Override
	protected RegexPattern getElementAt(int index) {
		return _patterns[index];
	}
	
	@Override
	protected RegexPattern getNullElement() {
		return null;
	}
	
	/**
	 * {@link PatternEnumerator} クラスのオブジェクトの文字列表現を返します。
	 */
	@Override
	public String toString() {
		RegexPattern current = getCurrent();
		int currentIndex = getCurrentIndex();
		String currentStr = (current == null) ? "NULL" : current.toString();
		return String.format("_index=%d, current=%s", currentIndex, currentStr);
	}
}
