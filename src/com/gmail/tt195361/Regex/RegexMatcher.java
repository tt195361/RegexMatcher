package com.gmail.tt195361.Regex;

/**
 * 指定の正規表現が指定の文字列と一致するかどうかを調べます。
 * 
 * <h1 id="一致を調べる手順">一致を調べる手順</h1>
 * <p>
 * ある正規表現が文字列と一致するかどうか調べる手順について考えます。
 * 正規表現が文字列に一致するとは、文字列のある位置から、
 * 正規表現を構成する文型が順にすべて一致することです。
 * これを調べるには、文字列の最初の文字から、
 * 2 番目の文字から、3 番目の文字から、...、と
 * 開始位置を順にずらして、文型の一致を検査します。
 * 文字列のどこかの位置で文型がすべて一致すれば、
 * 正規表現はその文字列に一致することがわかります。
 * 文字列のすべての位置で文型が一致しなければ、
 * その正規表現は文字列に一致しません。
 * <p>
 * 例として、正規表現 {@code "abc"} と
 * 文字列 {@code "aabcd"} で一致を調べてみます。
 * はじめに、文字列の先頭の位置 0 から正規表現が一致するか調べます。
 * この場合、文字列の位置 0 の文字 {@code a} と正規表現の位置 0 の
 * 文型 {@code OneChar: 'a'} は、一致します。
 * しかし、その次の文字 {@code a} と文型 {@code OneChar: 'b'} は、
 * 一致しません。
 * したがって、位置 0 からは一致しませんでした。
 * この状況を次の図に示します。
 * <br>
 * <div align="center">
 *   <img
 *   	src="{@docRoot}/figures/matching-procedure-1.png"
 *   	alt="一致を調べる手順-1">
 * </div>
 * <p>
 * 次に、文字列の開始位置を 1 つずらして、位置 1 から調べてみます。
 * このときは、文字 {@code a}, {@code b}, {@code c} が、
 * 文型 {@code OneChar: 'a'}, {@code OneChar: 'b'}, {@code OneChar: 'c'} に、
 * 順に一致します。したがって、この文型は文字列の位置 1 から
 * 一致することがわかります。
 * <br>
 * <div align="center">
 *   <img
 *   	src="{@docRoot}/figures/matching-procedure-2.png"
 *   	alt="一致を調べる手順-2">
 * </div>
 * <p>
 * 文字列の開始位置を最初から順にずらして調べると、
 * <a href="RegexParser.html#最左一致">最左一致</a> で説明した、
 * 文字列の中の最も左から始まる一致が見つかります。
 * たとえば、正規表現 "aa" は、文字列 "aabaac" と、
 * 開始位置 0 と 3 の 2 個所で一致します。
 * この方法では、より左の開始位置 0 からの一致が
 * 答えになります。
 * 
 * <h1 id="一致を調べるプログラム">一致を調べるプログラム</h1>
 * <p>
 * 上記の手順を、以下の 2 つに分けてより単純にします。
 * <ol>
 *   <li>文字列の開始位置を順に移動する。</li>
 *   <li>現在の開始位置から文型と文字列の一致を調べる。</li>
 * </ol>
 * <p>
 * 
 * <h1 id="文字列の開始位置の移動">文字列の開始位置の移動</h1>
 * <p>
 * 文字列の列挙子 {@link StringEnumerator} クラスの
 * <a href="StringEnumerator.html#開始位置の移動">開始位置の移動</a> で
 * 説明するメソッドを使うと、文字列の開始位置を移動する手順を実装する
 * {@link #match(String)} メソッドは、以下のようになります。
 * <pre>
        PatternEnumerator _patEnum;                         // 文型の列挙子 _patEnum はメンバー変数

        public MatchResult match(String str) {              // 引数は対象の文字列、戻り値は一致の結果
            StringEnumerator strEnum = new StringEnumerator(str);
            while (strEnum.hasValidStart()) {               // 有効な開始位置が存在する間繰り返す
                _patEnum の現在位置を最初に戻す;            // (1)
                if (matchFromCurrent(_patEnum, strEnum)) {  // 現在位置から文型と文字列が一致するか？
                    return 一致に成功;                      // (2)
                }
                strEnum.moveNextStart();                    // 一致しなければ、次の開始位置に移動
            }

            // すべての開始位置で一致しなかった。
            return 一致に失敗;                              // (2)
        }
 * </pre>
 * <ol>
 *   <li>_patEnum の現在位置を最初に戻すには、
 *       列挙子の状態の保存と復元を使います。</li>
 *   <li>一致を調べた結果は {@link MatchResult} クラスで
 *       表わします。</li>
 * </ol>
 * 
 * <h1 id="現在位置から一致を調べる">現在位置から一致を調べる</h1>
 * <p>
 * 正規表現文型と文字列のそれぞれの現在位置から、正規表現文型が
 * 文字列に一致するかどうかを調べるメソッドは
 * {@link #matchFromCurrent(PatternEnumerator, StringEnumerator)} です。
 * このメソッドは、正規表現文型の列挙子 {@link PatternEnumerator} を
 * 使って、正規表現文型 {@link RegexPattern} を 1 つずつ順に列挙し、
 * それらが文字列と一致するかを調べます。
 * 文型と文字列の一致を調べるには、
 * {@link RegexPattern#oneMatch(PatternEnumerator, StringEnumerator)} 
 * メソッドを使います。プログラムは次のようになります。
 * <pre>
    static boolean matchFromCurrent(PatternEnumerator patEnum, StringEnumerator strEnum) {
        // 正規表現文型が存在する間、繰り返します。
        while (patEnum.hasCurrent()) {

            // 正規表現文型がまだ存在するにもかかわらず、文字列の終わりを越えていれば、
            // 一致は失敗です。
            if (!strEnum.hasCurrentOrEnd()) {
                return false;
            }
            
            // 現在の正規表現文型が文字列に一致するかどうか調べます。一致しなければ失敗です。
            RegexPattern currentPat = patEnum.getCurrent();
            if (!currentPat.oneMatch(patEnum, strEnum)) {
                return false;
            }
            
            // 現在の正規表現文型の一致に成功しました。oneMatch() を呼ぶと、
            // patEnum は最後に一致した文型に移動するので、次の正規表現文型に
            // 移動します。strEnum は次に一致を調べる位置に移動しています。
            patEnum.moveNext();
        }

        // すべての正規表現文型が文字列の現在位置から一致したので、成功です。
        return true;
    }
 * </pre>
 */
