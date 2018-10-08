package com.gmail.tt195361;

import java.util.Stack;

/**
 * 指定の正規表現要素が 0 回以上繰り返す閉包を表わす正規表現要素です。
 */
class ClosureElement extends RegexElement {
	
	// メンバー変数: 繰り返す正規表現要素を保持します。
	private final RegexElement _repeatElement;
	
	/**
	 * 指定の繰り返す要素を持つ {@link ClosureElement} クラスのオブジェクトを作成します。
	 * 
	 * @param repeatElement　繰り返す正規表現要素を指定します。
	 */
	ClosureElement(RegexElement repeatElement) {
		_repeatElement = repeatElement;
	}
	
	/**
	 * 繰り返す正規表現要素を取得します。
	 * 
	 * @return 繰り返す正規表現要素を返します。
	 */
	RegexElement getRepeatElement() {
		return _repeatElement;
	}

	/**
	 * この正規表現要素が文字列の現在位置から一致するかどうかを調べます。
	 * 閉包は、指定の要素が 0 回以上なるべく多く繰り返し一致し、その後ろの正規表現要素が残りの文字列に
	 * 一致することを調べます。
	 * 
	 * @param elemEnum 正規表現要素の列挙子です。一致に成功した場合、呼び出し後の現在位置は
	 * 				最後に一致した正規表現要素に移動します。
	 * @param strEnum 文字列の列挙子です。一致に成功した場合、呼び出し後の現在位置は
	 * 				一致した最後の正規表現要素に対応する部分の次の文字に移動します。
	 * @return 一致に成功した場合は {@code true} を、失敗した場合は {@code false} を返します。
	 */
	@Override
	boolean oneMatch(ElementEnumerator elemEnum, StringEnumerator strEnum) {
		// 可能な繰り返し回数の候補を作成し、それぞれの候補について、残りが一致するかどうか調べます。
		Stack<EnumeratorState> strEnumCandidateStateStack =
				makeStrEnumCandidateStates(elemEnum, strEnum);
		return matchTheRest(elemEnum, strEnum, strEnumCandidateStateStack);
	}
	
	// 可能な繰り返し回数の候補を作成します。
	private Stack<EnumeratorState> makeStrEnumCandidateStates(
			ElementEnumerator elemEnum, StringEnumerator strEnum) {
		// なるべく長い繰り返し回数と一致させるため、候補はスタックに格納し、
		// 後に入れたより長い繰り返しを先に調べるようにします。
		Stack<EnumeratorState> candidateStateStack = new Stack<EnumeratorState>();
		
		// 繰り返し回数は 0 回でもいいので、現在の状態をそのまま候補にします。
		candidateStateStack.push(strEnum.saveState());

		// 繰り返す要素が文字列とどこまで一致するか調べ、異なる状態ならば候補として追加します。
		while (strEnum.hasCurrentOrEnd()) {
			if (!_repeatElement.oneMatch(elemEnum, strEnum)) {
				break;
			}
			
			// 状態が変わらないのは空文字列と一致する場合で、'^*', '$*', '.**' などです。
			EnumeratorState savedState = strEnum.saveState();
			if (savedState.equals(candidateStateStack.peek())) {
				break;
			}
			
			candidateStateStack.push(savedState);
		}
		
		return candidateStateStack;
	}
			
	// それぞれの文字列列挙子の候補の位置に対して、残りの正規表現要素が一致するかどうか調べます。
	private boolean matchTheRest(
			ElementEnumerator elemEnum, StringEnumerator strEnum,
			Stack<EnumeratorState> strEnumCandidateStateStack) {
		// この閉包の次に移動し、その位置を保存します。それぞれの候補の探索をこの位置から始められるようにします。
		elemEnum.moveNext();
		EnumeratorState savedElemEnumState = elemEnum.saveState();
		
		while (!strEnumCandidateStateStack.empty()) {
			// 正規表現要素の列挙子の現在位置をこの閉包の次に、
			// 文字列の列挙子の現在位置を次の候補に設定し、一致するかどうかを調べます。
			elemEnum.restoreState(savedElemEnumState);
			strEnum.restoreState(strEnumCandidateStateStack.pop());
			if (RegexMatcher.matchFromCurrent(elemEnum, strEnum)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * {@link ClosureElement} クラスのオブジェクトの文字列表現を返します。
	 */
	@Override
	public String toString() {
		return "Closure: " + _repeatElement.toString();
	}
}
