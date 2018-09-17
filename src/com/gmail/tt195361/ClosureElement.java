package com.gmail.tt195361;

import java.util.Stack;

class ClosureElement extends RegexElement {
	
	private final RegexElement _element;
	
	ClosureElement(RegexElement element) {
		_element = element;
	}
	
	RegexElement getRegexElement() {
		return _element;
	}

	@Override
	boolean oneMatch(ElementEnumerator elemEnum, StringEnumerator strEnum) {
		Stack<EnumeratorState> strEnumCandidateStateStack =
				makeStrEnumCandidateStates(elemEnum, strEnum);
		
		elemEnum.moveNext();
		return matchTheRestOfElements(elemEnum, strEnum, strEnumCandidateStateStack);
	}
	
	private Stack<EnumeratorState> makeStrEnumCandidateStates(
			ElementEnumerator elemEnum, StringEnumerator strEnum) {
		Stack<EnumeratorState> candidateStateStack = new Stack<EnumeratorState>();
		
		candidateStateStack.push(strEnum.saveState());
		while (strEnum.hasCurrent()) {
			if (!_element.oneMatch(elemEnum, strEnum)) {
				break;
			}
			
			candidateStateStack.push(strEnum.saveState());
		}
		
		return candidateStateStack;
	}
		
	private boolean matchTheRestOfElements(
			ElementEnumerator elemEnum, StringEnumerator strEnum,
			Stack<EnumeratorState> strEnumCandidateStateStack) {
		EnumeratorState savedElemEnumState = elemEnum.saveState();
		
		while (!strEnumCandidateStateStack.empty()) {
			elemEnum.restoreState(savedElemEnumState);
			strEnum.restoreState(strEnumCandidateStateStack.pop());
			
			if (RegexMatcher.doMatch(elemEnum, strEnum)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public String toString() {
		return "Closure: " + _element.toString();
	}
}
