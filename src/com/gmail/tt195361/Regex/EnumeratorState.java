package com.gmail.tt195361.Regex;

/**
 * 列挙子の状態を保存し復元できるようにします。
 * 
 * <h1 id="列挙子の状態の保存と復元">列挙子の状態の保存と復元</h1>
 * <p>
 * 列挙子の現在位置を保存し、あとで保存したときの状態に
 * 復元したいことがあります。
 * たとえば、<a href="ClosurePattern.html#閉包の一致の実装">
 * 閉包の一致の実装</a> では、{@link StringEnumerator} の候補位置を
 * 作成し、その中から順に残りの文型の一致を調べます。
 * <p>
 * オブジェクトの状態として何を保存・復元するかで、
 * 以下の方法が考えられます。
 * <ul>
 *   <li>状態 -- オブジェクトの中の状態を示す値を
 *       保存・復元します。保存する場合は、オブジェクトから
 *   	 その値を取得し格納します。復元は、格納した値を
 *       オブジェクトに設定し、元の状態に戻します。
 *       列挙子の場合は、現在位置の値を保存・復元します。</li>
 *   <li>状態を格納するオブジェクト -- 状態の値を格納する
 *       新しいクラスを作成します。状態の保存には、
 *       状態の値を格納したこのクラスのオブジェクトを作成します。
 *       復元は、このオブジェクトの値を元のオブジェクトに戻します。</li>
 *   <li>保存・復元するオブジェクト -- 保存・復元したいオブジェクトを
 *       そのままの形で使います。状態を保存するために、
 *       オブジェクトの複製を作れるようにし、そちらを保存します。
 *       復元は、複製に保存された内容を復元するオブジェクトに設定します。</li>
 * </ul>
 * <p>
 * それぞれの方法の評価を、次の表にまとめます。
 * <div align="center">
 * <table
 * 		border="1" rules="all"
 * 		summary="保存方法の評価">
 *   <tr bgcolor="lightgray" align="center">
 *     <th rowspan="2">観点</th>
 *     <th colspan="2">状態</th>
 *     <th colspan="2">状態を格納するオブジェクト</th>
 *     <th colspan="2">保存・復元するオブジェクト</th>
 *   </tr>
 *   <tr bgcolor="lightgray" align="center">
 *     <th>評価</th>
 *     <th>説明</th>
 *     <th>評価</th>
 *     <th>説明</th>
 *     <th>評価</th>
 *     <th>説明</th>
 *   </tr>
 *   <tr valign="top">
 *     <td bgcolor="lightgray">作成の手間</td>
 *     <td bgcolor="lightgreen">〇</td>
 *     <td>状態をそのまま使うので、
 *         新しいクラスなどは必要ありません。
 *         保存・復元するオブジェクトのクラスに
 *         状態の取得と設定のメソッドを追加します。</td>
 *     <td bgcolor="lightblue">△</td>
 *     <td>状態を格納するクラスを作成します。
 *         また、保存・復元を行うクラスに、
 *         それらを行うメソッドが必要になります。</td>
 *     <td bgcolor="lightgreen">〇</td>
 *     <td>保存・復元するオブジェクトをそのまま使うので、
 *         新しいクラスは必要ありません。
 *         複製を作成するメソッドと、複製から
 *         状態をコピーするメソッドが必要になります。</td>
 *   </tr>
 *   <tr valign="top">
 *     <td bgcolor="lightgray">情報の公開範囲</td>
 *     <td bgcolor="pink">×</td>
 *     <td>状態を設定するメソッドを追加するので、
 *         クラスの状態が外部から操作できるようになります。</td>
 *     <td bgcolor="lightblue">△</td>
 *     <td>状態についての情報の公開範囲は、そのクラスと
 *         状態を格納するクラスだけに限定されます。
 *         プログラムのその他の部分は、状態に関与しません。</td>
 *     <td bgcolor="lightgreen">〇</td>
 *     <td>状態はそのクラスの中だけで取り扱い、
 *         外部には公開しません。</td>
 *   </tr>
 *   <tr valign="top">
 *     <td bgcolor="lightgray">オブジェクトの使われ方</td>
 *     <td bgcolor="lightgreen">〇</td>
 *     <td>そのクラス本来の役割のみを行います。
 *         そのため、使われ方は単純になります。</td>
 *     <td bgcolor="lightgreen">〇</td>
 *     <td>そのクラスのオブジェクトは本来の役割に使われ、
 *         新しく作成するクラスのオブジェクトは状態の保存に使われます。
 *         それぞれのクラスのオブジェクトの役割は
 *         単純ではっきりします。</td>
 *     <td bgcolor="pink">×</td>
 *     <td>そのクラス本来の役割で使われること加えて、
 *         状態を保存するためにも使われます。
 *         使用目的が増えるので、より複雑になります。</td>
 *   </tr>
 * </table>
 * </div>
 * <p>
 * TODO: 説明を追加・変更する。
 * <p>
 * 列挙子の状態を保存し、保存した状態に復元するには、以下のメソッドを使います。
 * <ul>
 *   <li>{@link PatternEnumerator#saveState} メソッドで、
 *       列挙子の現在の状態を保存します。</li>
 *   <li>{@link PatternEnumerator#restoreState} メソッドで、
 *       列挙子を保存された状態に戻します。</li>
 * </ul>
 */
class EnumeratorState {

	// メンバー変数: 列挙子の現在位置です。
	private final int _currentIndex;
	
	/**
	 * パラメータで指定の列挙子の状態を格納する {@link EnumeratorState} クラスの
	 * オブジェクトを作成します。
	 * 
	 * @param currentIndex 状態を格納する列挙子の現在位置の値です。
	 */
	EnumeratorState(int currentIndex) {
		_currentIndex = currentIndex;
	}
	
	/**
	 * 保存した列挙子の現在位置の値を取得します。
	 * 
	 * @return　列挙子の現在位置の値を返します。
	 */
	int getCurrentIndex() {
		return _currentIndex;
	}
	
	/**
	 * このオブジェクトと他のオブジェクトが等しいかどうかを比較します。
	 * {@link EnumeratorState} クラスのオブジェクトは、別のオブジェクトでも
	 * 保持する状態が同じならば等しいものとします。
	 * 
	 * @return　このオブジェクトが指定のオブジェクトと等しい場合は {@code true}、
	 * 		等しくない場合は {@code false} を返します。
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (!(obj instanceof EnumeratorState)) {
			return false;
		}
		
		EnumeratorState that = (EnumeratorState)obj;
		return this._currentIndex == that._currentIndex;
	}
	
	/**
	 * オブジェクトのハッシュコードの値を取得します。
	 * {@link Object#equals} メソッドが {@code true} を返すオブジェクトは、
	 * 同じハッシュコードを返すようにします。そのため保持する状態に基づいて
	 * ハッシュコードを作成します。
	 * 
	 * @return このオブジェクトのハッシュコードの値を返します。
	 */
	@Override
	public int hashCode() {
		return _currentIndex;
	}
	
	/**
	 * {@link EnumeratorState} クラスのオブジェクトの文字列表現を返します。
	 */
	@Override
	public String toString() {
		return String.format("_index=%d", _currentIndex);
	}
}
