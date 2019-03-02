package com.gmail.tt195361.Regex;

/**
 * 正規表現を構成する一つの文型を表わす抽象クラスです。
 * 
 * <h1 id="文型の表わし方">文型の表わし方</h1>
 * <p>
 * 正規表現の文型をプログラムで表現するため、その共通点と相違点を考えます。
 * すべての文型に共通するのは、どの文型も文字列に一致する対象があることです。
 * しかし、文字列のどの部分と一致するかは、それぞれの文型によって異なります。
 * <p>
 * これらの点を表わすために、抽象的な文型を意味する基底クラスと、
 * それを継承し具体的な文型を表わす派生クラスを使ってみます。
 * 基底クラスはすべての派生クラスに共通する性質を、
 * 派生クラスはそれぞれのクラスごとに異なる性質を記述します。
 * <p>
 * すべての文型は文字列に一致する対象があり、
 * それを調べられなければなりません。
 * これを行うために、基底クラス {@link RegexPattern} に、
 * メソッド {@link #oneMatch(PatternEnumerator, StringEnumerator)} を定義します。
 * このメソッドの中身は文型によって異なるので、この段階では実装できません。
 * そのため、このメソッドは中身のない抽象メソッドにします。
 * 引数で渡される {@link PatternEnumerator} と {@link StringEnumerator} は、
 * 正規表現を構成する文型や、文字列中の文字を順に列挙する
 * <a href="PatternEnumerator.html#列挙子">列挙子</a> です。
 * <pre>
        abstract boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum);
 * </pre>
 * <p>
 * 取り扱うそれぞれの文型について、このクラスを継承した、
 * 以下の派生クラスを作成します。
 * <div align="center">
 * <table
 *     border="1" rules="all"
 *     summary="それぞれの文型を表わす派生クラス">
 *   <tr bgcolor="lightgray">
 *     <th align="center">文型</th>
 * 	   <th align="center">クラス</th>
 *   </tr>
 *   <tr>
 *     <td align="center">任意の 1 文字</td>
 *     <td align="center">{@link AnyCharPattern}</td>
 *   </tr>
 *   <tr>
 *     <td align="center">文字列の最初</td>
 *     <td align="center">{@link StartOfStringPattern}</td>
 *   </tr>
 *   <tr>
 *     <td align="center">文字列の最後</td>
 *     <td align="center">{@link EndOfStringPattern}</td>
 *   </tr>
 *   <tr>
 *     <td align="center">文字クラス</td>
 *     <td align="center">{@link CharClassPattern}</td>
 *   </tr>
 *   <tr>
 *     <td align="center">閉包</td>
 *     <td align="center">{@link ClosurePattern}</td>
 *   </tr>
 *   <tr>
 *     <td align="center">指定の 1 文字</td>
 *     <td align="center">{@link OneCharPattern}</td>
 *   </tr>
 * </table>
 * </div>
 * <p>
 * 上記の派生クラスは、基底クラスで定義された抽象メソッド 
 * {@link #oneMatch(PatternEnumerator, StringEnumerator)} を、
 * それぞれの文型に応じて実装します。
 * この内容がそれぞれの文型で異なるところである、
 * その文型が文字列のどの部分と一致するか、の記述になります。
 * <p>
 * ここで説明したクラスの関係を、次の図に示します。
 * <div align="center">
 *   <img
 *   	src="{@docRoot}/figures/regex-pattern-classes.png"
 *   	alt="正規表現の文型を表わすクラス">
 * </div>
 * <p>
 * 基底クラスと派生クラスを使う利点は、次のようになります。
 * <ul>
 *   <li>オブジェクトのクラスを区別することなく、
 *   	まとめて取り扱えるようになります。</li>
 *   <li>それと同時に、クラスを区別することなく、
 *   	オブジェクトの振る舞いを変えられます。</li>
 * </ul>
 * <p>
 * たとえば、正規表現を構成する文型のオブジェクトを
 * 配列に格納してみます。
 * この配列の型は、基底クラスの {@link RegexPattern} にします。
 * そうすると、この配列には派生クラスのオブジェクトならば、
 * どのクラスか区別することなく、基底クラスのオブジェクト
 * として格納できます。
 * <pre>
        RegexPattern[] patterns = new RegexPattern[3];
        patterns[0] = new StartOfStringPattern();
        patterns[1] = new OneCharPattern('a');
        patterns[2] = new EndOfStringPattern();
 * </pre>
 * <p>
 * この配列に格納された文型のオブジェクトが文字列と一致するか
 * どうかを調べるために、{@code oneMatch} メソッドを呼んでみます。
 * このとき実際に呼び出されるメソッドは、それぞれのオブジェクトが
 * 属するクラスで定義されたものになります。このしくみを使うと、
 * どのクラスか区別することなく、それぞれのクラスに対応する
 * 別々のメソッドを実行させることができます。
 * <pre>
       patterns[0].oneMatch(. . .);   // StartOfStringPattern.oneMatch
       patterns[1].oneMatch(. . .);   // OneCharPattern.oneMatch
       patterns[2].oneMatch(. . .);   // EndOfStringPattern.oneMatch
 * </pre>
 * 
 * <h1 id="文型の一致">文型の一致</h1>
 * <p>
 * それぞれの文型は、文字列のどの部分と一致するかに加えて、
 * どの文型まで一致を調べるか、文字列のうちの何文字と
 * 一致するかも異なります。
 * これらについて、次の表にまとめます。
 * <div align="center">
 * <table
 *     border="1" rules="all"
 *     summary="それぞれの文型を表わす派生クラス">
 *   <tr bgcolor="lightgray">
 *     <th align="center">文型</th>
 * 	   <th align="center">一致を調べる文型</th>
 * 	   <th align="center">一致する文字数</th>
 *   </tr>
 *   <tr>
 *     <td align="center">任意の 1 文字</td>
 *     <td>その文型の一致のみ</td>
 *     <td align="center">1 文字</td>
 *   </tr>
 *   <tr>
 *     <td align="center">文字列の最初</td>
 *     <td>その文型の一致のみ</td>
 *     <td align="center">0 文字</td>
 *   </tr>
 *   <tr>
 *     <td align="center">文字列の最後</td>
 *     <td>その文型の一致のみ</td>
 *     <td align="center">0 文字</td>
 *   </tr>
 *   <tr>
 *     <td align="center">文字クラス</td>
 *     <td>その文型の一致のみ</td>
 *     <td align="center">1 文字</td>
 *   </tr>
 *   <tr>
 *     <td align="center">閉包</td>
 *     <td>閉包の一致と、閉包に続く文型の一致も調べる</td>
 *     <td align="center">0 文字以上</td>
 *   </tr>
 *   <tr>
 *     <td align="center">指定の 1 文字</td>
 *     <td>その文型の一致のみ</td>
 *     <td align="center">1 文字</td>
 *   </tr>
 * </table>
 * </div>
 * <p>
 * それぞれの文型の一致でのこれらの異なる点は、
 * 抽象メソッド {@link #oneMatch(PatternEnumerator, StringEnumerator)} で
 * 取り扱います。このメソッドは、引数として {@link PatternEnumerator} と 
 * {@link StringEnumerator} を受け取り、それぞれの
 * 現在位置を次のように進めます。
 * <ul>
 *   <li>{@link PatternEnumerator} -- 
 *       最後に一致を調べた正規表現文型に移動します。
 *       その文型の一致のみを調べる場合、現在位置はそのまま移動しません。
 *       閉包の場合、それに続く最後に一致を調べた文型まで移動します。</li>
 *   <li>{@link StringEnumerator} -- 
 *       その正規表現文型に一致した文字をまたぎ越し、その次の文字に移動します。</li>
 * </ul>
 * <p>
 * それぞれの文型の一致について、以下で説明します。
 * <ul>
 *   <li><a href="AnyCharPattern.html#任意の 1 文字の一致">任意の 1 文字の一致</a></li>
 *   <li><a href="StartOfStringPattern.html#文字列の最初の一致">文字列の最初の一致</a></li>
 *   <li><a href="EndOfStringPattern.html#文字列の最後の一致">文字列の最後の一致</a></li>
 *   <li><a href="CharClassPattern.html#文字クラスの一致">文字クラスの一致</a></li>
 *   <li><a href="ClosurePattern.html#閉包の一致">閉包の一致</a></li>
 *   <li><a href="OneCharPattern.html#指定の 1 文字の一致">指定の 1 文字の一致</a></li>
 * </ul>
 */
abstract class RegexPattern {
	
	/**
	 * {@link RegexPattern} クラスのオブジェクトを作成します。
	 */
	protected RegexPattern() {
		//
	}
	
	/**
	 * この正規表現文型が文字列の現在位置から一致するかどうかを調べる
	 * 抽象メソッドです。
	 * 
	 * @param patEnum 正規表現文型の列挙子です。呼び出し時の現在位置は、
	 * 				この正規表現文型であるものとします。呼び出し後の現在位置は、
	 * 				最後に一致するかどうか調べた正規表現文型になります。
	 * @param strEnum 文字列の列挙子です。呼び出し後の現在位置は、
	 * 				この正規表現文型に一致した文字をまたぎ越し、
	 * 				その次の文字に移動します。
	 * @return 一致に成功した場合は {@code true} を、
	 * 			失敗した場合は {@code false} を返します。
	 */
	abstract boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum);
}