public class RegexMatcher {
	
	// メンバ－変数: 正規表現を表わす文字列から作成した正規表現文型の列挙子と、その初期状態です。
	private final PatternEnumerator _patEnum;
	private final EnumeratorState _initialPatEnumState;

	/**
	 * 指定の文型を持つ正規表現を表わす {@link RegexMatcher} クラスのオブジェクトを生成します。
	 * 
	 * @param pattern 正規表現の文型を指定する文字列です。
	 */
	public RegexMatcher(String pattern) {
		// 正規表現の文型を表わす文字列を解釈し、一つ一つの文型に分解し、
		// その文型を順に列挙する列挙子を作成します。
		_patEnum = RegexParser.parse(pattern);
		// 正規表現文型の列挙子を初期状態に戻すため、その状態を保存します。
		_initialPatEnumState = _patEnum.saveState();
	}
	
	/**
	 * この正規表現が指定の文字列と一致するかどうかを調べます。
	 * 
	 * @param str 正規表現と一致するかどうかを調べる文字列です。
	 * @return 一致を調べた結果を格納する {@link MatchResult} クラスのオブジェクトを返します。
	 */
	public MatchResult match(String str) {
		StringEnumerator strEnum = new StringEnumerator(str);

		// 指定の文字列に有効な開始位置が存在する間、一致を調べます。
		while (strEnum.hasValidStart()) {

			// 文字列は現在の開始位置から、文型は最初から、順に一致を調べます。
			_patEnum.restoreState(_initialPatEnumState);
			if (matchFromCurrent(_patEnum, strEnum)) {
				// 一致に成功すれば、その結果を返します。
				return MatchResult.makeSuccessResult(strEnum);
			}

			// 一致しなかった場合は、文字列の開始位置を次へ移動します。
			strEnum.moveNextStart();
		}
		
		// 文字列のすべての開始位置で正規表現が一致しなかったので、失敗の結果を返します。
		return MatchResult.makeFailResult();
	}
	
	/**
	 * 正規表現文型と文字列のそれぞれの現在位置から、正規表現文型が文字列に一致するかどうかを調べます。
	 * 
	 * @param patEnum 正規表現文型の列挙子です。呼び出し後の現在位置は
	 * 				最後に一致に成功した文型の次に移動します。
	 * @param strEnum 文字列の列挙子です。呼び出し後の現在位置は
	 * 				正規表現文型と一致した部分の次の文字に移動します。
	 * @return　一致に成功した場合は {@code true} を、失敗した場合は {@code false} を返します。
	 */
	static boolean matchFromCurrent(PatternEnumerator patEnum, StringEnumerator strEnum) {
		// 正規表現文型が存在する間、繰り返します。
		while (patEnum.hasCurrent()) {

			// 正規表現文型がまだ存在するにもかかわらず、文字列の終わりを越えていれば、一致は失敗です。
			if (!strEnum.hasCurrentOrEnd()) {
				return false;
			}
			
			// 現在の正規表現文型が文字列に一致するかどうか調べます。
			// 一致しなければ失敗です。
			RegexPattern currentPat = patEnum.getCurrent();
			if (!currentPat.oneMatch(patEnum, strEnum)) {
				return false;
			}
			
			// 現在の正規表現文型の一致に成功しました。oneMatch() を呼ぶと、
			// patEnum は最後に一致した文型に移動するので、次の正規表現文型に
			// 移動します。strEnum は次に一致を調べる位置に移動しています。
			patEnum.moveNext();
		}

		// すべての正規表現文型が文字列の現在位置から一致したので、成功です。
		return true;
	}
}
