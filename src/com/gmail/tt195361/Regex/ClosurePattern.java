package com.gmail.tt195361.Regex;

import java.util.Stack;

/**
 * 閉包を表わす正規表現文型です。閉包は指定の正規表現文型の 0 回以上の
 * 繰り返しを取り扱います。
 *
 * <h1 id="閉包の一致">閉包の一致</h1>
 * <p>
 * 閉包が一致する条件は、次のように書けます。
 * <ul>
 *   <li>閉包の前にある文型が 0 回以上文字列と一致し、かつ、</li>
 *   <li>閉包の後ろの文型が残りの文字列と一致する。</li>
 * </ul>
 * <p>
 * この条件は、次の手順で調べられます。
 * <ol>
 *   <li>候補作成 -- 閉包の前にある文型が文字列の現在位置から
 *       どこまで一致するか調べ、一致するそれぞれの位置を
 *       閉包との一致の候補にします。</li>
 *   <li>残りの一致調べ -- 文字列のそれぞれの候補位置から、
 *   	 閉包の後ろの文型が一致するか調べます。</li>
 * </ol>
 * <p>
 * たとえば、正規表現 {@code ".*b"} と文字列 {@code "ab"} の一致を
 * 考えます。この正規表現の中の閉包の前の文型は、
 * 任意の 1 文字 {@code "."} です。そのため、閉包は
 * 指定の文字列に対して、{@code ""} (空文字列)、{@code "a"}、
 * {@code "ab"} の 3 通りの一致の候補が考えられます。
 * 閉包は繰り返しが 0 回でもいいので、
 * 空文字列は常に候補になります。これらの候補について、
 * 閉包に続く指定の 1 文字 {@code "b"} と残り文字列の一致は、
 * 次の表のようになります。
 * <div align="center">
 * <table
 * 		border="1" rules="all"
 * 		summary="それぞれの候補位置での一致の結果">
 *   <tr bgcolor="lightgray" align="center">
 *     <th rowspan="2">番号</th>
 *     <th            >閉包 {@code ".*"}</th>
 *     <th colspan="2">指定の 1 文字 {@code "b"}</th>
 *   </tr>
 *   <tr bgcolor="whitesmoke" align="center">
 *     <th>一致の候補</th>
 *     <th>対応する文字</th>
 *     <th>一致</th>
 *   </tr>
 *   <tr align="center">
 *     <td bgcolor="whitesmoke">1</td>
 *     <td>{@code ""}</td>
 *     <td>{@code a}</td>
 *     <td bgcolor="pink">{@code ×}</td>
 *   </tr>
 *   <tr align="center">
 *     <td bgcolor="whitesmoke">2</td>
 *     <td>{@code a}</td>
 *     <td>{@code b}</td>
 *     <td bgcolor="lightblue">{@code 〇}</td>
 *   </tr>
 *   <tr align="center">
 *     <td bgcolor="whitesmoke">3</td>
 *     <td>{@code ab}</td>
 *     <td>{@code ""}</td>
 *     <td bgcolor="pink">{@code ×}</td>
 *   </tr>
 * </table>
 * </div>
 * <p>
 * この結果より、正規表現 {@code ".*b"} は
 * 文字列 {@code "ab"} に対して、
 * 閉包 {@code ".*"} を {@code "a"} に一致させれば、
 * 残りの文型の指定の 1 文字 {@code "b"} が
 * 残りの文字列 {@code "b"} と一致し、
 * 正規表現全体として文字列と一致することがわかります。
 * 
 * <h1 id="複数の閉包">複数の閉包</h1>
 * <p>
 * 一つの正規表現の中に複数の閉包がある場合でも、
 * それぞれの閉包について上記の手順を繰り返せば、
 * 一致を調べられます。
 * たとえば、正規表現 {@code "(.*,.*)"} と文字列
 * {@code "(a,b)"} の場合、次のようになります。
 * <ol>
 *   <li>最初の閉包の一致の候補は、{@code ""} (空文字列)、
 *   	 {@code "a"}、{@code "a,"}、{@code "a,b"}、{@code "a,b)"} の
 *   	 5 つになります。
 *       これらについて、閉包に続く文型 {@code ",.*)"} と
 *       残りの文字列の一致を調べます。</li>
 *   <li>最初の閉包に続く指定の一文字 {@code ","} が一致するのは、
 *       最初の閉包を文字列 {@code "a"} と一致させた場合です。</li>
 *   <li>この場合、2 番目の閉包 {@code ".*"} に対応する文字列は
 *       {@code "b)"} になります。
 *       このときの 2 番目の閉包の一致の候補は、{@code ""} (空文字列)、
 *       {@code "b"}、{@code "b)"} の 3 通りになります。</li>
 *   <li>これらの候補のうち、閉包の後ろの {@code ")"} が一致するのは、
 *       閉包を {@code "b"} に一致させたときです。</li>
 * </ol>
 * <p>
 * この内容を表にまとめると、次のようになります。
 * <div align="center">
 * <table
 * 		border="1" rules="all"
 * 		summary="複数の閉包の一致">
 *   <tr bgcolor="lightgray" align="center">
 *     <th rowspan="2">番号</th>
 *     <th colspan="2">指定の 1 文字 {@code "("}</th>
 *     <th            >閉包 {@code ".*"}</th>
 *     <th colspan="2">指定の 1 文字 {@code ","}</th>
 *     <th            >閉包 {@code ".*"}</th>
 *     <th colspan="2">指定の 1 文字 {@code ")"}</th>
 *   </tr>
 *   <tr bgcolor="whitesmoke" align="center">
 *     <th>対応する文字</th>
 *     <th>一致</th>
 *     <th>一致の候補</th>
 *     <th>対応する文字</th>
 *     <th>一致</th>
 *     <th>一致の候補</th>
 *     <th>対応する文字</th>
 *     <th>一致</th>
 *   </tr>
 *   <tr align="center">
 *     <td bgcolor="whitesmoke">1</td>
 *     <td rowspan="7">{@code (}</td>
 *     <td rowspan="7" bgcolor="lightblue">{@code 〇}</td>
 *     <td>{@code ""}</td>
 *     <td>{@code a}</td>
 *     <td bgcolor="pink">{@code ×}</td>
 *   </tr>
 *   <tr align="center">
 *     <td bgcolor="whitesmoke">2-1</td>
 *     <td rowspan="3">{@code a}</td>
 *     <td rowspan="3">{@code ,}</td>
 *     <td rowspan="3" bgcolor="lightblue">{@code 〇}</td>
 *     <td>{@code ""}</td>
 *     <td>{@code b}</td>
 *     <td bgcolor="pink">{@code ×}</td>
 *   </tr>
 *   <tr align="center">
 *     <td bgcolor="whitesmoke">2-2</td>
 *     <td>{@code b}</td>
 *     <td>{@code )}</td>
 *     <td bgcolor="lightblue">{@code 〇}</td>
 *   </tr>
 *   <tr align="center">
 *     <td bgcolor="whitesmoke">2-3</td>
 *     <td>{@code b)}</td>
 *     <td>{@code ""}</td>
 *     <td bgcolor="pink">{@code ×}</td>
 *   </tr>
 *   <tr align="center">
 *     <td bgcolor="whitesmoke">3</td>
 *     <td>{@code a,}</td>
 *     <td>{@code b}</td>
 *     <td bgcolor="pink">{@code ×}</td>
 *   </tr>
 *   <tr align="center">
 *     <td bgcolor="whitesmoke">4</td>
 *     <td>{@code a,b}</td>
 *     <td>{@code )}</td>
 *     <td bgcolor="pink">{@code ×}</td>
 *   </tr>
 *   <tr align="center">
 *     <td bgcolor="whitesmoke">5</td>
 *     <td>{@code a,b)}</td>
 *     <td>{@code ""}</td>
 *     <td bgcolor="pink">{@code ×}</td>
 *   </tr>
 * </table>
 * </div>
 * 
 * <h1 id="一致の長さ">一致の長さ</h1>
 * <p>
 * 閉包が含まれる正規表現と文字列の一致では、
 * 長さの異なる複数の候補が見つかることがあります。
 * たとえば、正規表現 {@code ".*"} と文字列 {@code "abc"} だと、
 * 一致の候補は {@code ""} (空文字列)、{@code "a"}、{@code "ab"}、
 * {@code "abc"} の 4 つになります。
 * 複数の一致の候補がある場合は、どれを結果と
 * するか決めておかなければなりません。
 * <p>
 * 複数の一致候補のどれを結果とするかは、次の方法が考えられます。
 * <ul>
 *   <li>最短一致 -- 最も短い一致を結果とします。</li>
 *   <li>最長一致 -- 最も長い一致を結果とします。</li>
 * </ul>
 * <p>
 * 正規表現 {@code ".*"} と文字列 {@code "abc"} の場合、
 * 最短一致は繰り返しが 0 回の空文字列になります。
 * また、最長一致は対象となる文字列全体の {@code "abc"} になります。
 * <p>
 * 正規表現 {@code "(.*,.*)"} と文字列 {@code "(a,b)(c,d)"} では、
 * それぞれの一致は以下のようになります。
 * <ul>
 *   <li>最短一致の場合、最初の閉包は {@code "a"} と、
 *       2 番目は {@code "b"} と一致します。
 *       正規表現全体としては、文字列 {@code "(a,b)"} と一致します。</li>
 *   <li>最長一致の場合、最初の閉包は {@code "a,b)(c"} と一致するのが
 *       最も長くなります。この場合、2 番目は {@code "d"} と一致し、
 *       全体としては {@code "(a,b)(c,d)"} と一致します。</li>
 * </ul>
 * <p>
 * このプログラムでは、<a href="RegexParser.html#一致の詳細">一致の詳細</a> で
 * 説明したように、最長一致の結果を返します。
 * 
 * <h1 id="メンバー変数">メンバー変数</h1>
 * <p>
 * {@link ClosurePattern} クラスは、以下のメンバー変数を使います。
 * <div align="center">
 * <table
 * 		border="1" rules="all"
 * 		summary="ClosurePattern クラスのメンバー変数">
 *   <tr bgcolor="lightgray">
 *     <th align="center">名前</th>
 *     <th align="center">型</th>
 *     <th align="center">説明</th>
 *   </tr>
 *   <tr>
 *     <td>{@code _repeatPattern}</td>
 *     <td>{@code final RegexPattern}</td>
 *     <td>繰り返す文型を保持します。
 *     	   この値は、コンストラクタで最初に設定したあとは変更しないので、
 *     	   {@code final} にします。</td>
 *   </tr>
 * </table>
 * </div>
 *
 * <h1 id="閉包の一致の実装">閉包の一致の実装</h1>
 * <p>
 * 閉包の一致を調べる {@link #oneMatch(PatternEnumerator, StringEnumerator)} メソッドは、
 * <a href="#閉包の一致">閉包の一致</a> で説明した手順を実装する
 * 以下のメソッドを順に呼び出します。
 * <ul>
 *   <li>候補作成 -- {@code makeStrEnumCandidateStates()}</li>
 *   <li>残りの一致調べ -- {@code matchTheRest()}</li>
 * </ul>
 * <p>
 * プログラムは、次のようになります。
 * <pre>
        &#64;Override
        boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum) {
            // 可能な繰り返し回数の候補を作成し、それぞれの候補について、
            // 残りが一致するかどうか調べます。
            Stack<EnumeratorState> strEnumCandidateStateStack =
                    makeStrEnumCandidateStates(patEnum, strEnum);
            return matchTheRest(patEnum, strEnum, strEnumCandidateStateStack);
        }
 * </pre>
 *
 * <h1 id="候補作成">候補作成</h1>
 * <p>
 * 候補作成では、閉包の前にある文型が文字列の現在位置から
 * どこまで一致するか調べ、一致するそれぞれの位置を
 * 一致の候補として返します。
 * <p>
 * 一致候補の値は {@link EnumeratorState} クラスのオブジェクト
 * が格納します。これについては、
 * <a href="EnumeratorState.html#列挙子の状態の保存と復元">
 * 列挙子の状態の保存と復元</a> で説明します。
 * <p>
 * 一致の候補は、次のように短いものから順に作成します。
 * <ol>
 *   <li>閉包は繰り返しが 0 回でもいいので、
 *       現在位置を候補に加えます。</li>
 *   <li>繰り返す文型 {@code _repeatPattern} が文字列と一致すれば、
 *       その位置を候補に加えます。</li>
 * </ol>
 * <p>
 * このプログラムは最長一致の結果を返すので、
 * 残りの一致調べは、最も長い候補から順に
 * <p>
 * 異なる一致の候補
 * <p>
 * TODO: 残りの説明を書く
 */
