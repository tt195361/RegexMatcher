/**
 * 正規表現が文字列と一致するかどうかを調べるプログラム {@link com.gmail.tt195361.Regex.RegexMatcher} について説明します。
 * {@link com.gmail.tt195361.Regex.RegexMatcher} は、実際に動作するある程度の大きさのプログラムで、それをどのように考えて作成したかを説明します。
 * <p>
 * このプログラムと説明を作成するにあたり、
 * <a
 *   href="https://www.amazon.co.jp/%E3%82%BD%E3%83%95%E3%83%88%E3%82%A6%E3%82%A7%E3%82%A2%E4%BD%9C%E6%B3%95-Brian-W-Kernighan/dp/4320021428">
 *   "ソフトウェア作法, Brian W.Kernighan, P.J.Plauger 著、木村 泉 訳"
 * </a>
 * を参考にさせていただきました。ここに記して、感謝いたします。
 * <p>
 * {@link com.gmail.tt195361.Regex.RegexMatcher} について、以下の内容を順に説明します。
 * <ul>
 *   <li>取り扱う正規表現 -- このプログラムが取り扱う正規表現について説明します。</li>
 *   <li>使い方 -- このプログラムをどのように使うか説明します。</li>
 *   <li>動作の流れ -- プログラム全体の動作を説明します。</li>
 *   <li>クラスの構成 -- プログラム中のクラスの構成と責務の分担について説明します。</li>
 *   <li>一致の調べ方 -- 正規表現と文字列の一致を調べる手順を説明します。</li>
 * </ul>
 * <h2>取り扱う正規表現</h2>
 * <p>
 * このプログラムでは、以下の正規表現を取り扱います。
 * <table
 * 		border="1" rules="all"
 * 		summary="このプログラムで取り扱う正規表現">
 *   <tr align="center">
 *     <th>正規表現</th>
 *     <th>一致する対象</th>
 *   </tr>
 *   <tr>
 *     <td align="center">{@code .}</td>
 *     <td>任意の 1 文字と一致します。</td>
 *   </tr>
 *   <tr>
 *     <td align="center">{@code ^}</td>
 *     <td>正規表現の文型の最初にある場合は、文字列の最初と一致します。
 *   		それ以外の場合、その文字自身と一致します。</td>
 *   </tr>
 *   <tr>
 *     <td align="center">{@code $}</td>
 *     <td>正規表現の文型の最後にある場合は、文字列の最後と一致します。
 *   		それ以外の場合は、その文字自身と一致します。</td>
 *   </tr>
 *   <tr>
 *     <td align="center">{@code [ ]}</td>
 *     <td>{@code [ ]} 内で指定の文字の中の 1 文字と一致します。文字は以下のように指定します。
 *       <ul>
 *         <li>文字 -- 指定の文字を含めます。たとえば、 {@code [abc]} は、
 *       		{@code a}, {@code b}, {@code c} のそれぞれの文字を指定します。</li>
 *         <li>開始文字{@code -}終了文字 -- 開始文字と終了文字を {@code -} で区切ると、
 *       		その範囲の文字を指定します。たとえば、{@code [A-Z]} は英大文字
 *       		({@code A}, {@code B}, ..., {@code Z}) を、{@code [0-9]} は数字
 *       		({@code 0}, {@code 1}, ..., {@code 9}) を、
 *       		{@code [A-Z0-9]} は、英大文字と数字を表わします。</li>
 *       </ul>
 *     </td>
 *   </tr>
 *   <tr>
 *     <td align="center">{@code [^ ]}</td>
 *     <td>{@code [ ]} 内で指定の文字の中の 1 文字以外と一致します。</td>
 *   </tr>
 *   <tr>
 *     <td align="center">{@code *}</td>
 *     <td>この前の文型の 0 回以上の繰り返しと一致します。たとえば、{@code a*} は
 *   		{@code a} の 0 回以上の繰り返し、{@code .*} は任意の文字の 0 回以上の
 *   		繰返し、{@code [0-9]*} は数字の 0 回以上の繰り返しと一致します。</td>
 *   </tr>
 *   <tr>
 *     <td align="center">{@code \}</td>
 *     <td>この後の文字をその文字自身として取り扱います。{@code [ ]} 内でも使えます。
 *   		たとえば {@code \.} は、{@code .} の特別な意味を打ち消し、{@code .} 自身
 *   		として取り扱います。また、{@code \\} は {@code \} を意味します。</td>
 *   </tr>
 *   <tr>
 *     <td align="center">上記以外の文字</td>
 *     <td>その文字自身と一致します。</td>
 *   </tr>
 * </table>
 * <p>
 * 正規表現と文字列の一致は、以下のように取り扱います。
 * <ul>
 *   <li>文字列のなるべく始めから一致させます。たとえば、正規表現 "aa" は、文字列
 *   	"aabaac" と、開始位置 0 と 3 の 2 個所で一致しますが、より始めの開始位置 0 から
 *   	一致させます。</li>
 *   <li>文字列のなるべく長い部分と一致させます。たとえば、正規表現 "a*" は、
 *   	文字列 "aaa" の一致では、 "", "a", "aa", "aaa" の 4 通りが考えられますが、
 *   	一番長い "aaa" を結果とします。</li>
 * </ul>
 * <h2>このプログラムの使い方</h2>
 * <p>
 * このプログラムを使って、指定の正規表現の文型が文字列を一致するかどうかを調べる手順は、
 * 以下のようになります。
 * <ol>
 *   <li>{@link com.gmail.tt195361.Regex.RegexMatcher} クラスのコンストラクタ
 *   	{@link com.gmail.tt195361.Regex.RegexMatcher#RegexMatcher(String)} を、正規表現の文型を表わす文字列を
 *   	引数として呼び出し、{@link com.gmail.tt195361.Regex.RegexMatcher} クラスのオブジェクトを作成します。</li>
 *   <li>作成した {@link com.gmail.tt195361.Regex.RegexMatcher} クラスのオブジェクトに対して、一致するかどうかを調べる
 *   	文字列を引数として　{@link RegexMatcher#match} メソッドを呼び出します。</li>
 *   <li>{@link RegexMatcher#match} メソッドは、{@link com.gmail.tt195361.Regex.MatchResult} クラスのオブジェクトを
 *   	返します。このオブジェクトは一致に成功したかどうかを返すなどのメソッドがあり、一致を調べた結果を
 *   	提供します。</li>
 * </ol>
 * <p>
 * たとえば、以下のプログラムは、正規表現の文型 {@code pattern} が文字列 {@code str} と
 * 一致するかどうかを調べます。
 * <pre>
 * <code>
 *		RegexMatcher matcher = new RegexMatcher(pattern);
 *		MatchResult result = matcher.match(str);
 *		if (result.isSuccess()) {
 *			// 正規表現が、文字列に一致した。
 *			. . . . .
 *		}
 * </code>
 * </pre>
 * <h2>プログラムの構成</h2>
 * <p>
 * {@link com.gmail.tt195361.Regex.RegexMatcher} プログラムは、以下の流れで動作します。
 * <ol>
 *   <li>{@link com.gmail.tt195361.Regex.RegexMatcher} クラスのコンストラクタ
 *   	{@link com.gmail.tt195361.Regex.RegexMatcher#RegexMatcher(String)} で受け取った正規表現の
 *   	文型を表わす文字列を解釈し、プログラム内で使用する表現を作成します。</li>
 *   <li>{@link com.gmail.tt195361.Regex.RegexMatcher#match} メソッドで指定された文字列に対して、
 *   	作成した内部表現が一致するかどうかを調べます。</li>
 * </ol>
 * <h2>正規表現の内部表現</h2>
 * <p>
 * 正規表現の文字列から作成するプログラムの内部表現は、以下の通りです。
 * <ul>
 *   <li>取り扱うそれぞれの正規表現の文型について、その文型を表わすクラスを作成します。</li>
 *   <li>それぞれの正規表現の文型を表わすクラスは、抽象クラス {@link com.gmail.tt195361.Regex.RegexPattern} から派生させます。</li>
 *   <li>. . . . .</li>
 * </ul>
 * <table
 * 		border="1" rules="all"
 * 		summary="クラスの分類">
 *   <tr>
 *     <th>役割</th>
 *     <th>クラス</th>
 *   </tr>
 *   <tr>
 *     <td rowspan="2" valign="top">全体の制御</td>
 *     <td>RegexMatcher</td>
 *   </tr>
 *   <tr>
 *     <td>MatchResult</td>
 *   </tr>
 *   <tr>
 *     <td rowspan="3" valign="top">正規表現の文型の解釈</td>
 *     <td>RegexParser</td>
 *   </tr>
 *   <tr>
 *     <td>CharClassParser</td>
 *   </tr>
 *   <tr>
 *     <td>PatternParseResult</td>
 *   </tr>
 *   <tr>
 *     <td rowspan="7" valign="top">正規表現の一つの文型</td>
 *     <td>RegexPattern</td>
 *   </tr>
 *   <tr>
 *     <td>AnyCharPattern</td>
 *   </tr>
 *   <tr>
 *     <td>CharClassPattern</td>
 *   </tr>
 *   <tr>
 *     <td>ClosurePattern</td>
 *   </tr>
 *   <tr>
 *     <td>EndOfStringPattern</td>
 *   </tr>
 *   <tr>
 *     <td>OneCharPattern</td>
 *   </tr>
 *   <tr>
 *     <td>StartOfStringPattern</td>
 *   </tr>
 *   <tr>
 *     <td rowspan="3" valign="top">コレクションの項目の列挙</td>
 *     <td>PatternEnumerator</td>
 *   </tr>
 *   <tr>
 *     <td>StringEnumerator</td>
 *   </tr>
 *   <tr>
 *     <td>EnumeratorState</td>
 *   </tr>
 *   <tr>
 *     <td rowspan="1" valign="top">ユーティリティ</td>
 *     <td>MathUtils</td>
 *   </tr>
 * </table>
 */
package com.gmail.tt195361.Regex;