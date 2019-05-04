package com.gmail.tt195361.Regex;

/**
 * 文字列中の文字の列挙に加えて、開始位置の移動、現在位置の判別を行います。
 *
 * <p>
 * このクラスについて、以下の項目を順に説明します。
 * <ul>
 *   <li><a href="#メンバー変数">メンバー変数</a></li>
 *   <li><a href="#現在位置の移動">現在位置の移動</a></li>
 *   <li><a href="#文字の列挙">文字の列挙</a></li>
 *   <li><a href="#現在位置の判別">現在位置の判別</a></li>
 *   <li><a href="#メソッドの動作例">メソッドの動作例</a></li>
 *   <li><a href="#列挙子を使うプログラム">列挙子を使うプログラム</a></li>
 *   <li><a href="#開始位置の移動">開始位置の移動</a></li>
 *   <li><a href="#状態の保存と復元">状態の保存と復元</a></li>
 * </ul>
 * 
 * <h1 id="メンバー変数">メンバー変数</h1>
 * <p>
 * このクラスは、以下のメンバー変数を使います。
 * <table
 * 		border="1" rules="all"
 * 		summary="StringEnumerator クラスのメンバー変数">
 *   <tr bgcolor="lightgray">
 *     <th align="center">名前</th>
 *     <th align="center">型</th>
 *     <th align="center">説明</th>
 *   </tr>
 *   <tr>
 *     <td>{@code _str}</td>
 *     <td>{@code final String}</td>
 *     <td>列挙する文字列を保持します。
 *     	   この値は、コンストラクタで最初に設定したあとは変更しないので、
 *     	   {@code final} にします。</td>
 *   </tr>
 *   <tr>
 *     <td>{@code _length}</td>
 *     <td>{@code final int}</td>
 *     <td>列挙する文字列の長さです。
 *   	   この値はこのクラスの中で何回か使うので、変数に入れておきます。
 *   	   この値も変更しないので、{@code final} にします。</td>
 *   </tr>
 *   <tr>
 *     <td>{@code _startIndex}</td>
 *     <td>{@code int}</td>
 *     <td>列挙の開始位置を保持します。</td>
 *   </tr>
 *   <tr>
 *     <td>{@code _currentIndex}</td>
 *     <td>{@code int}</td>
 *     <td>列挙の現在位置の値です。</td>
 *   </tr>
 * </table>
 *
 * <h1 id="現在位置の移動">現在位置の移動</h1>
 * <p>
 * 列挙の現在位置を保持する変数 {@code _currentIndex} の値は、
 * {@link #moveNext()} メソッドで次の位置へ進めます。
 * このメソッドを呼ぶごとに、現在位置は次のように移動します。
 * <ul>
 *   <li>指定の開始位置から、文字列中のそれぞれの文字を順に移動します。</li>
 *   <li>文字列中の文字を列挙したあとは、文字列の終わりに移動します。
 *       文字列の最後を表わす {@link EndOfStringPattern 正規表現文型 '$'} は、
 *       この位置と一致します。</li>
 *   <li>次にこのメソッドを呼ぶと、文字列の終わりを越えた位置に移動します。</li>
 *   <li>さらにこのメソッドを呼んでも、現在位置はこれ以上移動しません。</li>
 * </ul>
 * <p>
 * {@link #moveNext()} メソッドの実装を、次に示します。
 * {@link #hasCurrent()} メソッドを使って、
 * 現在位置に文字があるか、あるいは文字列の終わりであることを調べ、
 * この条件が満たされていれば現在位置を 1 つ進めます。
 * <pre>
        void moveNext() {
            if (hasCurrentOrEnd()) {
                setCurrentIndex(_currentIndex + 1);
            }
        }
 * </pre>
 * 
 * <h1 id="文字の列挙">文字の列挙</h1>
 * <p>
 * 文字列中の文字を列挙し順に処理するため、以下のメソッドを提供します。
 * <table
 * 		border="1" rules="all"
 * 		summary="文字を列挙するメソッド">
 *   <tr bgcolor="lightgray">
 *     <th align="center">名前</th>
 *     <th align="center">説明</th>
 *   </tr>
 *   <tr>
 *     <td>{@link #hasCurrent()}</td>
 *     <td>現在位置に文字があるかどうかを調べます。</td>
 *   </tr>
 *   <tr>
 *     <td>{@link #hasCurrent()}</td>
 *     <td>現在位置に文字があるか、あるいは、現在位置が
 *         文字列の終わりかを調べます。このメソッドは、
 *         正規表現文型の一致を調べるときに使います。
 *         {@link EndOfStringPattern 正規表現文型 '$'} が
 *         文字列の終わりと一致するためです。</td>
 *   </tr>
 *   <tr>
 *     <td>{@link #getCurrent()}</td>
 *     <td>現在位置の文字を取得します。</td>
 *   </tr>
 * </table>
 * <p>
 * {@link #hasCurrent()} メソッドの実装は、次のようになります。
 * 現在位置を示すメンバー変数 {@code _currentIndex} が、
 * {@code 0} 以上、かつ {@code _length} 未満であることを調べます。
 * <pre>
        boolean hasCurrent() {
            return 0 <= _currentIndex && _currentIndex < _length;
        }
 * </pre>
 * <p>
 * {@link #hasCurrent()} メソッドは、次に示すように、
 * {@link #hasCurrent()} と {@link #isEnd()} を使います。
 * <pre>
        boolean hasCurrentOrEnd() {
            return hasCurrent() || isEnd();
        }
 * </pre>
 *
 * <h1 id="現在位置の判別">現在位置の判別</h1>
 * <p>
 * 正規表現文型との一致を調べる場合や、文字列を正規表現文型と
 * して解釈する場合に、現在位置が文字列のどこなのか知りたい
 * ときがあります。このために、以下のメソッドを用意しました。
 * <table
 * 		border="1" rules="all"
 * 		summary="現在位置を判別するメソッド">
 *   <tr bgcolor="lightgray">
 *     <th align="center">名前</th>
 *     <th align="center">説明</th>
 *     <th align="center">条件</th>
 *   </tr>
 *   <tr>
 *     <td>{@link #isStart()}</td>
 *     <td>現在位置が文字列の最初かどうかを調べます。</td>
 *     <td>{@code  _currentIndex == 0}</td>
 *   </tr>
 *   <tr>
 *     <td>{@link #isLast()}</td>
 *     <td>現在位置が文字列の終わりかどうかを調べます。</td>
 *     <td>{@code _currentIndex == _length - 1}</td>
 *   </tr>
 *   <tr>
 *     <td>{@link #isEnd()}</td>
 *     <td>現在位置が文字列の終わりかどうかを調べます。</td>
 *     <td>{@code _currentIndex == _length}</td>
 *   </tr>
 * </table>
 *
 * <h1 id="メソッドの動作例">メソッドの動作例</h1>
 * <p>
 * 文字列 "abc" を、開始位置 0 から列挙する場合、
 * {@link #moveNext} メソッドで現在位置を移動するたびに、
 * <a href="#文字の列挙">文字の列挙</a> と
 * <a href="#現在位置の判別">現在位置の判別</a> の
 * メソッドが返す値は以下のようになります。
 * <table
 * 		border="1" rules="all"
 * 		summary="moveNext() の呼び出しと各メソッドの戻り値">
 *   <tr　align="center">
 *     <th>{@link #moveNext}<br>呼び出し回数</th>
 *     <th>{@link #hasCurrent}</th>
 *     <th>{@link #hasCurrent}</th>
 *     <th>{@link #getCurrent}</th>
 *     <th>{@link #isStart}</th>
 *     <th>{@link #isLast}</th>
 *     <th>{@link #isEnd}</th>
 *   </tr>
 *   <tr align="center">
 *     <td>0</td>
 *     <td>{@code true}</td>
 *     <td>{@code true}</td>
 *     <td>'a'</td>
 *     <td>{@code true}</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *   </tr>
 *   <tr align="center">
 *     <td>1</td>
 *     <td>{@code true}</td>
 *     <td>{@code true}</td>
 *     <td>'b'</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *   </tr>
 *   <tr align="center">
 *     <td>2</td>
 *     <td>{@code true}</td>
 *     <td>{@code true}</td>
 *     <td>'c'</td>
 *     <td>{@code false}</td>
 *     <td>{@code true}</td>
 *     <td>{@code false}</td>
 *   </tr>
 *   <tr align="center">
 *     <td>3</td>
 *     <td>{@code false}</td>
 *     <td>{@code true}</td>
 *     <td>{@code '\0'}</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *     <td>{@code true}</td>
 *   </tr>
 *   <tr align="center">
 *     <td>4</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *     <td>{@code '\0'}</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *   </tr>
 * </table>
 *
 * <h1 id="列挙子を使うプログラム">列挙子を使うプログラム</h1>
 * <p>
 * 列挙子を使って文字を順に処理するプログラムは、
 * 次のような形になります。
 * <pre>
        StringEnumerator strEnum;
        while (strEnum.hasCurrent()) {          // 現在位置に文字がある間繰り返す。
            char c = strEnum.getCurrent();      // 現在位置の文字を取得する。
            if (c == '^' && strEnum.isStart())  // '^' が文字列の最初かどうか調べる。
                . . . . .
            strEnum.moveNext();                 // 次の位置に移動する。
        }
 * </pre>
 * <p>
 * 列挙子を使わずに文字列とその現在位置を直接扱うと、
 * プログラムは以下のようになります。
 * <pre>
        String s;
        for (int i = 0; i < s.length(); ++i) {  // 文字列中のすべての文字について、
            char c = s.charAt(i);               // 現在位置の文字を取得する。
            if (c == '^' && i == 0)             // '^' が文字列の最初かどうか調べる。
                . . . . .
        }
 * </pre>
 * <p>
 * 列挙子を導入することにより、要素を列挙して処理するプログラムは、
 * 要素を列挙する列挙子と、列挙した要素を処理する部分に分かれます。
 * このように関連するものをまとめて処理を分割することで、
 * プログラムのそれぞれの部分がより単純になり、
 * プログラムをより読みやすく、より理解しやすくできます。
 *
 * <h1 id="開始位置の移動">開始位置の移動</h1>
 * <p>
 * 文字列の開始位置を移動するには、以下のメソッドを使います。
 * <table
 * 		border="1" rules="all"
 * 		summary="開始位置を移動するメソッド">
 *   <tr bgcolor="lightgray">
 *     <th align="center">名前</th>
 *     <th align="center">説明</th>
 *   </tr>
 *   <tr>
 *     <td>{@link #hasValidStart()}</td>
 *     <td>現在の開始位置が有効かどうかを返します。
 *         文型 {@code $} が文字列の最後に一致するので、
 *         文字列の最後までが有効な開始位置になります。</td>
 *   </tr>
 *   <tr>
 *     <td>{@link #moveNextStart()}</td>
 *     <td>開始位置を次に移動します。</td>
 *   </tr>
 * </table>
 * <p>
 * すべての開始位置を順に調べるプログラムは、以下のようになります。
 * <pre>
        StringEnumerator strEnum;
        while (strEnum.hasValidStart()) {   // 現在の開始位置が有効な間繰り返す。
            . . . . .                       // 現在の開始位置から処理する。
            strEnum.moveNextStart();        // 次の開始位置に移動する。
        }
 * </pre>
 *
 * <h1 id="状態の保存と復元">状態の保存と復元</h1>
 * <p>
 * TODO: 説明を追加する。
 */
