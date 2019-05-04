/**
 * 正規表現が文字列と一致するかどうかを調べるプログラムです。
 * 実際に動作するある程度の大きさのプログラムを使って、その内容を説明します。
 * <p>
 * このプログラムと説明を作成するにあたり、以下の書籍と Web サイトを
 * 参考にさせていただきました。
 * ここに記して、感謝いたします。
 * <ul>
 *   <li><a
 *   		href="https://www.amazon.co.jp/%E3%82%BD%E3%83%95%E3%83%88%E3%82%A6%E3%82%A7%E3%82%A2%E4%BD%9C%E6%B3%95-Brian-W-Kernighan/dp/4320021428">
 *   		ソフトウェア作法, Brian W.Kernighan, P.J.Plauger 著, 木村 泉 訳
 * 		 </a></li>
 *   <li><a
 *   		href="https://ja.wikipedia.org/wiki/%E6%AD%A3%E8%A6%8F%E8%A1%A8%E7%8F%BE">
 *   		正規表現 - Wikipedia
 *   	  </a></li>
 * </ul>
 * <p>
 * このプログラムについて、以下の内容を順に説明します。
 * <ul>
 *   <li><a href="RegexParser.html#取り扱う正規表現">取り扱う正規表現</a></li>
 *   <li><a href="RegexPattern.html#文型の表わし方">文型の表わし方</a></li>
 *   <li><a href="PatternEnumerator.html#正規表現の表わし方">正規表現の表わし方</a></li>
 *   <li><a href="RegexMatcher.html#一致を調べる手順">一致を調べる手順</a></li>
 *   <li><a href="RegexMatcher.html#一致を調べるプログラム">一致を調べるプログラム</a></li>
 *   <li><a href="PatternEnumerator.html#列挙子">列挙子</a></li>
 *   <li><a href="RegexMatcher.html#文字列の開始位置の移動">文字列の開始位置の移動</a></li>
 *   <li><a href="RegexMatcher.html#現在位置から一致を調べる">現在位置から一致を調べる</a></li>
 *   <li><a href="RegexPattern.html#文型の一致">文型の一致</a></li>
 * </ul>
 * 
 * <p>
 * TODO: ここから下は、以前の内容をそのまま残してあるもの。検討して変更する。
 * 
 * <h2>一致を調べる (1)</h2>
 * <p>
 * 上記の手順はそのままでは複雑なので、以下の 2 つに分けてみます。
 * <ol>
 *   <li>文字列の最初の文字から、2 番目の文字から、3 番目の文字から、...、と、
 *       開始位置を順に移動する。</li>
 *   <li>文字列の指定の開始位置から、文型が一致するかどうかを調べる。</li>
 * </ol>
 * <p>
 * 開始位置の移動には、文字列の列挙子 {@link com.gmail.tt195361.Regex.StringEnumerator} が
 * 提供する、以下のメソッドを使います。
 * <ul>
 *   <li>{@link com.gmail.tt195361.Regex.StringEnumerator#hasValidStart() StringEnumerator.hasCurrentStart()} -- 
 *   	現在の開始位置から一致を調べられるかどうかを返します。</li>
 *   <li>{@link com.gmail.tt195361.Regex.StringEnumerator#moveNextStart() StringEnumerator.moveNextStart()} -- 
 *   	開始位置を次の文字に移動します。</li>
 * </ul>
 * <p>
 * {@link com.gmail.tt195361.Regex.StringEnumerator#hasValidStart() StringEnumerator.hasCurrentStart()} で
 * 文字列のどこまで調べればよいか考えます。文型 {@code $} は文字列の最後に一致します。
 * 文字列の最後は、文字列の最後の文字の次の位置になります。
 * そのため、一致は {@code _startIndex} が {@code _length} に等しくなるまで調べる必要があります。
 * <pre>
        boolean hasCurrentStart() {
            // '$' は文字列の最後に一致するので、文字列の最後の文字の次の位置まで調べます。
            return _startIndex <= _length;
        }
 * </pre>
 * <p>
 * {@link com.gmail.tt195361.Regex.StringEnumerator#moveNextStart() StringEnumerator.moveNextStart()} は、
 * {@link com.gmail.tt195361.Regex.StringEnumerator#hasValidStart() StringEnumerator.hasCurrentStart()} で
 * 現在の開始位置が有効ならば次に進めます。
 * その中で使っている {@code setStartAndCurrentIndex()} は、
 * {@code _startIndex} と {@code _currentIndex} を、指定の値に設定します。
 * <pre>
        void moveNextStart() {
            if (hasCurrentStart()) {
                setStartAndCurrentIndex(_startIndex + 1);
            }
        }

        private void setStartAndCurrentIndex(int value) {
            _startIndex = _currentIndex = value;
        }
 * </pre>
 * <p>
 * これらを使うと、一致を調べる第 1 の手順を実装する
 * {@link com.gmail.tt195361.Regex.RegexMatcher#match(String)} メソッドは、以下のようになります。
 * {@code _patEnum} は、{@link com.gmail.tt195361.Regex.PatternEnumerator} 型のメンバー変数です。
 * {@link com.gmail.tt195361.Regex.PatternEnumerator#restoreState(EnumeratorState)} に
 * ついては、<a href="#列挙子の状態の保存と復元">列挙子の状態の保存と復元</a> で説明します。
 * {@link com.gmail.tt195361.Regex.RegexMatcher#matchFromCurrent(PatternEnumerator, StringEnumerator)} は、
 * 第 2 の手順を実装するメソッドです。
 * {@link com.gmail.tt195361.Regex.MatchResult} は、一致を調べた結果を保持するクラスで、
 * <a href="#一致を調べた結果">一致を調べた結果</a> で説明します。
 * <pre>
        public MatchResult match(String str) {
            StringEnumerator strEnum = new StringEnumerator(str);

            // 指定の文字列に調べられる開始位置が存在する間、一致を調べます。
            while (strEnum.hasCurrentStart()) {

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
 * </pre>
 * 
 * <h2>一致を調べる (2)</h2>
 * <p>
 * 正規表現と文字列の一致を調べる第 2 の手順では、文字列の現在の開始位置から、
 * 正規表現を構成する文型が順に一致するかどうかを調べます。このために
 * 文型の列挙子 {@link com.gmail.tt195361.Regex.PatternEnumerator} の
 * 以下のメソッドを使います。
 * <ul>
 *   <li>{@link com.gmail.tt195361.Regex.PatternEnumerator#hasCurrent()} -- 
 *   	現在位置に文型があるかどうかを調べます。</li>
 *   <li>{@link com.gmail.tt195361.Regex.PatternEnumerator#getCurrent()} -- 
 *   	現在位置の文型を取得します。</li>
 *   <li>{@link com.gmail.tt195361.Regex.PatternEnumerator#moveNext()} -- 
 *   	現在位置を次の文型に移動します。</li>
 * </ul>
 * <p>
 * {@link com.gmail.tt195361.Regex.PatternEnumerator#hasCurrent()} は、
 * 現在位置 {@code _currentIndex} が、文型の配列 {@code _patterns} の添え字の範囲である
 * {@code 0} 以上かつ {@code _patterns.length} 未満であることを調べます。
 * <pre>
        boolean hasCurrent() {
            return 0 <= _currentIndex && _currentIndex < _patterns.length;
        }
 * </pre>
 * <p>
 * {@link com.gmail.tt195361.Regex.PatternEnumerator#getCurrent()} は、
 * 現在位置に文型があればその文型を、なければ {@code null} を返します。
 * <pre>
        RegexPattern getCurrent() {
            if (!hasCurrent()) {
                return null;
            }
            else {
                return _patterns[_currentIndex];
            }
        }
 * </pre>
 * {@link com.gmail.tt195361.Regex.PatternEnumerator#moveNext()} は、
 * 現在位置 {@code _currentIndex} を一つ進めます。ただしこれは現在位置に文型がある場合だけ、
 * つまり {@link com.gmail.tt195361.Regex.PatternEnumerator#hasCurrent()} が、
 * {@code true} を返す場合だけにします。現在位置に文型がない場合は、それ以上先に進めません。
 * <pre>
        void moveNext() {
            if (hasCurrent()) {
                ++_currentIndex;
            }
        }
 * </pre>
 * <p>
 * 一致を調べる第 2 の手順 {@link com.gmail.tt195361.Regex.RegexMatcher#matchFromCurrent(PatternEnumerator, StringEnumerator)} メソッド
 * について説明します。このメソッドが {@code static} になっているのは、閉包の一致を調べるためにも使うためです。
 * {@link com.gmail.tt195361.Regex.StringEnumerator#hasCurrent() StringEnumerator.hasCurrentOrEnd()} は、
 * 文字列の現在位置に文字があるか文字列の終わりならば、{@code true} を返します。
 * {@link com.gmail.tt195361.Regex.RegexPattern#oneMatch(PatternEnumerator, StringEnumerator)} は、
 * <a href="#文型の表わし方">文型の表わし方</a> で説明した、{@link com.gmail.tt195361.Regex.RegexPattern} クラスの
 * 抽象メソッドで、その文型が文字列に一致するかどうかを調べます。
 * <pre>
        static boolean matchFromCurrent(PatternEnumerator patEnum, StringEnumerator strEnum) {
            // 正規表現文型が存在する間、繰り返します。
            while (patEnum.hasCurrent()) {

                // 正規表現文型がまだ存在するにもかかわらず、文字列の終わりを越えていれば、一致は失敗です。
                if (!strEnum.hasCurrentOrEnd()) {
                    return false;
                }

                // 現在の正規表現文型が文字列に一致するかどうか調べます。一致しなければ失敗です。
                RegexPattern currentPat = patEnum.getCurrent();
                if (!currentPat.oneMatch(patEnum, strEnum)) {
                    return false;
                }

                // 現在の正規表現文型の一致に成功したので、次の正規表現文型に移動します。
                patEnum.moveNext();
            }

            // すべての正規表現文型が文字列の現在位置から一致したので、成功です。
            return true;
        }
 * </pre>
 * 
 * <h2 id="文型の一致">文型の一致</h2>
 * <p>
 * それぞれの文型が文字列の現在位置からの一致は、
 * 抽象メソッド {@link com.gmail.tt195361.Regex.RegexPattern#oneMatch(PatternEnumerator, StringEnumerator)} を、
 * それぞれの文型に応じて具体的に実装します。
 * このメソッドは、一致したかどうかを返すとともに、それぞれの列挙子の現在位置を次のように進めます。
 * <ul>
 *   <li>{@link com.gmail.tt195361.Regex.PatternEnumerator} -- 
 *   	閉包以外でその文型自身と一致した場合、現在位置はそのまま移動しません。
 *   	閉包の場合、最後に一致した正規表現文型に移動します。</li>
 *   <li>{@link com.gmail.tt195361.Regex.StringEnumerator} -- 
 *   	その正規表現文型に一致した文字をまたぎ越し、その次の文字に移動します。</li>
 * </ul>
 * <p>
 * それぞれの文型の一致について、これから説明します。
 * 
 * <h3>任意の 1 文字 ({@link com.gmail.tt195361.Regex.AnyCharPattern})</h3>
 * <p>
 * 任意の 1 文字は、文字列の列挙子の現在位置に文字があれば、一致します。
 * 一致すれば、文字列の現在位置を次に進めます。
 * プログラムは次のようになります。
 * <pre>
        &#64;Override
        boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum) {
            if (!strEnum.hasCurrent()) {
                return false;
            }

            strEnum.moveNext();
            return true;
        }
 * </pre>
 * 
 * <h3>文字列の最初 ({@link com.gmail.tt195361.Regex.StartOfStringPattern})</h3>
 * <p>
 * 文字列の最初に一致する正規表現文型を表わす
 * {@link com.gmail.tt195361.Regex.StartOfStringPattern} クラスの場合、
 * {@link com.gmail.tt195361.Regex.StringEnumerator#isStart() StringEnumerator.isStart()} を呼び、
 * 現在位置が文字列の最初かどうかを調べます。
 * この文型は一致する文字はないので、{@code strEnum} の位置はそのままにします。
 * <pre>
        &#64;Override
        boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum) {
            return strEnum.isStart();
        }
 * </pre>
 * 
 * <h3>文字列の最後 ({@link com.gmail.tt195361.Regex.EndOfStringPattern})</h3>
 * <p>
 * 文字列の最後に一致する {@link com.gmail.tt195361.Regex.EndOfStringPattern} は、
 * {@link com.gmail.tt195361.Regex.StringEnumerator#isEnd() StringEnumerator.isEnd()} を使って
 * 現在位置が文字列の最後かどうかを調べます。
 * この文型も一致する文字はないので、{@code strEnum} の位置はそのまま移動しません。
 * <pre>
        &#64;Override
        boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum) {
            return strEnum.isEnd();
        }
 * </pre>
 * 
 * <h3>文字クラス ({@link com.gmail.tt195361.Regex.CharClassPattern})</h3>
 * <p>
 * 文字クラスを表わす {@link com.gmail.tt195361.Regex.CharClassPattern} クラスは、
 * 以下のメンバー変数を使います。
 * <ul>
 *   <li>{@code _notContains} -- 
 *   	指定の文字が含まれていないことを示す {@code boolean} の値です。</li>
 *   <li>{@code _charSet} -- 
 *   	指定の文字を格納する {@link java.util.HashSet} です。</li>
 * </ul>
 * <p>
 * 文字クラスが一致するのは、現在位置に文字があり、かつ、{@code _notContains} と
 * {@code _charSet} が以下の条件を満たすときです。
 * <ul>
 *   <li>{@code _notContains} が {@code false} ならば、
 * 		現在位置の文字が {@code _charSet} に含まれている、あるいは、</li>
 *   <li>{@code _notContains} が {@code true} ならば、
 * 		現在位置の文字が {@code _charSet} に含まれていない。</li>
 * </ul>
 * <p>
 * {@link com.gmail.tt195361.Regex.CharClassPattern#oneMatch(PatternEnumerator, StringEnumerator)} メソッドは、
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
 * 上のメソッドの中の {@code matchConditionSatisfied()} は、指定の文字が条件を満たすかどうかを返します。
 * この条件は、排他的論理和 (XOR) を使って、以下のように書けます。
 * <pre>
        private boolean matchConditionSatisfied(char ch) {
            // ^ は排他的論理和で、"false ^ true" あるいは "true ^ false" のとき、結果は true になります。
            return _notContains ^ _charSet.contains(ch);
        }
 * </pre>
 * 
 * <h3>閉包 ({@link com.gmail.tt195361.Regex.ClosurePattern})</h3>
 * <p>
 * 閉包の一致は、次の手順で調べます。
 * <ol>
 *   <li>閉包の前の文型を 0 回以上なるべく多く繰り返し一致させます。</li>
 *   <li>閉包の後ろの正規表現文型が、残りの文字列に一致するか確認します。</li>
 * </ol>
 * 
 * <h3>指定の 1 文字 ({@link com.gmail.tt195361.Regex.OneCharPattern})</h3>
 * <p>
 * 
 * 
 * <h2 id="一致を調べた結果">一致を調べた結果</h2>
 * <p>
 * 正規表現が文字列と一致するかどうかを調べた結果ですが、一致に成功したか失敗したかに加えて、
 * 文字列のどの位置から一致したか、実際に一致した文字列なども取得できると便利です、、、
 * 
 * <h2 id="列挙子の状態の保存と復元">列挙子の状態の保存と復元</h2>
 * <p>
 * 
 * <!--- ===== --->
 * <h2>正規表現文型の一致</h2>
 * <p>
 * <a href="#文型の表わし方">文型の表わし方</a> で説明したように、それぞれの正規表現文型を表わす
 * クラスは、
 * 
 * <h2>一致を調べるプログラム</h2>
 * <p>
 * このプログラムを使って、指定の正規表現文型が文字列と一致するかどうかを調べる手順を説明します。
 * <ol>
 *   <li>{@link com.gmail.tt195361.Regex.RegexMatcher} クラスのコンストラクタ
 *   	{@link com.gmail.tt195361.Regex.RegexMatcher#RegexMatcher(String)} を、
 *   	正規表現の文型を表わす文字列を引数として呼び出し、
 *   	{@link com.gmail.tt195361.Regex.RegexMatcher} クラスのオブジェクトを作成します。
 *   	正規表現が {@code "^[a-z]*$"} の場合は、次のようになります。
 *      <pre>
 *　  RegexMatcher matcher = new RegexMatcher("^[a-z]$");
 * 		</pre>
 *   </li>
 *   <li>作成した {@link com.gmail.tt195361.Regex.RegexMatcher} クラスのオブジェクトに対して、
 *   	一致するかどうかを調べる文字列を引数として
 *   	{@link com.gmail.tt195361.Regex.RegexMatcher#match} メソッドを呼び出します。
 *      文字列 {@code "abcxyz"} と一致するかどうかを調べる例を示します。
 *      <pre>
 *　  MatchResult result = matcher.match("abcxyz");
 * 		</pre>
 *   </li>
 *   <li>{@link com.gmail.tt195361.Regex.RegexMatcher#match} メソッドは、
 *   	{@link com.gmail.tt195361.Regex.MatchResult} クラスのオブジェクトを
 *   	返します。このオブジェクトは一致に成功したかどうかを返す
 *   	{@link com.gmail.tt195361.Regex.MatchResult#isSucceess} などのメソッドがあり、
 *   	一致を調べた結果を提供します。
 *      <pre>
 *　  if (result.isSuccess()) {                            // 一致に成功したかどうか調べる
 *　　  　　int startIndex = result.getStartIndex();       // 一致の開始位置を取得
 *　　　  　String matchString = result.getMatchString();  // 一致した部分の文字列を取得
 *　　　　  . . . . .
 *　  }
 * 		</pre>
 *   </li>
 * </ol>
 * 
 * <h2>動作の流れ</h2>
 * <p>
 * {@link com.gmail.tt195361.Regex.RegexMatcher} プログラムは、以下の流れで動作します。
 * <ol>
 *   <li>{@link com.gmail.tt195361.Regex.RegexMatcher} クラスのコンストラクタ
 *   	{@link com.gmail.tt195361.Regex.RegexMatcher#RegexMatcher(String)} で受け取った正規表現の
 *   	文型を表わす文字列を解釈し、プログラム内で使用する内部表現を作成します。</li>
 *   <li>{@link com.gmail.tt195361.Regex.RegexMatcher#match} メソッドで指定の文字列に対して、
 *   	作成した内部表現が一致するかどうかを調べます。このためには、指定の文字列の最初から順にそれぞれの位置で、
 *      正規表現のそれぞれの内部表現が文字列に順に一致するか調べます。</li>
 * </ol>
 * 
 * <h2>正規表現の文字列の解釈</h2>
 * <p>
 * {@link com.gmail.tt195361.Regex.RegexMatcher} クラスのコンストラクタ
 * {@link com.gmail.tt195361.Regex.RegexMatcher#RegexMatcher(String)} で受け取った正規表現の
 * 文型を表わす文字列を解釈し、プログラム内で使用する内部表現を作成します。
 * <p>
 * 内部表現の作成には、{@link com.gmail.tt195361.Regex.RegexParser#parse(String)}
 * メソッドを使います。関連するクラスは、以下の通りです。
 * <ul>
 *   <li>{@link com.gmail.tt195361.Regex.RegexParser}</li>
 *   <li>{@link com.gmail.tt195361.Regex.CharClassParser}</li>
 *   <li>{@link com.gmail.tt195361.Regex.PatternParseResult}</li>
 * </ul>
 * 
 * <h2>一致を調べる手順</h2>
 * <p>
 * {@link com.gmail.tt195361.Regex.RegexMatcher#match} メソッドで指定の文字列に対して、
 * 作成した内部表現が一致するかどうかを調べます。このためには、指定の文字列の最初から順にそれぞれの位置で、
 * 正規表現のそれぞれの内部表現が文字列に順に一致するか調べます。
 * <p>
 * 例として、正規表現 {@code "abc"} が文字列 {@code "aabcd"} と一致するかどうか調べる場合を
 * 考えます。次の図は、文字列の位置 0 から調べる場合です。この場合、文字列の位置 1 の文字 {@code a} と
 * 2 番目の内部表現  {@code OneChar: 'b'} が一致しません。
 * <pre>
 *    文字列
 *   位置 文字                  内部表現
 *        +---+   一致する  +--------------+
 *     0  | a |　&lt;--------&gt; | OneChar: 'a' |
 *        +---+   一致せず  +--------------+
 *     1  | a |　&lt;--------&gt; | OneChar: 'b' |
 *        +---+             +--------------+
 *     2  | b |             | OneChar: 'c' |
 *        +---+             +--------------+
 *     3  | c |
 *        +---+
 *     4  | d |
 *        +---+
 * </pre>
 * <p>
 * 次に、文字列の位置 1 から調べる場合の図を示します。このときは、すべての正規表現文型が一致します。
 * <pre>
 *    文字列
 *   位置 文字                  内部表現
 *        +---+
 *     0  | a |
 *        +---+   一致する  +--------------+
 *     1  | a |　&lt;--------&gt; | OneChar: 'a' |
 *        +---+   一致する  +--------------+
 *     2  | b |　&lt;--------&gt; | OneChar: 'b' |
 *        +---+   一致する  +--------------+
 *     3  | c |　&lt;--------&gt; | OneChar: 'c' |
 *        +---+             +--------------+
 *     4  | d |
 *        +---+
 * </pre>
 * 
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
