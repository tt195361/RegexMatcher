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
	boolean oneMatch(ElementBuffer elemBuffer, StringBuffer strBuffer) {
		Stack<BufferState> strBufferCandidateStates =
				makeStrBufferCandidateStates(elemBuffer, strBuffer);
		
		elemBuffer.moveNext();
		return matchTheRestOfElements(elemBuffer, strBuffer, strBufferCandidateStates);
	}
	
	private Stack<BufferState> makeStrBufferCandidateStates(
			ElementBuffer elemBuffer, StringBuffer strBuffer) {
		Stack<BufferState> candidateStates = new Stack<BufferState>();
		
		pushCandidateState(candidateStates, strBuffer);
		while (strBuffer.hasCurrent()) {
			if (!_element.oneMatch(elemBuffer, strBuffer)) {
				break;
			}
			
			pushCandidateState(candidateStates, strBuffer);
		}
		
		return candidateStates;
	}
	
	private void pushCandidateState(
			Stack<BufferState> candidates, StringBuffer strBuffer) {
		BufferState state = strBuffer.saveState();
		candidates.push(state);
	}
	
	private boolean matchTheRestOfElements(
			ElementBuffer elemBuffer, StringBuffer strBuffer,
			Stack<BufferState> strBufferCandidateStates) {
		BufferState savedElemBufferState = elemBuffer.saveState();
		
		while (!strBufferCandidateStates.empty()) {
			elemBuffer.restoreState(savedElemBufferState);
			strBuffer.restoreState(strBufferCandidateStates.pop());
			
			if (RegexMatcher.doMatch(elemBuffer, strBuffer)) {
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