class StringEnumerator extends Enumerator<Character> {
	
	/**
	 * 指定の文字列の文字を順に列挙する列挙子を、文字列の解釈用として作成します。
	 * 
	 * @param str 列挙する文字を含む文字列です。
	 * @return 作成した列挙子 {@link StringEnumerator} のオブジェクトを返します。
	 */
	static StringEnumerator makeForParse(String str) {
		// 文字列の解釈の場合は、文字列中の文字を列挙するだけです。
		int enumLength = str.length();
		return new StringEnumerator(str, enumLength);
	}
	
	/**
	 * 指定の文字列の文字を順に列挙する列挙子を、一致の検索用として作成します。
	 * 
	 * @param str 列挙する文字を含む文字列です。
	 * @return 作成した列挙子 {@link StringEnumerator} のオブジェクトを返します。
	 */
	static StringEnumerator makeForMatch(String str) {
		// 一致を調べる場合は、文字列の最後に一致する "$" のため、
		// 文字列中の文字に加えて、もう 1 文字列挙します。
		int enumLength = str.length() + 1;
		return new StringEnumerator(str, enumLength);
		
	}
	
	// メンバー変数: 文字列、文字列の長さ、開始位置を保持します。
	private final String _str;
	private final int _strLength;
	private int _startIndex;
	
