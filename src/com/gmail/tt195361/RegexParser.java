package com.gmail.tt195361;

import java.util.*;

class RegexParser {
	
	private static final char Any = '.';
	private static final char Start = '^';
	private static final char End = '$';
	private static final char Closure = '*';
	private static final char CharClassStart = '[';
	private static final char Escape = '\\';

	static ElementBuffer parse(String pattern) {
		StringBuffer strBuffer = new StringBuffer(pattern, 0);
		List<RegexElement> elemList = new ArrayList<RegexElement>();
		RegexElement lastElem = null;

		while (strBuffer.hasCurrent()) {
			ElementParseResult elemParseResult = parseElement(strBuffer, lastElem);

			if (elemParseResult.removeLastElem()) {
				elemList.remove(lastElem);
			}
			
			RegexElement parsedElement = elemParseResult.getElement();
			elemList.add(parsedElement);
			lastElem = parsedElement;
			
			strBuffer.moveNext();
		}
		
		return new ElementBuffer(elemList);
	}
	
	private static ElementParseResult parseElement(
			StringBuffer strBuffer, RegexElement lastElem) {
		RegexElement element;
		boolean removeLastElem = false;
		
		char ch = strBuffer.getCurrent();
		if (ch == Any) {
			element = new AnyCharElement();
		}
		else if (ch == Start && strBuffer.isStart()) {
			element = new StartOfStringElement();
		}
		else if (ch == End && strBuffer.isLast()) {
			element = new EndOfStringElement();
		}
		else if (ch == Closure && lastElem != null) {
			element = new ClosureElement(lastElem);
			removeLastElem = true;
		}
		else if (ch == CharClassStart && !strBuffer.isLast()) {
			CharClassParser charClassParser = new CharClassParser();
			element =  charClassParser.parse(strBuffer);
		}
		else {
			char escCh = parseEscapeChar(ch, strBuffer);
			element = new OneCharElement(escCh);
		}
		
		return new ElementParseResult(element, removeLastElem);
	}

	static char parseEscapeChar(char ch, StringBuffer strBuffer) {
		if	(ch != Escape) {
			// Escape でなければ、その文字。
			return ch;
		}
		
		if (strBuffer.isLast()) {
			// Escape が文字列の最後ならば Escape。
			return Escape;
		}
		
		// Escape が文字列の最後でなければ、その次の文字。
		strBuffer.moveNext();
		return strBuffer.getCurrent();
	}
}