class ClosurePattern extends RegexPattern {
	
	// メンバー変数: 繰り返す正規表現文型を保持します。
	private final RegexPattern _repeatPattern;
	
	/**
	 * 指定の繰り返す文型を持つ {@link ClosurePattern} クラスのオブジェクトを作成します。
	 * 
	 * @param repeatPattern 繰り返す正規表現文型を指定します。
	 */
	ClosurePattern(RegexPattern repeatPattern) {
		_repeatPattern = repeatPattern;
	}
	
	/**
	 * 繰り返す正規表現文型を取得します。
	 * 
	 * @return 繰り返す正規表現文型を返します。
	 */
	RegexPattern getRepeatPattern() {
		return _repeatPattern;
	}

	/**
	 * この正規表現文型が文字列の現在位置から一致するかどうかを調べます。
	 * 閉包は、指定の文型が 0 回以上なるべく多く繰り返し一致し、
	 * それに続く正規表現文型が残りの文字列に一致することを調べます。
	 * 
	 * @param patEnum 正規表現文型の列挙子です。呼び出し後の現在位置は
	 * 				最後に一致を調べた正規表現文型に移動します。
	 * @param strEnum 文字列の列挙子です。一致に成功した場合、呼び出し後の現在位置は
	 * 				正規表現文型に一致した部分の次の文字に移動します。
	 * @return 一致に成功した場合は {@code true} を、
	 * 			失敗した場合は {@code false} を返します。
	 */
	@Override
	boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum) {
		// 可能な繰り返し回数の候補を作成し、それぞれの候補について、
		// 残りが一致するかどうか調べます。
		Stack<EnumeratorState> strEnumCandidateStateStack =
				makeStrEnumCandidateStates(patEnum, strEnum);
		return matchTheRest(patEnum, strEnum, strEnumCandidateStateStack);
	}
	
	// 可能な繰り返し回数の候補を作成します。
	private Stack<EnumeratorState> makeStrEnumCandidateStates(
			PatternEnumerator patEnum, StringEnumerator strEnum) {
		// なるべく長い繰り返し回数と一致させるため、候補はスタックに格納し、
		// 後に入れたより長い繰り返しを先に調べるようにします。
		Stack<EnumeratorState> candidateStateStack = new Stack<EnumeratorState>();
		
		// 繰り返し回数は 0 回でもいいので、現在の状態をそのまま候補にします。
		candidateStateStack.push(strEnum.saveState());

		// 繰り返す文型が文字列とどこまで一致するか調べ、文字列の位置が
		// 異なるならば候補として追加します。
		while (strEnum.hasCurrentOrEnd()) {
			if (!_repeatPattern.oneMatch(patEnum, strEnum)) {
				break;
			}
			
			// 文字列の状態が変わらないのは空文字列と一致する場合で、
			// '^*', '$*', '.**' などです。
			EnumeratorState savedState = strEnum.saveState();
			if (savedState.equals(candidateStateStack.peek())) {
				break;
			}
			
			candidateStateStack.push(savedState);
		}
		
		return candidateStateStack;
	}
			
	// それぞれの文字列列挙子の候補の位置に対して、残りの正規表現文型が
	// 一致するかどうか調べます。
	private boolean matchTheRest(
			PatternEnumerator patEnum, StringEnumerator strEnum,
			Stack<EnumeratorState> strEnumCandidateStateStack) {
		// 文型をこの閉包の次に移動し、その位置を保存します。それぞれの候補の
		// 探索をこの位置から始められるようにします。
		patEnum.moveNext();
		EnumeratorState savedPatEnumState = patEnum.saveState();
		
		while (!strEnumCandidateStateStack.empty()) {
			// 正規表現文型の列挙子の現在位置をこの閉包の次に、
			// 文字列の列挙子の現在位置を次の候補に設定し、一致するかどうかを調べます。
			patEnum.restoreState(savedPatEnumState);
			strEnum.restoreState(strEnumCandidateStateStack.pop());
			if (RegexMatcher.matchFromCurrent(patEnum, strEnum)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * {@link ClosurePattern} クラスのオブジェクトの文字列表現を返します。
	 */
	@Override
	public String toString() {
		return "Closure: " + _repeatPattern.toString();
	}
}