	private StringEnumerator(String str, int enumLength) {
		super(enumLength);
		_str = str;
		_strLength = str.length();
		setStartAndCurrentIndex(0);
	}
	
	@Override
	protected Character getElementAt(int index) {
		if (hasCharAt(index)) {
			return _str.charAt(index);
		}
		else {
			return getNullElement();
		}
	}
	
	@Override
	protected Character getNullElement() {
		return '\0';
	}
	
	private void setStartAndCurrentIndex(int value) {
		setCurrentIndex(value);
		_startIndex = value;
	}
	
	/**
	 * 現在位置に文字があるかどうかを調べます。
	 * 
	 * @return 現在位置に文字があれば {@code true} を、
	 * 		文字がなければ {@code false} を返します。
	 */
	boolean hasChar() {
		int currentIndex = getCurrentIndex();
		return hasCharAt(currentIndex);
	}
	
	private boolean hasCharAt(int index) {
		return 0 <= index && index < _strLength;
	}
	
	/**
	 * 現在位置が文字列の最初かどうかを調べます。
	 * 
	 * @return 現在位置が文字列の最初ならば {@code true} を、
	 * 		最初でなければ {@code false} を返します。
	 */
	boolean isStart() {
		return getCurrentIndex() == 0;
	}
	
	/**
	 * 現在位置が文字列の最後の文字かどうかを調べます。
	 * 
	 * @return 現在位置が文字列の最後の文字ならば {@code true} を、
	 * 		最後でなければ {@code false} を返します。
	 */
	boolean isLast() {
		return getCurrentIndex() == _strLength - 1;
	}
	
