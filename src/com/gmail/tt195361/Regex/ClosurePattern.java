package com.gmail.tt195361.Regex;

import java.util.Stack;

/**
 * 閉包を表わす正規表現文型です。閉包は指定の正規表現文型の 0 回以上の繰り返しを取り扱います。
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
	 * 閉包は、指定の文型が 0 回以上なるべく多く繰り返し一致し、その後ろの正規表現文型が残りの文字列に
	 * 一致することを調べます。
	 * 
	 * @param patEnum 正規表現文型の列挙子です。一致に成功した場合、呼び出し後の現在位置は
	 * 				最後に一致した正規表現文型に移動します。
	 * @param strEnum 文字列の列挙子です。一致に成功した場合、呼び出し後の現在位置は
	 * 				一致した最後の正規表現文型に対応する部分の次の文字に移動します。
	 * @return 一致に成功した場合は {@code true} を、失敗した場合は {@code false} を返します。
	 */
	@Override
	boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum) {
		// 可能な繰り返し回数の候補を作成し、それぞれの候補について、残りが一致するかどうか調べます。
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

		// 繰り返す文型が文字列とどこまで一致するか調べ、異なる状態ならば候補として追加します。
		while (strEnum.hasCurrentOrEnd()) {
			if (!_repeatPattern.oneMatch(patEnum, strEnum)) {
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
			
	// それぞれの文字列列挙子の候補の位置に対して、残りの正規表現文型が一致するかどうか調べます。
	private boolean matchTheRest(
			PatternEnumerator patEnum, StringEnumerator strEnum,
			Stack<EnumeratorState> strEnumCandidateStateStack) {
		// この閉包の次に移動し、その位置を保存します。それぞれの候補の探索をこの位置から始められるようにします。
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
