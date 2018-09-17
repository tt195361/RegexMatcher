package com.gmail.tt195361;

import java.util.*;

class RegexParser {
	
	private static final char Any = '.';
	private static final char Start = '^';
	private static final char End = '$';
	private static final char Closure = '*';
	private static final char CharClassStart = '[';
	private static final char Escape = '\\';

	static ElementEnumerator parse(String pattern) {
		StringEnumerator strEnum = new StringEnumerator(pattern, 0);
		List<RegexElement> elemList = new ArrayList<RegexElement>();
		RegexElement lastElem = null;

		while (strEnum.hasCurrent()) {
			ElementParseResult elemParseResult = parseElement(strEnum, lastElem);

			if (elemParseResult.removeLastElem()) {
				elemList.remove(lastElem);
			}
			
			RegexElement parsedElement = elemParseResult.getElement();
			elemList.add(parsedElement);
			lastElem = parsedElement;
			
			strEnum.moveNext();
		}
		
		return new ElementEnumerator(elemList);
	}
	
	private static ElementParseResult parseElement(
			StringEnumerator strEnum, RegexElement lastElem) {
		RegexElement element;
		boolean removeLastElem = false;
		
		char ch = strEnum.getCurrent();
		if (ch == Any) {
			element = new AnyCharElement();
		}
		else if (ch == Start && strEnum.isStart()) {
			element = new StartOfStringElement();
		}
		else if (ch == End && strEnum.isLast()) {
			element = new EndOfStringElement();
		}
		else if (ch == Closure && lastElem != null) {
			element = new ClosureElement(lastElem);
			removeLastElem = true;
		}
		else if (ch == CharClassStart && !strEnum.isLast()) {
			CharClassParser charClassParser = new CharClassParser();
			element =  charClassParser.parse(strEnum);
		}
		else {
			char escCh = parseEscapeChar(ch, strEnum);
			element = new OneCharElement(escCh);
		}
		
		return new ElementParseResult(element, removeLastElem);
	}

	static char parseEscapeChar(char ch, StringEnumerator strEnum) {
		if	(ch != Escape) {
			// Escape でなければ、その文字。
			return ch;
		}
		
		if (strEnum.isLast()) {
			// Escape が文字列の最後ならば Escape。
			return Escape;
		}
		
		// Escape が文字列の最後でなければ、その次の文字。
		strEnum.moveNext();
		return strEnum.getCurrent();
	}
}