	/**
	 * 現在位置が文字列の終わりかどうかを調べます。
	 * 
	 * @return 現在位置が文字列の終わりならば {@code true} を、
	 * 		終わりでなければ {@code false} を返します。
	 */
	boolean isEnd() {
		return getCurrentIndex() == _strLength;
	}
	
	/**
	 * 開始位置が有効かどうかを返します。
	 * 
	 * @return 現在の開始位置が有効ならば {@code true} を、
	 * 		無効ならば {@code false} を返します。
	 */
	boolean hasValidStart() {
		// '$' は文字列の最後に一致するので、文字列の終わりまで調べます。
		return _startIndex <= _strLength;
	}
	
	/**
	 * 開始位置を次に移動します。あわせて現在位置を開始位置に移動します。
	 * 開始位置が無効な場合は、開始位置と現在位置はそのまま移動しません。
	 */
	void moveNextStart() {
		if (hasValidStart()) {
			setStartAndCurrentIndex(_startIndex + 1);
		}
	}
	
	/**
	 * 開始位置の値を取得します。
	 * 
	 * @return 開始位置の値を返します。
	 */
	int getStartIndex() {
		return _startIndex;
	}
	
	/**
	 * 開始位置から現在位置までの部分文字列を取得します。
	 * 
	 * @return 開始位置から現在位置までの部分文字列を返します。
	 */
	String getSubstring() {
		return _str.substring(_startIndex, getEndIndex());
	}
	
	/**
	 * {@link StringEnumerator} クラスのオブジェクトの文字列表現を返します。
	 */
	@Override
	public String toString() {
		return String.format(
				"_startIndex=%d, _currentIndex=%d, rest=%s",
				_startIndex, getCurrentIndex(), getRest());
	}
	
	private String getRest() {
		return _str.substring(getEndIndex());
	}
	
	private int getEndIndex() {
		return Math.min(getCurrentIndex(), _strLength);
	}
	
	/**
	 * 開始位置を設定します。単体テストで使うためのメソッドです。
	 * 現在位置も開始位置と同じ値に設定されます。
	 * 
	 * @param startIndex 設定する開始位置の値を指定します。
	 */
	void setStartIndexForUnitTest(int startIndex) {
		setStartAndCurrentIndex(startIndex);
	}
	
	/**
	 * 現在位置を設定します。単体テストで使うためのメソッドです。
	 * 
	 * @param currentIndex 設定する現在位置の値を指定します。
	 */
	void setCurrentIndexForUnitTest(int currentIndex) {
		setCurrentIndex(currentIndex);
	}
}
