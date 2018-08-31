
package com.gmail.tt195361;

import java.util.*;

public class ClosureElement extends RegexElement {
	
	private final RegexElement _element;
	
	public ClosureElement(RegexElement element) {
		_element = element;
	}

	@Override
	public boolean oneMatch(ElementBuffer elemBuffer, StringBuffer strBuffer) {
		Stack<BufferState> candidates = makeCandidates(elemBuffer, strBuffer);
		
		elemBuffer.moveNext();
		BufferState savedElemBufferState = elemBuffer.saveState();
		while (!candidates.empty()) {
			elemBuffer.restoreState(savedElemBufferState);
			strBuffer.restoreState(candidates.pop());
			if (RegexMatcher.match(elemBuffer, strBuffer)) {
				return true;
			}
		}
		
		return false;
	}
	
	private Stack<BufferState> makeCandidates(
			ElementBuffer elemBuffer, StringBuffer strBuffer) {
		Stack<BufferState> candidates = new Stack<BufferState>();
		
		pushCandidate(candidates, strBuffer);
		while (strBuffer.hasCurrent()) {
			if (!_element.oneMatch(elemBuffer, strBuffer)) {
				break;
			}
			
			pushCandidate(candidates, strBuffer);
		}
		
		return candidates;
	}
	
	private void pushCandidate(Stack<BufferState> candidates, StringBuffer strBuffer) {
		BufferState state = strBuffer.saveState();
		candidates.push(state);
	}

	@Override
	public String toString() {
		return "Closure: " + _element.toString();
	}
}
